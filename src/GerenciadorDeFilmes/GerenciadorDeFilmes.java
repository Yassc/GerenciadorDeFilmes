package GerenciadorDeFilmes;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GerenciadorDeFilmes implements InterfaceGerenciador {
    private Map<String, Filme> listaDeFilmes;
    private Map<String, Filme> filmesFavoritos;
    private Map<String, Filme> historicoAssistidos;
    private Map<String, Filme> listaDeDesejos;

    public GerenciadorDeFilmes() {
        this.listaDeFilmes = new HashMap<>();
        this.filmesFavoritos = new HashMap<>();
        this.historicoAssistidos = new HashMap<>();
        this.listaDeDesejos = new HashMap<>();
    }

    @Override
    public void adicionarFilme(Filme f) {
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

        if (f == null) {
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

    @Override
    public String adicionarAListaDeDesejos(String titulo) throws filmeNaoEncontradoException {
        Filme f = listaDeFilmes.get(titulo.toLowerCase());

        if (f == null) {
            throw new filmeNaoEncontradoException("O filme " + titulo + " não existe no catálogo.");
        }

        this.listaDeDesejos.put(f.getTitulo().toLowerCase(), f);
        return "O filme " + f.getTitulo() + " foi adicionado à sua Lista de Desejos!";
    }

    @Override
    public void listarListaDeDesejos() {
        if (listaDeDesejos.isEmpty()) {
            System.out.println("Sua lista de desejos está vazia.");
        } else {
            System.out.println("\n--- LISTA DE DESEJOS ---");
            for (Filme f : listaDeDesejos.values()) {
                System.out.println(f.ExibirDetalhes());
                System.out.println("-----------------------");
            }
        }
    }

    @Override
    public Map<String, Filme> getListaDeDesejos() {
        return this.listaDeDesejos;
    }

    public Map<String, Filme> getHistoricoAssistidos() {
        return historicoAssistidos;
    }

    @Override
    public void salvarDados() throws IOException {
        GravadorDeDados gravador = new GravadorDeDados();
        // Passa também a listaDeDesejos para o salvamento
        gravador.salvar(this.listaDeFilmes, this.filmesFavoritos, this.listaDeDesejos);
    }


    @Override
    public void recuperarDados() throws IOException, ClassNotFoundException {
        GravadorDeDados gravador = new GravadorDeDados();
        this.listaDeFilmes = gravador.recuperarCatalogo();
        this.filmesFavoritos = gravador.recuperarFavoritos();
        // Recupera a lista de desejos do arquivo 'desejos.dat'
        this.listaDeDesejos = gravador.recuperarDesejos();
    }
    @Override
    public void avaliarFilme(String titulo, double nota) throws filmeNaoEncontradoException, NotaDeAvaliacaoInvalidaException {
        if (nota < 0 || nota > 10) {
            throw new NotaDeAvaliacaoInvalidaException("A nota deve ser um valor entre 0 e 10.");
        }

        Filme f = buscarPorTitulo(titulo);
        f.setAvaliacao(nota);
    }

    @Override
    public String buscarFilmesPorNotaMinima(double notaMinima) {
        String resultado = listaDeFilmes.values().stream()
                .filter(f -> f.getAvaliacao() >= notaMinima)
                .map(f -> f.getTitulo() + " (Nota: " + f.getAvaliacao() + ")")
                .reduce("", (subtotal, element) -> subtotal + element + "\n");

        if (resultado.isEmpty()) {
            return "Nenhum filme encontrado com nota maior ou igual a " + notaMinima;
        }
        return resultado;
    }
}
