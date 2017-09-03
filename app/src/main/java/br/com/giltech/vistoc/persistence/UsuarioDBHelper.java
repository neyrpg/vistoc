package br.com.giltech.vistoc.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.giltech.vistoc.entidade.DadoCliente;

/**
 * Created by Gilciney Marques on 22/08/2017.
 */

public class UsuarioDBHelper extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "usuario.db";
    public static final String TOKEN = "TOKEN";
    public static final String TIPO = "TIPO";
    public static final String CONTA = "CONTA";
    public static final String AVATAR = "AVATAR";
    public static final String AGENCIA = "AGENCIA";
    public static final String NOME = "NOME";
    public static String SQL_CREATE_ENTRIES = "CREATE TABLE USUARIO (NOME TEXT PRIMARY KEY, AGENCIA TEXT, CONTA TEXT, TIPO TEXT, AVATAR TEXT, TOKEN TEXT )";
    public static String TABLE_USUARIO = "USUARIO";
    public static String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS usuario";


    public UsuarioDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }


    public boolean insertContact (DadoCliente dadoCliente) {
        if(numberOfRows() == 0) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(NOME, dadoCliente.getNome());
            contentValues.put(AGENCIA, dadoCliente.getAgencia());
            contentValues.put(CONTA, dadoCliente.getConta());
            contentValues.put(TIPO, dadoCliente.getTipo());
            contentValues.put(AVATAR, dadoCliente.getAvatar());
            contentValues.put(TOKEN, dadoCliente.getToken());
            long ret = db.insertOrThrow(TABLE_USUARIO, null, contentValues);
        }
        return true;
    }
    public DadoCliente getUsuario() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+TABLE_USUARIO, null );
        DadoCliente cliente = null;
        if(res.getCount() != 0) {
            res.moveToFirst();
            cliente = new DadoCliente();
            cliente.setNome(res.getString(res.getColumnIndex(NOME)));
            cliente.setAgencia(res.getString(res.getColumnIndex(AGENCIA)));
            cliente.setAvatar(res.getString(res.getColumnIndex(AVATAR)));
            cliente.setConta(res.getString(res.getColumnIndex(CONTA)));
            cliente.setTipo(res.getString(res.getColumnIndex(TIPO)));
            cliente.setToken(res.getString(res.getColumnIndex(TOKEN)));
        }
        return cliente;
    }
    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_USUARIO);
        return numRows;
    }

}
