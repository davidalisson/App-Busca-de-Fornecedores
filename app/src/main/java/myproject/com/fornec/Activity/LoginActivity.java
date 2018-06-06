package myproject.com.fornec.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import myproject.com.fornec.R;
import myproject.com.fornec.dao.ClienteDAO;
import myproject.com.fornec.dao.FornecedorDAO;
import myproject.com.fornec.dao.LoginDAO;
import myproject.com.fornec.model.Cliente;
import myproject.com.fornec.model.Fornecedor;
import myproject.com.fornec.util.Mensagem;

public class LoginActivity extends AppCompatActivity {
    private TextView textSemCadastro;
    private EditText editEmail, editSenha;
    private Button btLogar;
    private LoginDAO helper;
    private CheckBox checkCon;
    private static final String MANTER_CONECTADO = "manter_conectado";
        private static final String PREFERENCES_NAME = "LoginActivityPreferences";
    private Fornecedor fornecedor;
    private Cliente cliente;
    private FornecedorDAO fornecedorDAO;
    private ClienteDAO clienteDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fornecedor = new Fornecedor();
        cliente = new Cliente();
        fornecedorDAO = new FornecedorDAO(this);
        clienteDAO = new ClienteDAO(this);

        editEmail = (EditText) findViewById(R.id.editLoginEmail);
        editSenha = (EditText) findViewById(R.id.editLoginSenha);
        btLogar = (Button) findViewById(R.id.btLogar);
        textSemCadastro = (TextView) findViewById(R.id.textViewLoginSemCadastro);
        checkCon = (CheckBox) findViewById(R.id.checkManterConectado);
        helper = new LoginDAO(this);

        SharedPreferences preferences   = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
        boolean conectado               = preferences.getBoolean(MANTER_CONECTADO, false);

        if(conectado)
        {

            chamarActivityCliente();
        }

        textSemCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, OpcaoCadastroActivity.class);
                startActivity(intent);
            }
        });

        btLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //helper.verificaClientes(LoginActivity.this);
                String email = editEmail.getText().toString();
                String senha = editSenha.getText().toString();
                boolean validacao = true;

                if (email == null || email.equals(""))
                {
                    validacao = false;
                    editEmail.setError(getString(R.string.login_valUsuario));
                }
                if (senha == null || senha.equals(""))
                {
                    validacao = false;
                    editSenha.setError(getString(R.string.login_valSenha));
                }
                if(validacao)
                {

                    if(helper.logar(email, senha))
                    {
                        if(checkCon.isChecked())
                        {
                            SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putBoolean(MANTER_CONECTADO, true);
                            editor.commit();
                        }

                        if(helper.verificaTipo(email, LoginActivity.this)==1)
                        {
                            //String tipo = "cliente";
                            //Intent intent = new Intent(LoginActivity.this, PrincipalClienteActivity.class);
                            //intent.putExtra("usuario", tipo);

                            cliente = helper.getCliente();
                            Intent intent = new Intent(LoginActivity.this, PrincipalClienteActivity.class);
                            //Intent intentClienteChamada = new Intent(LoginActivity.this, PerfilFornecedorActivity.class);
                            //intent.putExtra("USUARIO_LOGADO_CLIENTE",cliente);
                            intent.putExtra("ID_USUARIO_CLIENTE", cliente.getId());
                            //intentClienteChamada.putExtra("ID_CLIENTE_LOGADO", cliente.getId());
                            startActivity(intent);
                            finish();
                            //chamarActivityCliente();

                        }else if (helper.verificaTipo(email, LoginActivity.this)==2)
                        {
                            fornecedor = helper.getFornecedor();
                            Intent intent = new Intent(LoginActivity.this, PrincipalFornecedorActivity.class);
                            //intent.putExtra("USUARIO_LOGADO_FORNECEDOR",fornecedor);
                            intent.putExtra("ID_USUARIO_FORNECEDOR", fornecedor.getId());
                            startActivity(intent);
                            finish();
                            //String tipo = "fornecedor";
                            //Intent intent = new Intent(LoginActivity.this, PrincipalFornecedorActivity.class);
                            //intent.putExtra("usuario", tipo);
                            //chamarActivityFornecedor();

                        }

                    }else
                    {
                        Mensagem.Msg(LoginActivity.this, getString(R.string.msg_login_incorreto));
                    }
                }


            }
        });

    }
    private void chamarActivityCliente()
    {

        startActivity(new Intent(LoginActivity.this, PrincipalClienteActivity.class));
        finish();
    }
    private void chamarActivityFornecedor()
    {

        finish();
    }
}
