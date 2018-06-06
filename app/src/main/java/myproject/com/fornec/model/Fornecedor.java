package myproject.com.fornec.model;

import java.io.Serializable;

/**
 * Created by Dom on 03/06/2017.
 */

public class Fornecedor implements Serializable
{
    private Integer _id;
    private String nome;
    private String email;
    private String senha;
    private String estado;
    private String cidade;
    private String bairro;
    private String telefone;
    private String cnpj;
    private String produtos;
    private int idCategoria;
    private int tipo;
    float media;

    public Fornecedor() {}

    public Fornecedor(Integer _id, String nome, String email, String senha, String estado, String cidade, String bairro, String telefone, String cnpj, String produtos, int idCategoria, int tipo) {
        this._id = _id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.telefone = telefone;
        this.cnpj = cnpj;
        this.produtos = produtos;
        this.idCategoria = idCategoria;
        this.tipo = tipo;
    }

    public Integer getId() {
        return _id;
    }

    public void setId(Integer _id) {
        this._id = _id;
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

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getProdutos() {
        return produtos;
    }

    public void setProdutos(String produtos) {
        this.produtos = produtos;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public float getMedia() {
        return media;
    }

    public void setMedia(float media) {
        this.media = media;
    }
}
