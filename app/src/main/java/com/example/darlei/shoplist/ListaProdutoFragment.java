package com.example.darlei.shoplist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.darlei.shoplist.adapter.ProdutosAdapter;
import com.example.darlei.shoplist.db.DBHelper;
import com.example.darlei.shoplist.model.Produto;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Darlei on 26/09/2015.
 */
public class ListaProdutoFragment extends android.support.v4.app.Fragment{
    private ListView lstCompras;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.aba_lista_produtos, container, false);

        //Alimentando o ListView de compras com dados da base
        lstCompras = (ListView)rootView.findViewById(R.id.lstCompras);
        DBHelper dbh = new DBHelper(getActivity());
        dbh.getReadableDatabase();
        try {
            Dao<Produto, Integer> daoProduto = dbh.getDao(Produto.class);
            List<Produto> produtos = daoProduto.queryForAll();
            //cria um adaptador entre a conexão e o componente visual, funciona como um DataSource
            //passa o layout criado linha_produto
            ProdutosAdapter aProdutos = new ProdutosAdapter(getActivity(), R.layout.linha_produto,produtos);
            lstCompras.setAdapter(aProdutos);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rootView;
    }
}
