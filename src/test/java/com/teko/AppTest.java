package com.teko;

import com.teko.ezcopy.core.CollectionRefReplicator;
import com.teko.ezcopy.core.DeepCopier;
import com.teko.ezcopy.core.MapRefReplicator;
import org.junit.Test;

import java.io.*;
import java.util.*;


/**
 * Unit test for simple App.
 */
public class AppTest{
    @Test
    public void MultiThread() throws IOException, InterruptedException {
        Thread.sleep(10000);
        int[] arr = new int[1000000000];
        Thread[] tarr = new Thread[100];
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            tarr[i] = new Thread(new Doer(i, arr));
            tarr[i].start();
        }
        for (int i = 0; i < 100; i++) {
            tarr[i].join();
        }
        //tarr[99].join();

        System.out.println(System.currentTimeMillis() - start);
//        for (int j : arr) {
//            System.out.print(j + " ");
//        }
    }

    @Test
    public void SingleThread() {
        int[] arr = new int[1000000000];
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000000; i++) {
            arr[i] = i;
        }
        System.out.println(System.currentTimeMillis() - start);
//        for (int j : arr) {
//            System.out.print(j + " ");
//        }
    }

    class Doer implements Runnable {
        private int i;

        private int[] arr;

        public Doer(int i, int[] arr) {
            this.i = i;
            this.arr = arr;
        }

        @Override
        public void run() {
            for (int j = 0; j < 10000000; j++) {
                this.arr[this.i * 10000000 + j] = this.i * 10000000 + j;
            }
        }
    }

    @Test
    public void MapRefReplicatorCopyTest() {
        HashMap<LinkedList<HashMap<Integer, Integer>>, HashMap<Integer, LinkedList<Integer>>> map = new HashMap<>();
        HashMap<Integer, Integer> map1 = new HashMap<>();
        map1.put(1, 2);
        map1.put(2, 3);
        LinkedList<HashMap<Integer, Integer>> l1 = new LinkedList<>();
        l1.add(map1);
        LinkedList<Integer> l2 = new LinkedList<>();
        l2.add(10);
        l2.add(11);
        HashMap<Integer, LinkedList<Integer>> map2 = new HashMap<>();
        map2.put(0, l2);
        map.put(l1, map2);
        MapRefReplicator copier = new MapRefReplicator(map);
        HashMap<LinkedList<HashMap<Integer, Integer>>, HashMap<Integer, LinkedList<Integer>>> copy =
                (HashMap<LinkedList<HashMap<Integer, Integer>>, HashMap<Integer, LinkedList<Integer>>>) copier.copyHierarchy();
        System.out.println(copy == map);
        System.out.println(copy);
        System.out.println(map);
    }
}
