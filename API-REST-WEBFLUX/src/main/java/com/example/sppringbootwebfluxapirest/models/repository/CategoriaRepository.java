package com.example.sppringbootwebfluxapirest.models.repository;

import com.example.sppringbootwebfluxapirest.models.documents.Categoria;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface CategoriaRepository extends ReactiveMongoRepository<Categoria, String> {

    Mono<Categoria> findByNombre(String nombre);

}
