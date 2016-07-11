package com.example.darlei.shoplist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.darlei.shoplist.db.DBHelper;
import com.example.darlei.shoplist.model.Usuario;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.List;

public class LoginActivity extends Activity implements View.OnClickListener{
    private EditText edtLogin;
    private EditText edtSenha;
    private Button btnLogin;
    private Button btnSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //findViewById, amarra as variaveis java com os componentes da tela(.xml)
        edtLogin = (EditText)findViewById(R.id.edtLogin);
        edtSenha = (EditText)findViewById(R.id.edtSenha);
        btnSair  = (Button)findViewById(R.id.btnSair);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        //amarra o evento do click a esta classe, Listener
        btnLogin.setOnClickListener(this);
        btnSair.setOnClickListener(this);

        edtLogin.setText("admin");//preenche os campos
        edtSenha.setText("admin");
    }

    @Override
    public void onClick(View v) { //todos os clicks na tela caem aqui
      switch (v.getId()){  //verifica qual é o botão clicado
          case R.id.btnLogin:
              String login = edtLogin.getText().toString();//recuperando valores da tela
              String senha = edtSenha.getText().toString();

              //Consultar o usuario no banco pelo login e senha
              try {
                  DBHelper dbh = new DBHelper(this);
                  dbh.getWritableDatabase();

                  Dao<Usuario, Integer> usuarioDao = dbh.getDao(Usuario.class);

                  //consultar usuario no banco pelo login e senha, usuarioDao é o objeto para comunicar com o banco
                  Where<Usuario, Integer> query = usuarioDao.queryBuilder().where().eq("login", login).and().eq("senha", senha);
                  List<Usuario> usuarios = query.query();

                  if (usuarios != null && usuarios.size()>0){  //teste se a query retornou valor
                      //criando mensagem para o android carregar a Activity
                      Intent it = new Intent(this, ShopListActivity.class);
                      it.putExtra("login",login);//parametro
                      startActivity(it);
                      //fecha a tela de login
                      finish();
                  } else {
                      Toast.makeText(this, "Usuario ou senha inválidos...", Toast.LENGTH_LONG).show();
                      edtLogin.setText("");
                      edtSenha.setText("");
                      edtLogin.requestFocus();//setFocus
                  }
              } catch (SQLException e) {
                  Log.e("ShopList",e.getMessage());
                  e.printStackTrace();
              }

              break;
          case R.id.btnSair:
              //exibindo dialogo
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
              dialogoSair.setNegativeButton("Não",null);
              dialogoSair.show();
              break;
      }
    }
}
