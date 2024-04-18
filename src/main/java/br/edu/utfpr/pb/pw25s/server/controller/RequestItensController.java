/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.utfpr.pb.pw25s.server.controller;

import br.edu.utfpr.pb.pw25s.server.dto.RequestDTO;
import br.edu.utfpr.pb.pw25s.server.dto.RequestItensDTO;
import br.edu.utfpr.pb.pw25s.server.dto.UserDTO;
import br.edu.utfpr.pb.pw25s.server.model.Request;
import br.edu.utfpr.pb.pw25s.server.model.RequestItens;
import br.edu.utfpr.pb.pw25s.server.service.ICrudService;
import br.edu.utfpr.pb.pw25s.server.service.impl.RequestItensServiceImpl;
import br.edu.utfpr.pb.pw25s.server.service.impl.RequestServiceImpl;
import br.edu.utfpr.pb.pw25s.server.shared.GenericResponse;
import jakarta.validation.Valid;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author mathe
 */
@RestController
@RequestMapping("/requestItens")
public class RequestItensController extends CrudController<RequestItens, RequestItensDTO, Long> {

    private final RequestItensServiceImpl requestItensService;
    private final ModelMapper modelMapper;

    public RequestItensController(RequestItensServiceImpl requestItensService, ModelMapper modelMapper) {
        super(RequestItens.class, RequestItensDTO.class);
        this.requestItensService = requestItensService;
        this.modelMapper = modelMapper;
    }

    @Override
    protected ICrudService<RequestItens, Long> getService() {
        return requestItensService;
    }

    @Override
    protected ModelMapper getModelMapper() {
        return modelMapper;
    }

    @PostMapping("/adicionarNoCarrinho")
    public ResponseEntity<GenericResponse> add(@Valid @RequestBody RequestItensDTO requestItens) {

        RequestItens requestItensEntity = modelMapper.map(requestItens, RequestItens.class);
        
        System.out.println("Request entity mapped: " + requestItensEntity);
        if (requestItensEntity != null) {
            requestItensService.save(requestItensEntity);
            GenericResponse genericResponse = new GenericResponse();
            genericResponse.setMessage("Request saved.");

            return ResponseEntity.status(HttpStatus.CREATED).body(genericResponse);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/listarCarrinho")
    public ResponseEntity<?> listarCarrinho() {
        List<RequestItens> requestItens = requestItensService.findAll();
        List<RequestItensDTO> requestItensDTOList = requestItens.stream()
                .map(requestItem -> modelMapper.map(requestItem, RequestItensDTO.class))
                .collect(Collectors.toList());
        if (requestItensDTOList != null) {
            return ResponseEntity.ok(requestItensDTOList);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
    
   

}
