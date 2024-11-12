package com.shams.hello.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Client {
    public static void main(String[] args) {
        System.out.println("Running the pod");

        String serviceHost = System.getenv().get("service_a_host");
        String servicePort = System.getenv().get("service_a_port");

        String env = System.getenv().get("env");
        System.out.println("Found env " + env);
        System.out.println("Found service host " + serviceHost);
        System.out.println("Found service port " + servicePort);

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(() -> {
            try {
            HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ZERO.plusSeconds(10)).build();
            URI uri = URI.create("http://"+serviceHost + ":" + servicePort + "/" + env + "/hello-service-a");
            System.out.println("Path to call " + uri.toURL());
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri).timeout(Duration.ofSeconds(10))
                    .build();

                System.out.println("Calling the api ...");
                HttpResponse<String> res = client.send(request, HttpResponse.BodyHandlers.ofString());
                if (res.statusCode() == 200)  {
                    System.out.println("Got response " + res.body());
                } else  {
                    System.out.println("Failed to get response = " + res.statusCode());
                }
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, 1, 10, TimeUnit.SECONDS);
    }
}
