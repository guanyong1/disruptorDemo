package com.disruptor.base;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * @Author:guang yong
 * Description:生产者
 * @Date:Created in 11:06 2018/8/27
 * @Modified By:
 */
public class LongEventProducer {

    private final RingBuffer<LongEvent> ringBuffer;
    public LongEventProducer(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    /**
     * onData用来发布事件，每调用一次就发布一次事件事件
     * 它的参数会通过事件传递给消费者
     *
     * @param bb
     */public void onData(ByteBuffer bb) {
        //可以把ringBuffer看做一个事件队列，那么next就是得到下面一个事件槽
        long sequence = ringBuffer.next();try {
            //用上面的索引取出一个空的事件用于填充（获取该序号对应的事件对象）
            LongEvent event = ringBuffer.get(sequence);// for the sequence
            //获取要通过事件传递的业务数据
            event.setValue(bb.getLong(0));
        } finally {
            //发布事件
            //注：最后的ringBuffer.publish方法必须含在finally中以确保必须得到调用
            ringBuffer.publish(sequence);
        }
    }
}
