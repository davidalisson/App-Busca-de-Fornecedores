package myproject.com.fornec.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import myproject.com.fornec.R;
import myproject.com.fornec.dao.CategoriaDAO;
import myproject.com.fornec.dao.ClienteDAO;
import myproject.com.fornec.model.Categoria;
import myproject.com.fornec.model.Cliente;
import myproject.com.fornec.util.Mensagem;

public class CadastroClienteActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {
    private EditText editNome;
    private EditText editEmail;
    private EditText editSenha;
    private EditText editConfirmSenha;
    private EditText editEstado;
    private EditText editCidade;
    private EditText editBairro;
    private EditText editTelefone;
    private Spinner spinnerCat;
    private ClienteDAO clienteDAO;
    private CategoriaDAO categoriaDAO;
    private Cliente cliente;
    private int idCliente;
    private int idCatInteresse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);

        clienteDAO = new ClienteDAO(this);
        categoriaDAO = new CategoriaDAO(this);


        editNome = (EditText) findViewById(R.id.editCadClienNome);
        editEmail = (EditText) findViewById(R.id.editCadClienEmail);
        editSenha = (EditText) findViewById(R.id.editCadClienSenha);
        editConfirmSenha = (EditText) findViewById(R.id.editCadClienConfirmSenha);
        editEstado = (EditText) findViewById(R.id.editCadClienEstado);
        editCidade = (EditText) findViewById(R.id.editCadClienCidade);
        editBairro = (EditText) findViewById(R.id.editCadClienBairro);
        editTelefone = (EditText) findViewById(R.id.editCadClienTelefone);
        spinnerCat = (Spinner) findViewById(R.id.spinnerCadCliente);

        spinnerCat.setOnItemSelectedListener(this);

       // ArrayAdapter<Categoria> adapter = new ArrayAdapter<Categoria>(this, android.R.layout.simple_spinner_dropdown_item, categoriaDAO.getCategorias());
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       // spinnerCat = new Spinner(this);
       // LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //spinnerCat.setLayoutParams(lp);
        //spinnerCat.setAdapter(adapter);
        //spinnerCat.setAdapter(new CategoriaAdapter(this), categoriaDAO.listarCategorias());
        loadSpinnerData();

        //Editar Cliente
        idCliente = getIntent().getIntExtra("CLIENTE_ID", 0);

        if(idCliente>0)
        {
            Cliente model = clienteDAO.buscarCliente(idCliente);
            editNome.setText(model.getNome());
            editEmail.setText(model.getEmail());
            editSenha.setText(model.getSenha());
            editEstado.setText(model.getEstado());
            editCidade.setText(model.getCidade());
            editBairro.setText(model.getBairro());
            editTelefone.setText(model.getTelefone());
            editBairro.setText(model.getBairro());
            spinnerCat.setSelection(model.getIdCategoriaInteresse()-1);
            setTitle("Editar dados");
        }

    }

    private void loadSpinnerData() {
        // database handler


        // Spinner Drop down elements
        List<String> lables = categoriaDAO.listarCategorias();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(CadastroClienteActivity.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerCat.setAdapter(dataAdapter);
    }

    @Override
    protected void onDestroy() {
        clienteDAO.fechar();
        super.onDestroy();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String label = parent.getItemAtPosition(position).toString();
        long label1 = parent.getSelectedItemId();
        //Mensagem.Msg(CadastroClienteActivity.this, "posicao do item: "+label);

        idCatInteresse = (int)label1;
       // Mensagem.Msg(CadastroClienteActivity.this, "posicao do item: "+idCatInteresse);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void cadastrar()
    {
        boolean validacao = true;
        String nome = editNome.getText().toString();
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();
        String confirmSenha = editConfirmSenha.getText().toString();
        String estado = editEstado.getText().toString();
        String cidade = editCidade.getText().toString();
        String bairro = editBairro.getText().toString();
        String telefone = editTelefone.getText().toString();
        int  idCatInteresse = this.idCatInteresse;


            if (nome == null || nome.equals("")) {
                validacao = false;
                editNome.setError(getString(R.string.campo_obrigatorio));
            }
            if (email == null || email.equals("")) {
                validacao = false;
                editEmail.setError(getString(R.string.campo_obrigatorio));
            }
            if (senha == null || senha.equals("")) {
                validacao = false;
                editSenha.setError(getString(R.string.campo_obrigatorio));
            }
            if (confirmSenha == null || confirmSenha.equals("")) {
                validacao = false;
                editConfirmSenha.setError(getString(R.string.campo_obrigatorio));
            }
            if (estado == null || estado.equals("")) {
                validacao = false;
                editEstado.setError(getString(R.string.campo_obrigatorio));
            }
            if (cidade == null || cidade.equals("")) {
                validacao = false;
                editCidade.setError(getString(R.string.campo_obrigatorio));
            }
            if (bairro == null || bairro.equals("")) {
                validacao = false;
                editBairro.setError(getString(R.string.campo_obrigatorio));
            }
            if (telefone == null || telefone.equals("")) {
                validacao = false;
                editTelefone.setError(getString(R.string.campo_obrigatorio));
            }
            if(clienteDAO.verificaEmail(email))
            {
                validacao = false;
                editEmail.setError(getString(R.string.mensagem_erro_email_cadastrado));
            }
        if (senha.equals(confirmSenha)) {
            if(validacao) {

                    cliente = new Cliente();
                    cliente.setNome(nome);
                    cliente.setEmail(email);
                    cliente.setSenha(senha);
                    cliente.setEstado(estado);
                    cliente.setCidade(cidade);
                    cliente.setBairro(bairro);
                    cliente.setTelefone(telefone);
                    cliente.setIdCategoriaInteresse(idCatInteresse+1);
                    cliente.setTipo(1);
                    if (idCliente > 0)
                    {
                        cliente.setId(idCliente);
                    }
                    long resultado = clienteDAO.salvarCliente(cliente);
                    if(resultado!=-1) {
                        if (idCliente > 0) {
                            Mensagem.Msg(this, getString(R.string.mensagem_atualizar));

                        } else {
                            Mensagem.Msg(this, getString(R.string.mensagem_cadastro));
                        }
                        finish();
                        Intent intent = new Intent(this, LoginActivity.class);
                        //intent.putExtra("ID_USUARIO_CLIENTE", idCliente);
                        startActivity(intent);
                        finish();
                    }else
                    {
                        Mensagem.Msg(this, getString(R.string.mensagem_erro));
                    }

                }

            }else {
                editSenha.setError(getString(R.string.mensagem_senhas_diferentes));
                editConfirmSenha.setError(getString(R.string.mensagem_senhas_diferentes));
            }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cadastro, menu);



        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.action_menu_salvar:
                this.cadastrar();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}


