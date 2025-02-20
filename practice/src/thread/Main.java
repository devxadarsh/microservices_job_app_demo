package thread;

// Step 1: Implement the Runnable interface
class MyRunnable implements Runnable {
    @Override
    public void run() {
        // Code to be executed in the new thread
        for (int i = 1; i <= 5; i++) {
            System.out.println("Thread: " + Thread.currentThread().getId() + " - Value: " + i);
            try {
                // Sleep for a while to simulate some work being done
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        // Step 2: Create a Runnable instance
        MyRunnable myRunnable = new MyRunnable();

        // Step 3: Create a Thread instance with the Runnable
        Thread thread = new Thread(myRunnable);

        // Step 4: Start the thread
        thread.start();

        // thread.Main thread continues to run
        for (int i = 1; i <= 5; i++) {
            System.out.println("thread.Main Thread - Value: " + i);
            try {
                // Sleep for a while to simulate some work being done
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                System.out.println("thread.Main thread interrupted");
            }
        }
    }
}
