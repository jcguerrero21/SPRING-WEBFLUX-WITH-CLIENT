package com.example.sppringbootwebfluxapirest;

import com.example.sppringbootwebfluxapirest.models.documents.Categoria;
import com.example.sppringbootwebfluxapirest.models.documents.Producto;
import com.example.sppringbootwebfluxapirest.models.service.ProductoService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class SppringBootWebFluxApirestApplicationTests {

    @Autowired
    private WebTestClient client;

    @Autowired
    private ProductoService productoService;

    @Value("${config.base.endpoint}")
    private String url;

    @Test
    public void listarTest() {
        client.get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBodyList(Producto.class)
                .consumeWith(response -> {
                    List<Producto> productos = response.getResponseBody();
                    assert productos != null;
                    productos.forEach(p -> System.out.println(p.getNombre()));
                    Assertions.assertThat(productos.size() > 0).isTrue();
                });
    }

    @Test
    public void verTest() {
        Producto producto = productoService.findProductoByNombre("TV Panasonic Pantalla LCD").block();
        assert producto != null;
        client.get()
                .uri(url + "/{id}", Collections.singletonMap("id", producto.getId()))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody(Producto.class)
                .consumeWith(response -> {
                    Producto p = response.getResponseBody();
                    assert p != null;
                    Assertions.assertThat(p.getId()).isNotEmpty();
                    Assertions.assertThat(p.getId().length() > 0).isTrue();
                    Assertions.assertThat(p.getNombre()).isEqualTo("TV Panasonic Pantalla LCD");
                });
//        .expectBody()
//                .jsonPath("$.id").isNotEmpty()
//                .jsonPath("$.nombre").isEqualTo("TV Panasonic Pantalla LCD");
    }

    @Test
    public void crearTest() {
        Categoria categoria = productoService.findByCategoriaNombre("Muebles").block();
        Producto producto = new Producto("Mesa comedor", 100.00, categoria);
        client.post().uri(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(producto), Producto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.nombre").isEqualTo("Mesa comedor")
                .jsonPath("$.categoria.nombre").isEqualTo("Muebles");
    }

    @Test
    public void editarTest() {
        Producto producto = productoService.findProductoByNombre("Sony Notebook").block();
        Categoria categoria = productoService.findByCategoriaNombre("Electrónico").block();

        Producto productoEditado = new Producto("Asus Notebook", 700.00, categoria);

        assert producto != null;
        client.put().uri(url + "/{id}", Collections.singletonMap("id", producto.getId()))
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(productoEditado), Producto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.nombre").isEqualTo("Asus Notebook")
                .jsonPath("$.categoria.nombre").isEqualTo("Electrónico");
    }

    @Test
    public void eliminarTest() {
        Producto producto = productoService.findProductoByNombre("Mica Cómoda 5 Cajones").block();

        assert producto != null;
        client.delete().uri(url + "/{id}", Collections.singletonMap("id", producto.getId()))
                .exchange()
                .expectStatus().isNoContent()
                .expectBody()
                .isEmpty();

        client.get().uri(url + "/{id}", Collections.singletonMap("id", producto.getId()))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .isEmpty();

    }

}
