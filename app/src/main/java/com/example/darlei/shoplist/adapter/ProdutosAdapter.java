package com.example.darlei.shoplist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.darlei.shoplist.R;
import com.example.darlei.shoplist.model.Produto;

import java.util.List;

/**
 * Created by Darlei on 31/10/2015.
 */
public class ProdutosAdapter extends ArrayAdapter<Produto>{


    public ProdutosAdapter(Context context, int resource, List<Produto> objects) {
        super(context, resource, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View v = convertView;

        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.linha_produto, null);
        }

        Produto p = getItem(position);

        if (p != null){
            TextView txtDescricao = (TextView) v.findViewById(R.id.txtDescricaoLP);
            TextView txtQuantidade = (TextView) v.findViewById(R.id.txtQuantidade);

            txtDescricao.setText(p.getDescricao());
            txtQuantidade.setText(p.getQuantidade().toString() + "  " + p.getUnidadeMedida());
        }
        return v;
    }
}
