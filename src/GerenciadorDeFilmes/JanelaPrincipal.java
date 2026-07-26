package GerenciadorDeFilmes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class JanelaPrincipal extends JFrame {

    private InterfaceGerenciador sistema;

    public JanelaPrincipal() {
        sistema = new GerenciadorDeFilmes();

        try {
            sistema.recuperarDados();
        } catch (Exception e) {
            System.out.println("Nenhum dado anterior encontrado. Iniciando catálogo vazio.");
        }

        setTitle("CineManager - Gerenciador de Filmes");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(44, 62, 80));

        setLayout(new BorderLayout(20, 20));

        JLabel lblTitulo = new JLabel("Catálogo de filmes", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Times New Roman", Font.PLAIN, 50));
        lblTitulo.setForeground(Color.WHITE);
        add(lblTitulo, BorderLayout.CENTER);

        JLabel lblSubtitulo = new JLabel("Clique em 'Operações' para gerir o sistema", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Arial", Font.ITALIC, 14));
        lblSubtitulo.setForeground(Color.LIGHT_GRAY);
        add(lblSubtitulo, BorderLayout.SOUTH);


        JMenuBar barraMenu = new JMenuBar();
        JMenu menuOperacoes = new JMenu("Operações");

        JMenuItem itemCadastrar = new JMenuItem("Cadastrar");
        JMenuItem itemPesquisar = new JMenuItem("Pesquisar");
        JMenuItem itemApagar = new JMenuItem("Apagar por Ano");
        JMenuItem itemSalvar = new JMenuItem("Salvar");

        menuOperacoes.add(itemCadastrar);
        menuOperacoes.add(itemPesquisar);
        menuOperacoes.add(itemApagar);
        menuOperacoes.addSeparator();
        menuOperacoes.add(itemSalvar);

        barraMenu.add(menuOperacoes);
        setJMenuBar(barraMenu);

        itemCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String titulo = JOptionPane.showInputDialog(JanelaPrincipal.this, "Digite o título do filme:");
                    if (titulo == null || titulo.trim().isEmpty()) return;

                    String diretor = JOptionPane.showInputDialog(JanelaPrincipal.this, "Digite o nome do diretor:");
                    int ano = Integer.parseInt(JOptionPane.showInputDialog(JanelaPrincipal.this, "Digite o ano de lançamento:"));
                    int duracao = Integer.parseInt(JOptionPane.showInputDialog(JanelaPrincipal.this, "Digite a duração (em minutos):"));

                    Filme novoFilme = new Filme(titulo, diretor, ano, duracao);
                    sistema.adicionarFilme(novoFilme);

                    JOptionPane.showMessageDialog(JanelaPrincipal.this, "Filme '" + titulo + "' cadastrado com sucesso!");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(JanelaPrincipal.this, "Erro: Ano e Duração têm de ser números inteiros!", "Erro de Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        itemPesquisar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String busca = JOptionPane.showInputDialog(JanelaPrincipal.this, "Digite o título do filme a pesquisar:");
                if (busca != null && !busca.trim().isEmpty()) {
                    try {
                        Filme f = sistema.buscarPorTitulo(busca);
                        JOptionPane.showMessageDialog(JanelaPrincipal.this, f.ExibirDetalhes(), "Filme Encontrado", JOptionPane.INFORMATION_MESSAGE);
                    } catch (filmeNaoEncontradoException ex) {
                        JOptionPane.showMessageDialog(JanelaPrincipal.this, "Erro: " + ex.getMessage(), "Não Encontrado", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

        itemApagar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputAno = JOptionPane.showInputDialog(JanelaPrincipal.this, "Digite o ano para remover o filme:");
                if (inputAno != null) {
                    try {
                        int ano = Integer.parseInt(inputAno);
                        String resultado = sistema.removerPorAno(ano);
                        JOptionPane.showMessageDialog(JanelaPrincipal.this, resultado);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(JanelaPrincipal.this, "Digite um ano válido.");
                    } catch (filmeNaoEncontradoException ex) {
                        JOptionPane.showMessageDialog(JanelaPrincipal.this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        itemSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sistema.salvarDados();
                    JOptionPane.showMessageDialog(JanelaPrincipal.this, "Dados gravados com sucesso via Serializable!");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(JanelaPrincipal.this, "Erro ao salvar ficheiro: " + ex.getMessage(), "Erro I/O", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new JanelaPrincipal().setVisible(true);
        });
    }
}