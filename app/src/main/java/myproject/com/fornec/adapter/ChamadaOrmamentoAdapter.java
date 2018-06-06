package myproject.com.fornec.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import myproject.com.fornec.R;
import myproject.com.fornec.dao.DatabaseHelper;
import myproject.com.fornec.model.ChamadaOrcamento;

/**
 * Created by Dom on 20/06/2017.
 */

public class ChamadaOrmamentoAdapter extends BaseAdapter {


    private Context context;
    private List<ChamadaOrcamento> lista;

    public ChamadaOrmamentoAdapter (Context ctx, List<ChamadaOrcamento> chamadaOrcamentos)
    {
        this.context = ctx;
        this.lista = chamadaOrcamentos;
    }
    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ChamadaOrcamento chamadaOrcamento = lista.get(position);

        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.chamada_orcamento, null);
        }

        TextView textViewNome = (TextView) view.findViewById(R.id.textViewChamadaOrcamentoListaNome);
        textViewNome.setText(chamadaOrcamento.getMensagem());

        return view;
    }
}
