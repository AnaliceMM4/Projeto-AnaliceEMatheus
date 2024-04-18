/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.edu.utfpr.pb.pw25s.server.repository;

import br.edu.utfpr.pb.pw25s.server.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author mathe
 */


public interface RequestRepository extends JpaRepository<Request, Long>{
    
}
