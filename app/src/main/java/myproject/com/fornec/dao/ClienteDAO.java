package myproject.com.fornec.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.List;

import myproject.com.fornec.model.Cliente;

/**
 * Created by Dom on 10/06/2017.
 */

public class ClienteDAO {
    private DatabaseHelper  databaseHelper;
    private SQLiteDatabase database;

    public ClienteDAO(Context context)
    {
        databaseHelper = new DatabaseHelper(context);
    }
    private SQLiteDatabase getDatabase()
    {
        if (database == null)
        {
            database = databaseHelper.getWritableDatabase();
        }
        return database;
    }

    public Cliente criarCliente(Cursor cursor)
    {
        Cliente model = new Cliente(
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Clientes._ID)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Clientes.NOME)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Clientes.EMAIL)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Clientes.SENHA)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Clientes.ESTADO)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Clientes.CIDADE)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Clientes.BAIRRO)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Clientes.TELEFONE)),
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Clientes.ID_CATEGORIA_INTERESSE)),
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Clientes.TIPO))
        );
        return model;
    }

    public List<Cliente> listarClientes()
    {
        Cursor cursor = getDatabase().query(DatabaseHelper.Clientes.TABELA,
                DatabaseHelper.Clientes.COLUNAS, null, null, null, null, null);
        List<Cliente>  clientes = new ArrayList<Cliente>();
        while (cursor.moveToNext())
        {
            Cliente model = criarCliente(cursor);
            clientes.add(model);
        }
        cursor.close();
        return clientes;
    }

    public long salvarCliente(Cliente cliente)
    {
        ContentValues valores = new ContentValues();
        valores.put(DatabaseHelper.Clientes.NOME, cliente.getNome());
        valores.put(DatabaseHelper.Clientes.EMAIL, cliente.getEmail());
        valores.put(DatabaseHelper.Clientes.SENHA, cliente.getSenha());
        valores.put(DatabaseHelper.Clientes.ESTADO, cliente.getEstado());
        valores.put(DatabaseHelper.Clientes.CIDADE, cliente.getCidade());
        valores.put(DatabaseHelper.Clientes.BAIRRO, cliente.getBairro());
        valores.put(DatabaseHelper.Clientes.TELEFONE, cliente.getTelefone());
        valores.put(DatabaseHelper.Clientes.ID_CATEGORIA_INTERESSE, cliente.getIdCategoriaInteresse());
        valores.put(DatabaseHelper.Clientes.TIPO, cliente.getTipo());

        if (cliente.getId()!= null)
        {
            return getDatabase().update(DatabaseHelper.Clientes.TABELA, valores, "_id=?", new String[]{cliente.getId().toString()});

        }
        return getDatabase().insert(DatabaseHelper.Clientes.TABELA, null, valores);
    }

    public boolean removerCliente(int id)
    {
        return getDatabase().delete(DatabaseHelper.Clientes.TABELA, "_id = ?", new String[]{Integer.toString(id)}) > 0;
    }

    public Cliente buscarCliente(int id)
    {
        Cursor cursor = getDatabase().query(DatabaseHelper.Clientes.TABELA,
                DatabaseHelper.Clientes.COLUNAS, "_id = ?", new String[]{Integer.toString(id)}, null, null, null);
        if (cursor.moveToNext())
        {
            Cliente model = criarCliente(cursor);
            cursor.close();
            return model;
        }
        return null;
    }

    public boolean logar(String usuario, String senha)
    {
        Cursor cursor = getDatabase().query(DatabaseHelper.Clientes.TABELA, null, "LOGIN = ? AND SENHA = ?",
                new String[]{usuario, senha}, null, null, null);
        if(cursor.moveToFirst())
        {
            return true;
        }else
        {
            return false;
        }
    }
    public boolean verificaEmail(String email)
    {
        String query1 = "select email from clientes where email = ?";
        Cursor cursor1 = getDatabase().rawQuery(query1, new String[]{String.valueOf(email)});

        String query2 = "select email from fornecedores where email = ?";
        Cursor cursor2 = getDatabase().rawQuery(query2, new String[]{String.valueOf(email)});

        if (cursor1.moveToFirst())
        {
            return true;
        }
        if(cursor2.moveToFirst())
        {
            return true;
        }

        return false;
    }

    public void fechar()
    {
        databaseHelper.close();
        database = null;
    }


}
