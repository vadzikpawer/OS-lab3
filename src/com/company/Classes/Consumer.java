package com.company.Classes;

import java.util.LinkedList;

public class Consumer implements Runnable {
    final LinkedList<Number> SharedQueue;
    String threadName;
    Boolean exitFlag = false;

    public Consumer() {
        SharedQueue = new LinkedList<Number>();
    }

    public Consumer(LinkedList<Number> mainQueue, String threadName) {
        this.threadName = threadName;
        SharedQueue = mainQueue;
    }

    @Override
    public void run() {
        System.out.println(threadName + " начал выполнение.");
        while (true) {
            try {
                if (SharedQueue.isEmpty() && exitFlag) {
                    System.out.println(threadName + " завершил выполнение.");
                    System.out.println("Элементов в очереди(" + threadName + "): " + SharedQueue.size());
                    return;
                }
                consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void disable() {
        exitFlag = true;
    }

    void consume() throws InterruptedException {
        synchronized (SharedQueue) {
            if (!SharedQueue.isEmpty()) {
                SharedQueue.poll();
                if (!exitFlag && SharedQueue.size() <= 80) {
                    SharedQueue.notifyAll();
                }
            }
        }
    }
}
