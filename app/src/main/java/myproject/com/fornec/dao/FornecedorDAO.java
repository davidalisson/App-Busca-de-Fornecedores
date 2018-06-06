package myproject.com.fornec.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import myproject.com.fornec.model.Avaliar;
import myproject.com.fornec.model.Fornecedor;


/**
 * Created by Dom on 10/06/2017.
 */

public class FornecedorDAO
{
    private DatabaseHelper  databaseHelper;
    private SQLiteDatabase database;
    private static final String errorLog = "coleta";
    private Avaliar avaliar;
    private AvaliarDAO avaliarDAO;
    public FornecedorDAO(Context context)
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
    public Fornecedor criarFornecedor(Cursor cursor)
    {
        Fornecedor model = new Fornecedor(
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Fornecedores._ID)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Fornecedores.NOME)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Fornecedores.EMAIL)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Fornecedores.SENHA)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Fornecedores.ESTADO)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Fornecedores.CIDADE)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Fornecedores.BAIRRO)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Fornecedores.TELEFONE)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Fornecedores.CNPJ)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Fornecedores.PRODUTOS)),
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Fornecedores.ID_CATEGORIA)),
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Fornecedores.TIPO))
        );
        return model;
    }
    public List<Fornecedor> listarFornecedores()
    {
        Cursor cursor = getDatabase().query(DatabaseHelper.Fornecedores.TABELA,
                DatabaseHelper.Fornecedores.COLUNAS, null, null, null, null, null);
        List<Fornecedor>  fornecedores = new ArrayList<Fornecedor>();
        while (cursor.moveToNext())
        {
            Fornecedor model = criarFornecedor(cursor);
            fornecedores.add(model);
        }
        cursor.close();
        return fornecedores;
    }

    public long salvarFornecedor(Fornecedor fornecedor)
    {
        ContentValues valores = new ContentValues();
        valores.put(DatabaseHelper.Fornecedores.NOME, fornecedor.getNome());
        valores.put(DatabaseHelper.Fornecedores.EMAIL, fornecedor.getEmail());
        valores.put(DatabaseHelper.Fornecedores.SENHA, fornecedor.getSenha());
        valores.put(DatabaseHelper.Fornecedores.ESTADO, fornecedor.getEstado());
        valores.put(DatabaseHelper.Fornecedores.CIDADE, fornecedor.getCidade());
        valores.put(DatabaseHelper.Fornecedores.BAIRRO, fornecedor.getBairro());
        valores.put(DatabaseHelper.Fornecedores.TELEFONE, fornecedor.getTelefone());
        valores.put(DatabaseHelper.Fornecedores.CNPJ, fornecedor.getCnpj());
        valores.put(DatabaseHelper.Fornecedores.PRODUTOS, fornecedor.getProdutos());
        valores.put(DatabaseHelper.Fornecedores.ID_CATEGORIA, fornecedor.getIdCategoria());
        valores.put(DatabaseHelper.Fornecedores.TIPO, fornecedor.getTipo());

        if (fornecedor.getId()!= null)
        {
            return getDatabase().update(DatabaseHelper.Fornecedores.TABELA, valores, "_id=?", new String[]{fornecedor.getId().toString()});

        }
        return getDatabase().insert(DatabaseHelper.Fornecedores.TABELA, null, valores);
    }
    public boolean removerFornecedor(int id)
    {
        return getDatabase().delete(DatabaseHelper.Fornecedores.TABELA, "_id = ?", new String[]{Integer.toString(id)}) > 0;
    }
    public Fornecedor buscarFornecedor(int id)
    {
        Cursor cursor = getDatabase().query(DatabaseHelper.Fornecedores.TABELA,
                DatabaseHelper.Fornecedores.COLUNAS, "_id = ?", new String[]{Integer.toString(id)}, null, null, null);
        if (cursor.moveToNext())
        {
            Fornecedor model = criarFornecedor(cursor);
            cursor.close();
            return model;
        }
        return null;
    }
    public List<Fornecedor> listarFornecedoresCategoria(int id)
    {
        try {
            String sql = "select f.* from fornecedores f inner join categorias c on f.id_categoria = c._id where c._id=?";
            Cursor cursor = getDatabase().rawQuery(sql, new String[]{String.valueOf(id)});

            String sql2 = "select AVG(av.nota) from avaliar av inner join clientes c, fornecedores f on av.id_cliente = c._id and av.id_fornecedor = f._id where id_fornecedor=?";


            List<Fornecedor> fornecedores = new ArrayList<Fornecedor>();
            while (cursor.moveToNext()) {
                Fornecedor model = criarFornecedor(cursor);
                Cursor cursor2 = getDatabase().rawQuery(sql2, new String[]{String.valueOf(model.getId())});
                if(cursor2.moveToFirst())
                    model.setMedia(cursor2.getInt(0));
                fornecedores.add(model);
            }
            cursor.close();
            return fornecedores;
        }catch (Exception e)
        {
            Log.i(errorLog, "Erro: "+e);
            return null;
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
