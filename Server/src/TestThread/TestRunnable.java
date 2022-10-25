package TestThread;

import java.util.Random;
import com.milosz000.Main;

public class TestRunnable implements Runnable{

    private int taskNumber;
    public TestRunnable(int task) {
        taskNumber = task;
    }

    @Override
    public void run() {
        String nameThread = Thread.currentThread().getName();
        System.out.println(nameThread);
        int idTh = Integer.parseInt(nameThread.split("-")[3]);

        Random random = new Random();
        Main.tableInt[random.nextInt(2000)] = idTh;

        int w = random.nextInt(1000);
        try {
            Thread.sleep(w);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
