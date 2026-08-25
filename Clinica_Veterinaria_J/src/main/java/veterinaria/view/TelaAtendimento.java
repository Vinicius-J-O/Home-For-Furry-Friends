package veterinaria.view;

import veterinaria.dao.AtendimentoDAO;
import veterinaria.dao.PetDAO;
import veterinaria.dao.VeterinarioDAO;
import veterinaria.model.Atendimento;
import veterinaria.model.Pet;
import veterinaria.model.Veterinario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.Time;

public class TelaAtendimento extends JFrame {

    private JComboBox<Pet> cbPets;
    private JComboBox<Veterinario> cbVets;
    private JTextArea txtDescricao, txtDiagnostico;
    private JTextField txtValor;
    private JTable tabela;
    private DefaultTableModel tableModel;

    private AtendimentoDAO atendimentoDAO;
    private PetDAO petDAO;
    private VeterinarioDAO vetDAO;

    public TelaAtendimento() {
        atendimentoDAO = new AtendimentoDAO();
        petDAO = new PetDAO();
        vetDAO = new VeterinarioDAO();

        setTitle("Home for Furry Friends - Registro de Atendimento Clínico");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel painelHeader = new JPanel();
        painelHeader.setBackground(Color.decode("#2E7D6B"));
        JLabel lblTitulo = new JLabel("Home for Furry Friends - Atendimento Clínico");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        painelHeader.add(lblTitulo);
        add(painelHeader, BorderLayout.NORTH);

        JPanel painelForm = new JPanel(new GridLayout(5, 2, 5, 5));
        painelForm.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        painelForm.add(new JLabel("Selecione o Pet*:"));
        cbPets = new JComboBox<>();
        carregarPets();
        painelForm.add(cbPets);

        painelForm.add(new JLabel("Selecione o Veterinário*:"));
        cbVets = new JComboBox<>();
        carregarVets();
        painelForm.add(cbVets);

        painelForm.add(new JLabel("Descrição da Consulta:"));
        txtDescricao = new JTextArea(2, 20);
        painelForm.add(new JScrollPane(txtDescricao));

        painelForm.add(new JLabel("Diagnóstico / Procedimentos:"));
        txtDiagnostico = new JTextArea(2, 20);
        painelForm.add(new JScrollPane(txtDiagnostico));

        painelForm.add(new JLabel("Valor Total (R$)*:"));
        txtValor = new JTextField("0.00");
        painelForm.add(txtValor);

        JButton btnFinalizar = new JButton("Finalizar Atendimento");
        btnFinalizar.setBackground(Color.decode("#43A047"));
        btnFinalizar.setForeground(Color.WHITE);
        btnFinalizar.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel painelCentral = new JPanel(new BorderLayout());
        painelCentral.add(painelForm, BorderLayout.CENTER);
        painelCentral.add(btnFinalizar, BorderLayout.SOUTH);

        add(painelCentral, BorderLayout.WEST);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Pet ID", "Vet ID", "Data", "Diagnóstico", "Valor (R$)"}, 0);
        tabela = new JTable(tableModel);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        btnFinalizar.addActionListener(e -> registrarAtendimento());

        carregarTabela();
    }

    private void carregarPets() {
        cbPets.removeAllItems();
        for (Pet p : petDAO.listarTodos()) {
            cbPets.addItem(p);
        }
    }

    private void carregarVets() {
        cbVets.removeAllItems();
        for (Veterinario v : vetDAO.listarTodos()) {
            cbVets.addItem(v);
        }
    }

    private void carregarTabela() {
        tableModel.setRowCount(0);
        for (Atendimento a : atendimentoDAO.listarTodos()) {
            tableModel.addRow(new Object[]{a.getId(), a.getPetId(), a.getVeterinarioId(), a.getDataAtendimento(), a.getDiagnostico(), a.getValor()});
        }
    }

    private void registrarAtendimento() {
        Pet pet = (Pet) cbPets.getSelectedItem();
        Veterinario vet = (Veterinario) cbVets.getSelectedItem();

        if (pet == null || vet == null || txtValor.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione o Pet, o Veterinário e informe o Valor!");
            return;
        }

        try {
            double valor = Double.parseDouble(txtValor.getText());
            Date dataAtual = new Date(System.currentTimeMillis());
            Time horaAtual = new Time(System.currentTimeMillis());

            Atendimento a = new Atendimento(pet.getId(), vet.getId(), dataAtual, horaAtual, txtDescricao.getText(), txtDiagnostico.getText(), valor);

            if (atendimentoDAO.registrar(a)) {
                JOptionPane.showMessageDialog(this, "Atendimento finalizado com sucesso!");
                txtDescricao.setText("");
                txtDiagnostico.setText("");
                txtValor.setText("0.00");
                carregarTabela();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Informe um valor numérico válido!");
        }
    }
}