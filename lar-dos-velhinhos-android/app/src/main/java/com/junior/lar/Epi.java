package com.junior.lar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Epi extends AppCompatActivity {

    private int id;
    private String nome;
    private String quantidade;

    @NonNull
    @Override
    public String toString() {
        return id + " - " + nome + " - " + quantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

}
