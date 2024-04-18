/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.utfpr.pb.pw25s.server.controller;

import br.edu.utfpr.pb.pw25s.server.dto.RequestDTO;
import br.edu.utfpr.pb.pw25s.server.model.Request;
import br.edu.utfpr.pb.pw25s.server.model.User;
import br.edu.utfpr.pb.pw25s.server.service.AuthService;
import br.edu.utfpr.pb.pw25s.server.service.ICrudService;
import br.edu.utfpr.pb.pw25s.server.service.impl.RequestServiceImpl;
import br.edu.utfpr.pb.pw25s.server.shared.GenericResponse;
import java.security.Principal;
import java.util.List;
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

    @GetMapping("/add")
    public ResponseEntity<GenericResponse> add(Principal principal) {
        GenericResponse genericResponse = new GenericResponse();
        if (principal != null) {

            String username = principal.getName();
            User user = (User) authService.loadUserByUsername(username);

            Request requestEntity = new Request();
            requestEntity.setUser(user);

            System.out.println("Request entity mapped: " + requestEntity);
            requestService.save(requestEntity);

            genericResponse.setMessage("Request saved.");

            return ResponseEntity.status(HttpStatus.CREATED).body(genericResponse);

        } else {
            genericResponse.setMessage("Erro na autenticação");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(genericResponse);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> listarDetalhesPedido(Principal principal) {
       
        if (principal != null) {
            String username = principal.getName();
            User user = (User) authService.loadUserByUsername(username);
            List<RequestDTO> pedidos = requestService.findRequestsByUser(user);
            
            return ResponseEntity.ok(pedidos);
        } else {
             GenericResponse genericResponse = new GenericResponse();
            genericResponse.setMessage("Erro na autenticação");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(genericResponse);
        }
    }
}
