package veterinaria.view;

import veterinaria.dao.PetDAO;
import veterinaria.dao.VacinaDAO;
import veterinaria.model.Pet;
import veterinaria.model.Vacina;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;

public class TelaVacina extends JFrame {
    private JComboBox<Pet> cbPets;
    private JTextField txtNomeVacina, txtDataAplicacao, txtProximaDose;
    private JTable tabela;
    private DefaultTableModel tableModel;
    private VacinaDAO vacinaDAO;
    private PetDAO petDAO;

    public TelaVacina() {
        vacinaDAO = new VacinaDAO();
        petDAO = new PetDAO();

        setTitle("Home for Furry Friends - Carteira de Vacinação");
        setSize(750, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel painelHeader = new JPanel();
        painelHeader.setBackground(Color.decode("#2E7D6B"));
        JLabel lblTitulo = new JLabel("Registro de Vacinas");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        painelHeader.add(lblTitulo);
        add(painelHeader, BorderLayout.NORTH);

        JPanel painelForm = new JPanel(new GridLayout(4, 2, 5, 5));
        painelForm.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        painelForm.add(new JLabel("Selecione o Pet*:"));
        cbPets = new JComboBox<>();
        carregarPetsCombo();
        painelForm.add(cbPets);

        painelForm.add(new JLabel("Nome da Vacina*:"));
        txtNomeVacina = new JTextField();
        painelForm.add(txtNomeVacina);

        painelForm.add(new JLabel("Data Aplicação (AAAA-MM-DD)*:"));
        txtDataAplicacao = new JTextField();
        painelForm.add(txtDataAplicacao);

        painelForm.add(new JLabel("Próxima Dose (AAAA-MM-DD):"));
        txtProximaDose = new JTextField();
        painelForm.add(txtProximaDose);

        JButton btnSalvar = new JButton("Registrar Vacina");
        btnSalvar.setBackground(Color.decode("#43A047"));
        btnSalvar.setForeground(Color.WHITE);

        JPanel painelEsquerda = new JPanel(new BorderLayout());
        painelEsquerda.add(painelForm, BorderLayout.CENTER);
        painelEsquerda.add(btnSalvar, BorderLayout.SOUTH);

        add(painelEsquerda, BorderLayout.WEST);

        tableModel = new DefaultTableModel(new Object[]{"ID", "Vacina", "Aplicação", "Próx. Dose"}, 0);
        tabela = new JTable(tableModel);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        cbPets.addActionListener(e -> carregarVacinasPet());
        btnSalvar.addActionListener(e -> registrarVacina());

        carregarVacinasPet();
    }

    private void carregarPetsCombo() {
        cbPets.removeAllItems();
        for (Pet p : petDAO.listarTodos()) {
            cbPets.addItem(p);
        }
    }

    private void carregarVacinasPet() {
        tableModel.setRowCount(0);
        Pet pet = (Pet) cbPets.getSelectedItem();
        if (pet != null) {
            for (Vacina v : vacinaDAO.listarPorPet(pet.getId())) {
                tableModel.addRow(new Object[]{v.getId(), v.getNome(), v.getDataAplicacao(), v.getProximaDose()});
            }
        }
    }

    private void registrarVacina() {
        Pet pet = (Pet) cbPets.getSelectedItem();
        if (pet == null || txtNomeVacina.getText().trim().isEmpty() || txtDataAplicacao.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pet, Nome da Vacina e Data de Aplicação são obrigatórios!");
            return;
        }

        try {
            Date dataAplica = Date.valueOf(txtDataAplicacao.getText());
            Date proxDose = txtProximaDose.getText().isEmpty() ? null : Date.valueOf(txtProximaDose.getText());

            Vacina v = new Vacina(pet.getId(), txtNomeVacina.getText(), dataAplica, proxDose);
            if (vacinaDAO.cadastrar(v)) {
                JOptionPane.showMessageDialog(this, "Vacina registrada com sucesso!");
                txtNomeVacina.setText("");
                txtDataAplicacao.setText("");
                txtProximaDose.setText("");
                carregarVacinasPet();
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Use o formato de data correto (AAAA-MM-DD)!");
        }
    }
}