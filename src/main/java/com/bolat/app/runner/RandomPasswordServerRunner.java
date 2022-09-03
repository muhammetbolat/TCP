package com.bolat.app.runner;

import org.csystem.util.exception.ExceptionUtil;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import static org.csystem.util.exception.ExceptionUtil.subscribeRunnable;

@Configuration
public class RandomPasswordServerRunner implements ApplicationRunner {
    private final ServerSocket serverSocket;
    private final ExecutorService threadPool;

    private void handleClient(Socket socket) {
        threadPool.execute(() -> generatePassword(socket));
    }

    private void generatePassword(Socket socket) {
        System.out.format("Host: %s, Port: %s, Local Port: %s%n", socket.getInetAddress().getHostAddress(),
                socket.getPort(), socket.getLocalPort());

    }
    private void acceptClient() throws IOException {
        System.out.println("Random password generator server is waiting for a client");

        handleClient(serverSocket.accept());

    }

    private void runForAccept() {
        for(;;) {
            subscribeRunnable(this::acceptClient, ex -> System.out.println(ex.getMessage()));
        }
    }

    public RandomPasswordServerRunner(ServerSocket serverSocket, ExecutorService threadPool) {
        this.serverSocket = serverSocket;
        this.threadPool = threadPool;
    }

    @Override
    public void run(ApplicationArguments args) {
        threadPool.execute(this::runForAccept);
    }
}
