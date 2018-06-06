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
import myproject.com.fornec.dao.FornecedorDAO;
import myproject.com.fornec.model.Fornecedor;
import myproject.com.fornec.util.Mensagem;

public class CadastroFornecedorActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    private EditText editNome;
    private EditText editEmail;
    private EditText editSenha;
    private EditText editConfirmSenha;
    private EditText editEstado;
    private EditText editCidade;
    private EditText editBairro;
    private EditText editTelefone;
    private EditText editCnpj;
    private Spinner spinnerCat;
    private EditText editProdutos;
    private FornecedorDAO fornecedorDAO;
    private CategoriaDAO categoriaDAO;
    private Fornecedor fornecedor;
    private int idFornecedor;
    private int idCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_fornecedor);

        fornecedorDAO = new FornecedorDAO(this);
        categoriaDAO = new CategoriaDAO(this);


        editNome = (EditText) findViewById(R.id.editCadFornecNome);
        editEmail = (EditText) findViewById(R.id.editCadFornecEmail);
        editSenha = (EditText) findViewById(R.id.editCadFornecSenha);
        editConfirmSenha = (EditText) findViewById(R.id.editCadFornecConfirmSenha);
        editEstado = (EditText) findViewById(R.id.editCadFornecEstado);
        editCidade = (EditText) findViewById(R.id.editCadFornecCidade);
        editBairro = (EditText) findViewById(R.id.editCadFornecBairro);
        editTelefone = (EditText) findViewById(R.id.editCadFornecTelefone);
        editCnpj = (EditText) findViewById(R.id.editCadFornecCnpj);
        spinnerCat = (Spinner) findViewById(R.id.spinnerCadFornec);
        editProdutos = (EditText) findViewById(R.id.editCadFornecProtutos);

        spinnerCat.setOnItemSelectedListener(this);

        loadSpinnerData();


    }
    private void loadSpinnerData() {
        // database handler


        // Spinner Drop down elements
        List<String> lables = categoriaDAO.listarCategorias();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(CadastroFornecedorActivity.this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerCat.setAdapter(dataAdapter);
    }

    @Override
    protected void onDestroy() {
        fornecedorDAO.fechar();
        super.onDestroy();
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String label = parent.getItemAtPosition(position).toString();
        long label1 = parent.getSelectedItemId();
        //Mensagem.Msg(CadastroClienteActivity.this, "posicao do item: "+label);

        idCategoria = (int) label1;
        // Mensagem.Msg(CadastroClienteActivity.this, "posicao do item: "+idCatInteresse);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void cadastrar() {
        boolean validacao = true;
        String nome = editNome.getText().toString();
        String email = editEmail.getText().toString();
        String senha = editSenha.getText().toString();
        String confirmSenha = editConfirmSenha.getText().toString();
        String estado = editEstado.getText().toString();
        String cidade = editCidade.getText().toString();
        String bairro = editBairro.getText().toString();
        String telefone = editTelefone.getText().toString();
        String cnpj = editCnpj.getText().toString();
        int idCategoriaCad = idCategoria;
        String produtos = editProdutos.getText().toString();


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
        if (cnpj == null || cnpj.equals("")) {
            validacao = false;
            editCnpj.setError(getString(R.string.campo_obrigatorio));
        }
        if (produtos == null || produtos.equals("")) {
            validacao = false;
            editProdutos.setError(getString(R.string.campo_obrigatorio));
        }
        if(fornecedorDAO.verificaEmail(email))
        {
            validacao = false;
            editEmail.setError(getString(R.string.mensagem_erro_email_cadastrado));
        }
        if (senha.equals(confirmSenha)) {
            if (validacao) {

                fornecedor = new Fornecedor();
                fornecedor.setNome(nome);
                fornecedor.setEmail(email);
                fornecedor.setSenha(senha);
                fornecedor.setEstado(estado);
                fornecedor.setCidade(cidade);
                fornecedor.setBairro(bairro);
                fornecedor.setTelefone(telefone);
                fornecedor.setCnpj(cnpj);
                fornecedor.setIdCategoria(idCategoriaCad + 1);
                fornecedor.setProdutos(produtos);
                fornecedor.setTipo(2);
                if (idFornecedor > 0) {
                    fornecedor.setId(idFornecedor);
                }
                long resultado = fornecedorDAO.salvarFornecedor(fornecedor);
                if (resultado != -1) {
                    if (idFornecedor > 0) {
                        Mensagem.Msg(this, getString(R.string.mensagem_atualizar));
                    } else {
                        Mensagem.Msg(this, getString(R.string.mensagem_cadastro));
                    }
                    finish();
                    startActivity(new Intent(this, PrincipalFornecedorActivity.class));
                } else {
                    Mensagem.Msg(this, getString(R.string.mensagem_erro));
                }

            }

        } else {
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
