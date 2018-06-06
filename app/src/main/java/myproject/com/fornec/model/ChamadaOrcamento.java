package myproject.com.fornec.model;

import java.io.Serializable;

/**
 * Created by Dom on 10/06/2017.
 */

public class ChamadaOrcamento implements Serializable
{
    private Integer _id;
    private int idCliente;
    private int idFornecedor;
    private String mensagem;
    private String horario;
    private String status;

    public ChamadaOrcamento() {}

    public ChamadaOrcamento(Integer _id, int idCliente, int idFornecedor, String mensagem, String horario, String status) {
        this._id = _id;
        this.idCliente = idCliente;
        this.idFornecedor = idFornecedor;
        this.mensagem = mensagem;
        this.horario = horario;
        this.status = status;
    }

    public Integer getId() {
        return _id;
    }

    public void setId(Integer _id) {
        this._id = _id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
