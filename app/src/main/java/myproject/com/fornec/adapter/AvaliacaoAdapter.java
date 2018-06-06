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
import myproject.com.fornec.model.Avaliar;

/**
 * Created by Dom on 25/06/2017.
 */

public class AvaliacaoAdapter extends BaseAdapter {

    private Context context;
    private List<Avaliar> lista;

    public AvaliacaoAdapter (Context ctx, List<Avaliar> avaliar)
    {
        this.context = ctx;
        this.lista = avaliar;
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
        Avaliar avaliar = lista.get(position);

        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.avaliacoes, null);
        }

        TextView textViewNome = (TextView) view.findViewById(R.id.textViewListaAvaliacaoNome);
        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBarListaAvaliacao);
        TextView textViewComentario = (TextView) view.findViewById(R.id.textViewListaAvaliacaoMensagem);
        textViewNome.setText(avaliar.getNomeCliente());
        ratingBar.setRating(avaliar.getNota());
        textViewComentario.setText(avaliar.getComentario());
        return view;
    }
}
