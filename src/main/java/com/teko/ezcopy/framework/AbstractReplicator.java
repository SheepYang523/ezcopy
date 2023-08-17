package com.teko.ezcopy.framework;

public abstract class AbstractReplicator<T> implements Replicator<T>{
    protected T template;

    public void setTemplate(T template) {
        this.template = template;
    }
}
