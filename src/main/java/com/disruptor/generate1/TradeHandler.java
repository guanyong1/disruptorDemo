package com.disruptor.generate1;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

import java.util.UUID;

/**
 * @Author:guang yong
 * Description:EventHandler<Trade>,WorkHandler<Trade>两者实现一个即可
 * 使用EventHandler<Trade>，则使用EventProcessor消息处理器
 * 使用WorkHandler<Trade>，则使用WorkerPool消息处理器
 * @Date:Created in 14:54 2018/8/27
 * @Modified By:
 */
public class TradeHandler implements EventHandler<Trade>,WorkHandler<Trade> {

    @Override
    public void onEvent(Trade trade, long l, boolean b) throws Exception {
        this.onEvent(trade);
    }

    @Override
    public void onEvent(Trade trade) throws Exception {
        //这里做具体的消费逻辑
        System.out.println(trade.getPrice());
    }
}
