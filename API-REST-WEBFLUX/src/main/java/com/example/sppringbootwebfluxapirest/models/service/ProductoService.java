package com.example.sppringbootwebfluxapirest.models.service;

import com.example.sppringbootwebfluxapirest.models.documents.Categoria;
import com.example.sppringbootwebfluxapirest.models.documents.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoService {

    Flux<Producto> findAll();

    Flux<Producto> findAllConNombreUpperCase();

    Flux<Producto> findAllConNombreUpperCaseRepeat();

    Mono<Producto> findById(String id);

    Mono<Producto> save(Producto producto);

    Mono<Void> delete(Producto producto);

    Flux<Categoria> findAllCategoria();

    Mono<Categoria> findCategoriaById(String id);

    Mono<Categoria> saveCategoria(Categoria categoria);

    Mono<Producto> findProductoByNombre(String nombre);

    Mono<Categoria> findByCategoriaNombre(String nombre);

}
