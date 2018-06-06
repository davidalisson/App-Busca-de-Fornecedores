package myproject.com.fornec.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

import myproject.com.fornec.R;
import myproject.com.fornec.adapter.AvaliacaoAdapter;
import myproject.com.fornec.dao.AvaliarDAO;
import myproject.com.fornec.dao.ClienteDAO;
import myproject.com.fornec.dao.FornecedorDAO;
import myproject.com.fornec.model.Avaliar;
import myproject.com.fornec.model.Cliente;
import myproject.com.fornec.model.Fornecedor;
import myproject.com.fornec.util.Mensagem;

public class AvaliacaoActivity extends AppCompatActivity {

    private int idFornecedor;
    private int idChamadaOrcamento;
    private ListView listView;
    private List<Avaliar> avaliarList;
    private AvaliacaoAdapter avaliarAdapter;
    private AvaliarDAO avaliarDAO;
    private Avaliar avaliar;
    private ClienteDAO clienteDAO;
    private Cliente cliente;
    private Fornecedor fornecedor;
    private FornecedorDAO fornecedorDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacao);

        cliente = new Cliente();
        clienteDAO = new ClienteDAO(this);
        fornecedor = new Fornecedor();
        fornecedorDAO = new FornecedorDAO(this);
        avaliarDAO = new AvaliarDAO(this);
        avaliar = new Avaliar();

        Intent intent = getIntent();
        idFornecedor = intent.getIntExtra("FORNECEDOR_ID_CHAMADA_ORCAMENTO", 0);
        avaliarList = avaliarDAO.listarAvaliacoes(idFornecedor, this);
        avaliarAdapter = new AvaliacaoAdapter(this, avaliarList);

        listView = (ListView) findViewById(R.id.listViewAvaliacoes);
        listView.setAdapter(avaliarAdapter);

        if(avaliarList.isEmpty())
        {
            Mensagem.Msg(this, "Nenhuma avaliação encontrada!");
        }


    }
}
