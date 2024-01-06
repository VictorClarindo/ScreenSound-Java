package br.com.alura.ScreenSoundJava.principal;

import br.com.alura.ScreenSoundJava.model.Artista;
import br.com.alura.ScreenSoundJava.model.Categoria;
import br.com.alura.ScreenSoundJava.model.Musica;
import br.com.alura.ScreenSoundJava.repository.ArtistaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    Scanner scanner = new Scanner(System.in);
    ArtistaRepository repositorio;
    List<Artista> artistasList = new ArrayList<>();
    List<Musica> musicasList = new ArrayList<>();
    Optional<Artista> artistaBusca;
    Integer opcao = -1;

    public Principal(ArtistaRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void exibeMenu() {
        while (opcao != 0) {
            System.out.println("""
                                    
                    *** ScreenSound Musicas ***
                                    
                    1 - Cadastrar artistas
                    2 - Cadastrar músicas
                    3 - Listar músicas
                    4 - Buscar músicas por artista
                    5 - Pesquisar dados sobre um artista
                    0 - Sair
                                    
                    Digite uma opção:
                    """);

            opcao = scanner.nextInt();
            scanner.nextLine();
            switch (opcao) {
                case 0:
                    break;
                case 1:
                    cadastrarArtista();
                    break;
                case 2:
                    cadatrarMusica();
                    break;
                case 3:
                    listarMusicas();
                    break;
                case 4:
                    buscarMusicaPorArtista();
                    break;
                case 5:
                    pesquisarDados();
                    break;
                case 6:
                    listarArtistas();
                    break;
                default:
                    System.out.printf("\nOpção Inválida");
            }
        }
    }

    private void cadastrarArtista() {
        var sair = "s";
        while(sair.equalsIgnoreCase("s")) {
            System.out.println("Digite o nome do artista:");
            var nome = scanner.nextLine();

            System.out.println("Informe o tipo desse artista: (solo, dupla, banda)\n");
            var tipo = scanner.nextLine();

            Categoria categoria = Categoria.fromPortugues(tipo);
            Artista artista = new Artista(nome, categoria);

            repositorio.save(artista);

            System.out.println("Deseja cadastrar outro artista? (S/N)");
            sair = scanner.nextLine();
        }
    }

    private void listarArtistas(){
        artistasList = repositorio.findAll();
        artistasList.forEach(a -> System.out.println(a.getNome()));
    }

    private void cadatrarMusica() {
        listarArtistas();
        System.out.println("Escolha um artista para adicionar uma música: ");
        var nomeArtista = scanner.nextLine();

        Optional<Artista> artista = artistasList.stream()
                .filter(a -> a.getNome().toLowerCase().contains(nomeArtista.toLowerCase()))
                .findFirst();

        if(artista.isPresent()){
            System.out.println("Digite uma música do artista " + artista.get().getNome());
            var nomeMusica = scanner.nextLine();
            Musica musica = new Musica(nomeMusica);

            artista.get().setMusicas(musica);
            repositorio.save(artista.get());

        }else {
            System.out.println("\nArtista não encontrado!");
        }
    }

    private void listarMusicas() {
        System.out.println("\nGostaria de ver músicas de qual artista?");
        var artistas = repositorio.findAll();
        artistas.forEach(a -> System.out.println(a.getNome()));

        var nomeArtista = scanner.nextLine();
        var artistaBusca = repositorio.findByNomeContainingIgnoreCase(nomeArtista);
        if(artistaBusca.isPresent()){
            List<Musica> musicas = artistaBusca.get().getMusicas()
                    .stream()
                    .map(m -> new Musica(m.getNome()))
                    .collect(Collectors.toList());
            System.out.println("\nMusicas de " + artistaBusca.get().getNome());
            System.out.println(musicas);
        } else{
            System.out.println("Artista não encontrado!");
        }
    }

    private void buscarMusicaPorArtista() {



    }

    private void pesquisarDados() {

    }
}
