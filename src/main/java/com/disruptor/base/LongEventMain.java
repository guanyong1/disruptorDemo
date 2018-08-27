package com.disruptor.base;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SleepingWaitStrategy;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author:guang yong
 * Description:
 * @Date:Created in 10:22 2018/8/27
 * @Modified By:
 */
public class LongEventMain {

    public static void main(String[] args) {
        //创建缓冲池
        ExecutorService executor = Executors.newCachedThreadPool();
        //创建工厂
        LongEventFactory factory = new LongEventFactory();
        //创建bufferSize，也就是Ringbuffer大小，必须是2的n次方
        int ringBufferSize = 1024;

        //创建disruptor
        //factory:工厂对象，创建LongEvent对象
        //ringBufferSize：缓冲区大小
        //executor线程池，进行disruptor内部的数据接收处理调度
        //ProducerType两种类型：.SINGLE 单个生成者
        //                    .MULTI 多个生产者
        //new YieldingWaitStrategy():一种策略WaitStrategy
        /**
         *  new BlockingWaitStrategy() 是最低效的策略，但其对CPU的消耗最小并且在各种不同部署环境中能提供更加一致的性能表现
         *  new SleepingWaitStrategy() 性能和BlockingWaitStrategy的性能差不多，对CPU的消耗也类似，但其对生产者线程的影响最小，适用于异步日志类处理
         *  new YieldingWaitStrategy() 性能是最好的，适用于低延迟的系统，在要求极高性能且事件处理线数小于CPU逻辑核心数的场景中，推荐使用此策略
         */
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(factory,ringBufferSize,executor, ProducerType.SINGLE,new SleepingWaitStrategy());

        //连接消费事件方法
        disruptor.handleEventsWith(new LongEventHandler());

        //启动
        disruptor.start();

        //disruptor的事件发布过程是一个两阶段提交的过程
        //发布事件，获取具体存放数据的容器ringBuffer（环形结构）
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        //LongEventProducer producer = new LongEventProducer(ringBuffer);
        LongEventProducerWithTranslator producer = new LongEventProducerWithTranslator(ringBuffer);

        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        for (int i = 0; i < 100; i++) {
            byteBuffer.putLong(0,i);
            producer.onData(byteBuffer);
        }

        disruptor.shutdown();//关闭disruptor，方法会阻塞，直至所有的事件都得到处理
        executor.shutdown();//关闭disruptor使用的线程池：如果需要的话，必须手动关闭，disruptor在shutdown时不会自动关闭
    }
}
