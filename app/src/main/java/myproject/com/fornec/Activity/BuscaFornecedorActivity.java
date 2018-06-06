package myproject.com.fornec.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import myproject.com.fornec.R;
import myproject.com.fornec.adapter.CategoriaAdapter;
import myproject.com.fornec.dao.CategoriaDAO;
import myproject.com.fornec.model.Categoria;
import myproject.com.fornec.util.Mensagem;

public class BuscaFornecedorActivity extends AppCompatActivity{

    private ListView listView;
    private List<Categoria> categoriaLista;
    private CategoriaAdapter categoriaAdapter;
    private CategoriaDAO categoriaDAO;
    private int idCategoria;
    private int idCliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_fornecedor);

        categoriaDAO = new CategoriaDAO(this);
        categoriaLista = categoriaDAO.listarCategoriasListView();
        categoriaAdapter = new CategoriaAdapter(this, categoriaLista);

        listView = (ListView) findViewById(R.id.listViewCategorias);
        listView.setAdapter(categoriaAdapter);
        Intent intent = getIntent();
        idCliente = intent.getIntExtra("CLIENTE_ID_CAT", 0);
        Mensagem.Msg(this, "Id cliente: "+String.valueOf(idCliente));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                idCategoria = position+1;


                Intent intent = new Intent(BuscaFornecedorActivity.this, ListarFornecedorActivity.class);
                intent.putExtra("CATEGORIA_ID", idCategoria);
                intent.putExtra("CLIENTE_ID_LISTA",idCliente);
                startActivity(intent);
            }
        });

    }

    }

