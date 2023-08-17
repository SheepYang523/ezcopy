package com.teko.ezcopy.core;

import java.io.*;

class ObjectReader implements Runnable {
    private PipedInputStream pis;
    private Serializable obj;

    public ObjectReader() { }

    @Override
    public void run() {
        this.obj = null;    // clear old copy
        try {
            ObjectInputStream ois = new ObjectInputStream(this.pis);
            this.obj = (Serializable) ois.readObject();
            ois.close();
            this.pis.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("exception occurred when reading object");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public PipedInputStream getPis() {
        return this.pis;
    }

    public Serializable getCopy() {
        if (this.obj == null)
            throw new RuntimeException("copy haven't been executed successfully");
        return this.obj;
    }

    public void reset() {
        this.pis = new PipedInputStream();
    }
}
