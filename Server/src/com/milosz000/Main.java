package com.milosz000;

import TestThread.TestRunnable;
import TestThread.TestThread;

public class Main {
    public static int[] tableInt;
    public static void main(String[] args) throws InterruptedException {
//        Server s = new Server(5501);
//        s.serverConnect();
//        FileServer fs = new FileServer(5501, "pliki_server\\");
//        fs.serverConnect();
//        fs.getFileFromClient();
//        fs.disconnectClient();
        tableInt = new int[2000];
        for (int i=0; i<2000; i++) {
            tableInt[i] =-1;
        }

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                for (int i=0; i<20; i++) {
                    System.out.println("Wartosc " + i);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
//        th.start();
//        th.join();
        TestThread tsR = new TestThread();
        tsR.startRunnable(10);
        System.out.println("Zakończenie wątku main");
    }
}