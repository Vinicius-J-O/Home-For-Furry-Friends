package veterinaria.view;

import veterinaria.dao.ExameDAO;
import veterinaria.model.Exame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaExame extends JFrame {
    private JTextField txtId, txtNome, txtValor;
    private JTextArea txtDescricao;
    private JTable tabela;
    private DefaultTableModel tableModel;
    private ExameDAO exameDAO;

    public TelaExame() {
        exameDAO = new ExameDAO();

        setTitle("Home for Furry Friends - Cadastro de Exames");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel painelHeader = new JPanel();
        painelHeader.setBackground(Color.decode("#2E7D6B"));
        JLabel lblTitulo = new JLabel("Cadastro de Exames");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        painelHeader.add(lblTitulo);
        add(painelHeader, BorderLayout.NORTH);

        JPanel painelForm = new JPanel(new GridLayout(4, 2, 5, 5));
        painelForm.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        painelForm.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEditable(false);
        painelForm.add(txtId);

        painelForm.add(new JLabel("Nome*:"));
        txtNome = new JTextField();
        painelForm.add(txtNome);

        painelForm.add(new JLabel("Descrição:"));
        txtDescricao = new JTextArea(2, 20);
        painelForm.add(new JScrollPane(txtDescricao));

        painelForm.add(new JLabel("Valor (R$)*:"));
        txtValor = new JTextField("0.00");
        painelForm.add(txtValor);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBackground(Color.decode("#43A047"));
        btnSalvar.setForeground(Color.WHITE);

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setBackground(Color.decode("#D9534F"));
        btnExcluir.setForeground(Color.WHITE);

        JPanel painelBotoes = new JPanel(new FlowLayout());
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnExcluir);

        JPanel painelEsquerda = new JPanel(new BorderLayout());
        painelEsquerda.add(painelForm, BorderLayout.CENTER);
        painelEsquerda.add(painelBotoes, BorderLayout.SOUTH);

        add(painelEsquerda, BorderLayout.WEST);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Valor"}, 0);
        tabela = new JTable(tableModel);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        btnSalvar.addActionListener(e -> salvar());
        btnExcluir.addActionListener(e -> excluir());

        carregarTabela();
    }

    private void carregarTabela() {
        tableModel.setRowCount(0);
        for (Exame ex : exameDAO.listarTodos()) {
            tableModel.addRow(new Object[]{ex.getId(), ex.getNome(), ex.getValor()});
        }
    }

    private void salvar() {
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome do exame é obrigatório!");
            return;
        }
        try {
            double valor = Double.parseDouble(txtValor.getText());
            Exame ex = new Exame(txtNome.getText(), txtDescricao.getText(), valor);
            if (exameDAO.cadastrar(ex)) {
                JOptionPane.showMessageDialog(this, "Exame cadastrado!");
                txtNome.setText("");
                txtDescricao.setText("");
                txtValor.setText("0.00");
                carregarTabela();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Informe um valor numérico válido!");
        }
    }

    private void excluir() {
        int linha = tabela.getSelectedRow();
        if (linha != -1) {
            int id = (int) tableModel.getValueAt(linha, 0);
            if (exameDAO.excluir(id)) {
                JOptionPane.showMessageDialog(this, "Exame excluído!");
                carregarTabela();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um exame na tabela para excluir.");
        }
    }
}