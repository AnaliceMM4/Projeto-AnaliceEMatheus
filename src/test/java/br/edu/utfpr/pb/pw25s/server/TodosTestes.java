/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.utfpr.pb.pw25s.server;

import br.edu.utfpr.pb.pw25s.server.dto.ProductDTO;
import br.edu.utfpr.pb.pw25s.server.dto.ProductViewDTO;
import br.edu.utfpr.pb.pw25s.server.model.Request;
import br.edu.utfpr.pb.pw25s.server.model.RequestItens;
import br.edu.utfpr.pb.pw25s.server.repository.ProductRepository;
import br.edu.utfpr.pb.pw25s.server.repository.RequestItensRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")

public class TodosTestes {

    private final String API_PRODUCTS = "/product";
    private final String API_REQUEST_ITEMS = "/requestItens";

    private final String API_REQUESTS = "/requests";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private RequestItensRepository requestItensRepository;

    @Test
    public void buscarProdutos_retornaListaDeProdutos() {
        // Faz uma requisição GET para a rota /products/searchProducts e obtém a resposta
        ResponseEntity<List<ProductDTO>> response = testRestTemplate.exchange(
                "/products/searchProducts",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductDTO>>() {
        }
        );

        // Verifica se o status da resposta é OK (200)
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Verifica se o corpo da resposta não é nulo e não está vazio
        assertThat(response.getBody()).isNotNull().isNotEmpty();

    }

    //Busca de ID inválido
    //Verificação se a resposta HTTP tem status 204 No Content, id de produto inválido sendo solicitado
    @Test
    public void buscarProduto_comIdInvalido() {
        ResponseEntity<Void> response
                = testRestTemplate.getForEntity("/products/111", Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void buscarProdutosPorCategoria_retornaListaDeProdutos() {
        //L DE LONG
        Long categoryId = 1L; // ID de uma categoria existente no banco de dados

        ResponseEntity<List<ProductViewDTO>> response = testRestTemplate.exchange(
                "/products/categories/" + categoryId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ProductViewDTO>>() {
        }
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull().isNotEmpty();
        //assertThat(response.getBody()).allMatch(product -> product.getCategory().getId().equals(categoryId));
    }

    //Adicionar um item com quantidade inválida, no exemplo abaixo é com quantidade negativa
    @Test
    public void adicionarItemComQuantidadeInvalida_retornaBadRequest() {
        RequestItens requestItens = createValidRequestItem();
        requestItens.setQuantidade(-1); // Quantidade inválida
        ResponseEntity<Object> response = testRestTemplate.postForEntity(
                API_REQUEST_ITEMS,
                requestItens,
                Object.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    private RequestItens createValidRequestItem() {
        // Criano uma nova instância de RequestItem válida para o teste
        RequestItens requestItens = new RequestItens();
        // Aqui teria que preencher os campos necessários do item da requisição (p, quantidade, etc.)
        return requestItens;
    }

    @Test
    public void criarSolicitacaoComDataNula() {
        // Cria uma nova instância de Request com data nula
        Request request = new Request();
        request.setData(null);

        // Tenta enviar uma requisição POST para criar a nova Request com data nula
        try {
            testRestTemplate.postForEntity(API_REQUESTS, request, Request.class);
        } catch (HttpClientErrorException e) {
            // Verifica se a requisição falhou com o status HTTP 400 (Bad Request)
            assertThat(e.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }
    }

}
