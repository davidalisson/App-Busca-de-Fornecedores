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
import android.widget.Button;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
//import com.mikepenz.materialdrawer.model.interfaces.Nameable;

import myproject.com.fornec.R;
import myproject.com.fornec.dao.ClienteDAO;
import myproject.com.fornec.model.Cliente;
import myproject.com.fornec.util.Mensagem;

public class PrincipalClienteActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private ClienteDAO clienteDAO;
    private Cliente cliente;
    int idCliente;
    private FloatingActionButton btBuscaFornecedor;
    //private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_cliente);
        btBuscaFornecedor = (FloatingActionButton) findViewById(R.id.btBuscarFornecedor);
        toolbar = (Toolbar) findViewById(R.id.toolbarCliente);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        //textView = (TextView) findViewById(R.id.textView);

        createDrawer();


        cliente = new Cliente();
        Intent intent = getIntent();
        //cliente =(Cliente) intent.getSerializableExtra("USUARIO_LOGADO_CLIENTE");
        //cliente.setId( intent.getIntExtra("ID_USUARIO_CLIENTE", 0));
        idCliente = (intent.getIntExtra("ID_USUARIO_CLIENTE", 0));


        btBuscaFornecedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCat = new Intent(PrincipalClienteActivity.this, BuscaFornecedorActivity.class);
                intentCat.putExtra("CLIENTE_ID_CAT", idCliente);
                startActivity(intentCat);
            }
        });

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
                            case 2:
                                Intent intentCat = new Intent(PrincipalClienteActivity.this, BuscaFornecedorActivity.class);
                                intentCat.putExtra("CLIENTE_ID_CAT", idCliente);
                                startActivity(intentCat);
                                break;


                            case 3:
                                Intent intent = new Intent(PrincipalClienteActivity.this, CadastroClienteActivity.class);
                                intent.putExtra("CLIENTE_ID", idCliente);
                                startActivity(intent);
                                break;

                             case 4:
                                     Mensagem.MsgConfirm(PrincipalClienteActivity.this, "Sair do sistema", "Deseja realmente sair?",
                                             R.drawable.ic_action_about, new DialogInterface.OnClickListener()
                                             {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                 SharedPreferences preferences   = getSharedPreferences("LoginActivityPreferences", Context.MODE_PRIVATE);
                                                 SharedPreferences.Editor editor = preferences.edit();
                                                 editor.clear();
                                                 editor.commit();
                                                 finish();
                                                 startActivity(new Intent(PrincipalClienteActivity.this, LoginActivity.class));

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
