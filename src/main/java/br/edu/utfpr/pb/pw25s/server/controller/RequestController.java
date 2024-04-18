/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.utfpr.pb.pw25s.server.controller;

import br.edu.utfpr.pb.pw25s.server.dto.RequestDTO;
import br.edu.utfpr.pb.pw25s.server.dto.UserDTO;
import br.edu.utfpr.pb.pw25s.server.model.Request;
import br.edu.utfpr.pb.pw25s.server.model.User;
import br.edu.utfpr.pb.pw25s.server.service.AuthService;
import br.edu.utfpr.pb.pw25s.server.service.ICrudService;
import br.edu.utfpr.pb.pw25s.server.service.impl.RequestServiceImpl;
import br.edu.utfpr.pb.pw25s.server.shared.GenericResponse;
import jakarta.validation.Valid;
import java.security.Principal;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author mathe
 */
@RestController
@RequestMapping("/requests")
public class RequestController extends CrudController<Request, RequestDTO, Long> {

    private final RequestServiceImpl requestService;
    @Autowired
    private final AuthService authService;
    private final ModelMapper modelMapper;

    public RequestController(RequestServiceImpl requestService, ModelMapper modelMapper, AuthService authService) {
        super(Request.class, RequestDTO.class);
        this.requestService = requestService;
        this.modelMapper = modelMapper;
        this.authService = authService;
    }

    @Override
    protected ICrudService<Request, Long> getService() {
        return requestService;
    }

    @Override
    protected ModelMapper getModelMapper() {
        return modelMapper;
    }

    /* @PostMapping("/add")
    public ResponseEntity<GenericResponse> add(@Valid @RequestBody RequestDTO request) {

        Request requestEntity = modelMapper.map(request, Request.class);
        System.out.println("Request entity mapped: " + requestEntity);
        if (requestEntity != null) {
            requestService.save(requestEntity);
            GenericResponse genericResponse = new GenericResponse();
            genericResponse.setMessage("Request saved.");

            return ResponseEntity.status(HttpStatus.CREATED).body(genericResponse);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
     */
    @PostMapping("/finalizarPedido")
    public ResponseEntity<GenericResponse> finalizarPedido(@Valid @RequestBody RequestDTO request) {
        
        Request requestEntity = modelMapper.map(request, Request.class);

        System.out.println("Request entity mapped: " + requestEntity);
        if (requestEntity != null) {
            requestService.save(requestEntity);
            GenericResponse genericResponse = new GenericResponse();
            genericResponse.setMessage("Request saved.");

            return ResponseEntity.status(HttpStatus.CREATED).body(genericResponse);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

}
