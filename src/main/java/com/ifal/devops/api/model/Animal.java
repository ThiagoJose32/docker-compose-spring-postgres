package com.ifal.devops.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Animal {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Getter private Long id;
    @Getter @Setter private String nome;
    @Getter @Setter private String foto;
    @Getter @Setter private Character sexo;
    @Getter @Setter private String especie;
    @Getter @Setter private String raca;
    @Getter @Setter private LocalDate dataNascimento;
    @Getter @Setter private String descricao;
    @Getter @Setter private Boolean castrado;
    @Getter @Setter private String status;
    @Getter @Setter private String adotante;
    @Getter @Setter private String criadoPor;
    @Getter @Setter private LocalDate criadoEm;
    @Getter @Setter private String modificadoPor;
    @Getter @Setter private LocalDate modificadoEm;
}
