package com.service.addressAPI.Domain.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="cep")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cep extends DefaultEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true)
    private String cep;
    private String logradouro;
    private String complemento;
    @Column(name = "unidade", nullable = true)
    private String unidade;
    private String bairro;
    private String localidade;
    private String uf;
    private String estado;
    private String regiao;
    private Integer ibge;
    @Column(name = "gia", nullable = true)
    private String gia;
    private Integer ddd;
    private String siafi;

}
