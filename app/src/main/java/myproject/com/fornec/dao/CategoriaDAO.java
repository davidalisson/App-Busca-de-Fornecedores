package myproject.com.fornec.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import myproject.com.fornec.model.Categoria;

/**
 * Created by Dom on 10/06/2017.
 */

public class CategoriaDAO {
    private DatabaseHelper  databaseHelper;
    private SQLiteDatabase database;

    public CategoriaDAO(Context context)
    {
        databaseHelper = new DatabaseHelper(context);
    }
    private SQLiteDatabase getDatabase()
    {
        if (database == null)
        {
            database = databaseHelper.getReadableDatabase();
        }
        return database;
    }

    private Categoria criarCategoria(Cursor cursor)
    {
        Categoria model = new Categoria(
                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.Categorias._ID)),
                cursor.getString(cursor.getColumnIndex(DatabaseHelper.Categorias.NOME))

        );
        return model;
    }

    /*
    */
    public List<String> listarCategorias(){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + DatabaseHelper.Categorias.TABELA;

        Cursor cursor =getDatabase().rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        getDatabase().close();

        // returning lables
        return labels;
    }
    public List<Categoria> listarCategoriasListView()
    {
        Cursor cursor = getDatabase().query(DatabaseHelper.Categorias.TABELA,
                DatabaseHelper.Categorias.COLUNAS, null, null, null, null, null);
        List<Categoria>  categorias = new ArrayList<Categoria>();
        while (cursor.moveToNext())
        {
            Categoria model = criarCategoria(cursor);
            categorias.add(model);
        }
        cursor.close();
        return categorias;
    }
    /*
    public ArrayList<Categoria> getCategorias()
    {
        ArrayList<Categoria>  list = new ArrayList<Categoria>();
        Cursor cursor = getDatabase().query(DatabaseHelper.Categorias.TABELA,
         DatabaseHelper.Categorias.COLUNAS, null, null, null, null, null);

        while (cursor.moveToNext())
        {
            Categoria model = criarCategoria(cursor);
            list.add(model);
        }
        cursor.close();
        return list;
    }
    */
}
