package com.company;

import java.util.concurrent.Semaphore;

class Q {
    int n;
    // Начать с недоступным семафором потребителя .
    static Semaphore semCon = new Semaphore(0);
    static Semaphore semProd = new Semaphore(1);

    void get() {
        try {
            semCon.acquire();
        } catch (InterruptedException е) {
            System.out.println("Пepexвaчeнo исключение InterruptedException");
        }
        System.out.println("Пoлyчeнo : " + n);
        semProd.release();
    }

    void put(int n) {
        try {
            semProd.acquire();
        } catch (InterruptedException е) {
            System.out.println(" Пepexвaчeнo исключение Interrupted Exception ");
        }
        this.n = n;
        System.out.println("Oтпpaвлeнo : " + n);
        semCon.release();
    }
}

class Producer implements Runnable {
    Q q;

    Producer(Q q) {
        this.q = q;
    }

    public void run() {
        for (int i = 0; i < 20; i++)
            q.put(i);
    }
}

class Consumer implements Runnable {
    Q q;

    Consumer(Q q) {
        this.q = q;
    }

    public void run() {
        for (int i = 0; i < 20; i++)
            q.get();
    }
}

public class Main {

    public static void main(String[] args) {
        Q q = new Q();
        new Thread(new Consumer(q), "Consumer").start();
        new Thread(new Producer(q), "Producer").start();
    }
}
