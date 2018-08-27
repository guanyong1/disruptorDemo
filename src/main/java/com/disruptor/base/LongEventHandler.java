package com.disruptor.base;

import com.lmax.disruptor.EventHandler;

/**
 * @Author:guang yong
 * Description:事件消费者，事件处理器
 * @Date:Created in 10:40 2018/8/27
 * @Modified By:
 */
public class LongEventHandler implements EventHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent longEvent, long l, boolean b) throws Exception {

        System.out.println(longEvent.getValue());
    }
}
