package br.com.giltech.vistoc.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import br.com.giltech.vistoc.entidade.DadoCliente;

/**
 * Created by Gilciney Marques on 21/08/2017.
 */

public class PreferencesUtil {

    public static PreferencesUtil getInstance(){
        return new PreferencesUtil();
    }

    public void write(DadoCliente dadoCliente, Context ctx){
        SharedPreferences prefs = ctx.getSharedPreferences(Constantes.STORAGE_PREFERENCES, ctx.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String dataJson = gson.toJson(dadoCliente);
        editor.putString(Constantes.STORAGE_USER_DATA, dataJson);
        editor.commit();
    }


    public DadoCliente recuperaData(Context ctx){
        DadoCliente dadoCliente = new DadoCliente();
        SharedPreferences prefs = ctx.getSharedPreferences(Constantes.STORAGE_PREFERENCES, ctx.MODE_PRIVATE);
        if (prefs.contains(Constantes.STORAGE_USER_DATA)) {
            Gson gson = new Gson();

            String dataJson = prefs.getString(Constantes.STORAGE_USER_DATA,"");
            dadoCliente = gson.fromJson(dataJson, DadoCliente.class);

        }
        return dadoCliente;
    }

    public boolean hasUserData(Context ctx){
        SharedPreferences prefs = ctx.getSharedPreferences(Constantes.STORAGE_PREFERENCES, ctx.MODE_PRIVATE);
        return prefs.contains(Constantes.STORAGE_USER_DATA);
    }
}
