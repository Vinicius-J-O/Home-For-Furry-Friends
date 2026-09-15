package veterinaria.view;

import veterinaria.dao.VeterinarioDAO;
import veterinaria.model.Veterinario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaVeterinario extends JFrame {

    private JTextField txtId, txtNome, txtCrmv, txtTelefone, txtEspecialidade;
    private JTable tabela;
    private DefaultTableModel tableModel;
    private VeterinarioDAO vetDAO;

    // cores usadas na tela
    private final Color verdeEscuro = Color.decode("#245B52");
    private final Color verde = Color.decode("#3D8B7D");
    private final Color azul = Color.decode("#4F9DAC");
    private final Color fundo = Color.decode("#F3F5F4");
    private final Color branco = Color.WHITE;
    private final Color vermelho = Color.decode("#D9534F");
    private final Color cinza = Color.decode("#757575");
    private final Color borda = Color.decode("#D5DDDA");

    public TelaVeterinario() {
        vetDAO = new VeterinarioDAO();

        setTitle("Home for Furry Friends - Cadastro de Veterinários");
        setSize(1000, 560);
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

        JLabel lblTitulo = new JLabel("Cadastro de Veterinários");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 21));

        painelHeader.add(lblTitulo);
        painelPrincipal.add(painelHeader, BorderLayout.NORTH);

        // painel da esquerda
        JPanel painelEsquerda = new JPanel(new BorderLayout());
        painelEsquerda.setBackground(fundo);
        painelEsquerda.setPreferredSize(new Dimension(420, 450));
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
        painelForm.add(criarLabel("ID"), gbc);

        txtId = criarCampo(18);
        txtId.setEditable(false);

        gbc.gridx = 1;
        painelForm.add(txtId, gbc);

        // nome
        gbc.gridx = 0;
        gbc.gridy = 1;
        painelForm.add(criarLabel("Nome *"), gbc);

        txtNome = criarCampo(18);

        gbc.gridx = 1;
        painelForm.add(txtNome, gbc);

        // CRMV
        gbc.gridx = 0;
        gbc.gridy = 2;
        painelForm.add(criarLabel("CRMV *"), gbc);

        txtCrmv = criarCampo(18);

        gbc.gridx = 1;
        painelForm.add(txtCrmv, gbc);

        // telefone
        gbc.gridx = 0;
        gbc.gridy = 3;
        painelForm.add(criarLabel("Telefone"), gbc);

        txtTelefone = criarCampo(18);

        gbc.gridx = 1;
        painelForm.add(txtTelefone, gbc);

        // especialidade
        gbc.gridx = 0;
        gbc.gridy = 4;
        painelForm.add(criarLabel("Especialidade"), gbc);

        txtEspecialidade = criarCampo(18);

        gbc.gridx = 1;
        painelForm.add(txtEspecialidade, gbc);

        painelEsquerda.add(painelForm, BorderLayout.CENTER);

        // botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 15));
        painelBotoes.setBackground(fundo);

        JButton btnSalvar = criarBotao("Salvar", verde);
        JButton btnEditar = criarBotao("Atualizar", azul);
        JButton btnExcluir = criarBotao("Excluir", vermelho);
        JButton btnLimpar = criarBotao("Limpar", cinza);

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnLimpar);

        painelEsquerda.add(painelBotoes, BorderLayout.SOUTH);
        painelPrincipal.add(painelEsquerda, BorderLayout.WEST);

        // tabela
        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.setBackground(fundo);
        painelTabela.setBorder(new EmptyBorder(25, 0, 25, 25));

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Nome", "CRMV", "Telefone", "Especialidade"}, 0
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

        // centraliza o ID
        DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
        centro.setHorizontalAlignment(SwingConstants.CENTER);
        tabela.getColumnModel().getColumn(0).setCellRenderer(centro);

        tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(140);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(110);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(150);

        JScrollPane scrollTabela = new JScrollPane(tabela);
        scrollTabela.setBorder(BorderFactory.createLineBorder(borda));
        scrollTabela.getViewport().setBackground(Color.WHITE);

        painelTabela.add(scrollTabela, BorderLayout.CENTER);
        painelPrincipal.add(painelTabela, BorderLayout.CENTER);

        // ações dos botões
        btnSalvar.addActionListener(e -> salvarVet());
        btnEditar.addActionListener(e -> atualizarVet());
        btnExcluir.addActionListener(e -> excluirVet());
        btnLimpar.addActionListener(e -> limparCampos());

        // quando clica numa linha, coloca os dados nos campos
        tabela.getSelectionModel().addListSelectionListener(e -> selecionarLinha());

        carregarTabela();
    }

    // cria os textos dos campos no mesmo estilo
    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        return label;
    }

    // cria os campos com o mesmo tamanho
    private JTextField criarCampo(int colunas) {
        JTextField campo = new JTextField(colunas);
        campo.setPreferredSize(new Dimension(190, 30));
        campo.setFont(new Font("Arial", Font.PLAIN, 13));
        return campo;
    }

    // cria os botões já com o estilo pronto
    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setFont(new Font("Arial", Font.BOLD, 12));
        botao.setPreferredSize(new Dimension(90, 36));
        return botao;
    }

    // pega os dados da linha clicada e coloca nos campos
    private void selecionarLinha() {
        int linha = tabela.getSelectedRow();

        if (linha != -1) {
            txtId.setText(tableModel.getValueAt(linha, 0).toString());
            txtNome.setText(tableModel.getValueAt(linha, 1).toString());
            txtCrmv.setText(tableModel.getValueAt(linha, 2).toString());

            txtTelefone.setText(
                    tableModel.getValueAt(linha, 3) != null
                            ? tableModel.getValueAt(linha, 3).toString()
                            : ""
            );

            txtEspecialidade.setText(
                    tableModel.getValueAt(linha, 4) != null
                            ? tableModel.getValueAt(linha, 4).toString()
                            : ""
            );
        }
    }

    // carrega todos os veterinarios do banco
    private void carregarTabela() {
        tableModel.setRowCount(0);

        for (Veterinario v : vetDAO.listarTodos()) {
            tableModel.addRow(new Object[]{
                    v.getId(),
                    v.getNome(),
                    v.getCrmv(),
                    v.getTelefone(),
                    v.getEspecialidade()
            });
        }
    }

    // cadastra um novo veterinario
    private void salvarVet() {
        if (txtNome.getText().trim().isEmpty() || txtCrmv.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e CRMV são obrigatórios!");
            return;
        }

        Veterinario v = new Veterinario(
                txtNome.getText(),
                txtCrmv.getText(),
                txtTelefone.getText(),
                txtEspecialidade.getText()
        );

        if (vetDAO.cadastrar(v)) {
            JOptionPane.showMessageDialog(this, "Veterinário cadastrado!");
            limparCampos();
            carregarTabela();
        }
    }

    // atualiza o veterinario selecionado
    private void atualizarVet() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um veterinário na tabela para atualizar!");
            return;
        }

        if (txtNome.getText().trim().isEmpty() || txtCrmv.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e CRMV são obrigatórios!");
            return;
        }

        Veterinario v = new Veterinario(
                Integer.parseInt(txtId.getText()),
                txtNome.getText(),
                txtCrmv.getText(),
                txtTelefone.getText(),
                txtEspecialidade.getText()
        );

        if (vetDAO.atualizar(v)) {
            JOptionPane.showMessageDialog(this, "Veterinário atualizado com sucesso!");
            limparCampos();
            carregarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar veterinário.");
        }
    }

    // exclui o veterinario escolhido
    private void excluirVet() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um veterinário para excluir!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir este veterinário?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(txtId.getText());

            if (vetDAO.excluir(id)) {
                JOptionPane.showMessageDialog(this, "Veterinário excluído com sucesso!");
                limparCampos();
                carregarTabela();
            }
        }
    }

    // limpa os campos e tira a seleção da tabela
    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtCrmv.setText("");
        txtTelefone.setText("");
        txtEspecialidade.setText("");
        tabela.clearSelection();
    }
}