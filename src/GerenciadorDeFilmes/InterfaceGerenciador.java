package GerenciadorDeFilmes;

import java.io.IOException;

/**
 * Interface que define as regras de negócio para o sistema de gerenciamento de filmes.
 * @author Yasmim Castilho
 */
public interface InterfaceGerenciador {

    /**
     * Adiciona um novo filme ao catálogo utilizando o título em minúsculo como chave.
     * @param f O objeto Filme a ser cadastrado.
     */
    void adicionarFilme(Filme f);

    /**
     * Busca um filme no catálogo através do seu título.
     * @param titulo O título do filme buscado.
     * @return O objeto Filme correspondente.
     * @throws filmeNaoEncontradoException Caso o filme não exista no sistema.
     */
    Filme buscarPorTitulo(String titulo) throws filmeNaoEncontradoException;

    /**
     * Remove o primeiro filme encontrado que corresponda ao ano enviado.
     * @param ano O ano do filme que deve ser deletado.
     * @return Mensagem de sucesso na remoção.
     * @throws filmeNaoEncontradoException Caso nenhum filme do ano seja localizado.
     */
    String removerPorAno(int ano) throws filmeNaoEncontradoException;

    /**
     * Vincula um filme cadastrado à lista de favoritos do usuário.
     * @param titulo O título do filme a ser favoritado.
     * @return Mensagem de sucesso na operação.
     * @throws filmeNaoEncontradoException Caso o filme não exista no catálogo geral.
     */
    String adicionarAosFavoritos(String titulo) throws filmeNaoEncontradoException;

    /**
     * Marca um filme do catálogo como assistido e o adiciona ao histórico.
     * @param titulo O título do filme assistido.
     * @return Mensagem de confirmação da operação.
     * @throws filmeNaoEncontradoException Caso o filme não exista no catálogo.
     */
    String marcarComoAssistido(String titulo) throws filmeNaoEncontradoException;

    /**
     * Imprime na tela todos os filmes do histórico de assistidos.
     */
    void listarAssistidos();

    /**
     * Imprime na tela todos os filmes favoritados pelo usuário.
     */
    void listarFavoritos();

    /**
     * Salva os mapas de dados atuais em um arquivo binário persistente.
     * @throws IOException Se houver erro na gravação do arquivo.
     */
    void salvarDados() throws IOException;

    /**
     * Recupera os mapas de dados gravados anteriormente no arquivo local.
     * @throws IOException Se houver erro na leitura do arquivo.
     * @throws ClassNotFoundException Se a classe dos objetos gravados não for identificada.
     */
    void recuperarDados() throws IOException, ClassNotFoundException;
    /**
     * Adiciona um filme à lista de desejos.
     */
    String adicionarAListaDeDesejos(String titulo) throws filmeNaoEncontradoException;

    /**
     * Lista todos os filmes da lista de desejos.
     */
    void listarListaDeDesejos();

    /**
     * Retorna o mapa da lista de desejos.
     */
    java.util.Map<String, Filme> getListaDeDesejos();
}