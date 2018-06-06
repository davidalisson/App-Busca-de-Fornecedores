package myproject.com.fornec.model;

/**
 * Created by Dom on 09/06/2017.
 */

public class Avaliar {
    private Integer _id;
    private int nota;
    private int idCliente;
    private int idFornecedor;
    private String comentario;
    private String nomeCliente;

    public Avaliar(){}

    public Avaliar(Integer _id, int nota, int idCliente, int idFornecedor, String comentario) {
        this._id = _id;
        this.nota = nota;
        this.idCliente = idCliente;
        this.idFornecedor = idFornecedor;
        this.comentario = comentario;
    }

    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
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

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }
}
