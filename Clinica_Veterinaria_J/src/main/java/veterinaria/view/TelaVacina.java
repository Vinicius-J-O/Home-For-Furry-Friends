package veterinaria.view;

import veterinaria.dao.PetDAO;
import veterinaria.dao.VacinaDAO;
import veterinaria.model.Pet;
import veterinaria.model.Vacina;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
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

    // cores usadas na tela
    private final Color verdeEscuro = Color.decode("#245B52");
    private final Color verde = Color.decode("#3D8B7D");
    private final Color fundo = Color.decode("#F3F5F4");
    private final Color branco = Color.WHITE;
    private final Color borda = Color.decode("#D5DDDA");

    public TelaVacina() {
        vacinaDAO = new VacinaDAO();
        petDAO = new PetDAO();

        setTitle("Home for Furry Friends - Carteira de Vacinação");
        setSize(950, 520);
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

        JLabel lblTitulo = new JLabel("Registro de Vacinas");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 21));

        painelHeader.add(lblTitulo);
        painelPrincipal.add(painelHeader, BorderLayout.NORTH);

        // painel da esquerda
        JPanel painelEsquerda = new JPanel(new BorderLayout());
        painelEsquerda.setBackground(fundo);
        painelEsquerda.setPreferredSize(new Dimension(450, 420));
        painelEsquerda.setBorder(new EmptyBorder(25, 30, 20, 20));

        // formulario
        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setBackground(branco);
        painelForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borda),
                new EmptyBorder(25, 25, 25, 25)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        // pet
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        painelForm.add(criarLabel("Pet *"), gbc);

        cbPets = new JComboBox<>();
        carregarPetsCombo();

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelForm.add(cbPets, gbc);

        // vacina
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        painelForm.add(criarLabel("Vacina *"), gbc);

        txtNomeVacina = criarCampo(18);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelForm.add(txtNomeVacina, gbc);

        // data de aplicação
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        painelForm.add(criarLabel("Data Aplicação *"), gbc);

        txtDataAplicacao = criarCampo(18);
        txtDataAplicacao.setToolTipText("Formato: AAAA-MM-DD");

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelForm.add(txtDataAplicacao, gbc);

        // proxima dose
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        painelForm.add(criarLabel("Próxima Dose"), gbc);

        txtProximaDose = criarCampo(18);
        txtProximaDose.setToolTipText("Formato: AAAA-MM-DD");

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painelForm.add(txtProximaDose, gbc);

        // deixa o formulario compacto no topo
        painelEsquerda.add(painelForm, BorderLayout.NORTH);

        // botão
        JButton btnSalvar = new JButton("Registrar Vacina");
        btnSalvar.setBackground(verde);
        btnSalvar.setForeground(Color.WHITE);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 12));
        btnSalvar.setPreferredSize(new Dimension(145, 38));

        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 15));
        painelBotao.setBackground(fundo);
        painelBotao.add(btnSalvar);

        painelEsquerda.add(painelBotao, BorderLayout.SOUTH);
        painelPrincipal.add(painelEsquerda, BorderLayout.WEST);

        // tabela
        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.setBackground(fundo);
        painelTabela.setBorder(new EmptyBorder(25, 0, 25, 25));

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Vacina", "Aplicação", "Próx. Dose"}, 0
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

        // centraliza ID e datas
        DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
        centro.setHorizontalAlignment(SwingConstants.CENTER);

        tabela.getColumnModel().getColumn(0).setCellRenderer(centro);
        tabela.getColumnModel().getColumn(2).setCellRenderer(centro);
        tabela.getColumnModel().getColumn(3).setCellRenderer(centro);

        tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(150);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(110);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(110);

        JScrollPane scrollTabela = new JScrollPane(tabela);
        scrollTabela.setBorder(BorderFactory.createLineBorder(borda));
        scrollTabela.getViewport().setBackground(Color.WHITE);

        painelTabela.add(scrollTabela, BorderLayout.CENTER);
        painelPrincipal.add(painelTabela, BorderLayout.CENTER);

        // quando troca o pet, muda as vacinas mostradas
        cbPets.addActionListener(e -> carregarVacinasPet());

        // registra uma nova vacina
        btnSalvar.addActionListener(e -> registrarVacina());

        carregarVacinasPet();
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

    // coloca os pets cadastrados no combo
    private void carregarPetsCombo() {
        cbPets.removeAllItems();

        for (Pet p : petDAO.listarTodos()) {
            cbPets.addItem(p);
        }
    }

    // carrega só as vacinas do pet selecionado
    private void carregarVacinasPet() {
        tableModel.setRowCount(0);

        Pet pet = (Pet) cbPets.getSelectedItem();

        if (pet != null) {
            for (Vacina v : vacinaDAO.listarPorPet(pet.getId())) {
                tableModel.addRow(new Object[]{
                        v.getId(),
                        v.getNome(),
                        v.getDataAplicacao(),
                        v.getProximaDose()
                });
            }
        }
    }

    // registra uma vacina nova
    private void registrarVacina() {
        Pet pet = (Pet) cbPets.getSelectedItem();

        if (pet == null
                || txtNomeVacina.getText().trim().isEmpty()
                || txtDataAplicacao.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Pet, Nome da Vacina e Data de Aplicação são obrigatórios!"
            );
            return;
        }

        try {
            Date dataAplica = Date.valueOf(txtDataAplicacao.getText());

            // se não tiver proxima dose, salva null
            Date proxDose = txtProximaDose.getText().trim().isEmpty()
                    ? null
                    : Date.valueOf(txtProximaDose.getText());

            Vacina v = new Vacina(
                    pet.getId(),
                    txtNomeVacina.getText(),
                    dataAplica,
                    proxDose
            );

            if (vacinaDAO.cadastrar(v)) {
                JOptionPane.showMessageDialog(this, "Vacina registrada com sucesso!");

                txtNomeVacina.setText("");
                txtDataAplicacao.setText("");
                txtProximaDose.setText("");

                carregarVacinasPet();
            }

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Use o formato de data correto (AAAA-MM-DD)!"
            );
        }
    }
}