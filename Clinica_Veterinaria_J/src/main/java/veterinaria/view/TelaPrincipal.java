package veterinaria.view;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("Home for Furry Friends - Sistema Veterinário");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        JPanel painelHeader = new JPanel();
        painelHeader.setBackground(Color.decode("#2E7D6B"));
        JLabel lblTitulo = new JLabel("Home for Furry Friends");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 22));
        painelHeader.add(lblTitulo);
        add(painelHeader, BorderLayout.NORTH);

        // Grade de Botões
        JPanel painelBotoes = new JPanel(new GridLayout(3, 2, 15, 15));
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JButton btnTutores = new JButton("Gerenciar Tutores");
        JButton btnPets = new JButton("Gerenciar Pets");
        JButton btnVets = new JButton("Gerenciar Veterinários");
        JButton btnVacinas = new JButton("Carteira de Vacinas");
        JButton btnExames = new JButton("Gerenciar Exames");
        JButton btnProcedimentos = new JButton("Gerenciar Procedimentos");

        Font fonteBotao = new Font("Arial", Font.BOLD, 14);
        btnTutores.setFont(fonteBotao);
        btnPets.setFont(fonteBotao);
        btnVets.setFont(fonteBotao);
        btnVacinas.setFont(fonteBotao);
        btnExames.setFont(fonteBotao);
        btnProcedimentos.setFont(fonteBotao);

        painelBotoes.add(btnTutores);
        painelBotoes.add(btnPets);
        painelBotoes.add(btnVets);
        painelBotoes.add(btnVacinas);
        painelBotoes.add(btnExames);
        painelBotoes.add(btnProcedimentos);

        add(painelBotoes, BorderLayout.CENTER);

        // Painel Inferior para o Atendimento Clínico (Destaque)
        JPanel painelAtendimento = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelAtendimento.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        JButton btnAtendimentos = new JButton("Registrar Atendimento Clínico");
        btnAtendimentos.setFont(new Font("Arial", Font.BOLD, 15));
        btnAtendimentos.setBackground(Color.decode("#43A047"));
        btnAtendimentos.setForeground(Color.WHITE);
        btnAtendimentos.setPreferredSize(new Dimension(350, 45));
        painelAtendimento.add(btnAtendimentos);

        add(painelAtendimento, BorderLayout.SOUTH);

        // Ações dos Botões
        btnTutores.addActionListener(e -> new TelaTutor().setVisible(true));
        btnPets.addActionListener(e -> new TelaPet().setVisible(true));
        btnVets.addActionListener(e -> new TelaVeterinario().setVisible(true));
        btnVacinas.addActionListener(e -> new TelaVacina().setVisible(true));
        btnExames.addActionListener(e -> new TelaExame().setVisible(true));
        btnProcedimentos.addActionListener(e -> new TelaProcedimento().setVisible(true));
        btnAtendimentos.addActionListener(e -> new TelaAtendimento().setVisible(true));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}