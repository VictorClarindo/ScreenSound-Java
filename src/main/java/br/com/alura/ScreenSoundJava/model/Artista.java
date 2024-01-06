package br.com.alura.ScreenSoundJava.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "artistas")
public class Artista {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Enumerated(EnumType.STRING)
    private Categoria categogia;

    @OneToMany(mappedBy = "artista", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Musica> musicas;

    public Artista(){}
    public Artista(String nome, Categoria categogia) {
        this.nome = nome;
        this.categogia = categogia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Categoria getCategogia() {
        return categogia;
    }

    public void setCategogia(Categoria categogia) {
        this.categogia = categogia;
    }

    public List<Musica> getMusicas() {
        return musicas;
    }

    public void setMusicas(Musica musica) {
    this.musicas.add(musica);
    musica.setArtista(this);
    }

    @Override
    public String toString() {
        return "nome ='" + nome + '\'' +
                ", categogia =" + categogia +
                "\nmusicas =" + musicas ;
    }
}
