package cat.itacademy.barcelonactiva.martos.sandra.s05.t01.n03.services;

import cat.itacademy.barcelonactiva.martos.sandra.s05.t01.n03.domain.FlorEntity;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t01.n03.dto.FlorDTO;
import cat.itacademy.barcelonactiva.martos.sandra.s05.t01.n03.exceptions.ClientExceptions;
import org.springframework.http.HttpStatusCode;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface FlorClientService {
    public Mono<String> addFlor(FlorEntity florEntity) throws ClientExceptions;
    public Mono<String> updateFlor(FlorEntity florEntity) throws ClientExceptions;
    public Mono<Void> deleteFlor(Integer id) throws ClientExceptions;
    public Mono<FlorDTO> getOneFlor(Integer id) throws ClientExceptions;
    public Flux<FlorDTO> getAllFlor() throws ClientExceptions;
    public Mono<? extends Throwable> handleErrorResponse(HttpStatusCode httpStatusCode) throws ClientExceptions;
}
