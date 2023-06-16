package com.dongle.sys.redpacket;

/**
 * 固定红包总额和数量的抢红包抽象逻辑
 */
public abstract class FixRedPacket extends AbstractGradRedPacket{

    protected int redPacketNumber;

    public FixRedPacket(int redPacket,int redPacketNumber) {
        super(redPacket);
        this.redPacketNumber = redPacketNumber;
    }

    @Override
    synchronized void grad() {
        if (redPacketNumber == 0){
            System.out.println(Thread.currentThread().getName() + " 未抢到，感觉错过了一个亿！");
            return;
        }
        int currentRedPacket = gradCurRedPacket();
//        assert currentRedPacket > 0:"红包大小不应该为0！";
        if (currentRedPacket == 0){
            throw new RuntimeException("红包大小不应该为0！");
        }
        redPacketNumber--;
        System.out.println(Thread.currentThread().getName() + " 抢到红包" + currentRedPacket + "分，奖励一下！");
        grad(currentRedPacket);
    }

    abstract int gradCurRedPacket();
}
