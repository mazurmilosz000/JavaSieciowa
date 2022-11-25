package com.milosz000;

import com.milosz000.TestThread.TestThread;



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


        TestThread tsR = new TestThread(200, 10);
//        tsR.startThread();
//        tsR.startRunnable(10);
        tsR.startRunnableFixed(5);
        System.out.println("Zakończenie wątku main");

    }
}