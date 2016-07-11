package com.example.darlei.shoplist;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.darlei.shoplist.db.DBHelper;
import com.example.darlei.shoplist.model.Usuario;
import com.j256.ormlite.dao.Dao;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

public class CadastroUsuarioActivity extends ActionBarActivity implements View.OnClickListener{

    private Button btnSalvar;
    private Button btnCancelar;
    private EditText edtNome;
    private EditText edtLogin;
    private EditText edtSenha;
    private EditText edtLongitude;
    private EditText edtLatitude;
    private ImageView imgFoto;

    protected static final String TAG = null;
    private Context context;
    private LocationManager lm;
    private Location location;
    private volatile boolean stop = false;
    private static final int UM_SEGUNDO = 1000;
    private int tempoTotalBusca = 10;
    protected ProgressDialog progressDialog;
    private Button btnMapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        edtNome = (EditText)findViewById(R.id.edtNome);
        edtLogin = (EditText)findViewById(R.id.edtLogin);
        edtSenha = (EditText)findViewById(R.id.edtSenha);
        edtLongitude = (EditText)findViewById(R.id.edtLogitude);
        edtLatitude = (EditText)findViewById(R.id.edtLatitude);
        btnSalvar  = (Button)findViewById(R.id.btnSalvaCadUsuario);
        btnCancelar = (Button)findViewById(R.id.btnCancelCadUsuario);
        imgFoto = (ImageView)findViewById(R.id.imgFoto);
        btnMapa = (Button)findViewById(R.id.btnMapa);
        //amarra o evento do click a esta classe, Listener
        btnSalvar.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
        btnMapa.setOnClickListener(this);
        //cria o evento do clique longo na imagem -- Feito na aula..
        imgFoto.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent itCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(itCamera, 100);

                return false;
            }
        });

        Bitmap bitmap= BitmapFactory.decodeResource(this.getResources(), R.drawable.user);
        imgFoto.setImageBitmap(bitmap);

        GPSTracker gps  = new GPSTracker(this);

        if(gps.canGetLocation()){
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            edtLongitude.setText(Double.toString(longitude));
            edtLatitude.setText(Double.toString(latitude));
        }else{
            gps.showSettingsAlert();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_cadastro_usuario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSalvaCadUsuario:
                String nome = edtNome.getText().toString();
                String login = edtLogin.getText().toString();
                String senha = edtSenha.getText().toString();
                double latitude = Double.parseDouble(edtLatitude.getText().toString());
                double longitude = Double.parseDouble(edtLongitude.getText().toString());

                Usuario u = new Usuario();
                u.setNome(nome);
                u.setLogin(login);
                u.setSenha(senha);
                u.setLatitude(latitude);
                u.setLongitude(longitude);

                DBHelper dbh = new DBHelper(this);
                dbh.getWritableDatabase();
                try {
                    //cria conexão e salva o produto
                    Dao<Usuario, Integer> usuarioDao = dbh.getDao(Usuario.class);
                    usuarioDao.create(u);
                    usuarioDao = null;
                    dbh.close();
                    dbh = null;
                    limpar();
                    Toast.makeText(getApplicationContext(), "Cadastrado efetuado com sucesso!", Toast.LENGTH_LONG).show();
                    Intent itShopListActivity = new Intent(this, ShopListActivity.class);
                    startActivity(itShopListActivity);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.btnCancelCadUsuario:
                    limpar();
                    Toast.makeText(getApplicationContext(), "Cadastrado cancelado!", Toast.LENGTH_LONG).show();
                    Intent itShopListActivity = new Intent(this, ShopListActivity.class);
                startActivity(itShopListActivity);
                break;

            case R.id.btnMapa:
                Intent itMapa = new Intent(CadastroUsuarioActivity.this, MapaActivity.class);
                startActivityForResult(itMapa, 110);

                break;

        }
    }
    private void limpar() {
        edtNome.setText("");
        edtLogin.setText("");
        edtSenha.setText("");
        edtLongitude.setText("");
        edtLatitude.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK) {
            if(data != null) {
                Bitmap foto = (Bitmap) data.getExtras().get("data");
                imgFoto.setImageBitmap(foto);

                //salvando a foto no cartão, comprime com 40% da qualidade original da foto. O getAbsolutePath tem o diretorio da foto
                try {
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    foto.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
                    File arquivoFoto = new File(Environment.getExternalStorageDirectory() + File.separator +"foto.jpg");
                    arquivoFoto.createNewFile();
                    FileOutputStream fos = new FileOutputStream(arquivoFoto.getAbsolutePath());
                    fos.write(bytes.toByteArray());
                    fos.close();
                    fos = null;
                    arquivoFoto = null;
                } catch (IOException e) {
                    Log.e("ShopList", e.getMessage());
                    Toast.makeText(this, "Erro ao salvar imagem", Toast.LENGTH_LONG).show();
                }

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getBaseContext(), "A captura foi cancelada",
                        Toast.LENGTH_SHORT);

            } else {
                Toast.makeText(getBaseContext(), "A câmera foi fechada",
                        Toast.LENGTH_SHORT);
            }
        }
    }

}
