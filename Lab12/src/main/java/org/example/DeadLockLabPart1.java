package org.example;


// Working with deadlock.
public class DeadLockLabPart1 {

    final Object resource1 = new Object();
    final Object resource2 = new Object();

    final int n = 100000;

    Thread t1 = new Thread( new Runnable() {
        public void run() {

            for (int x = 1; x <= n; x++) {
                System.out.println();

                for (int i = 1; i <= 10; i++) {
                    System.out.print(" t1: " + i);
                }


            }
        }
    });

    Thread t2 = new Thread( new Runnable() {
        public void run() {
            for (int x = 1; x <= n; x++) {
                System.out.println();
                for (int i = 1; i <= 10; i++) {
                    System.out.print(" t2: " + i);

                }
            }
        }
    });

    public void foo() {
        // Start up two threads that may become deadlocked.
        t1.start();
        t2.start();
    }
    public static void main(String[] args) {
        System.out.println("About to startup and run two threads.");
        new DeadLockLabPart1().foo();
        System.out.println("Startup complete but threads may still be running.");
    }
}
