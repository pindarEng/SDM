package ex2;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        ex2Thread[] threads = new ex2Thread[5]; //vector de threaduri
        for (int i = 0; i < threads.length; i++) {
            int threadId = i + 1;
            Random random = new Random();
            int totalSteps = random.nextInt(41) + 10; // random from 10 catre 50
            threads[i] = new ex2Thread(threadId, totalSteps);
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

