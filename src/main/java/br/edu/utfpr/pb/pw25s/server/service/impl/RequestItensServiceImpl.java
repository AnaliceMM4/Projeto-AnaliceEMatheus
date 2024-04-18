package br.edu.utfpr.pb.pw25s.server.service.impl;

import br.edu.utfpr.pb.pw25s.server.controller.CrudController;
import br.edu.utfpr.pb.pw25s.server.model.Request;
import br.edu.utfpr.pb.pw25s.server.model.RequestItens;
import br.edu.utfpr.pb.pw25s.server.repository.RequestItensRepository;
import br.edu.utfpr.pb.pw25s.server.repository.RequestRepository;
import br.edu.utfpr.pb.pw25s.server.service.IRequestItensService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class RequestItensServiceImpl extends CrudServiceImpl<RequestItens, Long> implements IRequestItensService {

    private RequestItensRepository requestItensRepository;


    public RequestItensServiceImpl(RequestItensRepository requestItensRepository) {
        this.requestItensRepository = requestItensRepository;
    }

    @Override
    protected JpaRepository<RequestItens, Long> getRepository() {
        return requestItensRepository;
    }

}
