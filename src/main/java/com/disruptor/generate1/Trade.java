package com.disruptor.generate1;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author:guang yong
 * Description:
 * @Date:Created in 14:32 2018/8/27
 * @Modified By:
 */
public class Trade {

    private String id;
    private String name;
    private double price;//金额
    private AtomicInteger count = new AtomicInteger(0);

    public Trade() {
    }

    public Trade(String id, String name, double price, AtomicInteger count) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public AtomicInteger getCount() {
        return count;
    }

    public void setCount(AtomicInteger count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", count=" + count +
                '}';
    }
}
