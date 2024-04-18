/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.utfpr.pb.pw25s.server.service.impl;

import br.edu.utfpr.pb.pw25s.server.model.Product;
import br.edu.utfpr.pb.pw25s.server.model.Request;
import br.edu.utfpr.pb.pw25s.server.model.RequestItens;
import br.edu.utfpr.pb.pw25s.server.repository.ProductRepository;
import br.edu.utfpr.pb.pw25s.server.repository.RequestItensRepository;
import br.edu.utfpr.pb.pw25s.server.repository.RequestRepository;
import br.edu.utfpr.pb.pw25s.server.service.IRequestItensService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**
 *
 * @author mathe
 */
@Service
public class RequestItensServiceImpl extends CrudServiceImpl<RequestItens, Long> implements IRequestItensService {

    private RequestItensRepository requestItensRepository;
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private RequestServiceImpl requestService;

    public RequestItensServiceImpl(RequestItensRepository requestItensRepository) {
        this.requestItensRepository = requestItensRepository;
    }

    @Override
    protected JpaRepository<RequestItens, Long> getRepository() {
        return requestItensRepository;
    }

    @Override
    public RequestItens save(RequestItens entity) {

        Optional<Product> productOptional = productRepository.findById(entity.getProduct().getId());
        Product product = productOptional.get();

        RequestItens productItems = requestItensRepository.findByProductId(entity.getProduct().getId());
        Request request = entity.getRequest();

       
        entity.setPreco(product.getPrice());

        if (productItems != null) {
            entity.setId(productItems.getId());
            entity.setRequest(productItems.getRequest());
            entity.setQuantidade(entity.getQuantidade() + productItems.getQuantidade());
            entity.setPreco(product.getPrice().multiply(BigDecimal.valueOf(entity.getQuantidade())));
        }

        return super.save(entity);
    }
}
