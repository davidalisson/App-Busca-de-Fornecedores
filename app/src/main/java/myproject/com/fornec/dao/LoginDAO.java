package myproject.com.fornec.dao;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import myproject.com.fornec.model.Cliente;
import myproject.com.fornec.model.Fornecedor;
import myproject.com.fornec.util.Mensagem;

/**
 * Created by Dom on 11/06/2017.
 */

public class LoginDAO
{
    private DatabaseHelper  databaseHelper;
    private SQLiteDatabase database;

    private Fornecedor fornecedor;
    private Cliente cliente;
    private FornecedorDAO fornecedorDAO;
    private ClienteDAO clienteDAO;

    public LoginDAO(Context context)
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


    public boolean logar(String email, String senha)
    {
        Cursor cursor = getDatabase().rawQuery("select email, senha from clientes where email='"+email+"' and senha = '"+senha+"' " +
                "UNION ALL" +
                " select email, senha from fornecedores where email='"+email+"' and senha = '"+senha+"'", null);

        if (cursor.moveToFirst())
        {
            return true;
        }
        return false;
    }

    public void verificaClientes(Context ctx)
    {
        String sql = "select * from clientes where tipo = 1";
        Cursor cursor = getDatabase().rawQuery(sql, null);
        while (cursor.moveToNext())
        {
            cliente = new Cliente();
            clienteDAO = new ClienteDAO((Activity)ctx);
            cliente=clienteDAO.criarCliente(cursor);
            System.out.println(cliente.getEmail()+"-------"+cliente.getSenha());
        }
    }

    /*

    public boolean logar(String email, String senha)
    {
        String query1 = "select * from clientes where email = "+email+" and senha =?"+senha;
        Cursor cursor1 = getDatabase().rawQuery(query1, null);

        String query2 = "select * from clientes where email = "+email+" and senha =?"+senha;
        Cursor cursor2 = getDatabase().rawQuery(query2, null);

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
*/

    public int verificaTipo(String email, Context ctx)
    {
        //Cursor cursor = getDatabase().rawQuery("select email, tipo from clientes where email='"+email+"' "+
        //        "UNION ALL" +
        //        " select email, tipo from fornecedores where email='"+email+"'", null);
        String query = "select * from clientes where email = ?";
        Cursor cursor = getDatabase().rawQuery(query, new String[]{String.valueOf(email)});

        String query2 = "select * from fornecedores where email = ?";
        Cursor cursor2 = getDatabase().rawQuery(query2, new String[]{String.valueOf(email)});

        if (cursor.moveToFirst())
        {
            cliente = new Cliente();
            clienteDAO = new ClienteDAO(ctx);
            cliente = clienteDAO.criarCliente(cursor);
            //Mensagem.Msg((Activity)ctx, cliente.getNome());
            return 1;
        }
        if (cursor2.moveToFirst())
        {

            fornecedor = new Fornecedor();
            fornecedorDAO = new FornecedorDAO(ctx);
            fornecedor = fornecedorDAO.criarFornecedor(cursor2);
            return 2;
        }

        return 0;
    }

    public Fornecedor getFornecedor()
    {
        return fornecedor;
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
