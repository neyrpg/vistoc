package br.com.giltech.vistoc.service;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.internal.LinkedTreeMap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.giltech.vistoc.entidade.DadoCliente;
import br.com.giltech.vistoc.entidade.Extrato;
import br.com.giltech.vistoc.entidade.Operacao;
import br.com.giltech.vistoc.entidade.Resposta;
import br.com.giltech.vistoc.entidade.Usuario;
import br.com.giltech.vistoc.util.Constantes;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gilciney Marques on 21/08/2017.
 */

public class VistoCaixaService extends AsyncTask {

    private Retrofit retrofit;

    public VistoCaixaService() {

        retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    @Override
    protected Object doInBackground(Object[] objects) {

        switch (objects[0].toString()){
            case Constantes.SERVICO_AUTENTICACAO : {
                return realizarAcesso((Usuario)objects[1]);
            }
            case Constantes.SERVICO_EXTRATO : {
                return recuperaExtrato(objects[1].toString());
            }
        }
        return null;
    }


    private Resposta realizarAcesso(Usuario usuario) {
        VistoMethods vistoService = retrofit.create(VistoMethods.class);
        Call<Resposta> call = vistoService.acessar(usuario);
        try {
            Resposta resposta = call.execute().body();
            if (resposta.getSucesso()) {
                DadoCliente dadoCliente = new DadoCliente();
                LinkedTreeMap<String, String> map = (LinkedTreeMap<String, String>) resposta.getData();
                dadoCliente.setNome(map.get("nome"));
                dadoCliente.setAgencia(map.get("agencia"));
                dadoCliente.setConta(map.get("conta"));
                dadoCliente.setTipo(map.get("tipo"));
                dadoCliente.setAvatar(map.get("avatar"));
                dadoCliente.setToken(map.get("token"));
                resposta.setData(dadoCliente);
            }
            return resposta;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(VistoCaixaService.class.getName(), e.getMessage());
            Resposta resposta = new Resposta();
            resposta.setSucesso(false);
            resposta.setErro("Não foi possível obter conexão com o serviço.");
            return resposta;
        }

    }


    private Resposta recuperaExtrato(String token) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            VistoMethods vistoService = retrofit.create(VistoMethods.class);
            Call<Resposta> call = vistoService.getExtrato(token);
            Resposta resposta = call.execute().body();
            List<LinkedTreeMap<String,String>> operacoes;
            Extrato extrato = new Extrato();
            if(resposta.getSucesso()) {
                List<Operacao> operacoesList = new ArrayList<Operacao>();
                LinkedTreeMap<String, Object> map = (LinkedTreeMap<String, Object>) resposta.getData();
                extrato.setSaldo((String)map.get("saldo"));
                operacoes =  ( List<LinkedTreeMap<String,String>>) map.get("operacoes");
                for (LinkedTreeMap<String,String> tree : operacoes){
                    Operacao op = new Operacao();
                    op.setTipo(tree.get("tipo"));
                    op.setData(dateFormat.parse(tree.get("data")));
                    op.setDescricao(tree.get("descricao"));
                    op.setValor(tree.get("valor"));
                    operacoesList.add(op);
                }
                extrato.setOperacoes(operacoesList);
                resposta.setData(extrato);
            }
            return resposta;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(VistoCaixaService.class.getName(), e.getMessage());
            Resposta resposta = new Resposta();
            resposta.setSucesso(false);
            resposta.setErro("Não foi possível obter conexão com o serviço.");
            return resposta;
        }
    }

}
