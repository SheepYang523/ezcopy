package com.teko.ezcopy.core;

import com.teko.ezcopy.framework.AbstractRefReplicator;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

public class CollectionRefReplicator extends AbstractRefReplicator<Collection> {
    public CollectionRefReplicator(Collection c) {
        this.template = c;
    }

    @Override
    public Collection getone() {
        Collection copy = null;
        try {
            copy = this.template.getClass().getDeclaredConstructor().newInstance();
        } catch (InstantiationException
                 | IllegalAccessException
                 | InvocationTargetException
                 | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        for (Object o : this.template) {
            copy.add(o);
        }
        return copy;
    }

    public Collection copyHierarchy() {
        return CollectionRefReplicator.DFCopy(this.template);
    }

    protected static Collection DFCopy(Collection target) {
        Collection copy = null;
        try {
            copy = target.getClass().getDeclaredConstructor().newInstance();
        } catch (InstantiationException
                 | IllegalAccessException
                 | InvocationTargetException
                 | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        for (Object o : target) {
            if (o instanceof Collection) {
                copy.add(CollectionRefReplicator.DFCopy((Collection) o));
            }
            else if (o instanceof Map) {
                copy.add(MapRefReplicator.DFCopy((Map) o));
            }
            else {
                copy.add(o);
            }
        }
        return copy;
    }

}
