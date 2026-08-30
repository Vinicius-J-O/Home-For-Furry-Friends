package veterinaria.view;

import veterinaria.dao.PetDAO;
import veterinaria.dao.TutorDAO;
import veterinaria.model.Pet;
import veterinaria.model.Tutor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;

public class TelaPet extends JFrame {

    private JTextField txtId, txtNome, txtEspecie, txtRaca, txtSexo, txtDataNasc, txtPeso;
    private JComboBox<Tutor> cbTutores;
    private JTable tabela;
    private DefaultTableModel tableModel;
    private PetDAO petDAO;
    private TutorDAO tutorDAO;

    public TelaPet() {
        petDAO = new PetDAO();
        tutorDAO = new TutorDAO();

        setTitle("Home for Furry Friends - Gerenciamento de Pets");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Cabeçalho
        JPanel painelHeader = new JPanel();
        painelHeader.setBackground(Color.decode("#2E7D6B"));
        JLabel lblTitulo = new JLabel("Home for Furry Friends - Cadastro de Pets");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        painelHeader.add(lblTitulo);
        add(painelHeader, BorderLayout.NORTH);

        // Formulário
        JPanel painelForm = new JPanel(new GridLayout(8, 2, 5, 5));
        painelForm.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        painelForm.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEditable(false);
        painelForm.add(txtId);

        painelForm.add(new JLabel("Tutor Responsible*:"));
        cbTutores = new JComboBox<>();
        carregarTutoresCombo();
        painelForm.add(cbTutores);

        painelForm.add(new JLabel("Nome do Pet*:"));
        txtNome = new JTextField();
        painelForm.add(txtNome);

        painelForm.add(new JLabel("Espécie*:"));
        txtEspecie = new JTextField();
        painelForm.add(txtEspecie);

        painelForm.add(new JLabel("Raça:"));
        txtRaca = new JTextField();
        painelForm.add(txtRaca);

        painelForm.add(new JLabel("Sexo (M/F):"));
        txtSexo = new JTextField();
        painelForm.add(txtSexo);

        painelForm.add(new JLabel("Data Nasc. (AAAA-MM-DD):"));
        txtDataNasc = new JTextField();
        painelForm.add(txtDataNasc);

        painelForm.add(new JLabel("Peso (kg):"));
        txtPeso = new JTextField();
        painelForm.add(txtPeso);

        // Botoes
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

        // Tabela
        tableModel = new DefaultTableModel(new Object[]{"ID", "Tutor ID", "Nome", "Espécie", "Raça", "Sexo", "Peso"}, 0);
        tabela = new JTable(tableModel);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        btnSalvar.addActionListener(e -> salvarPet());
        btnEditar.addActionListener(e -> atualizarPet());
        btnExcluir.addActionListener(e -> excluirPet());
        btnLimpar.addActionListener(e -> limparCampos());

        tabela.getSelectionModel().addListSelectionListener(e -> selecionarLinha());

        carregarTabela();
    }

    private void selecionarLinha() {
        int linha = tabela.getSelectedRow();
        if (linha != -1) {
            txtId.setText(tableModel.getValueAt(linha, 0).toString());
            int tutorId = (int) tableModel.getValueAt(linha, 1);
            for (int i = 0; i < cbTutores.getItemCount(); i++) {
                if (cbTutores.getItemAt(i).getId() == tutorId) {
                    cbTutores.setSelectedIndex(i);
                    break;
                }
            }
            txtNome.setText(tableModel.getValueAt(linha, 2).toString());
            txtEspecie.setText(tableModel.getValueAt(linha, 3).toString());
            txtRaca.setText(tableModel.getValueAt(linha, 4) != null ? tableModel.getValueAt(linha, 4).toString() : "");
            txtSexo.setText(tableModel.getValueAt(linha, 5) != null ? tableModel.getValueAt(linha, 5).toString() : "");
            txtPeso.setText(tableModel.getValueAt(linha, 6).toString());
        }
    }

    private void carregarTutoresCombo() {
        cbTutores.removeAllItems();
        for (Tutor t : tutorDAO.listarTodos()) {
            cbTutores.addItem(t);
        }
    }

    private void carregarTabela() {
        tableModel.setRowCount(0);
        for (Pet p : petDAO.listarTodos()) {
            tableModel.addRow(new Object[]{p.getId(), p.getTutorId(), p.getNome(), p.getEspecie(), p.getRaca(), p.getSexo(), p.getPeso()});
        }
    }

    private void salvarPet() {
        Tutor tutorSelecionado = (Tutor) cbTutores.getSelectedItem();
        if (tutorSelecionado == null || txtNome.getText().trim().isEmpty() || txtEspecie.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha os campos obrigatórios (Tutor, Nome e Espécie)!");
            return;
        }

        try {
            double peso = txtPeso.getText().isEmpty() ? 0.0 : Double.parseDouble(txtPeso.getText());
            Date dataNasc = txtDataNasc.getText().isEmpty() ? new Date(System.currentTimeMillis()) : Date.valueOf(txtDataNasc.getText());

            Pet pet = new Pet(tutorSelecionado.getId(), txtNome.getText(), txtEspecie.getText(), txtRaca.getText(), txtSexo.getText(), dataNasc, peso);

            if (petDAO.cadastrar(pet)) {
                JOptionPane.showMessageDialog(this, "Pet cadastrado com sucesso!");
                limparCampos();
                carregarTabela();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro nos dados inseridos: " + ex.getMessage());
        }
    }

    private void atualizarPet() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um Pet na tabela para atualizar!");
            return;
        }
        Tutor tutorSelecionado = (Tutor) cbTutores.getSelectedItem();
        if (tutorSelecionado == null || txtNome.getText().trim().isEmpty() || txtEspecie.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha os campos obrigatórios (Tutor, Nome e Espécie)!");
            return;
        }

        try {
            double peso = txtPeso.getText().isEmpty() ? 0.0 : Double.parseDouble(txtPeso.getText());
            Date dataNasc = txtDataNasc.getText().isEmpty() ? new Date(System.currentTimeMillis()) : Date.valueOf(txtDataNasc.getText());

            Pet pet = new Pet(Integer.parseInt(txtId.getText()), tutorSelecionado.getId(), txtNome.getText(), txtEspecie.getText(), txtRaca.getText(), txtSexo.getText(), dataNasc, peso);

            if (petDAO.atualizar(pet)) {
                JOptionPane.showMessageDialog(this, "Pet atualizado com sucesso!");
                limparCampos();
                carregarTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar pet.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro nos dados inseridos: " + ex.getMessage());
        }
    }

    private void excluirPet() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um Pet para excluir!");
            return;
        }
        int id = Integer.parseInt(txtId.getText());
        if (petDAO.excluir(id)) {
            JOptionPane.showMessageDialog(this, "Pet excluído!");
            limparCampos();
            carregarTabela();
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtEspecie.setText("");
        txtRaca.setText("");
        txtSexo.setText("");
        txtDataNasc.setText("");
        txtPeso.setText("");
        tabela.clearSelection();
    }
}