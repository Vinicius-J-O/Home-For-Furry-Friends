package veterinaria.view;

import veterinaria.dao.ProcedimentoDAO;
import veterinaria.model.Procedimento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaProcedimento extends JFrame {
    private JTextField txtId, txtNome, txtValor;
    private JTextArea txtDescricao;
    private JTable tabela;
    private DefaultTableModel tableModel;
    private ProcedimentoDAO procedimentoDAO;

    public TelaProcedimento() {
        procedimentoDAO = new ProcedimentoDAO();

        setTitle("Home for Furry Friends - Cadastro de Procedimentos");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel painelHeader = new JPanel();
        painelHeader.setBackground(Color.decode("#2E7D6B"));
        JLabel lblTitulo = new JLabel("Cadastro de Procedimentos");
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

        JPanel painelBotoes = new JPanel(new FlowLayout());
        painelBotoes.add(btnSalvar);

        JPanel painelEsquerda = new JPanel(new BorderLayout());
        painelEsquerda.add(painelForm, BorderLayout.CENTER);
        painelEsquerda.add(painelBotoes, BorderLayout.SOUTH);

        add(painelEsquerda, BorderLayout.WEST);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Valor"}, 0);
        tabela = new JTable(tableModel);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        btnSalvar.addActionListener(e -> salvar());

        carregarTabela();
    }

    private void carregarTabela() {
        tableModel.setRowCount(0);
        for (Procedimento p : procedimentoDAO.listarTodos()) {
            tableModel.addRow(new Object[]{p.getId(), p.getNome(), p.getValor()});
        }
    }

    private void salvar() {
        if (txtNome.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome é obrigatório!");
            return;
        }
        try {
            double valor = Double.parseDouble(txtValor.getText());
            Procedimento p = new Procedimento(txtNome.getText(), txtDescricao.getText(), valor);
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