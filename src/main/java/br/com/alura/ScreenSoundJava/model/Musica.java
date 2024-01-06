package br.com.alura.ScreenSoundJava.model;

import jakarta.persistence.*;
import org.hibernate.jpa.internal.MutableJpaComplianceImpl;
import org.hibernate.service.spi.InjectService;

@Entity
@Table(name = "musicas")
public class Musica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @ManyToOne
    private Artista artista;

    public Musica(){}
    public Musica(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    @Override
    public String toString() {
        return "nome='" + nome + '\'' +
                ", artista=" + artista.getNome();
    }
}
