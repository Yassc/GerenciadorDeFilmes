import org.junit.Test;
import static org.junit.Assert.*;

public class GerenciadorDeFilmesTest {

    @Test
    public void testAdicionarEBuscarFilmeComSucesso() throws FilmeNaoEncontradoException {
        GerenciadorDeFilmes gerenciador = new GerenciadorDeFilmes();
        Filme f1 = new Filme("Avatar", "James Cameron", 2009, 162);

        gerenciador.adicionarFilme(f1);

        Filme encontrado = gerenciador.buscarPorTitulo("AVATAR");

        assertNotNull(encontrado);
        assertEquals("James Cameron", encontrado.getDiretor());
    }

    @Test
    public void testSobrescritaOuAtualizacao() throws FilmeNaoEncontradoException {
        GerenciadorDeFilmes gerenciador = new GerenciadorDeFilmes();
        Filme f2 = new Filme("matrix", "Lana e Lilly Wachowski", 1999, 136);
        gerenciador.adicionarFilme(f2);

        Filme f3 = new Filme("matrix", "Lana e Lilly Wachowski", 2021, 136);
        gerenciador.adicionarFilme(f3);

        assertEquals(2021, gerenciador.buscarPorTitulo("matrix").getAno());
    }

    @Test
    public void testIgnorarLetraMinuscula() throws FilmeNaoEncontradoException {
        GerenciadorDeFilmes gerenciador = new GerenciadorDeFilmes();
        Filme f4 = new Filme("La la land", "Damien Chazelle", 2016, 128);
        gerenciador.adicionarFilme(f4);

        Filme encontrado = gerenciador.buscarPorTitulo("LA LA LAND");
        assertNotNull(encontrado);
        assertEquals("Damien Chazelle", encontrado.getDiretor());
    }

    @Test(expected = FilmeNaoEncontradoException.class)
    public void testBuscarFilmeInexistenteLancaExcecao() throws FilmeNaoEncontradoException {
        GerenciadorDeFilmes gerenciador = new GerenciadorDeFilmes();
        gerenciador.buscarPorTitulo("Batman");
    }

    @Test
    public void testRemoverPorAnoComSucesso() throws FilmeNaoEncontradoException {
        GerenciadorDeFilmes gerenciador = new GerenciadorDeFilmes();
        Filme f5 = new Filme("Percy Jackson e o Ladrão de Raios", "Chris Columbus", 2010, 118);
        gerenciador.adicionarFilme(f5);

        String removido = gerenciador.removerPorAno(2010);
        assertNotNull(removido);
        assertEquals("Filme do ano 2010 removido com sucesso!", removido);
    }

    @Test
    public void testAdicionarAosFavoritosComSucesso() throws FilmeNaoEncontradoException {
        GerenciadorDeFilmes gerenciador = new GerenciadorDeFilmes();
        Filme f6 = new Filme("Coraline", "Henry Selick", 2009, 100);
        gerenciador.adicionarFilme(f6);

        String mensagem = gerenciador.adicionarAosFavoritos("Coraline");

        assertNotNull(mensagem);
        assertEquals("O filme Coraline foi adicionado aos seus favoritos!", mensagem);
    }

    @Test(expected = FilmeNaoEncontradoException.class)
    public void testAdicionarFilmeFantasmaAosFavoritosLancaExcecao() throws FilmeNaoEncontradoException {
        GerenciadorDeFilmes gerenciador = new GerenciadorDeFilmes();
        gerenciador.adicionarAosFavoritos("Filme Fantasma");
    }

    @Test
    public void testAvaliarFilmeComSucesso() throws FilmeNaoEncontradoException, NotaDeAvaliacaoInvalidaException {
        GerenciadorDeFilmes gerenciador = new GerenciadorDeFilmes();
        Filme f = new Filme("Inception", "Christopher Nolan", 2010, 148);
        gerenciador.adicionarFilme(f);
        assertThrows(FilmeNaoEncontradoException.class,
                () -> {
                    gerenciador.adicionarAosFavoritos("Filme Fantasma");
                });
        gerenciador.avaliarFilme("Inception", 9.5);

        assertEquals(9.5, gerenciador.buscarPorTitulo("Inception").getAvaliacao(), 0.001);
    }

    @Test(expected = NotaDeAvaliacaoInvalidaException.class)
    public void testAvaliarFilmeComNotaInvalidaLancaExcecao() throws FilmeNaoEncontradoException, NotaDeAvaliacaoInvalidaException {
        GerenciadorDeFilmes gerenciador = new GerenciadorDeFilmes();
        Filme f = new Filme("Interstellar", "Christopher Nolan", 2014, 169);
        gerenciador.adicionarFilme(f);

        gerenciador.avaliarFilme("Interstellar", 11.0);
    }
    @Test
    public void testMarcarComoAssistidoComSucesso() throws FilmeNaoEncontradoException {
        GerenciadorDeFilmes gerenciador = new GerenciadorDeFilmes();
        Filme f = new Filme("Matrix", "Wachowski", 1999, 136);
        gerenciador.adicionarFilme(f);

        String mensagem = gerenciador.marcarComoAssistido("Matrix");

        assertNotNull(mensagem);
        assertEquals("O filme Matrix foi marcado como assistido!", mensagem);
    }
}