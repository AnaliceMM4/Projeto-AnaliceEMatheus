package br.edu.utfpr.pb.pw25s.server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_request_itens")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RequestItens {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "request_id")
    @NotNull
    private Request request;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private BigDecimal preco;

    private Integer quantidade;

}
