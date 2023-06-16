package com.dongle.sys.redpacket;

import java.util.Random;

/**
 * 随机红包大小
 */
public class RandomGradRedPacket extends FixRedPacket{

    private final Random random = new Random();
    private final int minRedPacket = 1;

    public RandomGradRedPacket(int redPacket,int redPacketNumber) {
        super(redPacket,redPacketNumber);
        System.out.println("。。。随机红包逻辑。。。");
    }

    @Override
    synchronized int gradCurRedPacket() {
        int residue = this.redPacket - this.gradRedPacket;
        if (redPacketNumber == 1) return residue;
        int currentRedPacket = 0;
        while (currentRedPacket == 0){
            currentRedPacket = random.nextInt(residue - (redPacketNumber - 1) * minRedPacket);
        }
        return currentRedPacket;
    }

    private int gradRedPacket(int residue){
        int currentRedPacket = 0;
        while (currentRedPacket == 0){
            currentRedPacket = random.nextInt(residue - (redPacketNumber - 1) * minRedPacket);
        }
        return currentRedPacket;
    }
}
