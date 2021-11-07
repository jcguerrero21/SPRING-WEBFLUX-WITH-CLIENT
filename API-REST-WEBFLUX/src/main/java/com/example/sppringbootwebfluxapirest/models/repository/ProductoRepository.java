package com.example.sppringbootwebfluxapirest.models.repository;

import com.example.sppringbootwebfluxapirest.models.documents.Producto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductoRepository extends ReactiveMongoRepository<Producto, String>{

}
