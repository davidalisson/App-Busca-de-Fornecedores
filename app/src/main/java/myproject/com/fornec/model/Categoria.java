package myproject.com.fornec.model;

/**
 * Created by Dom on 09/06/2017.
 */

public class Categoria {
    private int _id;
    private String nome;

    public Categoria(){}

    public Categoria(int _id, String nome) {
        this._id = _id;
        this.nome = nome;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
