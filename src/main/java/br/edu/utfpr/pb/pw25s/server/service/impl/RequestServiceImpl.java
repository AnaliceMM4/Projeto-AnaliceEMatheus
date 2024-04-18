/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.utfpr.pb.pw25s.server.service.impl;

import br.edu.utfpr.pb.pw25s.server.dto.ProductDTO;
import br.edu.utfpr.pb.pw25s.server.dto.RequestDTO;
import br.edu.utfpr.pb.pw25s.server.dto.UserDTO;
import br.edu.utfpr.pb.pw25s.server.dto.UserViewDTO;
import br.edu.utfpr.pb.pw25s.server.model.Request;
import br.edu.utfpr.pb.pw25s.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import br.edu.utfpr.pb.pw25s.server.repository.RequestRepository;
import br.edu.utfpr.pb.pw25s.server.service.IRequestService;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author mathe
 */
@Service
public class RequestServiceImpl extends CrudServiceImpl<Request, Long> implements IRequestService {

    private RequestRepository requestRepository;

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

}
