/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.utfpr.pb.pw25s.server.service.impl;

import br.edu.utfpr.pb.pw25s.server.dto.CategoryDTO;
import br.edu.utfpr.pb.pw25s.server.dto.ProductDTO;
import br.edu.utfpr.pb.pw25s.server.dto.RequestDTO;
import br.edu.utfpr.pb.pw25s.server.dto.RequestItensDTO;
import br.edu.utfpr.pb.pw25s.server.dto.UserViewDTO;
import br.edu.utfpr.pb.pw25s.server.model.Request;
import br.edu.utfpr.pb.pw25s.server.model.RequestItens;
import br.edu.utfpr.pb.pw25s.server.model.User;
import br.edu.utfpr.pb.pw25s.server.repository.RequestItensRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import br.edu.utfpr.pb.pw25s.server.repository.RequestRepository;
import br.edu.utfpr.pb.pw25s.server.service.IRequestService;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author mathe
 */
@Service
public class RequestServiceImpl extends CrudServiceImpl<Request, Long> implements IRequestService {

    private RequestRepository requestRepository;
    @Autowired
    private RequestItensRepository requestItensRepository;
    
    public RequestServiceImpl(RequestRepository pedidoRepository) {
        this.requestRepository = pedidoRepository;
    }

    @Override
    protected JpaRepository<Request, Long> getRepository() {
        return requestRepository;
    }

    @Override
    public Request save(Request entity) {

        LocalDate localDate = LocalDate.now();
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay(zoneId).toInstant();
        Date date = Date.from(instant);

        entity.setData(date);

        return super.save(entity);
    }

    public List<RequestDTO> findRequestsByUser(User user) {
        List<Request> requests =  requestRepository.findByUser(user);
        List<RequestDTO> requestDTOs = new ArrayList<>();

        for (Request request : requests) {
            RequestDTO requestDTO = new RequestDTO();
            UserViewDTO userViewDTO = new UserViewDTO();
            requestDTO.setId(request.getId());
            requestDTO.setData(request.getData());
            userViewDTO.setUsername(request.getUser().getUsername());
            userViewDTO.setId(request.getUser().getId());
            requestDTO.setUser(userViewDTO);
            requestDTOs.add(requestDTO);
        }

        return requestDTOs;
    }
    
    public List<RequestItensDTO> findRequestItensById(Long requestId){
        List<RequestItens> requestItensList = (List<RequestItens>) requestItensRepository.findByRequestId(requestId);
    List<RequestItensDTO> requestItensDTOList = requestItensList.stream()
            .map(requestItens -> {
                RequestItensDTO requestItensDTO = new RequestItensDTO();
                ProductDTO productDTO = new ProductDTO();
                CategoryDTO categoryDTO = new CategoryDTO();
                requestItensDTO.setId(requestItens.getId());
                productDTO.setId(requestItens.getProduct().getId());
                productDTO.setName(requestItens.getProduct().getName());
                productDTO.setPrice(requestItens.getProduct().getPrice());
                productDTO.setUrlImage(requestItens.getProduct().getUrlImage());
                productDTO.setDescription(requestItens.getProduct().getDescription());
                categoryDTO.setId(requestItens.getProduct().getCategory().getId());
                categoryDTO.setName(requestItens.getProduct().getCategory().getName());
                productDTO.setCategory(categoryDTO);
                requestItensDTO.setProduct(productDTO);
                requestItensDTO.setQuantidade(requestItens.getQuantidade());
                requestItensDTO.setPreco(requestItens.getPreco());
                return requestItensDTO;
            })
            .collect(Collectors.toList());
    return requestItensDTOList;
    }

}
