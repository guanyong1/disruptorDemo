package com.disruptor.generate2;

import com.disruptor.generate1.Trade;
import com.lmax.disruptor.EventHandler;

/**
 * @Author:guang yong
 * Description:
 * @Date:Created in 16:53 2018/8/27
 * @Modified By:
 */
public class Handler5 implements EventHandler<Trade>{
    @Override
    public void onEvent(Trade trade, long l, boolean b) throws Exception {
        System.out.println("handler5:"+trade.getPrice());
        trade.setPrice(trade.getPrice()+3.0);
    }
}
