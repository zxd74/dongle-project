package com.dongle.sys.redpacket;

import java.util.concurrent.CountDownLatch;

//均分
public class GradThread implements Runnable{
    private int redPacketNumber;
    private final int redPacketSize;
    private final int lastRedPacketSize;

    private final CountDownLatch countDownLatch;

    private final Object lock = new Object();
    public GradThread(int redPacket, int redPacketNumber,CountDownLatch countDownLatch) {
        this.redPacketNumber = redPacketNumber;
        this.countDownLatch = countDownLatch;
        this.redPacketSize = redPacket / redPacketNumber;
        this.lastRedPacketSize = this.redPacketSize + redPacket % redPacketNumber;
    }

    @Override
    public void run() {
        try {
            countDownLatch.await(); // 争取同时开始竞价
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        synchronized (lock){ // 加锁避免超出
            if (redPacketNumber>0){
                redPacketNumber--;
                int currentRedPacket = redPacketSize;
                if (redPacketNumber == 1){
                    currentRedPacket = lastRedPacketSize;
                }
                System.out.println(Thread.currentThread().getName() + " 抢到红包" + currentRedPacket + "分，奖励一下！");
            }else {
                System.out.println(Thread.currentThread().getName() + " 未抢到，感觉错过了一个亿！");
            }
        }

    }
}
