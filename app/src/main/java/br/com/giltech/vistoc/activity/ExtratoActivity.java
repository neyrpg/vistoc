package br.com.giltech.vistoc.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import br.com.giltech.vistoc.R;
import br.com.giltech.vistoc.adapter.ExtratoAdapter;
import br.com.giltech.vistoc.entidade.DadoCliente;
import br.com.giltech.vistoc.entidade.Extrato;
import br.com.giltech.vistoc.entidade.Resposta;
import br.com.giltech.vistoc.persistence.UsuarioDBHelper;
import br.com.giltech.vistoc.service.VistoCaixaService;
import br.com.giltech.vistoc.util.Constantes;
import br.com.giltech.vistoc.util.PreferencesUtil;

public class ExtratoActivity extends AppCompatActivity {

    private DadoCliente dadoCliente;
    private Extrato extrato;
    private ImageView imagemConta;
    private TextView tv_extrato_nome_cliente;
    private TextView tv_extrato_tipo_conta;
    private TextView tv_extrato_ag_conta;
    private TextView tv_extrato_valor_saldo;
    private ListView lv_extrato;
    private ExtratoAdapter extratoAdapter;
    private ProgressBar bar;

    private Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extrato);

        imagemConta = (ImageView) findViewById(R.id.iv_extrato_imagem_conta);
        tv_extrato_nome_cliente = (TextView) findViewById(R.id.tv_extrato_nome_cliente);
        tv_extrato_tipo_conta = (TextView) findViewById(R.id.tv_extrato_tipo_conta);
        tv_extrato_ag_conta = (TextView) findViewById(R.id.tv_extrato_ag_conta);
        lv_extrato = (ListView) findViewById(R.id.lv_extrato_listview_extrato);
        tv_extrato_valor_saldo = (TextView) findViewById(R.id.tv_extrato_valor_saldo);
        bar = (ProgressBar) findViewById(R.id.pb_login_progress);
        init();

    }

    private void init() {
        carregaValores();
        recuperaExtratoService(Constantes.EXTRATO_5_DIAS);

    }

    public void filtro5(View filtro){
        carregaListView(Constantes.EXTRATO_5_DIAS);
        Drawable selected = getDrawable(R.drawable.botao_gradiente);
        filtro.setBackground(selected);
        Drawable normal = getDrawable(R.drawable.botao_gradiente_default);
        findViewById(R.id.btn_extrato_filtro_15).setBackground(normal);
        findViewById(R.id.btn_extrato_filtro_30).setBackground(normal);
    }
    public void filtro15(View filtro){
        carregaListView(Constantes.EXTRATO_15_DIAS);
        Drawable selected = getDrawable(R.drawable.botao_gradiente);
        filtro.setBackground(selected);
        Drawable normal = getDrawable(R.drawable.botao_gradiente_default);
        findViewById(R.id.btn_extrato_filtro_5).setBackground(normal);
        findViewById(R.id.btn_extrato_filtro_30).setBackground(normal);
    }
    public void filtro30(View filtro){
        carregaListView(Constantes.EXTRATO_30_DIAS);
        Drawable selected = getDrawable(R.drawable.botao_gradiente);
        Drawable normal = getDrawable(R.drawable.botao_gradiente_default);
        filtro.setBackground(selected);
        findViewById(R.id.btn_extrato_filtro_15).setBackground(normal);
        findViewById(R.id.btn_extrato_filtro_5).setBackground(normal);
    }

    private void carregaValores() {
        UsuarioDBHelper dbHelper = new UsuarioDBHelper(this);
        dadoCliente =  dbHelper.getUsuario();

        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.WHITE)
                .borderWidthDp(3)
                .cornerRadiusDp(30)
                .oval(true)
                .build();

        Picasso.with(this)
                .load(dadoCliente.getAvatar())
                .fit()
                .transform(transformation)
                .into(imagemConta);

        tv_extrato_nome_cliente.setText(dadoCliente.getNome());
        tv_extrato_tipo_conta.setText(dadoCliente.getTipo());
        tv_extrato_ag_conta.setText("Ag: "+dadoCliente.getAgencia() + "  Conta: " + dadoCliente.getConta());
    }

    private void recuperaExtratoService(Integer periodo) {

        VistoCaixaService service = new VistoCaixaService() {
            @Override
            protected void onPreExecute() {
                bar.setVisibility(View.VISIBLE);
                Log.i(LoginActivity.class.getName(), "Iniciando Recuperação de extrato");
            }

            @Override
            protected void onPostExecute(Object o) {
                bar.setVisibility(View.INVISIBLE);
                if (o != null) {
                    Resposta resposta = (Resposta) o;
                    if (!resposta.getSucesso()) {
                        Toast.makeText(ctx, resposta.getErro(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    extrato = resposta.getAsExtrato();
                    carregaListView(Constantes.EXTRATO_5_DIAS);
                }

            }
        };
        service.execute(Constantes.SERVICO_EXTRATO, dadoCliente.getToken());


    }

    private void carregaListView(Integer filtro) {
        extratoAdapter = new ExtratoAdapter(ctx, extrato, filtro);
        lv_extrato = (ListView) findViewById(R.id.lv_extrato_listview_extrato);
        lv_extrato.setAdapter(extratoAdapter);
        tv_extrato_valor_saldo.setText(extrato.getSaldo());
    }
}
