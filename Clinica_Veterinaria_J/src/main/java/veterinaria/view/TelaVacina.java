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

        // Header
        JPanel painelHeader = new JPanel();
        painelHeader.setBackground(Color.decode("#2E7D6B"));
        JLabel lblTitulo = new JLabel("Registro de Vacinas");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        painelHeader.add(lblTitulo);
        add(painelHeader, BorderLayout.NORTH);

        // Formulário
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

        // Botão
        JButton btnSalvar = new JButton("Registrar Vacina");
        btnSalvar.setBackground(Color.decode("#43A047"));
        btnSalvar.setForeground(Color.WHITE);

        JPanel painelEsquerda = new JPanel(new BorderLayout());
        painelEsquerda.add(painelForm, BorderLayout.CENTER);
        painelEsquerda.add(btnSalvar, BorderLayout.SOUTH);

        add(painelEsquerda, BorderLayout.WEST);

        // Tabela que mostra as vacinas do pet selecionado no combo.
        tableModel = new DefaultTableModel(new Object[]{"ID", "Vacina", "Aplicação", "Próx. Dose"}, 0);
        tabela = new JTable(tableModel);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Sempre que o usuário troca o pet selecionado no combo, recarregamos
        // a tabela para mostrar as vacinas do novo pet escolhido.
        cbPets.addActionListener(e -> carregarVacinasPet());
        btnSalvar.addActionListener(e -> registrarVacina());

        // Já carrega a tabela com as vacinas do primeiro pet da lista, ao abrir a tela.
        carregarVacinasPet();
    }

    // Busca todos os pets no banco de dados (usando o PetDAO) e preenche o combo de seleção.
    private void carregarPetsCombo() {
        cbPets.removeAllItems();
        for (Pet p : petDAO.listarTodos()) {
            cbPets.addItem(p);
        }
    }

    // Busca as vacinas do pet que está selecionado no combo, e atualiza a tabela.
    private void carregarVacinasPet() {
        tableModel.setRowCount(0); // Limpa a tabela antes de recarregar
        Pet pet = (Pet) cbPets.getSelectedItem();
        if (pet != null) { // Pode ser null se não tiver nenhum pet cadastrado ainda
            for (Vacina v : vacinaDAO.listarPorPet(pet.getId())) {
                tableModel.addRow(new Object[]{v.getId(), v.getNome(), v.getDataAplicacao(), v.getProximaDose()});
            }
        }
    }

    // Registra uma nova vacina para o pet selecionado no combo.
    private void registrarVacina() {
        Pet pet = (Pet) cbPets.getSelectedItem();
        if (pet == null || txtNomeVacina.getText().trim().isEmpty() || txtDataAplicacao.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pet, Nome da Vacina e Data de Aplicação são obrigatórios!");
            return;
        }

        try {
            // Converte o texto digitado (AAAA-MM-DD) para um objeto Date.
            Date dataAplica = Date.valueOf(txtDataAplicacao.getText());
            // A próxima dose é opcional: se o campo estiver vazio armazenamos "null" no banco de dados
            // (ou seja, "sem próxima dose definida").
            Date proxDose = txtProximaDose.getText().isEmpty() ? null : Date.valueOf(txtProximaDose.getText());

            Vacina v = new Vacina(pet.getId(), txtNomeVacina.getText(), dataAplica, proxDose);
            if (vacinaDAO.cadastrar(v)) {
                JOptionPane.showMessageDialog(this, "Vacina registrada com sucesso!");
                txtNomeVacina.setText("");
                txtDataAplicacao.setText("");
                txtProximaDose.setText("");
                carregarVacinasPet(); // Atualiza a tabela para mostrar a vacina registrada
            }
        } catch (IllegalArgumentException e) {
            // Date.valueOf() lança esse erro se o texto digitado não estiver
            // no formato de data esperado (AAAA-MM-DD).
            JOptionPane.showMessageDialog(this, "Use o formato de data correto (AAAA-MM-DD)!");
        }
    }
}
