// Klasa MainServer
package com.example.musicapp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainServer {
    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Tworzenie puli wątków z 10 wątkami
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // Uruchamianie serwera w puli wątków na różnych portach
        for (int i = 0; i < 1; i++) {
            executorService.submit(new Server(12345 + i));
        }

        // Zamykanie puli wątków po zakończeniu pracy
        executorService.shutdown();
    }
}
