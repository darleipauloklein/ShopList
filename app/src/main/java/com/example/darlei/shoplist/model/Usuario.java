package com.example.darlei.shoplist.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Classe que representa um usuário
 * @author Darlei
 * @since 05/09/2015
 */

@DatabaseTable(tableName = "usuario")
public class Usuario {
    @DatabaseField(generatedId = true, columnName = "id")
    private Integer id;

    @DatabaseField(canBeNull = false)
    private String nome;

// se não passar parametros pelo @DatabaseField o nome do campo fica igual ao da variavel e canBeNull = false por padrão

    @DatabaseField
    private String login;

    @DatabaseField
    private String senha;

    @DatabaseField
    private String foto;

    @DatabaseField
    private Double latitude;

    @DatabaseField
    private Double longitude;

    public Usuario() {} //construtor vazio, necessário para o ORMLite


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
