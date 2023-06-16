package com.dongle.sys.redpacket;

public class GradCommon {
    private int redPacket;
    private int redPacketNumber;
    private final int redPacketSize;
    private final int lastRedPacketSize;

    public GradCommon(int redPacket,int redPacketNumber) {
        this.redPacketNumber = redPacketNumber;
        this.redPacketSize = redPacket/redPacketNumber;
        this.lastRedPacketSize = redPacketSize + redPacket % redPacketNumber;
    }

    public int grad(){
        int currentRedPacket = 0;
        if (redPacketNumber>0){
            redPacketNumber--;
            currentRedPacket = redPacketSize;
            if (redPacketNumber == 1){
                currentRedPacket = lastRedPacketSize;
            }
            System.out.println(Thread.currentThread().getName() + " 抢到红包" + currentRedPacket + "分，奖励一下！");
        }else {
            System.out.println(Thread.currentThread().getName() + " 未抢到，感觉错过了一个亿！");
        }
        return currentRedPacket;
    }
}
