package cat.itacademy.barcelonactiva.martos.sandra.s05.t01.n03.webclient;


import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

public class FlorWebClient {
    public static WebClient getClient() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                .responseTimeout(Duration.ofMillis(5000))
                .doOnConnected((conn->
                        conn.addHandlerLast(new ReadTimeoutHandler(10))
                                .addHandlerLast(new WriteTimeoutHandler(10))));

        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:9001/flor/v1")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
        return webClient;
    }
}
