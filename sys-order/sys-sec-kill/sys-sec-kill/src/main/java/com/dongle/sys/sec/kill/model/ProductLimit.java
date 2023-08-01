package com.dongle.sys.sec.kill.model;

import java.util.Date;

public class ProductLimit {
    private int singleNumber;
    private Date start;
    private Date end;

    public int getSingleNumber() {
        return singleNumber;
    }

    public void setSingleNumber(int singleNumber) {
        this.singleNumber = singleNumber;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
}
