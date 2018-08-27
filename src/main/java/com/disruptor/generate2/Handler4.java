package com.disruptor.generate2;

import com.disruptor.generate1.Trade;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * @Author:guang yong
 * Description:
 * @Date:Created in 16:52 2018/8/27
 * @Modified By:
 */
public class Handler4 implements EventHandler<Trade>,WorkHandler<Trade>{

    @Override
    public void onEvent(Trade trade, long l, boolean b) throws Exception {
        this.onEvent(trade);
    }

    @Override
    public void onEvent(Trade trade) throws Exception {
        System.out.println("handler4:get name:"+trade.getName());
    }
}
