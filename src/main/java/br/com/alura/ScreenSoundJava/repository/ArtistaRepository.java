package br.com.alura.ScreenSoundJava.repository;

import br.com.alura.ScreenSoundJava.model.Artista;
import br.com.alura.ScreenSoundJava.model.Musica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {
    Optional<Artista> findByNomeContainingIgnoreCase(String nomeArtista);

    @Query("SELECT a FROM Artista a WHERE a.nome ILIKE %:artista%")
    Optional<Artista> encontrarArtista(String artista);

    @Query("SELECT m FROM Artista a JOIN a.musicas m WHERE a = :artista ORDER BY m.nome")
    List<Musica> buscarMusicaPorArtista(Artista artista);
}
