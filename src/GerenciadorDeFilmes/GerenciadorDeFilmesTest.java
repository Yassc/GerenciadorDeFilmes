package Projetofinalpoo.GerenciadorDeFilmes;

import org.junit.Test;
import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

public class GerenciadorDeFilmesTest {

    @Test
    public void testAdicionarEBuscarFilmeComSucesso() throws filmeNaoEncontradoException {
        GerenciadorDeFilmes gerenciador = new GerenciadorDeFilmes();
        Filme f1 = new Filme("Avatar", "James Cameron", 2009, 162);

        gerenciador.adicionarFilme(f1);

        Filme encontrado = gerenciador.buscarPorTitulo("AVATAR");

        assertNotNull(encontrado);
        assertEquals("James Cameron", encontrado.getDiretor());
    }

    @Test
    public void testSobrescritaOuAtualizacao() throws filmeNaoEncontradoException {
        GerenciadorDeFilmes gerenciador = new GerenciadorDeFilmes();
        Filme f2 = new Filme("matrix", "Lana e Lilly Wachowski", 1999, 136 );
        gerenciador.adicionarFilme(f2);

        Filme f3 = new Filme ("matrix", "Lana e Lilly Wachowski", 2021, 136 );
        gerenciador.adicionarFilme(f3);

        assertEquals(2021, gerenciador.buscarPorTitulo("matrix").getAno());
    }

    @Test
    public void testIgnorarLetraMinuscula() throws filmeNaoEncontradoException {
        GerenciadorDeFilmes gerenciador = new GerenciadorDeFilmes();
        Filme f4 = new Filme("La la land", "Damien Chazelle", 2016 , 128);
        gerenciador.adicionarFilme(f4);

        Filme Encontrado = gerenciador.buscarPorTitulo("LA LA LAND");
        assertNotNull(Encontrado);
        assertEquals("Damien Chazelle", Encontrado.getDiretor());

    }

    @Test
    public void testBuscarFilmeInexistenteLancaExcecao() {
        GerenciadorDeFilmes gerenciador = new GerenciadorDeFilmes();
        assertThrows(filmeNaoEncontradoException.class, () -> {
            gerenciador.buscarPorTitulo("Batman");
        });
    }

    @Test
    public void testRemoverPorAnoComSucesso() throws filmeNaoEncontradoException {
        GerenciadorDeFilmes gerenciador = new GerenciadorDeFilmes();
        Filme f5 = new Filme("Percy Jackson e o Ladrão de Raios", "Chris Columbus", 2010, 118 );
        gerenciador.adicionarFilme(f5);

        String removido = gerenciador.removerPorAno(2010);
        assertNotNull(removido);
        assertEquals("Filme do ano 2010 removido com sucesso!", removido);

        assertThrows(filmeNaoEncontradoException.class, () -> {
            gerenciador.buscarPorTitulo("Percy Jackson e o Ladrão de Raios");
        });

    }

    @Test
    public void testAdicionarAosFavoritosComSucesso() throws filmeNaoEncontradoException {
        GerenciadorDeFilmes gerenciador = new GerenciadorDeFilmes();
        Filme f6 = new Filme("Coraline", "Henry Selick", 2009, 100);
        gerenciador.adicionarFilme(f6);

        String mensagem = gerenciador.adicionarAosFavoritos("Coraline");

        assertNotNull(mensagem);
        assertEquals("O filme Coraline foi adicionado aos seus favoritos!", mensagem);

    }

    @Test
    public void testAdicionarFilmeFantasmaAosFavoritosLancaExcecao() {

        GerenciadorDeFilmes gerenciador = new GerenciadorDeFilmes();

        assertThrows(filmeNaoEncontradoException.class,
                () -> {
            gerenciador.adicionarAosFavoritos("Filme Fantasma");
                });
    }
}