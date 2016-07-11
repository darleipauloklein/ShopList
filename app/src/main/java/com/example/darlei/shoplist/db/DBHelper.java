package com.example.darlei.shoplist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.darlei.shoplist.model.Produto;
import com.example.darlei.shoplist.model.Usuario;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Classe da conexão com banco de dados
 * @author Darlei
 * @since 05/09/2015
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {
    private final static String DATABASE_NAME = "ShopList.db";
    private final static int DATABASE_VERSION = 3;

    public DBHelper(Context context){
       super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            //criando as tabelas no banco de dados...
            TableUtils.createTable(connectionSource, Usuario.class);
            TableUtils.createTable(connectionSource, Produto.class);
            //inserindo um usuario padrão
            Usuario uPadrao = new Usuario();
            uPadrao.setNome("admin");
            uPadrao.setLogin("admin");
            uPadrao.setSenha("admin");
            //criando o usuario padrão
            getDao(Usuario.class).create(uPadrao);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try { //onUpgrade só é chamado quando for alterado o DATABASE_VERSION
            TableUtils.dropTable(connectionSource, Usuario.class, true);//deleta a tabela
            TableUtils.dropTable(connectionSource, Produto.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        }catch (SQLException ex){
            Log.e("ShopList", ex.getMessage());//registra o log
            ex.printStackTrace();
        }
    }
}
