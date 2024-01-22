package cat.itacademy.barcelonactiva.martos.sandra.s05.t01.n03.services.impl;

import cat.itacademy.barcelonactiva.martos.sandra.s05.t01.n03.domain.FlorEntity;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t01.n03.dto.FlorDTO;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t01.n03.exceptions.BadRequestException;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t01.n03.exceptions.ClientExceptions;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t01.n03.exceptions.DuplicatedNameException;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t01.n03.exceptions.FlorNotFoundException;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t01.n03.services.FlorClientService;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t01.n03.webclient.FlorWebClient;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;


@Service
public class FlorClientServiceImpl implements FlorClientService {
    private WebClient webClient;

    public FlorClientServiceImpl(){
        this.webClient = FlorWebClient.getClient();
    }

    @Override
    public Mono<String> addFlor(FlorEntity florEntity) {

        return webClient.post()
                .uri("/add")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(florEntity), FlorEntity.class)
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve()
                .onStatus(httpStatusCode -> !httpStatusCode.is2xxSuccessful(), clientResponse -> handleErrorResponse(clientResponse.statusCode()))
                .bodyToMono(String.class);
    }

    @Override
    public Mono<String> updateFlor(FlorEntity florEntity) {

        return webClient.put()
                .uri("/update/{id}", florEntity.getPk_FlorID())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(florEntity), FlorEntity.class)
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve()
                .onStatus(httpStatusCode -> !httpStatusCode.is2xxSuccessful(), clientResponse -> handleErrorResponse(clientResponse.statusCode()))
                .bodyToMono(String.class);
    }

    @Override
    public Mono<Void> deleteFlor(Integer id) {
        return webClient.delete()
                .uri("/delete/{id}", id)
                .retrieve()
                .onStatus(httpStatusCode -> !httpStatusCode.is2xxSuccessful(), clientResponse -> handleErrorResponse(clientResponse.statusCode()))
                .bodyToMono(Void.class);
    }

    @Override
    public Mono<FlorDTO> getOneFlor(Integer id) {
        return webClient.get()
                .uri("/getOne/{id}", id)
                .retrieve()
                .onStatus(httpStatusCode -> !httpStatusCode.is2xxSuccessful(), clientResponse -> handleErrorResponse(clientResponse.statusCode()))
                .bodyToMono(FlorDTO.class);
    }

    @Override
    public Flux<FlorDTO> getAllFlor(){
        return webClient.get()
                .uri("/getAll")
                .retrieve()
                .onStatus(httpStatusCode -> !httpStatusCode.is2xxSuccessful(), clientResponse -> handleErrorResponse(clientResponse.statusCode()))
                .bodyToFlux(FlorDTO.class);
    }
    @Override
    public Mono<? extends Throwable> handleErrorResponse(HttpStatusCode httpStatusCode) throws ClientExceptions{
        int errorValue = httpStatusCode.value();
        if (errorValue == HttpStatus.BAD_REQUEST.value()) {
            throw new BadRequestException();
        }
        if (errorValue == HttpStatus.CONFLICT.value()) {
            throw new DuplicatedNameException();
        }
        if (errorValue == HttpStatus.NOT_FOUND.value()) {
            throw new FlorNotFoundException();
        }
        else {
            throw new ClientExceptions("Alguna cosa inesperada ha fallat");
        }
    }
}
