package com.example.sppringbootwebfluxapirest.models.repository;

import com.example.sppringbootwebfluxapirest.models.documents.Producto;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ProductoRepository extends ReactiveMongoRepository<Producto, String> {

    Mono<Producto> findByNombre(String nombre);

    @Query("{ 'nombre': ?0 }")
    Mono<Producto> obtenerPorNombre(String nombre);
}
