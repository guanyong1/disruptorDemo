package com.disruptor.generate1;

import com.lmax.disruptor.*;

import java.util.concurrent.*;

/**
 * @Author:guang yong
 * Description:使用EventProcessor
 * @Date:Created in 14:37 2018/8/27
 * @Modified By:
 */
public class Main1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int BUFFER_SIZE = 1024;
        int THREAD_NUMBERS = 4;

        /**
         * createSingleProducer创建一个单生产者的RingBuffer
         * EventFactory event工厂，产生数据填充RingBuffer的区块
         * new YieldingWaitStrategy() RingBuffer的生产都在没有可用区块的时候（可能是消费者（或者说是事件处理器）太慢了）的等待策略
         */
        final RingBuffer<Trade> ringBuffer = RingBuffer.createSingleProducer(new EventFactory<Trade>() {
            @Override
            public Trade newInstance() {
                return new Trade();
            }
        },BUFFER_SIZE,new YieldingWaitStrategy());

        //创建线程池
        ExecutorService executors = Executors.newFixedThreadPool(THREAD_NUMBERS);

        //创建SequenceBarrier,类似设置一个屏障，解决生产，消费者一个快一个慢的情况
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();

        //创建消息处理器
        BatchEventProcessor<Trade> tradeProcessor = new BatchEventProcessor<Trade>(
                ringBuffer,sequenceBarrier,new TradeHandler());

        //把消费者的位置信息引用注入到生产者，如果只有一个消费者的情况下可以省略
        ringBuffer.addGatingSequences(tradeProcessor.getSequence());

        //把消息处理器提交到线程池
        executors.submit(tradeProcessor);
        //如果存在多个消费者，那就重复上面三行代码，把TradeHandler换成其他消费者

        //模拟生产者
        Future<?> future = executors.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                long seq;
                for (int i = 0; i < 10; i++) {
                    //占个坑，ringBuffer一个可用区块
                    seq = ringBuffer.next();
                    //给这个区块放入数据
                    ringBuffer.get(seq).setPrice(Math.random()*9999);
                    //发布这个区块的数据使handler（consumer）可见
                    ringBuffer.publish(seq);
                }
                return null;
            }
        });

        //等待生产者结束
        future.get();
        //等1秒，等消费都处理完成
        Thread.sleep(1000);
        //通知事件（或者说消息）处理器可以结束了（并不是马上结束）
        tradeProcessor.halt();
        //终止线程
        executors.shutdown();
    }
}
