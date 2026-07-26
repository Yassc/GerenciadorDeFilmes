package Projetofinalpoo.GerenciadorDeFilmes;

public class Filme implements java.io.Serializable{

    private String titulo;
    private String diretor;
    private int ano;
    private int duracao;

    public Filme(String titulo, String diretor, int ano, int duracao){
        this.titulo = titulo;
        this.diretor = diretor;
        this.ano = ano;
        this.duracao = duracao;
    }
    public Filme(String titulo,  int ano){
        this.titulo = titulo;
        this.ano = ano;
    }
    public Filme(Filme outro) {
        this.titulo = outro.titulo;
        this.diretor = outro.diretor;
        this.ano = outro.ano;
        this.duracao = outro.duracao;
    }


    //metodos
    public String ExibirDetalhes(){
        return toString();
    }

    public boolean EhClassico(){
        int anoAtual = java.time.LocalDate.now().getYear();
        return anoAtual - ano >= 25;
    }

    public String conversorDuracao(){
        int horas = this.duracao/ 60;
        int minutos = this.duracao%60;
        return horas + "h" + minutos + "min";
    }


    //to String
    @Override
    public String toString() {
        return "Título: " + this.titulo +
                "\nDirigido por: " + this.diretor+
                "\nAno de lançamento: " + this.ano + " (É clássico?" + EhClassico() + ")"+
                "\nDuração: "+ conversorDuracao() + "(" + this.duracao + "min)";

    }

    //getters e setters
    public int getAno() {
        return ano;
    }

    public String getDiretor() {
        return diretor;
    }

    public int getDuracao() {
        return duracao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
