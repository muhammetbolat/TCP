package com.bolat.app.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.ServerSocket;

@Configuration
public class ServerSocketConfig {
    @Value("${server.port:50500}")
    private int port;

    @Value("${server.backlog:128}")
    private int backlog;

    @Bean
    public ServerSocket getServerSocket() throws IOException {
        return new ServerSocket(port, backlog);
    }

}
