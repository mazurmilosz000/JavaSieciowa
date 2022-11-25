package com.milosz000.TestThread;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class TestThread {

    private List<File> fileList;
    private int taskNumber;
    private int threadNumber;

    public TestThread(int taskNumber, int threadNumber) {
        this.taskNumber = taskNumber;
        this.threadNumber = threadNumber;
    }

    public void startThread() {
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());

                for (int i = 0; i < 20; i++) {
                    System.out.println("Wartość " + i);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        th.start();
        try {
            th.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startRunnable(int maxThread) {
        ExecutorService service = Executors.newCachedThreadPool();
        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) service;

        int taskNumber =0;
        while (taskNumber < 20) {
            if(threadPool.getActiveCount() < maxThread) {
                service.submit(new TestRunnable(taskNumber));
                taskNumber++;
            }
        }
        service.shutdown();
    }
    public void startRunnableFixed(int maxThread) {
        ExecutorService service = Executors.newFixedThreadPool(maxThread);
//        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) service;
//
//        int numberTask =0;
//        while (numberTask < 20) {
        for(int i=0; i<2000; i++) {
                service.submit(new TestRunnable(i));
            }
        service.shutdown();
    }
}
