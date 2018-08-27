package com.disruptor.base;

import com.lmax.disruptor.EventFactory;

/**
 * @Author:guang yong
 * Description:工厂Event类,用于创建Event类实例对象
 * @Date:Created in 10:19 2018/8/27
 * @Modified By:
 */
public class LongEventFactory implements EventFactory{
    @Override
    public Object newInstance() {
        return new LongEvent();
    }
}
