package com.parse.starter.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.parse.starter.R;
import com.parse.starter.util.ParseErros;

import org.w3c.dom.Text;

public class CadastroActivity extends AppCompatActivity {

    private EditText textoUsuario;
    private EditText textoEmail;
    private EditText textoSenha;
    private Button botaoCadastrar;
    private TextView facaLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        textoUsuario = (EditText) findViewById(R.id.text_usuario);
        textoEmail = (EditText) findViewById(R.id.text_email);
        textoSenha = (EditText) findViewById(R.id.text_senha);
        facaLogin = (TextView) findViewById(R.id.text_faca_login);
        botaoCadastrar = (Button)findViewById(R.id.button_cadastrar);

        facaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirLoginUsuario();
            }
        });

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrarUsuario();
            }
        });
    }

    private void cadastrarUsuario(){
        //Cria o objeto usuário

        ParseUser usuario = new ParseUser();
        usuario.setUsername(textoUsuario.getText().toString() );
        usuario.setEmail(textoEmail.getText().toString() );
        usuario.setPassword(textoSenha.getText().toString());

        //salvar o usuario

        usuario.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {

                if ( e == null){ //Salvou!
                    Toast.makeText(CadastroActivity.this, "Cadastro efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                    abrirLoginUsuario();


                }else{ //Nao salvou!
                    //Recuperar erros!
                    ParseErros parseErros = new ParseErros();
                    String erro = parseErros.getErro( e.getCode() );
                    //------------------------------------------
                    Toast.makeText(CadastroActivity.this, erro , Toast.LENGTH_SHORT).show();

                }
            }
        });



    }

    // Redirecionar usuario para a tela de login

    private void abrirLoginUsuario(){
        Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


}
