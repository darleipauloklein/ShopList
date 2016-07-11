package com.example.darlei.shoplist.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Classe que representa um Produto
 * @author Darlei
 * @since 05/09/2015
 */
@DatabaseTable(tableName = "produto")
public class Produto {
   @DatabaseField(generatedId = true, columnName = "id")
    private Integer id;

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", quantidade=" + quantidade +
                ", unidadeMedida='" + unidadeMedida + '\'' +
                '}';
    }

    @DatabaseField(columnName = "descricao", canBeNull = false)
    private String descricao;

    @DatabaseField(columnName = "quantidade", canBeNull = false)
    private Double quantidade;

    @DatabaseField(columnName = "umedida", canBeNull = false)
    private String unidadeMedida;

    public  Produto(){}//construtor vazio, necessário para o ORMLite


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }
}
