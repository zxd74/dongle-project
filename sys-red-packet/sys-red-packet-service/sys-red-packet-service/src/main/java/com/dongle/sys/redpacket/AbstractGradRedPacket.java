package com.dongle.sys.redpacket;

public abstract class AbstractGradRedPacket {
    protected int redPacket;
    protected int gradRedPacketNumber;
    protected int gradRedPacket;

    public AbstractGradRedPacket(int redPacket) {
        this.redPacket = redPacket;
    }

    abstract void grad();
    public synchronized void grad(int gradRedPacket){
        gradRedPacketNumber++;
        this.gradRedPacket += gradRedPacket;
        System.out.println("已抢红包"+gradRedPacketNumber + "个，已抢红包" + gradRedPacket + "分，剩余红包" + (redPacket - this.gradRedPacket) + "分。");
    }
}
