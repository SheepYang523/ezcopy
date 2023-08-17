package com.teko;

import com.teko.ezcopy.core.DeepCopier;
import org.junit.Test;

import java.io.*;
import java.util.LinkedList;


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
    public void CopyTest() {
        Integer i = 1000;
        DeepCopier dc = new DeepCopier(i);
        Integer i1 = dc.getone(i.getClass());
        Integer i2 = dc.getone(Integer.class);
        System.out.println(i1 == i2);
    }
}
