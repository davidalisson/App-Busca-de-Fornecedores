package myproject.com.fornec.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import myproject.com.fornec.R;
import myproject.com.fornec.adapter.ChamadaOrmamentoAdapter;
import myproject.com.fornec.dao.ChamadaOrcamentoDAO;
import myproject.com.fornec.dao.ClienteDAO;
import myproject.com.fornec.dao.FornecedorDAO;
import myproject.com.fornec.model.ChamadaOrcamento;
import myproject.com.fornec.model.Cliente;
import myproject.com.fornec.model.Fornecedor;
import myproject.com.fornec.util.Mensagem;

public class ChamadaOrcamentoActivity extends AppCompatActivity {
    private int idCliente;
    private int idFornecedor;
    private int idChamadaOrcamento;
    private ListView listView;
    private List<ChamadaOrcamento> chamadaOrcamentoList;
    private ChamadaOrmamentoAdapter chamadaOrmamentoAdapter;
    private ChamadaOrcamentoDAO chamadaOrcamentoDAO;
    private ChamadaOrcamento chamadaOrcamento;
    private ClienteDAO clienteDAO;
    private Cliente cliente;
    private Fornecedor fornecedor;
    private FornecedorDAO fornecedorDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chamada_orcamento);
        cliente = new Cliente();
        clienteDAO = new ClienteDAO(this);
        fornecedor = new Fornecedor();
        fornecedorDAO = new FornecedorDAO(this);
        chamadaOrcamento = new ChamadaOrcamento();
        chamadaOrcamentoDAO = new ChamadaOrcamentoDAO(this);
        Intent intent = getIntent();
        idFornecedor = intent.getIntExtra("FORNECEDOR_ID_CHAMADA_ORCAMENTO", 0);
        chamadaOrcamentoList = chamadaOrcamentoDAO.listarChamadasOrcamentosPorStatus(idFornecedor);
        chamadaOrmamentoAdapter = new ChamadaOrmamentoAdapter(this, chamadaOrcamentoList);
        //chamadaOrcamento = (ChamadaOrcamento) chamadaOrcamentoDAO.listarChamadasOrcamentos();



        listView = (ListView) findViewById(R.id.listViewChamadaOrmamento);
        listView.setAdapter(chamadaOrmamentoAdapter);
        if(chamadaOrcamentoList.isEmpty())
        {
            Mensagem.Msg(this, "Nenhuma solicitação encontrada!");
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chamadaOrcamento =(ChamadaOrcamento) chamadaOrmamentoAdapter.getItem(position);
                idChamadaOrcamento = chamadaOrcamento.getId();
                cliente = clienteDAO.buscarCliente(chamadaOrcamento.getIdCliente());
                fornecedor = fornecedorDAO.buscarFornecedor(chamadaOrcamento.getIdFornecedor());

                //Mensagem.Msg(ChamadaOrcamentoActivity.this, "idChamadaOrcamento: "+idChamadaOrcamento+"/n"+"Id Fornecedor: "+idFornecedor);
                Intent intent = new Intent(ChamadaOrcamentoActivity.this, PerfilChamadaOrcamentoActivity.class);
                intent.putExtra("PERFIL_CHAMADA_ORCAMENTO_ID", idChamadaOrcamento);
                intent.putExtra("FORNECEDOR_ID_PERFIL_CHAMADA_ORCAMENTO",idFornecedor);
                intent.putExtra("CLIENTE_OBJETO_PERFIL_CHAMADA_ORCAMENTO", cliente);
                intent.putExtra("FORNECEDOR_OBJETO_PERFIL_CHAMADA_ORCAMENTO", fornecedor);
                intent.putExtra("OBJETO_PERFIL_CHAMADA_ORCAMENTO", chamadaOrcamento);
                finish();
                startActivity(intent);
            }
        });

    }
}
