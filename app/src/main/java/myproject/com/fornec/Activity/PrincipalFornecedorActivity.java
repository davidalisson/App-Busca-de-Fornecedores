package myproject.com.fornec.Activity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RatingBar;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;

import myproject.com.fornec.R;
import myproject.com.fornec.model.Fornecedor;
import myproject.com.fornec.util.Mensagem;



public class PrincipalFornecedorActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Fornecedor fornecedor;
    private int idFornecedor;
    private FloatingActionButton btSolicitacoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_fornecedor);
        toolbar = (Toolbar) findViewById(R.id.toolbarFornecedor);
        btSolicitacoes = (FloatingActionButton) findViewById(R.id.btSolicitacoes);

        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        //textView = (TextView) findViewById(R.id.textView);

        //fornecedor = new Fornecedor();
        Intent intent = getIntent();
        idFornecedor = (intent.getIntExtra("ID_USUARIO_FORNECEDOR", 0));

        btSolicitacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentChamadaOrcamento = new Intent(PrincipalFornecedorActivity.this, ChamadaOrcamentoActivity.class);
                intentChamadaOrcamento.putExtra("FORNECEDOR_ID_CHAMADA_ORCAMENTO", idFornecedor);
                startActivity(intentChamadaOrcamento);
            }
        });




        createDrawer();
    }
    private void createDrawer(){
        //Itens do Drawer
        PrimaryDrawerItem item1 = new PrimaryDrawerItem()
                .withIdentifier(1).withName(R.string.menu_principal).withIcon(R.drawable.ic_menu_home);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem()
                .withIdentifier(2).withName(R.string.menu_editar_perfil).withIcon(R.drawable.ic_menu_edit);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem()
                .withIdentifier(3).withName(R.string.menu_avaliacoes).withIcon(R.drawable.ic_menu_like);
        PrimaryDrawerItem item4 = new PrimaryDrawerItem()
                .withIdentifier(4).withName(R.string.menu_solicitacao).withIcon(R.drawable.ic_menu_dialog);
        PrimaryDrawerItem item5 = new PrimaryDrawerItem()
                .withIdentifier(5).withName(R.string.menu_sair).withIcon(R.drawable.ic_menu_exit);

        //Definição do Drawer
        Drawer drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1,
                        new SectionDrawerItem().withName(R.string.sessao_minha_conta),//Seção
                        item2,
                        new SectionDrawerItem().withName(R.string.sessao_navegacao),//Seção
                        item4,
                        item3,
                        item5


                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem)
                    {
                        //        textView.setText("Você clicou em: "+ ((Nameable) drawerItem).getName().getText(PrincipalClienteActivity.this));
                        int id = (int) drawerItem.getIdentifier();
                        switch (id)
                        {
                            case 3:
                                Intent intentAvaliar = new Intent(PrincipalFornecedorActivity.this, AvaliacaoActivity.class);
                                intentAvaliar.putExtra("FORNECEDOR_ID_CHAMADA_ORCAMENTO", idFornecedor);
                                startActivity(intentAvaliar);
                                break;

                            case 4:
                                Intent intentChamadaOrcamento = new Intent(PrincipalFornecedorActivity.this, ChamadaOrcamentoActivity.class);
                                intentChamadaOrcamento.putExtra("FORNECEDOR_ID_CHAMADA_ORCAMENTO", idFornecedor);
                                startActivity(intentChamadaOrcamento);
                                break;

                            case 5:
                                Mensagem.MsgConfirm(PrincipalFornecedorActivity.this, "Sair do sistema", "Deseja realmente sair?", R.drawable.ic_action_about, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SharedPreferences preferences   = getSharedPreferences("LoginActivityPreferences", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.clear();
                                        editor.commit();
                                        finish();
                                        startActivity(new Intent(PrincipalFornecedorActivity.this, LoginActivity.class));

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
