package com.disruptor.generate1;

import com.lmax.disruptor.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author:guang yong
 * Description:使用WorkProcessor
 * @Date:Created in 15:25 2018/8/27
 * @Modified By:
 */
public class Main2 {

    public static void main(String[] args) throws InterruptedException {

        int BUFFER_SIZE = 1024;
        int THREAD_NUMBERS = 4;

        EventFactory<Trade> eventFactory = new EventFactory<Trade>() {
            @Override
            public Trade newInstance() {
                return new Trade();
            }
        };

        RingBuffer<Trade> ringBuffer = RingBuffer.createSingleProducer(eventFactory,BUFFER_SIZE);

        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUMBERS);

        WorkHandler handler = new TradeHandler();
        WorkerPool<Trade> workerPool = new WorkerPool<Trade>(
                ringBuffer,sequenceBarrier,new IgnoreExceptionHandler(),handler
        );

        workerPool.start(executor);

        //模拟生产者
        for (int i = 0; i < 10; i++) {
            long seq = ringBuffer.next();
            ringBuffer.get(seq).setPrice(Math.random()*9999);
            ringBuffer.publish(seq);
        }

        //等待生产者结束
        Thread.sleep(1000);
        workerPool.halt();
        executor.shutdown();
    }
}
