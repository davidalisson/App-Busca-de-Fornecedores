package myproject.com.fornec.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import myproject.com.fornec.R;
import myproject.com.fornec.model.Fornecedor;

/**
 * Created by Dom on 16/06/2017.
 */

public class FornecedorAdapter extends BaseAdapter {

    private Context context;
    private List<Fornecedor> lista;

    public FornecedorAdapter (Context ctx, List<Fornecedor> fornecedors)
    {
        this.context = ctx;
        this.lista = fornecedors;
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
        Fornecedor fornecedor = lista.get(position);

        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.fornecedores, null);
        }

        TextView textViewNome = (TextView) view.findViewById(R.id.textViewFornecedoresListaNome);
        textViewNome.setText(fornecedor.getNome());
        TextView textViewCidade = (TextView) view.findViewById(R.id.textViewFornecedoresListaCidade);
        textViewCidade.setText(fornecedor.getCidade());
        RatingBar ratingAvalia = (RatingBar) view.findViewById(R.id.ratingBarListaFornec);
        ratingAvalia.setRating(fornecedor.getMedia());

        return view;

    }
}
