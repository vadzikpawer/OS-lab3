package com.company;

import com.company.Classes.Consumer;
import com.company.Classes.Producer;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    public static LinkedList<Number> SharedQueue = new LinkedList<>();
    public static Producer[] tProd = new Producer[3];
    public static Consumer[] tCons = new Consumer[2];
    static Thread[] Producers = new Thread[3];
    static Thread[] Consumers = new Thread[3];
    static Scanner in = new Scanner(System.in);
    static boolean exitFlag = false;

    public static void main(String[] args) throws InterruptedException {

        for (int i = 0; i < 3; i++) {
            tProd[i] = new Producer(SharedQueue, "Продюсер номер " + i);
        }
        for (int i = 0; i < 2; i++) {
            tCons[i] = new Consumer(SharedQueue, "Консумер номер " + i);
        }

        for (int i = 0; i < 3; i++) {
            Producers[i] = new Thread(tProd[i]);
            Producers[i].start();
        }
        for (int i = 0; i < 2; i++) {
            Consumers[i] = new Thread(tCons[i]);
            Consumers[i].start();
        }

        while (!exitFlag) {
            String exitInput = "";
            exitInput = in.nextLine();
            if (exitInput.equals("q")) {
                exitFlag = true;
                for (int i = 0; i < 3; i++) {
                    tProd[i].disable();
                    Producers[i].join();
                }
                for (int i = 0; i < 2; i++) {
                    tCons[i].disable();
                    Consumers[i].join();
                }
            }
        }
        System.out.println("Элементов в очереди(Main): " + SharedQueue.size());
    }
}
