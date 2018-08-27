package com.disruptor.generate2;

import com.disruptor.generate1.Trade;
import com.lmax.disruptor.EventHandler;

/**
 * @Author:guang yong
 * Description:
 * @Date:Created in 15:58 2018/8/27
 * @Modified By:
 */
public class Handler3 implements EventHandler<Trade>{
    @Override
    public void onEvent(Trade trade, long l, boolean b) throws Exception {

        System.out.println("Handler3:name:"+trade.getName()+","
        +"price:"+trade.getPrice()+";instance:"+trade.hashCode());
    }
}
