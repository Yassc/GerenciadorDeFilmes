package Projetofinalpoo.GerenciadorDeFilmes;

public class filmeNaoEncontradoException extends Exception {
    public filmeNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}