package com.example.darlei.shoplist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.darlei.shoplist.db.DBHelper;
import com.example.darlei.shoplist.model.Produto;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

/**
 * Created by Darlei on 26/09/2015.
 */
public class CadastroProdutoFragment extends android.support.v4.app.Fragment
implements View.OnClickListener {

    private Button btnSalvar;
    private Button btnCancelar;
    private EditText edtDescricao;
    private EditText edtQuantidade;
    private Spinner spnUnidadeMedida;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.aba_cadastro_produto, container, false);

        //vinculando os elementos visuais aos atributos da classe
        btnSalvar = (Button)rootView.findViewById(R.id.btnSalvar);
        btnCancelar = (Button)rootView.findViewById(R.id.btnCancelar);
        edtDescricao = (EditText)rootView.findViewById(R.id.edtDescricao);
        edtQuantidade = (EditText)rootView.findViewById(R.id.edtQuantidade);
        spnUnidadeMedida = (Spinner)rootView.findViewById(R.id.spnUnidadeMedida);

       //amarrando os botões ao seu listener, faz com que no click execute o evento desta classe, onClick
        btnSalvar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSalvar:
                String descricao = edtDescricao.getText().toString();

                double quantidade = Double.parseDouble(edtQuantidade.getText().toString());

                String unidadeMedida = spnUnidadeMedida.getSelectedItem().toString();

                //Criando um novo produto
                Produto p = new Produto();
                p.setDescricao(descricao);
                p.setQuantidade(quantidade);
                p.setUnidadeMedida(unidadeMedida);

                DBHelper dbh = new DBHelper(getActivity());
                dbh.getWritableDatabase();

                try {
                    //cria conexão e salva o produto
                    Dao<Produto, Integer> produtoDao = dbh.getDao(Produto.class);
                    produtoDao.create(p);
                    produtoDao = null;
                    //finaliza conexão
                    dbh.close();
                    dbh = null;
                    limpar();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnCancelar:
                limpar();
                break;
        }
    }

    private void limpar() {
        edtDescricao.setText("");
        edtQuantidade.setText("");
        spnUnidadeMedida.setSelection(0);
        //troca a aba
        ((ShopListActivity)getActivity()).trocaAba(1);
    }
}
