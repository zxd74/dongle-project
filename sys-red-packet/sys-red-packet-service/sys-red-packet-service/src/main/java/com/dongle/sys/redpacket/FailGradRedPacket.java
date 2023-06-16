package com.dongle.sys.redpacket;

/**
 * 公平红包，固定红包大小
 */
public class FailGradRedPacket extends FixRedPacket{

    private final int redPacketSize;
    private final int lastRedPacketSize;

    public FailGradRedPacket(int redPacket, int redPacketNumber) {
        super(redPacket,redPacketNumber);
        this.redPacketSize = redPacket / redPacketNumber;
        this.lastRedPacketSize = this.redPacketSize + redPacket % redPacketNumber;
        System.out.println("。。。公平红包逻辑。。。");
    }

    @Override
    int gradCurRedPacket() {
        int currentRedPacket = redPacketSize;
        if (redPacketNumber == 1){
            currentRedPacket = lastRedPacketSize;
        }
        return currentRedPacket;
    }
}
