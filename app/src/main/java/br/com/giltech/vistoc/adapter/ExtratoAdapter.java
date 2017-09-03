package br.com.giltech.vistoc.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.giltech.vistoc.R;
import br.com.giltech.vistoc.entidade.Extrato;
import br.com.giltech.vistoc.entidade.Operacao;
import br.com.giltech.vistoc.util.UtilDate;


/**
 * Created by Gilciney Marques on 21/08/2017.
 */
public class ExtratoAdapter extends BaseAdapter {

    private Extrato extrato;
    private List<Operacao> operacoes;
    private Context ctx;
    private LayoutInflater infalInflater;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public ExtratoAdapter(Context ctx, Extrato extrato, Integer filtro) {
        this.extrato = extrato;
        this.ctx = ctx;
        infalInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        operacoes = new ArrayList<>();
        filtrarOperacoes(filtro);
    }

    private void filtrarOperacoes(Integer filtro) {
        for (Operacao op : extrato.getOperacoes()) {
            if (UtilDate.verificaFiltro(op.getData(), filtro)) {
                operacoes.add(op);
            }
        }

    }


    @Override
    public int getCount() {
        return operacoes.size();
    }

    @Override
    public Object getItem(int i) {
        return operacoes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        View view = infalInflater.inflate(R.layout.list_view_layout_custom, null);
        Operacao operacao = operacoes.get(i);
        TextView tv_extrato_list_view_dt_operacao = view.findViewById(R.id.tv_extrato_list_view_dt_operacao);
        TextView tv_extrato_list_view_descricao_operacao = view.findViewById(R.id.tv_extrato_list_view_descricao_operacao);
        TextView tv_extrato_list_view_valor_operacao = view.findViewById(R.id.tv_extrato_list_view_valor_operacao);

        tv_extrato_list_view_dt_operacao.setText(sdf.format(operacao.getData()));
        tv_extrato_list_view_descricao_operacao.setText(operacao.getDescricao());
        tv_extrato_list_view_valor_operacao.setText(operacao.getValor());

        if (operacao.getTipo().equals("D")) {
            tv_extrato_list_view_valor_operacao.setTextColor(Color.RED);
        } else {
            tv_extrato_list_view_valor_operacao.setTextColor(Color.BLUE);
        }
        return view;
    }


}
