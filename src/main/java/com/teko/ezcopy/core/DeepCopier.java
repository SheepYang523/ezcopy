package com.teko.ezcopy.core;

import com.teko.ezcopy.framework.AbstractDeepCopier;

import java.io.Serializable;

public class DeepCopier extends AbstractDeepCopier<Serializable> {
    private final ObjectWriter ow;
    private final ObjectReader or;

    public DeepCopier(Serializable obj) {
        this.template = obj;
        this.or = new ObjectReader();
        this.ow = new ObjectWriter(this.template, this.or);
    }

    private void copy() {
        Thread writerThread = new Thread(this.ow);
        Thread readerThread = new Thread(this.or);
        writerThread.start();
        readerThread.start();
        try {
            readerThread.join();
            writerThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Serializable getone() {
        this.copy();
        return this.or.getCopy();
    }

    public<T> T getone(Class<T> clazz) {
        this.copy();
        return clazz.cast(or.getCopy());
    }
}
