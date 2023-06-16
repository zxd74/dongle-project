package com.dongle.sys.redpacket;

import java.util.concurrent.CountDownLatch;

public class RedPacketThread implements Runnable{

    private final CountDownLatch countDownLatch;
    private final AbstractGradRedPacket redPacket;

    public RedPacketThread(CountDownLatch countDownLatch, AbstractGradRedPacket redPacket) {
        this.countDownLatch = countDownLatch;
        this.redPacket = redPacket;
    }

    @Override
    public void run() {
        try {
            countDownLatch.await(); // 争取同时开始竞价
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        redPacket.grad();
    }
}
