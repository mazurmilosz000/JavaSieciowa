package TestThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class TestThread {
    public void startRunnable(int maxThread) {
        ExecutorService service = Executors.newCachedThreadPool();
        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) service;

        int numberTask =0;
        while (numberTask < 20) {
            if(threadPool.getActiveCount() < maxThread) {
                service.submit(new TestRunnable(numberTask));
                numberTask++;
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
