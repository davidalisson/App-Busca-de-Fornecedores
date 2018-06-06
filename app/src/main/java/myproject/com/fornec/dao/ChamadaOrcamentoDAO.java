package myproject.com.fornec.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import myproject.com.fornec.model.ChamadaOrcamento;

/**
 * Created by Dom on 10/06/2017.
 */

public class ChamadaOrcamentoDAO {
    private DatabaseHelper  databaseHelper;
    private SQLiteDatabase database;

    public ChamadaOrcamentoDAO(Context context)
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

    public ChamadaOrcamento criarChamadaOrcamento(Cursor cursor)
    {
        ChamadaOrcamento model = new ChamadaOrcamento(
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ChamadaOrcamento._ID)),
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ChamadaOrcamento.ID_CLIENTE)),
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.ChamadaOrcamento.ID_FORNECEDOR)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.ChamadaOrcamento.MENSAGEM)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.ChamadaOrcamento.HORARIO)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.ChamadaOrcamento.STATUS))
        );
        return model;
    }

    public List<ChamadaOrcamento> listarChamadasOrcamentos()
    {
        Cursor cursor = getDatabase().query(DatabaseHelper.ChamadaOrcamento.TABELA,
                DatabaseHelper.ChamadaOrcamento.COLUNAS, null, null, null, null, null);
        List<ChamadaOrcamento>  chamadaOrcamentos = new ArrayList<ChamadaOrcamento>();
        while (cursor.moveToNext())
        {
            ChamadaOrcamento model = criarChamadaOrcamento(cursor);
            chamadaOrcamentos.add(model);
        }
        cursor.close();
        return chamadaOrcamentos;
    }
    public List<ChamadaOrcamento> listarChamadasOrcamentosPorStatus(int id)
    {
        String sql = "select co.* from chamada_orcamento co inner join fornecedores f" +
                " on co.id_fornecedor = f._id where status = 'aberto' and id_fornecedor =?";
        Cursor cursor = getDatabase().rawQuery(sql, new String[]{Integer.toString(id)});
        List<ChamadaOrcamento>  chamadaOrcamentos = new ArrayList<ChamadaOrcamento>();
        while (cursor.moveToNext())
        {
            ChamadaOrcamento model = criarChamadaOrcamento(cursor);
            chamadaOrcamentos.add(model);
        }
        cursor.close();
        return chamadaOrcamentos;
    }

    public long salvarChamadaOrcamento(ChamadaOrcamento chamadaOrcamento)
    {
        ContentValues valores = new ContentValues();
        valores.put(DatabaseHelper.ChamadaOrcamento.ID_CLIENTE, chamadaOrcamento.getIdCliente());
        valores.put(DatabaseHelper.ChamadaOrcamento.ID_FORNECEDOR, chamadaOrcamento.getIdFornecedor());
        valores.put(DatabaseHelper.ChamadaOrcamento.MENSAGEM, chamadaOrcamento.getMensagem());
        valores.put(DatabaseHelper.ChamadaOrcamento.HORARIO, chamadaOrcamento.getHorario());
        valores.put(DatabaseHelper.ChamadaOrcamento.STATUS, chamadaOrcamento.getStatus());

        if (chamadaOrcamento.getId()!= null)
        {
            return getDatabase().update(DatabaseHelper.ChamadaOrcamento.TABELA, valores, "_id=?", new String[]{chamadaOrcamento.getId().toString()});

        }
        return getDatabase().insert(DatabaseHelper.ChamadaOrcamento.TABELA, null, valores);
    }

    public ChamadaOrcamento buscarChamadaOrcamento(int id)
    {
        Cursor cursor = getDatabase().query(DatabaseHelper.ChamadaOrcamento.TABELA,
                DatabaseHelper.ChamadaOrcamento.COLUNAS, "_id = ?", new String[]{Integer.toString(id)}, null, null, null);
        if (cursor.moveToNext())
        {
            ChamadaOrcamento model = criarChamadaOrcamento(cursor);
            cursor.close();
            return model;
        }
        return null;
    }

    public void alterarStatus(String status, int id)
    {
            String sql = "update chamada_orcamento set status = '" + status + "' where _id =" + id;
            getDatabase().execSQL(sql);
    }

    public void fechar()
    {
        databaseHelper.close();
        database = null;
    }

}
