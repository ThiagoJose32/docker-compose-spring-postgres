package com.ifal.devops.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String foto;
    private Character sexo;
    private String especie;
    private String raca;
    private LocalDate dataNascimento;
    private String descricao;
    private Boolean castrado;
    private String status;
    private String adotante;
    private String criadoPor;
    private LocalDate criadoEm;
    private String modificadoPor;
    private LocalDate modificadoEm;

    public Animal() {
    }

    // Se quiser manter o construtor completo:
    public Animal(Long id, String nome, String foto, Character sexo, String especie,
                  String raca, LocalDate dataNascimento, String descricao, Boolean castrado,
                  String status, String adotante, String criadoPor, LocalDate criadoEm,
                  String modificadoPor, LocalDate modificadoEm) {
        this.id = id;
        this.nome = nome;
        this.foto = foto;
        this.sexo = sexo;
        this.especie = especie;
        this.raca = raca;
        this.dataNascimento = dataNascimento;
        this.descricao = descricao;
        this.castrado = castrado;
        this.status = status;
        this.adotante = adotante;
        this.criadoPor = criadoPor;
        this.criadoEm = criadoEm;
        this.modificadoPor = modificadoPor;
        this.modificadoEm = modificadoEm;
    }

    // GETTERS e SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) { // útil pros testes
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getCastrado() {
        return castrado;
    }

    public void setCastrado(Boolean castrado) {
        this.castrado = castrado;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAdotante() {
        return adotante;
    }

    public void setAdotante(String adotante) {
        this.adotante = adotante;
    }

    public String getCriadoPor() {
        return criadoPor;
    }

    public void setCriadoPor(String criadoPor) {
        this.criadoPor = criadoPor;
    }

    public LocalDate getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDate criadoEm) {
        this.criadoEm = criadoEm;
    }

    public String getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(String modificadoPor) {
        this.modificadoPor = modificadoPor;
    }

    public LocalDate getModificadoEm() {
        return modificadoEm;
    }

    public void setModificadoEm(LocalDate modificadoEm) {
        this.modificadoEm = modificadoEm;
    }
}
