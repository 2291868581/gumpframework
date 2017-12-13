package com.dai.test.thread;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestThread {
    public static void main(String[] args){
        Long startime = System.currentTimeMillis();

        TestThread testThread = new TestThread();
         TestThreadImpl impl = new TestThreadImpl();
        testThread.testSynchronized(impl);
//        impl.run();
        System.out.println("消耗时间"+(System.currentTimeMillis()-startime));
    }

    public void testSynchronized(TestThreadImpl testThread){

//        Thread thread1 = new Thread(testThread,"thread1");
//        Thread thread2 = new Thread(testThread,"thread2");
//        Thread thread3 = new Thread(testThread,"thread3");
//        Thread thread4 = new Thread(testThread,"thread4");
//        Thread thread5 = new Thread(testThread,"thread5");
//        Thread thread6 = new Thread(testThread,"thread6");
//        thread1.start();
//        thread2.start();
//        thread3.start();
//        thread4.start();
//        thread5.start();
//        thread6.start();

        for (int i=0;i<20;i++){
            Thread thread1 = new Thread(testThread,"thread"+i);
            thread1.start();
        }

    }

    public static class TestThreadImpl implements Runnable{
        Long count=10000000L;
//        Hashtable<Long,String> h = new Hashtable<>();
        @Override
        public void run() {
//            synchronized (count){
                while (count>0){
//                    h.put(count,Thread.currentThread().getName());
                    count--;
                    System.out.println(Thread.currentThread().getName() + ":" + count);
//                }
//            System.out.println("test");
            }
        }
    }

    public void testThreadPool(int count){
        Long startTime = System.currentTimeMillis();
        final List<Integer> l = Lists.newArrayList();
        Random random = new Random();
        ThreadPoolExecutor tp = new ThreadPoolExecutor(1,1,60, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(count));
        for (int i=0;i<count;i++){
            tp.execute(new Runnable() {
                @Override
                public void run() {
                    l.add(random.nextInt());
                }
            });
        }
        tp.shutdown();
        try {
            tp.awaitTermination(1,TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(System.currentTimeMillis()-startTime);
        System.out.println(l.size());
    }
}
