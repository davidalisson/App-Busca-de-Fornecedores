package myproject.com.fornec.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import myproject.com.fornec.model.Avaliar;
import myproject.com.fornec.model.Cliente;

/**
 * Created by Dom on 10/06/2017.
 */

public class AvaliarDAO {

    private DatabaseHelper  databaseHelper;
    private SQLiteDatabase database;
    private Cliente cliente;
    private ClienteDAO clienteDao;

    public AvaliarDAO(Context context)
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
    public Avaliar criarAvaliacao(Cursor cursor)
    {
        Avaliar model = new Avaliar(
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Avaliar._ID)),
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Avaliar.NOTA)),
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Avaliar.ID_CLIENTE)),
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Avaliar.ID_FORNECEDOR)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Avaliar.COMENTARIO))
        );
        return model;
    }


    public long salvarAvaliacao(Avaliar avaliar)
    {
        ContentValues valores = new ContentValues();
        valores.put(DatabaseHelper.Avaliar.NOTA, avaliar.getNota());
        valores.put(DatabaseHelper.Avaliar.ID_CLIENTE, avaliar.getIdCliente());
        valores.put(DatabaseHelper.Avaliar.ID_FORNECEDOR, avaliar.getIdFornecedor());
        valores.put(DatabaseHelper.Avaliar.COMENTARIO, avaliar.getComentario());

        if (avaliar.get_id()!= null)
        {
            return getDatabase().update(DatabaseHelper.Avaliar.TABELA, valores, "_id=?", new String[]{avaliar.get_id().toString()});

        }
        return getDatabase().insert(DatabaseHelper.Avaliar.TABELA, null, valores);
    }


    public List<Avaliar> listarAvaliacoes(int id, Context ctx)
    {
        String sql = "select av.* from avaliar av inner join clientes c, fornecedores f on" +
                " av.id_cliente = c._id and av.id_fornecedor = f._id where id_fornecedor=?";
        Cursor cursor = getDatabase().rawQuery(sql, new String[]{Integer.toString(id)});
        List<Avaliar>  avaliars = new ArrayList<Avaliar>();

        String sql2 = "select c.* from avaliar av inner join clientes c, fornecedores f on" +
                " av.id_cliente = c._id and av.id_fornecedor = f._id where id_fornecedor=?";
        Cursor cursor2 = getDatabase().rawQuery(sql2, new String[]{Integer.toString(id)});

        cliente = new Cliente();
        clienteDao = new ClienteDAO(ctx);

            while (cursor.moveToNext() && cursor2.moveToNext()) {
                Avaliar model = criarAvaliacao(cursor);
                cliente = clienteDao.criarCliente(cursor2);
                model.setNomeCliente(cliente.getNome());
                avaliars.add(model);
            }

        cursor.close();
        return avaliars;
    }

    public List<Avaliar> listarMediaAvaliacoes(int id)
    {
        String sql = "select av.* from avaliar av inner join clientes c, fornecedores f on" +
                " av.id_cliente = c._id and av.id_fornecedor = f._id where id_fornecedor=?";
        Cursor cursor = getDatabase().rawQuery(sql, new String[]{Integer.toString(id)});
        List<Avaliar>  chamadaOrcamentos = new ArrayList<Avaliar>();
        while (cursor.moveToNext())
        {
            Avaliar model = criarAvaliacao(cursor);
            chamadaOrcamentos.add(model);
        }
        cursor.close();
        return chamadaOrcamentos;
    }
    public Cliente getCliente()
    {
        return cliente;
    }
    public void fechar()
    {
        databaseHelper.close();
        database = null;
    }

}
