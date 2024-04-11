/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.utfpr.pb.pw25s.server.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;

/**
 *
 * @author mathe
 */

@Data
public class ProductDTO {

    private int id;
    
    @NotNull
    @Size(min = 2, max = 50)
    private String name;

    private String description;
    private BigDecimal price;
    private CategoryDTO category;
}
