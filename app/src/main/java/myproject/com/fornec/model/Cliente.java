package myproject.com.fornec.model;

import java.io.Serializable;

/**
 * Created by Dom on 03/06/2017.
 */

public class Cliente implements Serializable{
    private Integer _id;
    private String nome;
    private String email;
    private String senha;
    private String estado;
    private String cidade;
    private String bairro;
    private String telefone;
    private int idCategoriaInteresse;
    private int tipo;

    public Cliente(){}

    public Cliente(Integer _id, String nome, String email, String senha, String estado, String cidade, String bairro, String telefone, int idCategoriaInteresse, int tipo) {
        this._id = _id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.telefone = telefone;
        this.idCategoriaInteresse = idCategoriaInteresse;
        this.tipo = tipo;
    }

    public Integer getId() {
        return _id;
    }

    public void setId(Integer id) {
        this._id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getIdCategoriaInteresse() {
        return idCategoriaInteresse;
    }

    public void setIdCategoriaInteresse(int idCategoriaInteresse) {
        this.idCategoriaInteresse = idCategoriaInteresse;
    }
    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}