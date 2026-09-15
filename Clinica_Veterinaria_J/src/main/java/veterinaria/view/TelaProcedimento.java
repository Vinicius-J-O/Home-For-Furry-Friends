package veterinaria.view;

import veterinaria.dao.ProcedimentoDAO;
import veterinaria.model.Procedimento;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaProcedimento extends JFrame {

    private JTextField txtId, txtNome, txtValor;
    private JTextArea txtDescricao;
    private JTable tabela;
    private DefaultTableModel tableModel;
    private ProcedimentoDAO procedimentoDAO;

    // cores usadas na tela
    private final Color verdeEscuro = Color.decode("#245B52");
    private final Color verde = Color.decode("#3D8B7D");
    private final Color fundo = Color.decode("#F3F5F4");
    private final Color branco = Color.WHITE;
    private final Color borda = Color.decode("#D5DDDA");

    public TelaProcedimento() {
        procedimentoDAO = new ProcedimentoDAO();

        setTitle("Home for Furry Friends - Cadastro de Procedimentos");
        setSize(900, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(fundo);
        setContentPane(painelPrincipal);

        // cabeçalho
        JPanel painelHeader = new JPanel();
        painelHeader.setBackground(verdeEscuro);
        painelHeader.setBorder(new EmptyBorder(12, 10, 12, 10));

        JLabel lblTitulo = new JLabel("Cadastro de Procedimentos");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 21));

        painelHeader.add(lblTitulo);
        painelPrincipal.add(painelHeader, BorderLayout.NORTH);

        // lado esquerdo
        JPanel painelEsquerda = new JPanel(new BorderLayout());
        painelEsquerda.setBackground(fundo);
        painelEsquerda.setPreferredSize(new Dimension(420, 420));
        painelEsquerda.setBorder(new EmptyBorder(25, 30, 20, 20));

        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setBackground(branco);
        painelForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borda),
                new EmptyBorder(20, 22, 20, 22)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 8, 7, 8);
        gbc.anchor = GridBagConstraints.WEST;

        // ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        painelForm.add(criarLabel("ID"), gbc);

        txtId = criarCampo(18);
        txtId.setEditable(false);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelForm.add(txtId, gbc);

        // nome
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        painelForm.add(criarLabel("Nome *"), gbc);

        txtNome = criarCampo(18);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelForm.add(txtNome, gbc);

        // descrição
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        painelForm.add(criarLabel("Descrição"), gbc);

        txtDescricao = new JTextArea(4, 18);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        txtDescricao.setFont(new Font("Arial", Font.PLAIN, 13));

        JScrollPane scrollDescricao = new JScrollPane(txtDescricao);
        scrollDescricao.setPreferredSize(new Dimension(200, 80));

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelForm.add(scrollDescricao, gbc);

        // valor
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        painelForm.add(criarLabel("Valor (R$) *"), gbc);

        txtValor = criarCampo(12);
        txtValor.setText("0.00");

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelForm.add(txtValor, gbc);

        painelEsquerda.add(painelForm, BorderLayout.NORTH);

        // botão
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBackground(verde);
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 12));
        btnSalvar.setPreferredSize(new Dimension(110, 38));

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 15));
        painelBotoes.setBackground(fundo);
        painelBotoes.add(btnSalvar);

        painelEsquerda.add(painelBotoes, BorderLayout.SOUTH);
        painelPrincipal.add(painelEsquerda, BorderLayout.WEST);

        // tabela
        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.setBackground(fundo);
        painelTabela.setBorder(new EmptyBorder(25, 0, 25, 25));

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Nome", "Valor"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(tableModel);
        tabela.setRowHeight(31);
        tabela.setFont(new Font("Arial", Font.PLAIN, 12));
        tabela.setGridColor(Color.decode("#E1E7E5"));
        tabela.setShowVerticalLines(false);
        tabela.setShowHorizontalLines(true);
        tabela.setSelectionBackground(Color.decode("#CFE5DF"));
        tabela.setSelectionForeground(Color.BLACK);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tabela.getTableHeader().setBackground(verdeEscuro);
        tabela.getTableHeader().setForeground(Color.WHITE);
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabela.getTableHeader().setPreferredSize(new Dimension(0, 35));
        tabela.getTableHeader().setReorderingAllowed(false);

        // centraliza ID e valor
        DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
        centro.setHorizontalAlignment(SwingConstants.CENTER);

        tabela.getColumnModel().getColumn(0).setCellRenderer(centro);
        tabela.getColumnModel().getColumn(2).setCellRenderer(centro);

        tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(180);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(100);

        JScrollPane scrollTabela = new JScrollPane(tabela);
        scrollTabela.setBorder(BorderFactory.createLineBorder(borda));
        scrollTabela.getViewport().setBackground(Color.WHITE);

        painelTabela.add(scrollTabela, BorderLayout.CENTER);
        painelPrincipal.add(painelTabela, BorderLayout.CENTER);

        // salva o procedimento
        btnSalvar.addActionListener(e -> salvar());

        carregarTabela();
    }

    // cria os labels no mesmo estilo
    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        return label;
    }

    // cria os campos no mesmo padrão
    private JTextField criarCampo(int colunas) {
        JTextField campo = new JTextField(colunas);
        campo.setPreferredSize(new Dimension(190, 30));
        campo.setFont(new Font("Arial", Font.PLAIN, 13));
        return campo;
    }

    // pega os procedimentos do banco e mostra na tabela
    private void carregarTabela() {
        tableModel.setRowCount(0);

        for (Procedimento p : procedimentoDAO.listarTodos()) {
            tableModel.addRow(new Object[]{
                    p.getId(),
                    p.getNome(),
                    String.format("R$ %.2f", p.getValor())
            });
        }
    }

    // cadastra um novo procedimento
    private void salvar() {
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome é obrigatório!");
            return;
        }

        try {
            // permite digitar valor com ponto ou virgula
            double valor = Double.parseDouble(
                    txtValor.getText().replace(",", ".")
            );

            Procedimento p = new Procedimento(
                    txtNome.getText(),
                    txtDescricao.getText(),
                    valor
            );

            if (procedimentoDAO.cadastrar(p)) {
                JOptionPane.showMessageDialog(this, "Procedimento cadastrado!");

                txtNome.setText("");
                txtDescricao.setText("");
                txtValor.setText("0.00");

                carregarTabela();
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Valor inválido!");
        }
    }
}