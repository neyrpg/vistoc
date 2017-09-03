package br.com.giltech.vistoc.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import br.com.giltech.vistoc.R;
import br.com.giltech.vistoc.entidade.Resposta;
import br.com.giltech.vistoc.entidade.Usuario;
import br.com.giltech.vistoc.persistence.UsuarioDBHelper;
import br.com.giltech.vistoc.service.VistoCaixaService;
import br.com.giltech.vistoc.util.Constantes;

public class LoginActivity extends AppCompatActivity {

    private EditText etLoginUsuario;
    private EditText etLoginSenha;
    private Button btnLoginAcessar;
    private ImageView iv_logo_caixa;
    private ProgressBar bar;
    private Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checaAcesso();
        etLoginUsuario = (EditText) findViewById(R.id.et_login_usuario);
        etLoginSenha = (EditText) findViewById(R.id.et_login_senha);
        btnLoginAcessar = (Button) findViewById(R.id.btn_login_acessar);
        bar = (ProgressBar) findViewById(R.id.pb_login_progress);
        iv_logo_caixa = (ImageView) findViewById(R.id.iv_login_logo);


        Picasso.with(this)
                .load(R.drawable.caixa_logo)
                .fit()
                .centerInside()
                .into(iv_logo_caixa);

        btnLoginAcessar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acessar(etLoginUsuario.getText().toString(), etLoginSenha.getText().toString());
            }
        });

    }


    private void acessar(String usuario, String senha) {
        try {
            Log.i(LoginActivity.class.getName(), usuario + senha);

            VistoCaixaService service = new VistoCaixaService() {
                @Override
                protected void onPreExecute() {
                    bar.setVisibility(View.VISIBLE);
                    Log.i(LoginActivity.class.getName(), "Iniciando autenticação");
                }

                @Override
                protected void onPostExecute(Object o) {
                    bar.setVisibility(View.INVISIBLE);
                    if (o == null) {
                        Toast.makeText(ctx, "Erro interno", Toast.LENGTH_LONG).show();
                    }
                    Resposta resposta = (Resposta) o;
                    if (!resposta.getSucesso()) {
                        Toast.makeText(ctx, resposta.getErro(), Toast.LENGTH_LONG).show();
                    } else {
                        //PreferencesUtil.getInstance().write(resposta.getAsData(), ctx);
                        UsuarioDBHelper dbHelper = new UsuarioDBHelper(ctx);
                        dbHelper.insertContact(resposta.getAsData());
                        redirExtrato();
                    }
                }
            };
            service.execute(Constantes.SERVICO_AUTENTICACAO, new Usuario(usuario, senha));


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro de conexão ao serviço", Toast.LENGTH_LONG).show();
        }

    }

    private void checaAcesso() {
        UsuarioDBHelper dbHelper = new UsuarioDBHelper(this);
        if(dbHelper.numberOfRows() > 0){
            redirExtrato();
        }
    }

    private void redirExtrato() {
        Intent i = new Intent(ctx, ExtratoActivity.class);
        startActivity(i);
    }
}
