package com.teko.ezcopy.core;

import java.io.*;

public class ObjectWriter implements Runnable {
    private Serializable obj;
    private PipedOutputStream pos;
    private ObjectReader or;

    public ObjectWriter(Serializable obj, ObjectReader or) {
        this.or = or;
        this.obj = obj;
    }

    private void connect() {
        this.pos = new PipedOutputStream();
        this.or.reset();
        try {
            this.pos.connect(this.or.getPis());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        try {
            this.connect();
            ObjectOutputStream oos = new ObjectOutputStream(this.pos);
            oos.writeObject(this.obj);
            oos.close();
            this.pos.close();
        } catch (Exception e) {
            throw new RuntimeException("exception occurred when writing object");
        }
    }
}
