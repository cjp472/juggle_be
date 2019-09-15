package com.juggle.juggle.primary.shop.dto;

import java.io.Serializable;

public class ShopOrderCensus implements Serializable {
    private int open;

    private int paid;

    private int shipped;

    private int received;

    private int complete;

    private int cancel;

    public int getOpen() {
        return open;
    }

    public void setOpen(int open) {
        this.open = open;
    }

    public int getPaid() {
        return paid;
    }

    public void setPaid(int paid) {
        this.paid = paid;
    }

    public int getShipped() {
        return shipped;
    }

    public void setShipped(int shipped) {
        this.shipped = shipped;
    }

    public int getReceived() {
        return received;
    }

    public void setReceived(int received) {
        this.received = received;
    }

    public int getComplete() {
        return complete;
    }

    public void setComplete(int complete) {
        this.complete = complete;
    }

    public int getCancel() {
        return cancel;
    }

    public void setCancel(int cancel) {
        this.cancel = cancel;
    }
}
