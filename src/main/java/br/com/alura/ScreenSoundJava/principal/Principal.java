package br.com.alura.ScreenSoundJava.principal;

import br.com.alura.ScreenSoundJava.model.Artista;
import br.com.alura.ScreenSoundJava.model.Categoria;
import br.com.alura.ScreenSoundJava.model.Musica;
import br.com.alura.ScreenSoundJava.repository.ArtistaRepository;
import br.com.alura.ScreenSoundJava.service.ConsultaChatGPT;

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

            Categoria categoria = Categoria.valueOf(tipo.toUpperCase());
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

        Optional<Artista> artista = repositorio.findByNomeContainingIgnoreCase(nomeArtista);

        if(artista.isPresent()){
            System.out.println("Digite uma música de " + artista.get().getNome());
            var nomeMusica = scanner.nextLine();
            Musica musica = new Musica(nomeMusica, artista.get());

            artista.get().setMusicas(musica);
            repositorio.save(artista.get());

        }else {
            System.out.println("\nArtista não encontrado!");
        }
    }

    private void listarMusicas() {
        var artistas = repositorio.findAll();
        System.out.println("Listando todas as músicas:\n");
        artistas.forEach(a -> System.out.println(a.getMusicas()));
    }

    private void buscarMusicaPorArtista() {
        System.out.println("\nGostaria de ver músicas de qual artista?");
        var artistas = repositorio.findAll();
        artistas.forEach(a -> System.out.println(a.getNome()));

        var nomeArtista = scanner.nextLine();
        Optional<Artista> artista = repositorio.encontrarArtista(nomeArtista);

        if(artista.isPresent()){
            var musicasArtista = repositorio.buscarMusicaPorArtista(artista.get());
            System.out.println("\nMusicas de " + artista.get().getNome() + ":\n");
            musicasArtista.forEach(m -> System.out.println(m.getNome()));
        } else{
            System.out.println("Artista não encontrado!");
        }

    }

    private void pesquisarDados() {
        System.out.println("Pesquisar dados sobre qual artista? ");
        var nome = scanner.nextLine();
        var resposta = ConsultaChatGPT.obterInformacao(nome);
        System.out.println(resposta.trim());

    }
}
