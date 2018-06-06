package myproject.com.fornec.dao;

import android.content.Context;
import android.database.sqlite.*;
import android.net.http.SslCertificate;


/**
 * Created by Dom on 03/06/2017.
 */


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String BANCO_DADOS = "dbfornec";
    private static final int VERSAO = 1;

    public DatabaseHelper(Context context) {
        super(context, BANCO_DADOS, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Tabela de categorias de produtos
        db.execSQL("CREATE TABLE categorias (_id INTEGER primary key autoincrement, nome TEXT not null)");
        //tabela de clientes
        db.execSQL("CREATE TABLE clientes(" +
                "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                " nome TEXT NOT NULL," +
                " email TEXT NOT NULL," +
                " senha TEXT NOT NULL," +
                " estado TEXT NOT NULL," +
                " cidade TEXT NOT NULL," +
                " bairro TEXT NOT NULL," +
                " telefone TEXT NOT NULL," +
                " id_categoria_interesse INTEGER DEFAULT 0," +
                " tipo INTEGER not null, "+
                " CONSTRAINT FK_CATEGORIA_INTERESSE FOREIGN KEY(id_categoria_interesse) references categorias(_id))");
        //TABELA DE FORNECEDORES
        db.execSQL("create table fornecedores(" +
                "_id INTEGER primary key autoincrement," +
                " nome text NOT NULL," +
                " email text NOT NULL," +
                " senha text NOT NULL," +
                " estado text NOT NULL," +
                " cidade text NOT NULL," +
                " bairro text NOT NULL," +
                " telefone text NOT NULL," +
                " cnpj text NOT NULL," +
                " id_categoria INTEGER NOT NULL, "+
                " produtos text NOT NULL, "+
                " tipo INTEGER not null, "+
                " CONSTRAINT FK_ID_CATEGORIA foreign key (id_categoria) references categorias(_id))");

        // Tabela de avaliação
        db.execSQL("create table avaliar(" +
                "_id INTEGER primary key autoincrement, " +
                "nota INTEGER NOT NULL, " +
                "id_cliente INTEGER NOT NULL, " +
                "id_fornecedor INTEGER NOT NULL, " +
                "comentario text defalt null, " +
                "CONSTRAINT FK_ID_CLIENTE FOREIGN KEY (id_cliente) references clientes(_id)," +
                " CONSTRAINT FK_ID_FORNECEDOR FOREIGN KEY (id_fornecedor) references fornecedor(_id))");

        db.execSQL("create table chamada_orcamento (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " id_cliente INTEGER NOT NULL," +
                " id_fornecedor INTEGER NOT NULL," +
                " mensagem text not null," +
                " horario text not null," +
                " status text not null," +
                " CONSTRAINT FK_ID_FORNECEDOR_CHAMADA FOREIGN KEY (id_fornecedor) references fornecedores(_id)," +
                " CONSTRAINT FK_ID_CLIENTE_CHAMADA FOREIGN KEY (id_cliente) references clientes(_id));");

        db.execSQL("insert into categorias(nome) values('Alimentos e Bebidas')");
        db.execSQL("insert into categorias(nome) values('Automação')");
        db.execSQL("insert into categorias(nome) values('Bebês e Crianças')");
        db.execSQL("insert into categorias(nome) values('Beleza e Estética')");
        db.execSQL("insert into categorias(nome) values('Brindes')");
        db.execSQL("insert into categorias(nome) values('Brinquedos e Games')");
        db.execSQL("insert into categorias(nome) values('Cama Mesa e Banho')");
        db.execSQL("insert into categorias(nome) values('Carros Motos e Autopeças')");
        db.execSQL("insert into categorias(nome) values('Celulares e Telefones')");
        db.execSQL("insert into categorias(nome) values('Componentes Eletrônicos')");
        db.execSQL("insert into categorias(nome) values('Eletrodomésticos')");
        db.execSQL("insert into categorias(nome) values('Eletrônicos TV Som e DVD')");
        db.execSQL("insert into categorias(nome) values('Embalagens')");
        db.execSQL("insert into categorias(nome) values('Escolas e Cursos')");
        db.execSQL("insert into categorias(nome) values('Escritórios e Suprimentos')");
        db.execSQL("insert into categorias(nome) values('Esporte e Lazer')");
        db.execSQL("insert into categorias(nome) values('Ferramentas e Máquinas')");
        db.execSQL("insert into categorias(nome) values('Foto Câmera e Filmadora')");
        db.execSQL("insert into categorias(nome) values('Gráficas e Editoras')");
        db.execSQL("insert into categorias(nome) values('Indústria e Comércio')");
        db.execSQL("insert into categorias(nome) values('Informática')");
        db.execSQL("insert into categorias(nome) values('Joias e Relógios')");
        db.execSQL("insert into categorias(nome) values('Lojas e Variedades')");
        db.execSQL("insert into categorias(nome) values('Materiais de Limpeza')");
        db.execSQL("insert into categorias(nome) values('Moda e Acessórios')");
        db.execSQL("insert into categorias(nome) values('Móveis e Decoração')");
        db.execSQL("insert into categorias(nome) values('Papelarias e Livrarias')");
        db.execSQL("insert into categorias(nome) values('Perfumes e Cosméticos')");
        db.execSQL("insert into categorias(nome) values('Pet Shop')");
        db.execSQL("insert into categorias(nome) values('Restaurantes e Bares')");
        db.execSQL("insert into categorias(nome) values('Utensílios Domésticos')");


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static class Clientes {
        public static final String TABELA = "clientes";
        public static final String _ID = "_id";
        public static final String NOME = "nome";
        public static final String EMAIL = "email";
        public static final String SENHA = "senha";
        public static final String ESTADO = "estado";
        public static final String CIDADE = "cidade";
        public static final String BAIRRO = "bairro";
        public static final String TELEFONE = "telefone";
        public static final String ID_CATEGORIA_INTERESSE = "id_categoria_interesse";
        public static final String TIPO = "tipo";


        public static final String[] COLUNAS = {_ID, NOME, EMAIL, SENHA, ESTADO, CIDADE, BAIRRO, TELEFONE, ID_CATEGORIA_INTERESSE, TIPO};
    }

    public static class Fornecedores {
        public static final String TABELA = "fornecedores";
        public static final String _ID = "_id";
        public static final String NOME = "nome";
        public static final String EMAIL = "email";
        public static final String SENHA = "senha";
        public static final String ESTADO = "estado";
        public static final String CIDADE = "cidade";
        public static final String BAIRRO = "bairro";
        public static final String TELEFONE = "telefone";
        public static final String CNPJ = "cnpj";
        public static final String PRODUTOS = "produtos";
        public static final String ID_CATEGORIA = "id_categoria";
        public static final String TIPO = "tipo";

        public static final String[] COLUNAS = {_ID, NOME, EMAIL, SENHA, ESTADO, CIDADE, BAIRRO, TELEFONE, CNPJ, PRODUTOS, ID_CATEGORIA, TIPO};
    }

    public static class Categorias {
        public static final String TABELA = "categorias";
        public static final String _ID = "_id";
        public static final String NOME = "nome";

        public static final String[] COLUNAS = {_ID, NOME};
    }

    public static class Avaliar {
        public static final String TABELA = "avaliar";
        public static final String _ID = "_id";
        public static final String NOTA = "nota";
        public static final String ID_CLIENTE = "id_cliente";
        public static final String ID_FORNECEDOR = "id_fornecedor";
        public static final String COMENTARIO = "comentario";

        public static final String[] COLUNAS = {_ID, NOTA, ID_CLIENTE, ID_FORNECEDOR, COMENTARIO};
    }
    public static class ChamadaOrcamento {
        public static final String TABELA = "chamada_orcamento";
        public static final String _ID = "_id";
        public static final String ID_CLIENTE = "id_cliente";
        public static final String ID_FORNECEDOR = "id_fornecedor";
        public static final String MENSAGEM = "mensagem";
        public static final String HORARIO = "horario";
        public static final String STATUS = "status";

        public static final String[] COLUNAS = {_ID, ID_CLIENTE, ID_FORNECEDOR, MENSAGEM, HORARIO, STATUS};
    }
}