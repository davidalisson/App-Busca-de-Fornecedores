package myproject.com.fornec.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import myproject.com.fornec.R;
import myproject.com.fornec.adapter.FornecedorAdapter;
import myproject.com.fornec.dao.FornecedorDAO;
import myproject.com.fornec.model.Fornecedor;
import myproject.com.fornec.util.Mensagem;

public class ListarFornecedorActivity extends AppCompatActivity {

    private Fornecedor fornecedor;
    private ListView listView;
    private List<Fornecedor> fornecedorLista;
    private FornecedorAdapter fornecedorAdapter;
    private FornecedorDAO fornecedorDAO;
    private int idCategoria;
    private int idFornecedor;
    private int idCliente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_fornecedor);

        idCategoria = getIntent().getIntExtra("CATEGORIA_ID", 0);
        idCliente = getIntent().getIntExtra("CLIENTE_ID_LISTA", 0);
        Mensagem.Msg(this, "ID_CLIENTE: "+idCliente);

        fornecedorDAO = new FornecedorDAO(this);
        fornecedorLista = fornecedorDAO.listarFornecedoresCategoria(idCategoria);
        fornecedorAdapter = new FornecedorAdapter(this, fornecedorLista);



        listView = (ListView) findViewById(R.id.listViewFornecedores);
        listView.setAdapter(fornecedorAdapter);

        if(fornecedorLista.isEmpty())
        {
            Mensagem.Msg(this, "Desculpe, nenhum fornecedor encontrado para essa categoria.");

        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fornecedor = fornecedorLista.get(position);
                Intent intent = new Intent(ListarFornecedorActivity.this, PerfilFornecedorActivity.class);
                intent.putExtra("FORNECEDOR", fornecedor);
                intent.putExtra("CLIENTE_ID_PERFIL", idCliente);
                intent.putExtra("FORNECEDOR_MEDIA_AVALIACAO", fornecedor.getMedia());
                startActivity(intent);
            }
        });

    }
}
