package GerenciadorDeFilmes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Map;

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
        setSize(550, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        getContentPane().setBackground(new Color(44, 62, 80));
        setLayout(new BorderLayout(20, 20));

        JLabel lblTitulo = new JLabel("Catálogo de Filmes", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Times New Roman", Font.PLAIN, 46));
        lblTitulo.setForeground(Color.WHITE);
        add(lblTitulo, BorderLayout.CENTER);

        JLabel lblSubtitulo = new JLabel("Utilize os menus superiores para gerenciar o sistema", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Arial", Font.ITALIC, 14));
        lblSubtitulo.setForeground(Color.LIGHT_GRAY);
        add(lblSubtitulo, BorderLayout.SOUTH);

        // --- BARRA DE MENUS ---
        JMenuBar barraMenu = new JMenuBar();

        // Menu 1: Catálogo
        JMenu menuCatalogo = new JMenu("Catálogo");
        JMenuItem itemCadastrar = new JMenuItem("Cadastrar Filme");
        JMenuItem itemPesquisar = new JMenuItem("Pesquisar Filme");
        JMenuItem itemApagar = new JMenuItem("Apagar por Ano");
        JMenuItem itemAvaliar = new JMenuItem("Avaliar Filme");
        JMenuItem itemFiltrarNota = new JMenuItem("Filtrar por Nota Mínima");

        menuCatalogo.add(itemCadastrar);
        menuCatalogo.add(itemPesquisar);
        menuCatalogo.add(itemApagar);
        menuCatalogo.addSeparator();
        menuCatalogo.add(itemAvaliar);
        menuCatalogo.add(itemFiltrarNota);

        // Menu 2: Minhas Listas
        JMenu menuListas = new JMenu("Minhas Listas");
        JMenuItem itemAddFavorito = new JMenuItem("Adicionar aos Favoritos");
        JMenuItem itemListarFavoritos = new JMenuItem("Listar Favoritos");
        JMenuItem itemMarcarAssistido = new JMenuItem("Marcar como Assistido");
        JMenuItem itemListarAssistidos = new JMenuItem("Listar Historico Assistidos");
        JMenuItem itemAddDesejo = new JMenuItem("Adicionar à Lista de Desejos");
        JMenuItem itemListarDesejos = new JMenuItem("Listar Lista de Desejos");

        menuListas.add(itemAddFavorito);
        menuListas.add(itemListarFavoritos);
        menuListas.addSeparator();
        menuListas.add(itemMarcarAssistido);
        menuListas.add(itemListarAssistidos);
        menuListas.addSeparator();
        menuListas.add(itemAddDesejo);
        menuListas.add(itemListarDesejos);

        // Menu 3: Sistema
        JMenu menuSistema = new JMenu("Sistema");
        JMenuItem itemSalvar = new JMenuItem("Salvar Dados");
        menuSistema.add(itemSalvar);

        // Monta a barra de menus
        barraMenu.add(menuCatalogo);
        barraMenu.add(menuListas);
        barraMenu.add(menuSistema);
        setJMenuBar(barraMenu);

        // --- LISTENERS DE AÇÕES DO CATÁLOGO ---

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

        itemAvaliar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = JOptionPane.showInputDialog(JanelaPrincipal.this, "Digite o título do filme a avaliar:");
                if (titulo != null && !titulo.trim().isEmpty()) {
                    try {
                        double nota = Double.parseDouble(JOptionPane.showInputDialog(JanelaPrincipal.this, "Digite a nota do filme (0 a 10):"));
                        sistema.avaliarFilme(titulo, nota);
                        JOptionPane.showMessageDialog(JanelaPrincipal.this, "Filme '" + titulo + "' avaliado com sucesso com nota " + nota + "!");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(JanelaPrincipal.this, "Erro: A nota precisa ser um número!", "Erro de Input", JOptionPane.ERROR_MESSAGE);
                    } catch (filmeNaoEncontradoException | NotaDeAvaliacaoInvalidaException ex) {
                        JOptionPane.showMessageDialog(JanelaPrincipal.this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        itemFiltrarNota.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputNota = JOptionPane.showInputDialog(JanelaPrincipal.this, "Exibir filmes com nota maior ou igual a:");
                if (inputNota != null) {
                    try {
                        double notaMinima = Double.parseDouble(inputNota);
                        String relatorio = sistema.buscarFilmesPorNotaMinima(notaMinima);
                        JOptionPane.showMessageDialog(JanelaPrincipal.this, relatorio, "Filmes Bem Avaliados", JOptionPane.INFORMATION_MESSAGE);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(JanelaPrincipal.this, "Digite um valor numérico válido.");
                    }
                }
            }
        });

        // --- LISTENERS DE MINHAS LISTAS ---

        itemAddFavorito.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = JOptionPane.showInputDialog(JanelaPrincipal.this, "Digite o título do filme para favoritar:");
                if (titulo != null && !titulo.trim().isEmpty()) {
                    try {
                        String msg = sistema.adicionarAosFavoritos(titulo);
                        JOptionPane.showMessageDialog(JanelaPrincipal.this, msg, "Favoritos", JOptionPane.INFORMATION_MESSAGE);
                    } catch (filmeNaoEncontradoException ex) {
                        JOptionPane.showMessageDialog(JanelaPrincipal.this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        itemListarFavoritos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sistema.listarFavoritos();
                JOptionPane.showMessageDialog(JanelaPrincipal.this, "A lista de favoritos foi exibida no console do sistema!", "Favoritos", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        itemMarcarAssistido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = JOptionPane.showInputDialog(JanelaPrincipal.this, "Digite o título do filme assistido:");
                if (titulo != null && !titulo.trim().isEmpty()) {
                    try {
                        String msg = sistema.marcarComoAssistido(titulo);
                        JOptionPane.showMessageDialog(JanelaPrincipal.this, msg, "Histórico", JOptionPane.INFORMATION_MESSAGE);
                    } catch (filmeNaoEncontradoException ex) {
                        JOptionPane.showMessageDialog(JanelaPrincipal.this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        itemListarAssistidos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sistema.listarAssistidos();
                if (sistema instanceof GerenciadorDeFilmes) {
                    GerenciadorDeFilmes g = (GerenciadorDeFilmes) sistema;
                    Map<String, Filme> assistidos = g.getHistoricoAssistidos();
                    if (assistidos == null || assistidos.isEmpty()) {
                        JOptionPane.showMessageDialog(JanelaPrincipal.this, "Seu histórico de assistidos está vazio.", "Histórico de Assistidos", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        StringBuilder sb = new StringBuilder("--- HISTÓRICO DE ASSISTIDOS ---\n\n");
                        for (Filme f : assistidos.values()) {
                            sb.append("• ").append(f.getTitulo()).append(" (").append(f.getAno()).append(")\n");
                        }
                        JOptionPane.showMessageDialog(JanelaPrincipal.this, sb.toString(), "Histórico de Assistidos", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });

        itemAddDesejo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = JOptionPane.showInputDialog(JanelaPrincipal.this, "Digite o título do filme para a Lista de Desejos:");
                if (titulo != null && !titulo.trim().isEmpty()) {
                    try {
                        String msg = sistema.adicionarAListaDeDesejos(titulo);
                        JOptionPane.showMessageDialog(JanelaPrincipal.this, msg, "Lista de Desejos", JOptionPane.INFORMATION_MESSAGE);
                    } catch (filmeNaoEncontradoException ex) {
                        JOptionPane.showMessageDialog(JanelaPrincipal.this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        itemListarDesejos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sistema.listarListaDeDesejos();
                Map<String, Filme> desejos = sistema.getListaDeDesejos();
                if (desejos == null || desejos.isEmpty()) {
                    JOptionPane.showMessageDialog(JanelaPrincipal.this, "Sua lista de desejos está vazia.", "Lista de Desejos", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    StringBuilder sb = new StringBuilder("--- LISTA DE DESEJOS ---\n\n");
                    for (Filme f : desejos.values()) {
                        sb.append("• ").append(f.getTitulo()).append(" (").append(f.getAno()).append(")\n");
                    }
                    JOptionPane.showMessageDialog(JanelaPrincipal.this, sb.toString(), "Lista de Desejos", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        // --- LISTENER DO SISTEMA ---

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