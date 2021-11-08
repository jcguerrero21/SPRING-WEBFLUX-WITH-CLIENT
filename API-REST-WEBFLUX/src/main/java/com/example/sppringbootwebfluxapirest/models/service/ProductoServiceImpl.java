package com.example.sppringbootwebfluxapirest.models.service;

import com.example.sppringbootwebfluxapirest.models.documents.Categoria;
import com.example.sppringbootwebfluxapirest.models.documents.Producto;
import com.example.sppringbootwebfluxapirest.models.repository.CategoriaRepository;
import com.example.sppringbootwebfluxapirest.models.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public Flux<Producto> findAll() {
        return productoRepository.findAll();
    }

    @Override
    public Mono<Producto> findById(String id) {
        return productoRepository.findById(id);
    }

    @Override
    public Mono<Producto> save(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public Mono<Void> delete(Producto producto) {
        return productoRepository.delete(producto);
    }

    @Override
    public Flux<Producto> findAllConNombreUpperCase() {
        return productoRepository.findAll().map(producto -> {
            producto.setNombre(producto.getNombre().toUpperCase());
            return producto;
        });
    }

    @Override
    public Flux<Producto> findAllConNombreUpperCaseRepeat() {
        return findAllConNombreUpperCase().repeat(5000);
    }

    @Override
    public Flux<Categoria> findAllCategoria() {
        return categoriaRepository.findAll();
    }

    @Override
    public Mono<Categoria> findCategoriaById(String id) {
        return categoriaRepository.findById(id);
    }

    @Override
    public Mono<Categoria> saveCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    @Override
    public Mono<Producto> findProductoByNombre(String nombre) {
        return productoRepository.obtenerPorNombre(nombre);
    }

    @Override
    public Mono<Categoria> findByCategoriaNombre(String nombre) {
        return categoriaRepository.findByNombre(nombre);
    }


}
