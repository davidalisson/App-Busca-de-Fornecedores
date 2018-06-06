package myproject.com.fornec.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import myproject.com.fornec.R;


public class OpcaoCadastroActivity extends AppCompatActivity {

    private Button cadCliente;
    private Button cadFornec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcao_cadastro);
        cadCliente = (Button) findViewById(R.id.btOpcaoCadastroCliente);
        cadFornec = (Button) findViewById(R.id.btOpcaoCadastroFornecedor);

        cadCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OpcaoCadastroActivity.this, CadastroClienteActivity.class);
                startActivity(intent);
            }
        });
        cadFornec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OpcaoCadastroActivity.this, CadastroFornecedorActivity.class);
                startActivity(intent);
            }
        });
    }
}
