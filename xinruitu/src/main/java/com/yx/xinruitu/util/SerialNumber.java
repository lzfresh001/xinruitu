package com.yx.xinruitu.util;
abstract class SerialNumber {

    public synchronized String getSerialNumber() {
        return process();
    }
    protected abstract String process();
}

