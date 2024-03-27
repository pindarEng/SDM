package ex2;
import java.util.Random;

class ex2Thread extends Thread {
    private int threadId;
    private int totalSteps;

    public ex2Thread(int threadId, int totalSteps) {
        this.threadId = threadId;
        this.totalSteps = totalSteps;
    }

    @Override
    public void run() {
        Random rand = new Random();
        for (int step = 1; step <= totalSteps; step++) {
            System.out.println("Thread with ID " + threadId + " is at step " + step + " out of " + totalSteps + ".");
            try {
                Thread.sleep(rand.nextInt(991) + 10); // somn intre 10ms si 1000ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Thread with ID " + threadId + " has stopped executing.");
    }
}

