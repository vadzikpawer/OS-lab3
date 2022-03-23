package com.company.Classes;

import com.company.Main;

import java.util.LinkedList;
import java.util.Random;

public class Producer implements Runnable {
    public final LinkedList<Number> SharedQueue;
    Random random = new Random();
    String threadName;
    boolean exitFlag = false;

    public Producer() {
        SharedQueue = new LinkedList<>();
    }

    public Producer(LinkedList<Number> mainQueue, String threadName) {
        this.threadName = threadName;
        SharedQueue = mainQueue;
    }

    @Override
    public void run() {
        System.out.println(threadName + " начал выполнение.");
        while (!exitFlag) {
            try {
                produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(threadName + " завершил выполнение.");
    }

    public void disable() {
        exitFlag = true;
        System.out.println("Элементов в очереди(" + threadName + "): " + SharedQueue.size());
    }

    private void produce() throws InterruptedException {
        synchronized (SharedQueue) {
            if (exitFlag) {
                SharedQueue.notifyAll();
                return;
            }
            if (SharedQueue.size() < Main.MAX_QUEUE_ELEMENTS) {
                SharedQueue.add(random.nextInt(100));
                SharedQueue.notifyAll();
                return;
            }
            SharedQueue.wait();
        }
    }
}
