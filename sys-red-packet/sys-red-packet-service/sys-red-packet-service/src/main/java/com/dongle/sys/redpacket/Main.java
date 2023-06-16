package com.dongle.sys.redpacket;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class Main {

    public static void main(String[] args) {
        gradRedPacket();
    }

    private static void gradRedPacket(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入红包总金额(分)：");
        int redPacket = scanner.nextInt();
        System.out.println("请输入红包总数：");
        int redPacketNumber = scanner.nextInt();
        System.out.println("请输入抢红包人数");
        int people = scanner.nextInt();
        CountDownLatch countDownLatch = new CountDownLatch(1);
//        GradThread gradThread = new GradThread(redPacket,redPacketNumber,countDownLatch);
        AbstractGradRedPacket gradRedPacket;
//        gradRedPacket = new FailGradRedPacket(redPacket,redPacketNumber);
        gradRedPacket = new RandomGradRedPacket(redPacket,redPacketNumber);
        RedPacketThread redPacketThread = new RedPacketThread(countDownLatch,gradRedPacket);
        for (int i = 0; i < people; i++) {
            new Thread(redPacketThread).start();
        }
        countDownLatch.countDown();
        try {
            Thread.sleep(1000);
            System.out.println("已抢红包人数：" + gradRedPacket.gradRedPacketNumber);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
