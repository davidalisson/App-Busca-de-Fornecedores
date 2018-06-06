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
import myproject.com.fornec.model.Categoria;

/**
 * Created by Dom on 16/06/2017.
 */

public class CategoriaAdapter extends BaseAdapter {

    private Context context;
    private List<Categoria> lista;

    public CategoriaAdapter (Context ctx, List<Categoria> categorias)
    {
        this.context = ctx;
        this.lista = categorias;
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
        Categoria categoria = lista.get(position);

        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.categorias, null);
        }

        TextView textViewNome = (TextView) view.findViewById(R.id.textViewCategoriasListaNome);
        textViewNome.setText(categoria.getNome());

        return view;

    }
}
