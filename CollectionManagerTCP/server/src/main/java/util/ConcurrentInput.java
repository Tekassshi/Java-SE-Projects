package util;

import managers.CollectionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConcurrentInput {
    private static InputStreamReader isr = new InputStreamReader(System.in);
    private static BufferedReader reader = new BufferedReader(isr);
    private static Thread inputThread;

    public static void createInputThread(CollectionManager manager){
        inputThread = new Thread(() -> {
            Thread.currentThread().setName("input");
            while (true) {
                try {
                    String inp = reader.readLine();
                    if (!inp.equals("save"))
                        throw new IOException();
                    manager.save();
                } catch (IOException e) {
                    System.out.println("\nWrong command!\n");
                }

            }
        });
        inputThread.start();
    }
}
