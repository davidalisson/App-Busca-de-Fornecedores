package myproject.com.fornec.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import myproject.com.fornec.R;
import myproject.com.fornec.dao.ChamadaOrcamentoDAO;
import myproject.com.fornec.model.ChamadaOrcamento;
import myproject.com.fornec.model.Cliente;
import myproject.com.fornec.model.Fornecedor;
import myproject.com.fornec.util.Mensagem;

public class PerfilChamadaOrcamentoActivity extends AppCompatActivity {

    private TextView txtNome;
    private TextView txtMensagem;
    private TextView txtHorario;
    private TextView txtTelefone;
    private Button btFinalizar;
    private int idFornecedor;
    private Cliente cliente;
    private Fornecedor fornecedor;
    private ChamadaOrcamento chamadaOrcamento;
    private ChamadaOrcamentoDAO chamadaOrcamentoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_chamada_orcamento);

        cliente = new Cliente();
        chamadaOrcamento = new ChamadaOrcamento();
        fornecedor = new Fornecedor();
        chamadaOrcamentoDAO = new ChamadaOrcamentoDAO(this);

        txtNome = (TextView) findViewById(R.id.textViewPerfilChamOrcNomeCliente);
        txtMensagem = (TextView) findViewById(R.id.textViewPerfilChamOrcMensagem);
        txtHorario = (TextView) findViewById(R.id.textViewPerfilChamOrcHorario);
        txtTelefone = (TextView) findViewById(R.id.textViewPerfilChamOrcTelefone);
        btFinalizar = (Button) findViewById(R.id.btPerfilChamOrcFinalizar);


        Intent intent = getIntent();
        cliente =(Cliente) intent.getSerializableExtra("CLIENTE_OBJETO_PERFIL_CHAMADA_ORCAMENTO");
        fornecedor =(Fornecedor) intent.getSerializableExtra("FORNECEDOR_OBJETO_PERFIL_CHAMADA_ORCAMENTO");
        chamadaOrcamento =(ChamadaOrcamento) intent.getSerializableExtra("OBJETO_PERFIL_CHAMADA_ORCAMENTO");
        //idFornecedor = intent.getIntExtra("FORNECEDOR_ID_PERFIL_CHAMADA_ORCAMENTO", 0);
        setTitle("Chamadas e Orçamentos");
        txtNome.setText(cliente.getNome().toString());
        txtMensagem.setText(chamadaOrcamento.getMensagem().toString());
        txtHorario.setText(chamadaOrcamento.getHorario().toString());
        txtTelefone.setText(cliente.getTelefone().toString());

        btFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mensagem.MsgConfirm(PerfilChamadaOrcamentoActivity.this, "Confirmação", "Deseja finalizar a solicitação?", R.drawable.ic_action_about,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                chamadaOrcamentoDAO.alterarStatus("fechado", chamadaOrcamento.getId());
                                Mensagem.Msg(PerfilChamadaOrcamentoActivity.this, "Solicitação finalizada com sucesso!");


                                Intent intent = new Intent(PerfilChamadaOrcamentoActivity.this, ChamadaOrcamentoActivity.class);
                                //intent.putExtra("PERFIL_CHAMADA_ORCAMENTO_ID", idChamadaOrcamento);
                                intent.putExtra("FORNECEDOR_ID_PERFIL_CHAMADA_ORCAMENTO",idFornecedor);
                                intent.putExtra("CLIENTE_OBJETO_PERFIL_CHAMADA_ORCAMENTO", cliente);
                                intent.putExtra("FORNECEDOR_OBJETO_PERFIL_CHAMADA_ORCAMENTO", fornecedor);
                                intent.putExtra("OBJETO_PERFIL_CHAMADA_ORCAMENTO", chamadaOrcamento);
                                intent.putExtra("FORNECEDOR_ID_CHAMADA_ORCAMENTO", fornecedor.getId());
                                finish();
                                startActivity(intent);


                            }

                        }
                );
            }
        });



    }
}
