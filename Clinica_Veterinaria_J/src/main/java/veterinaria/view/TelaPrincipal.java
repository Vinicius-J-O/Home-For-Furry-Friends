package veterinaria.view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaPrincipal extends JFrame {
    private static final Color COR_PRINCIPAL = Color.decode("#2E7D6B");
    private static final Color COR_PRINCIPAL_ESCURA = Color.decode("#205B4E");
    private static final Color COR_DESTAQUE = Color.decode("#5BB8C5");
    private static final Color COR_FUNDO = Color.decode("#F7FAF9");
    private static final Color COR_CARD = Color.WHITE;
    private static final Color COR_TEXTO = Color.decode("#263238");
    private static final Color COR_TEXTO_SECUNDARIO = Color.decode("#607D7B");
    private static final Color COR_BORDA = Color.decode("#D9E5E2");
    private static final Color COR_SUCESSO = Color.decode("#43A047");

    public TelaPrincipal() {

        // Configurações básicas da janela:
        setTitle("Home for Furry Friends - Sistema Veterinário");
        setSize(760, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela na tela do computador
        setLayout(new BorderLayout());
        getContentPane().setBackground(COR_FUNDO);

        // Header
        add(criarCabecalho(), BorderLayout.NORTH);

        // Painel central
        JPanel painelConteudo = new JPanel(new BorderLayout(0, 20));
        painelConteudo.setBackground(COR_FUNDO);
        painelConteudo.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        // Botões agora em formato de "cards" com seus respectivos ícones.
        JPanel painelBotoes = new JPanel(new GridLayout(2, 3, 18, 18));
        painelBotoes.setBackground(COR_FUNDO);

        JButton btnTutores = criarBotaoCard("Tutores", "👤");
        JButton btnPets = criarBotaoCard("Pets", "🐾");
        JButton btnVets = criarBotaoCard("Veterinários", "🏥");
        JButton btnVacinas = criarBotaoCard("Vacinas", "💉");
        JButton btnExames = criarBotaoCard("Exames", "🔬");
        JButton btnProcedimentos = criarBotaoCard("Procedimentos", "📋");

        painelBotoes.add(btnTutores);
        painelBotoes.add(btnPets);
        painelBotoes.add(btnVets);
        painelBotoes.add(btnVacinas);
        painelBotoes.add(btnExames);
        painelBotoes.add(btnProcedimentos);

        painelConteudo.add(painelBotoes, BorderLayout.CENTER);

        // Footer
        JPanel painelRodape = new JPanel();
        painelRodape.setLayout(new BoxLayout(painelRodape, BoxLayout.Y_AXIS));
        painelRodape.setBackground(COR_FUNDO);

        JButton btnAtendimentos = new JButton("Registrar Atendimento Clínico");
        btnAtendimentos.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnAtendimentos.setBackground(COR_SUCESSO);
        btnAtendimentos.setForeground(Color.WHITE);
        // Remove aquela linha que aparece em volta do texto
        // ou do ícone quando o botão é selecionado ou clicado.
        btnAtendimentos.setFocusPainted(false);
        btnAtendimentos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Muda o ponteiro do mouse
        btnAtendimentos.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        btnAtendimentos.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza o botão horizontalmente (eixo X)
        // Efeito hover que escurece um pouco na cor verde quando passa o mouse por cima.
        btnAtendimentos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) { btnAtendimentos.setBackground(COR_PRINCIPAL_ESCURA); }
            @Override
            public void mouseExited(MouseEvent e) { btnAtendimentos.setBackground(COR_SUCESSO); }
        });

        JLabel lblRodape = new JLabel("Home for Furry Friends — Sistema de Gerenciamento Veterinário");
        lblRodape.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblRodape.setForeground(COR_TEXTO_SECUNDARIO);
        lblRodape.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblRodape.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        painelRodape.add(btnAtendimentos);
        painelRodape.add(lblRodape);

        painelConteudo.add(painelRodape, BorderLayout.SOUTH);

        add(painelConteudo, BorderLayout.CENTER);

        // Adiciona ações para os botões.
        // O ".addActionListener(e -> ...)" registra o que deve acontecer
        // quando o botão é clicado. O "e ->" é uma "expressão lambda": que
        // seria uma forma curta de escrever uma função ao invés de criar uma
        // classe inteira só para isso. Aqui, ao clicar, chamamos o método 
        // abrirTela() passando uma nova instância da tela correspondente.
        btnTutores.addActionListener(e -> abrirTela(new TelaTutor()));
        btnPets.addActionListener(e -> abrirTela(new TelaPet()));
        btnVets.addActionListener(e -> abrirTela(new TelaVeterinario()));
        btnVacinas.addActionListener(e -> abrirTela(new TelaVacina()));
        btnExames.addActionListener(e -> abrirTela(new TelaExame()));
        btnProcedimentos.addActionListener(e -> abrirTela(new TelaProcedimento()));
        btnAtendimentos.addActionListener(e -> abrirTela(new TelaAtendimento()));
    }

    // Monta o painel do header (fundo verde escuro, título grande e um
    // subtítulo menor abaixo, ambos centralizados).
    private JPanel criarCabecalho() {
        JPanel painelHeader = new JPanel();
        painelHeader.setLayout(new BoxLayout(painelHeader, BoxLayout.Y_AXIS)); // Deixa o layout empilhado na vertical
        painelHeader.setBackground(COR_PRINCIPAL);
        painelHeader.setBorder(BorderFactory.createEmptyBorder(18, 10, 18, 10));

        JLabel lblTitulo = new JLabel("Home for Furry Friends");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubtitulo = new JLabel("Sistema de Gerenciamento Veterinário");
        lblSubtitulo.setForeground(Color.decode("#E9F5F1"));
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSubtitulo.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));

        painelHeader.add(lblTitulo);
        painelHeader.add(lblSubtitulo);
        return painelHeader;
    }

    // Cria um botão de navegação no formato de "card" (com fundo branco,
    // ícone grande em cima, texto embaixo, borda arredondada suave, e um
    // hover quando se passa o mouse por cima).
    private JButton criarBotaoCard(String texto, String emoji) {
        // O texto do botão é montado em HTML simples (suportado pelo Swing
        // dentro de um JLabel/JButton) para conseguir colocar o ícone em
        // uma linha e o texto em outra, com tamanhos de fonte diferentes.
        JButton btn = new JButton("<html><div style='text-align:center;'>"
                                + "<span style='font-size:24px;'>" + emoji + "</span><br>"
                                + "<span style='font-size:13px;'><b>" + texto + "</b></span>"
                                + "</div></html>");

        btn.setBackground(COR_CARD);
        btn.setForeground(COR_TEXTO);
        btn.setFocusPainted(false); 
        btn.setFocusable(false); // Não permite que o botão seja focado usando tab
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // createLineBorder(cor, espessura, arredondado), desenha uma borda fina
        // com cantos um pouco arredondados, isso dá um efeito de "card" sem
        // precisar desenhar nada manualmente.
        Border bordaNormal = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_BORDA, 1, true),
                BorderFactory.createEmptyBorder(12, 8, 12, 8) // Cria uma margem transparente (cima, esquerda, baixo, direita)
        );
        Border bordaDestaque = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_DESTAQUE, 2, true),
                BorderFactory.createEmptyBorder(11, 7, 11, 7)
        );
        btn.setBorder(bordaNormal);

        // MouseAdapter é uma forma de "escutar" os eventos do mouse (entrar e
        // sair da área do botão), sem precisar implementar todos os métodos da
        // interface MouseListener, apenas sobrescrevendo os dois que usamos.
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBorder(bordaDestaque);
                btn.setBackground(COR_FUNDO);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBorder(bordaNormal);
                btn.setBackground(COR_CARD);
            }
        });

        return btn;
    }

    // Método auxiliar que abre uma nova tela (JFrame) sempre 40 pixels para 
    // a direita e para baixo em relação à posição atual da TelaPrincipal.
    // Isso garante que a nova janela nunca abra exatamente em cima da anterior
    // ficando sempre visível que são janelas diferentes.
    private void abrirTela(JFrame tela) {
        tela.setLocation(getX() + 40, getY() + 40);
        tela.setVisible(true);
    }

    // Teste para a tela sozinha, sem passar pelo menu principal.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}
