package veterinaria.view;

import veterinaria.dao.TutorDAO;
import veterinaria.model.Tutor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaTutor extends JFrame {
    
    private JTextField txtId, txtNome, txtCpf, txtTelefone, txtEmail, txtEndereco;
    private JTable tabela;
    private DefaultTableModel tableModel;
    private TutorDAO tutorDAO;

    public TelaTutor() {
        tutorDAO = new TutorDAO();

        setTitle("Gerenciador de Tutores - Home For Furry Friends");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Header
        JPanel painelHeader = new JPanel();
        painelHeader.setBackground(Color.decode("#2E7D6B"));
        JLabel lblTitulo = new JLabel("Cadastro e Gerenciamento de Tutores");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        painelHeader.add(lblTitulo);
        add(painelHeader, BorderLayout.NORTH);

        // Formulário de Dados
        JPanel painelForm = new JPanel(new GridLayout(6, 2, 5, 5));
        painelForm.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        painelForm.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEditable(false);
        painelForm.add(txtId);

        painelForm.add(new JLabel("Nome*:"));
        txtNome = new JTextField();
        painelForm.add(txtNome);

        painelForm.add(new JLabel("Cpf*:"));
        txtCpf = new JTextField();
        painelForm.add(txtCpf);

        painelForm.add(new JLabel("Telefone:"));
        txtTelefone = new JTextField();
        painelForm.add(txtTelefone);

        painelForm.add(new JLabel("E-mail:"));
        txtEmail = new JTextField();
        painelForm.add(txtEmail);

        painelForm.add(new JLabel("Endereço:"));
        txtEndereco = new JTextField();
        painelForm.add(txtEndereco);

        // Botões de Ação
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBackground(Color.decode("#43A047"));
        btnSalvar.setForeground(Color.WHITE);

        JButton btnEditar = new JButton("Atualizar");
        btnEditar.setBackground(Color.decode("#5BB8C5"));
        btnEditar.setForeground(Color.WHITE);

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setBackground(Color.decode("#D9534F"));
        btnExcluir.setForeground(Color.WHITE);

        JButton btnLimpar = new JButton("Limpar");

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnLimpar);

        JPanel painelCentral = new JPanel(new BorderLayout());
        painelCentral.add(painelForm, BorderLayout.NORTH);
        painelCentral.add(painelBotoes, BorderLayout.SOUTH);

        add(painelCentral, BorderLayout.WEST);

        // Tabela de Dados
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "CPF", "Telefone", "Email", "Endereço"}, 0);
        tabela = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
        add(scrollPane, BorderLayout.CENTER);

        // Eventos
        btnSalvar.addActionListener(e -> salvarTutor());
        btnEditar.addActionListener(e -> atualizarTutor());
        btnExcluir.addActionListener(e -> excluirTutor());
        btnLimpar.addActionListener(e -> limparCampos());

        tabela.getSelectionModel().addListSelectionListener(e -> selecionarLinha());

        carregarTabela();
    }

    private void carregarTabela() {
        tableModel.setRowCount(0);
        for (Tutor t : tutorDAO.listarTodos()) {
            tableModel.addRow(new Object[]{t.getId(), t.getNome(), t.getCpf(), t.getTelefone(), t.getEmail(), t.getEndereco()});
        }
    }

    private void salvarTutor() {
        if (txtNome.getText().trim().isEmpty() || txtCpf.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha os campos obrigatórios (Nome e CPF)!");
            return;
        }
        Tutor t = new Tutor(txtNome.getText(), txtCpf.getText(), txtTelefone.getText(), txtEmail.getText(), txtEndereco.getText());
        if (tutorDAO.cadastrar(t)) {
            JOptionPane.showMessageDialog(this, "Tutor cadastrado com sucesso!");
            limparCampos();
            carregarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar tutor.");
        }
    }

    private void atualizarTutor() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um tutor na tabela para atualizar!");
            return;
        }
        Tutor t = new Tutor(Integer.parseInt(txtId.getText()), txtNome.getText(), txtCpf.getText(), txtTelefone.getText(), txtEmail.getText(), txtEndereco.getText());
        if (tutorDAO.atualizar(t)) {
            JOptionPane.showMessageDialog(this, "Tutor atualizado com sucesso!");
            limparCampos();
            carregarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar tutor.");
        }
    }

    private void excluirTutor() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um tutor na tabela para excluir!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este tutor?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(txtId.getText());
            if (tutorDAO.excluir(id)) {
                JOptionPane.showMessageDialog(this, "Tutor excluído com sucesso!");
                limparCampos();
                carregarTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao excluir tutor.");
            }
        }
    }

    private void selecionarLinha() {
        int linha = tabela.getSelectedRow();
        if (linha != -1) {
            txtId.setText(tableModel.getValueAt(linha, 0).toString());
            txtNome.setText(tableModel.getValueAt(linha, 1).toString());
            txtCpf.setText(tableModel.getValueAt(linha, 2).toString());
            txtTelefone.setText(tableModel.getValueAt(linha, 3) != null ? tableModel.getValueAt(linha, 3).toString() : "");
            txtEmail.setText(tableModel.getValueAt(linha, 4) != null ? tableModel.getValueAt(linha, 4).toString() : "");
            txtEndereco.setText(tableModel.getValueAt(linha, 5) != null ? tableModel.getValueAt(linha, 5).toString() : "");
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtEndereco.setText("");
        tabela.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaTutor().setVisible(true));
    }
}