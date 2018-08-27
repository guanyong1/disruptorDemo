package com.disruptor.generate2;

import com.disruptor.generate1.Trade;
import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @Author:guang yong
 * Description:
 * @Date:Created in 16:02 2018/8/27
 * @Modified By:
 */
public class TradePublisher implements Runnable{

    Disruptor<Trade> disruptor;
    private CountDownLatch latch;

    //模拟百万次交易的发生
    private static int LOOP = 1;

    public TradePublisher(CountDownLatch latch,Disruptor<Trade> disruptor){
        this.disruptor = disruptor;
        this.latch = latch;
    }
    @Override
    public void run() {
        TradeEventTranslator translator = new TradeEventTranslator();
        for (int i = 0; i < LOOP; i++) {
            //发布事件
            disruptor.publishEvent(translator);
        }
        latch.countDown();
    }
}

class TradeEventTranslator implements EventTranslator<Trade>{

    private Random random = new Random();

    private Trade generateTrade(Trade trade){
        trade.setPrice(random.nextDouble()*9999);
        return trade;
    };
    @Override
    public void translateTo(Trade trade, long l) {
        this.generateTrade(trade);
    }
}
