package GerenciadorDeFilmes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GerenciadorDeFilmes implements InterfaceGerenciador {
    private Map<String, Filme> listaDeFilmes;
    private Map<String, Filme> filmesFavoritos;
    private Map<String, Filme> historicoAssistidos;


    public GerenciadorDeFilmes() {
        this.listaDeFilmes = new HashMap<>();
        this.filmesFavoritos = new HashMap<>();
        this.historicoAssistidos = new HashMap<>();
    }
    @Override
    public void adicionarFilme(Filme f){
        this.listaDeFilmes.put(f.getTitulo().toLowerCase(), f);

    }
    @Override
    public Filme buscarPorTitulo(String titulo) throws filmeNaoEncontradoException {
        Filme f = listaDeFilmes.get(titulo.toLowerCase());

        if (f == null) {
            throw new filmeNaoEncontradoException(titulo);
        }
        return f;
    }
    @Override
    public String removerPorAno(int ano) throws filmeNaoEncontradoException {
        String remover = null;
        for (Filme f : listaDeFilmes.values()) {
            if (f.getAno() == ano) {
                remover = f.getTitulo().toLowerCase();
               break;
            }
        }
        if (remover != null) {
            listaDeFilmes.remove(remover);
            return "Filme do ano " + ano + " removido com sucesso!";
        }

        throw new filmeNaoEncontradoException("Não há filmes do ano " + ano + " no catálogo.");
    }
    @Override
    public String adicionarAosFavoritos(String titulo) throws filmeNaoEncontradoException {

        Filme f = listaDeFilmes.get(titulo.toLowerCase());

        if(f == null){
            throw new filmeNaoEncontradoException("O filme " + titulo + " não existe no catálogo.");
        }

        this.filmesFavoritos.put(f.getTitulo().toLowerCase(), f);
        return "O filme " + titulo + " foi adicionado aos seus favoritos!";


    }
    @Override
    public void listarFavoritos() {
        if (filmesFavoritos.isEmpty()) {
            System.out.println("Sua lista de favoritos está vazia.");
        } else {
            System.out.println("\n--- MEUS FAVORITOS ---");
            for (Filme f : filmesFavoritos.values()) {
                System.out.println(f.ExibirDetalhes());
                System.out.println("-----------------------");
            }
        }
    }

    @Override
    public String marcarComoAssistido(String titulo) throws filmeNaoEncontradoException {
        Filme f = buscarPorTitulo(titulo);
        this.historicoAssistidos.put(f.getTitulo().toLowerCase(), f);
        return "O filme " + f.getTitulo() + " foi marcado como assistido!";
    }

    @Override
    public void listarAssistidos() {
        if (historicoAssistidos.isEmpty()) {
            System.out.println("Seu histórico de assistidos está vazio.");
        } else {
            System.out.println("\n--- HISTÓRICO DE ASSISTIDOS ---");
            for (Filme f : historicoAssistidos.values()) {
                System.out.println(f.ExibirDetalhes());
                System.out.println("-----------------------");
            }
        }
    }

    public Map<String, Filme> getHistoricoAssistidos() {
        return historicoAssistidos;
    }
    @Override
    public void salvarDados() throws IOException {
        GravadorDeDados gravador = new GravadorDeDados();
        gravador.salvar(this.listaDeFilmes, this.filmesFavoritos);
    }

    @Override
    public void recuperarDados() throws IOException, ClassNotFoundException {
        GravadorDeDados gravador = new GravadorDeDados();
        this.listaDeFilmes = gravador.recuperarCatalogo();
        this.filmesFavoritos = gravador.recuperarFavoritos();
    }
}

