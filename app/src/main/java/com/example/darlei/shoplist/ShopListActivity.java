package com.example.darlei.shoplist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

// ORMLite framework de persistencia

public class ShopListActivity extends AppCompatActivity {

    private ActionBar ab;
    private ActionBar.Tab tabListaProdutos;
    private ActionBar.Tab tabCadastroProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_list);

        //recuperando a variavel da intent
        if (getIntent() != null){
            String login = getIntent().getStringExtra("login");
            Toast.makeText(this, "Seja bem vindo "+ login, Toast.LENGTH_LONG).show();
        }
        //caminho da base no android: /data/data/com.example.darlei.shoplist/databases/ShopList.db

        //configurando estrutura de abas
        ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setBackgroundDrawable(getResources().getDrawable(R.drawable.abc_ab_share_pack_mtrl_alpha));
        ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        //criando abas
        tabListaProdutos = ab.newTab();
        tabListaProdutos.setText("Lista de Produtos");
        tabListaProdutos.setIcon(R.drawable.menu);
        //configurando listener das abas
        tabListaProdutos.setTabListener(new NavegacaoAbas(new ListaProdutoFragment()));
        ab.addTab(tabListaProdutos);

        tabCadastroProdutos = ab.newTab();
        tabCadastroProdutos.setText("Produto");
        tabCadastroProdutos.setIcon(R.drawable.produto);
        //configurando listener das abas
        tabCadastroProdutos.setTabListener(new NavegacaoAbas(new CadastroProdutoFragment()));
        ab.addTab(tabCadastroProdutos);

        ab.selectTab(tabListaProdutos);
    }

    public void trocaAba(int aba){
        switch (aba){
            case 1: ab.selectTab(tabListaProdutos); break;
            case 2: ab.selectTab(tabCadastroProdutos); break;
        }
    }

    private class NavegacaoAbas implements  ActionBar.TabListener{
        private Fragment frag;

        public NavegacaoAbas(Fragment frag){
            this.frag = frag;
        }
        @Override
        public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
            FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
            fts.replace(R.id.shoplistAbas, frag);
            fts.commit();
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
            FragmentTransaction fts = getSupportFragmentManager().beginTransaction();
            fts.remove(frag);
            fts.commit();
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shop_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mn_sair) {
            //fecha a tela ao clicar em sair   //exibindo dialogo
            AlertDialog.Builder dialogoSair = new AlertDialog.Builder(this);
            dialogoSair.setTitle("Sair");
            dialogoSair.setMessage("Deseja realmente sair?");
            //configurando botão do "sim" no dialogo
            dialogoSair.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //finalizar aplicativo
                    finish();
                }
            });
            //configurando botão "não" no dialogo
            dialogoSair.setNegativeButton("Não", null);
            dialogoSair.show();
        }

        if (id == R.id.mn_usuario){
            Intent itUsuario = new Intent(this, CadastroUsuarioActivity.class);
            startActivity(itUsuario);
        }

        return super.onOptionsItemSelected(item);
    }
}
