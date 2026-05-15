package com.example.livro;

public class livros {

    private String Titulo;
    private String Autor;
    private String Genero;
    private int Ano;

    private String Status;

    private Boolean Favorito;


public livros (String Titulo, String Autor, String Genero, int Ano, String Status, Boolean Favorito){
    this.Titulo = Titulo;
    this.Autor= Autor;
    this.Genero = Genero;
    this.Ano = Ano;
    this.Status = Status;
    this.Favorito = Favorito;
}


    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String titulo) {
        Titulo = titulo;
    }

    public String getAutor() {
        return Autor;
    }

    public void setAutor(String autor) {
        Autor = autor;
    }

    public String getGenero() {
        return Genero;
    }

    public void setGenero(String genero) {
        Genero = genero;
    }

    public int getAno() {
        return Ano;
    }

    public void setAno(int ano) {
        Ano = ano;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Boolean getFavorito() {
        return Favorito;
    }

    public void setFavorito(Boolean favorito) {
        Favorito = favorito;
    }

    @Override
    public String toString() {
        return Titulo + " (" + Autor + ")\n" + "Gênero: " + Genero + "\n" + "Ano: " + Ano + "\n" + "Status: " + Status + "\n" + "Favorito: " + (Favorito);
    }
}
