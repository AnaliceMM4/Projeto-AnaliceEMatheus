/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.utfpr.pb.pw25s.server.service.impl;

import br.edu.utfpr.pb.pw25s.server.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import br.edu.utfpr.pb.pw25s.server.repository.RequestRepository;
import br.edu.utfpr.pb.pw25s.server.service.IRequestService;

/**
 *
 * @author mathe
 */
@Service
public class RequestServiceImpl extends CrudServiceImpl<Request, Long> implements IRequestService {

    private RequestRepository requestRepository;

    public RequestServiceImpl(RequestRepository pedidoRepository) {
        this.requestRepository= pedidoRepository;
    }

    @Override
    protected JpaRepository<Request, Long> getRepository() {
        return requestRepository;
    }

}
