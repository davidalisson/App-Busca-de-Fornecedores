package myproject.com.fornec.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.List;

import myproject.com.fornec.R;
import myproject.com.fornec.dao.AvaliarDAO;
import myproject.com.fornec.dao.ChamadaOrcamentoDAO;
import myproject.com.fornec.model.Avaliar;
import myproject.com.fornec.model.ChamadaOrcamento;
import myproject.com.fornec.model.Fornecedor;
import myproject.com.fornec.util.Mensagem;

public class PerfilFornecedorActivity extends AppCompatActivity {
    private TextView tvNome;
    private TextView tvProdutos;
    private TextView tvCidade;
    //private Button btSolicita;
    private Fornecedor fornecedor;
    private int idCliente;
    private ChamadaOrcamentoDAO chamadaOrcamentoDAO;
    private int idChamadaOrcamento;
    private Toolbar toolbar;
    private FloatingActionButton btSolicita;
     private static RatingBar ratingBarAvaliar;
    private int idAvaliacao;
    private AvaliarDAO avaliarDAO;
    private float mediaAvaliacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_fornecedor);

        fornecedor = new Fornecedor();
        chamadaOrcamentoDAO = new ChamadaOrcamentoDAO(this);
        avaliarDAO = new AvaliarDAO(this);

        tvNome = (TextView) findViewById(R.id.textViewPerfilNome);
        tvProdutos = (TextView) findViewById(R.id.textViewPerfilProdutos);
        tvCidade = (TextView) findViewById(R.id.textViewPerfilCidade2);
        btSolicita = (FloatingActionButton) findViewById(R.id.btSolicitarOrcamento);
        ratingBarAvaliar = (RatingBar) findViewById(R.id.ratingBarPerfilFornec);
        toolbar = (Toolbar) findViewById(R.id.toolbarPerfilFornec);
        setSupportActionBar(toolbar);

        ratingBarAvaliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Intent intent = getIntent();
        fornecedor =(Fornecedor) intent.getSerializableExtra("FORNECEDOR");

        idCliente = intent.getIntExtra("CLIENTE_ID_PERFIL", 0);
        Mensagem.Msg(this, "ID_Cliente"+idCliente);

        mediaAvaliacao = intent.getFloatExtra("FORNECEDOR_MEDIA_AVALIACAO", 0);
        ratingBarAvaliar.setRating(mediaAvaliacao);

        tvNome.setText(fornecedor.getNome());
        tvProdutos.setText(fornecedor.getProdutos());
        tvCidade.setText(fornecedor.getCidade());
        btSolicita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicitaOrcamento();
            }
        });

        ratingBarAvaliar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    avaliar();
                }
                return true;
            }
        });

        createDrawer();

    }


    private void avaliar(){
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.layout_dialog_rating);

        //define o título do Dialog
        dialog.setTitle("Avaliar");

        //instancia os objetos que estão no layout customdialog.xml
        final Button btConfirmar = (Button) dialog.findViewById(R.id.btAvaliarEnviar);
        final Button btCancelar = (Button) dialog.findViewById(R.id.btAvaliarCancelar);
        final EditText editMsg = (EditText) dialog.findViewById(R.id.edtMessage);
        final RatingBar ratingBar = (RatingBar) dialog.findViewById(R.id.ratingBarAvaliarDiaglog);




        btConfirmar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //etRetorno.setText(editText.getText());
                String mensagem = editMsg.getText().toString();
                int nota = (int) ratingBar.getRating();


                Avaliar avaliar = new Avaliar();
                    avaliar.setIdCliente(idCliente);
                    avaliar.setIdFornecedor(fornecedor.getId());
                    avaliar.setComentario(mensagem);
                    avaliar.setNota(nota);

                    if (idAvaliacao > 0) {
                        avaliar.set_id(idChamadaOrcamento);
                    }
                    long resultado = avaliarDAO.salvarAvaliacao(avaliar);
                    if (resultado != -1) {
                        if (idAvaliacao > 0) {
                            Mensagem.Msg(PerfilFornecedorActivity.this, getString(R.string.mensagem_atualizar));

                        } else {
                            Mensagem.Msg(PerfilFornecedorActivity.this, getString(R.string.mensagem_avaliacao));
                        }
                    }
                    //finaliza o dialog
                    dialog.dismiss();
                }
            });

        btCancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //finaliza o dialog
                dialog.dismiss();
            }
        });

        //exibe na tela o dialog
        dialog.show();

    }



    private void solicitaOrcamento(){
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.dialog_orcamento_layout);

        //define o título do Dialog
        dialog.setTitle("Solicitar Chamada ou Orçamento:");

        //instancia os objetos que estão no layout customdialog.xml
        final Button btConfirmar = (Button) dialog.findViewById(R.id.btSolicitaEnviar);
        final Button btCancelar = (Button) dialog.findViewById(R.id.btSolicitaCancelar);
        final EditText editMsg = (EditText) dialog.findViewById(R.id.editSolicitaMsg);
        final Spinner spinnerTurno = (Spinner) dialog.findViewById(R.id.spinnerSolicitaTurno);
        loadSpinnerData(spinnerTurno);



        btConfirmar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean validacao = true;
                //etRetorno.setText(editText.getText());
                String mensagem = editMsg.getText().toString();
                String horario = (String) spinnerTurno.getSelectedItem();

                if(mensagem == null || mensagem.equals(""))
                {
                    validacao=false;
                    editMsg.setError(getString(R.string.campo_obrigatorio));

                }
                if (validacao) {
                    ChamadaOrcamento chamadaOrcamento = new ChamadaOrcamento();
                    chamadaOrcamento.setIdCliente(idCliente);
                    chamadaOrcamento.setIdFornecedor(fornecedor.getId());
                    chamadaOrcamento.setMensagem(mensagem);
                    chamadaOrcamento.setHorario(horario);
                    chamadaOrcamento.setStatus("aberto");

                    if (idChamadaOrcamento > 0) {
                        chamadaOrcamento.setId(idChamadaOrcamento);
                    }
                    long resultado = chamadaOrcamentoDAO.salvarChamadaOrcamento(chamadaOrcamento);
                    if (resultado != -1) {
                        if (idChamadaOrcamento > 0) {
                            Mensagem.Msg(PerfilFornecedorActivity.this, getString(R.string.mensagem_atualizar));

                        } else {
                            Mensagem.Msg(PerfilFornecedorActivity.this, getString(R.string.mensagem_cadastro));
                        }
                    }
                    //finaliza o dialog
                    dialog.dismiss();
                }
            }
        });

        btCancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //finaliza o dialog
                dialog.dismiss();
            }
        });

        //exibe na tela o dialog
        dialog.show();

    }
    private void loadSpinnerData(Spinner spn) {
        // database handler
        List<String> turnos = new ArrayList<String>();
        turnos.add("Manhã");
        turnos.add("Tarde");
        turnos.add("Noite");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(PerfilFornecedorActivity.this,
                android.R.layout.simple_spinner_item, turnos);

        ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spn.setAdapter(spinnerArrayAdapter);

    }

    private void createDrawer(){
        //Itens do Drawer
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.menu_principal).withIcon(R.drawable.ic_menu_home);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName(R.string.menu_busca_fornecedores).withIcon(R.drawable.ic_menu_search);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName(R.string.menu_editar_perfil).withIcon(R.drawable.ic_menu_edit);
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(4).withName(R.string.menu_sair).withIcon(R.drawable.ic_menu_exit);


        //Definição do Drawer
        Drawer drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        item2,

                        new SectionDrawerItem().withName(R.string.sessao_minha_conta),//Seção
                        item3,

                        new SectionDrawerItem().withName(R.string.sessao_navegacao),//Seção
                        item4

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        //        textView.setText("Você clicou em: "+ ((Nameable) drawerItem).getName().getText(PrincipalClienteActivity.this));
                        int id = (int) drawerItem.getIdentifier();
                        switch (id)
                        {
                            case 1:
                                Intent intentPrincipal = new Intent(PerfilFornecedorActivity.this, PrincipalClienteActivity.class);
                                intentPrincipal.putExtra("ID_USUARIO_CLIENTE", idCliente);
                                startActivity(intentPrincipal);
                                break;

                            case 2:
                                Intent intentCat = new Intent(PerfilFornecedorActivity.this, BuscaFornecedorActivity.class);
                                intentCat.putExtra("CLIENTE_ID_CAT", idCliente);
                                startActivity(intentCat);
                                break;


                            case 3:
                                Intent intent = new Intent(PerfilFornecedorActivity.this, CadastroClienteActivity.class);
                                intent.putExtra("CLIENTE_ID", idCliente);
                                startActivity(intent);
                                break;

                            case 4:
                                Mensagem.MsgConfirm(PerfilFornecedorActivity.this, "Sair do sistema", "Deseja realmente sair?",
                                        R.drawable.ic_action_about, new DialogInterface.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                SharedPreferences preferences   = getSharedPreferences("LoginActivityPreferences", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = preferences.edit();
                                                editor.clear();
                                                editor.commit();
                                                finish();
                                                startActivity(new Intent(PerfilFornecedorActivity.this, LoginActivity.class));

                                            }
                                        });

                                break;
                        }


                        return false;
                    }
                })
                .withSelectedItemByPosition(0)
                .build();
    }

}

