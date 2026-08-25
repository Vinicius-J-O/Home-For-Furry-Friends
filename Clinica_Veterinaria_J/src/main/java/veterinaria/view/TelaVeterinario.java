package veterinaria.view;

import veterinaria.dao.VeterinarioDAO;
import veterinaria.model.Veterinario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaVeterinario extends JFrame {

    private JTextField txtId, txtNome, txtCrmv, txtTelefone, txtEspecialidade;
    private JTable tabela;
    private DefaultTableModel tableModel;
    private VeterinarioDAO vetDAO;

    public TelaVeterinario() {
        vetDAO = new VeterinarioDAO();

        setTitle("Home for Furry Friends - Gerenciamento de Veterinários");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel painelHeader = new JPanel();
        painelHeader.setBackground(Color.decode("#2E7D6B"));
        JLabel lblTitulo = new JLabel("Home for Furry Friends - Cadastro de Veterinários");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        painelHeader.add(lblTitulo);
        add(painelHeader, BorderLayout.NORTH);

        JPanel painelForm = new JPanel(new GridLayout(5, 2, 5, 5));
        painelForm.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        painelForm.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEditable(false);
        painelForm.add(txtId);

        painelForm.add(new JLabel("Nome*:"));
        txtNome = new JTextField();
        painelForm.add(txtNome);

        painelForm.add(new JLabel("CRMV*:"));
        txtCrmv = new JTextField();
        painelForm.add(txtCrmv);

        painelForm.add(new JLabel("Telefone:"));
        txtTelefone = new JTextField();
        painelForm.add(txtTelefone);

        painelForm.add(new JLabel("Especialidade:"));
        txtEspecialidade = new JTextField();
        painelForm.add(txtEspecialidade);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBackground(Color.decode("#43A047"));
        btnSalvar.setForeground(Color.WHITE);

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setBackground(Color.decode("#D9534F"));
        btnExcluir.setForeground(Color.WHITE);

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnExcluir);

        JPanel painelCentral = new JPanel(new BorderLayout());
        painelCentral.add(painelForm, BorderLayout.NORTH);
        painelCentral.add(painelBotoes, BorderLayout.SOUTH);

        add(painelCentral, BorderLayout.WEST);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "CRMV", "Telefone", "Especialidade"}, 0);
        tabela = new JTable(tableModel);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        btnSalvar.addActionListener(e -> salvarVet());
        btnExcluir.addActionListener(e -> excluirVet());

        carregarTabela();
    }

    private void carregarTabela() {
        tableModel.setRowCount(0);
        for (Veterinario v : vetDAO.listarTodos()) {
            tableModel.addRow(new Object[]{v.getId(), v.getNome(), v.getCrmv(), v.getTelefone(), v.getEspecialidade()});
        }
    }

    private void salvarVet() {
        if (txtNome.getText().trim().isEmpty() || txtCrmv.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e CRMV são obrigatórios!");
            return;
        }
        Veterinario v = new Veterinario(txtNome.getText(), txtCrmv.getText(), txtTelefone.getText(), txtEspecialidade.getText());
        if (vetDAO.cadastrar(v)) {
            JOptionPane.showMessageDialog(this, "Veterinário cadastrado!");
            txtNome.setText("");
            txtCrmv.setText("");
            txtTelefone.setText("");
            txtEspecialidade.setText("");
            carregarTabela();
        }
    }

    private void excluirVet() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um veterinário para excluir!");
            return;
        }
        if (vetDAO.excluir(Integer.parseInt(txtId.getText()))) {
            JOptionPane.showMessageDialog(this, "Excluído com sucesso!");
            carregarTabela();
        }
    }
}