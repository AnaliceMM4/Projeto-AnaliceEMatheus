/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.utfpr.pb.pw25s.server.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author mathe
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     @JsonIgnore
    private int id;

    @NotNull
    @Size(min = 2, max = 50)
    private String name;
    private BigDecimal price;
    private String urlImage;
    @JsonIgnore
    private String description;
    @JsonIgnore
    private CategoryDTO category;

}
