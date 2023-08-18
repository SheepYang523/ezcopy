package com.teko.ezcopy.core;

import com.teko.ezcopy.framework.AbstractRefReplicator;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

public class MapRefReplicator extends AbstractRefReplicator<Map> {
    public MapRefReplicator(Map map) {
        this.template = map;
    }
    @Override
    public Map getone() {
        Map copy = null;
        try {
            copy = this.template.getClass().getDeclaredConstructor().newInstance();
        } catch (InstantiationException
                 | IllegalAccessException
                 | InvocationTargetException
                 | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        for (Object key : this.template.keySet()) {
            copy.put(key, this.template.get(key));
        }
        return copy;
    }

    public Map copyHierarchy() {
        return MapRefReplicator.DFCopy(this.template);
    }

    protected static Map DFCopy(Map target) {
        Map copy = null;
        try {
            copy = target.getClass().getDeclaredConstructor().newInstance();
        } catch (InstantiationException
                 | IllegalAccessException
                 | InvocationTargetException
                 | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        for (Object key : target.keySet()) {
            if (key instanceof Map) {
                copy.put(MapRefReplicator.DFCopy((Map) key), null);
            }
            else if (key instanceof Collection) {
                copy.put(CollectionRefReplicator.DFCopy((Collection) key), null);
            }
            else {
                copy.put(key, null);
            }

            if (target.get(key) instanceof Map) {
                copy.replace(key, MapRefReplicator.DFCopy((Map) target.get(key)));
            }
            else if (target.get(key) instanceof Collection) {
                copy.replace(key, CollectionRefReplicator.DFCopy((Collection) target.get(key)));
            }
            else {
                copy.put(key, target.get(key));
            }
        }
        return copy;
    }
}
