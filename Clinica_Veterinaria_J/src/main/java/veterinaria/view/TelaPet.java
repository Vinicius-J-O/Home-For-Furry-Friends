package veterinaria.view;

import veterinaria.dao.PetDAO;
import veterinaria.dao.TutorDAO;
import veterinaria.model.Pet;
import veterinaria.model.Tutor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
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

    // cores usadas na tela
    private final Color verdeEscuro = Color.decode("#245B52");
    private final Color verde = Color.decode("#3D8B7D");
    private final Color azul = Color.decode("#4F9DAC");
    private final Color fundo = Color.decode("#F3F5F4");
    private final Color branco = Color.WHITE;
    private final Color vermelho = Color.decode("#D9534F");
    private final Color cinza = Color.decode("#757575");
    private final Color borda = Color.decode("#D5DDDA");

    public TelaPet() {
        petDAO = new PetDAO();
        tutorDAO = new TutorDAO();

        setTitle("Home for Furry Friends - Cadastro de Pets");
        setSize(1100, 650);
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

        JLabel lblTitulo = new JLabel("Cadastro de Pets");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 21));

        painelHeader.add(lblTitulo);
        painelPrincipal.add(painelHeader, BorderLayout.NORTH);

        // painel onde ficam os campos
        JPanel painelEsquerda = new JPanel(new BorderLayout());
        painelEsquerda.setBackground(fundo);
        painelEsquerda.setPreferredSize(new Dimension(450, 520));
        painelEsquerda.setBorder(new EmptyBorder(25, 30, 20, 20));

        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setBackground(branco);
        painelForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borda),
                new EmptyBorder(20, 22, 20, 22)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        painelForm.add(criarLabel("ID"), gbc);

        txtId = criarCampo(18);
        txtId.setEditable(false);

        gbc.gridx = 1;
        painelForm.add(txtId, gbc);

        // combo com os tutores cadastrados
        gbc.gridx = 0;
        gbc.gridy = 1;
        painelForm.add(criarLabel("Tutor *"), gbc);

        cbTutores = new JComboBox<>();
        cbTutores.setPreferredSize(new Dimension(190, 30));
        carregarTutoresCombo();

        gbc.gridx = 1;
        painelForm.add(cbTutores, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        painelForm.add(criarLabel("Nome do Pet *"), gbc);

        txtNome = criarCampo(18);

        gbc.gridx = 1;
        painelForm.add(txtNome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        painelForm.add(criarLabel("Espécie *"), gbc);

        txtEspecie = criarCampo(18);

        gbc.gridx = 1;
        painelForm.add(txtEspecie, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        painelForm.add(criarLabel("Raça"), gbc);

        txtRaca = criarCampo(18);

        gbc.gridx = 1;
        painelForm.add(txtRaca, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        painelForm.add(criarLabel("Sexo (M/F)"), gbc);

        txtSexo = criarCampo(18);

        gbc.gridx = 1;
        painelForm.add(txtSexo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        painelForm.add(criarLabel("Data Nasc."), gbc);

        txtDataNasc = criarCampo(18);
        txtDataNasc.setToolTipText("Formato: AAAA-MM-DD");

        gbc.gridx = 1;
        painelForm.add(txtDataNasc, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        painelForm.add(criarLabel("Peso (kg)"), gbc);

        txtPeso = criarCampo(18);

        gbc.gridx = 1;
        painelForm.add(txtPeso, gbc);

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
                new Object[]{"ID", "Tutor ID", "Nome", "Espécie", "Raça", "Sexo", "Data Nasc.", "Peso"}, 0
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

        // centraliza algumas colunas
        DefaultTableCellRenderer centro = new DefaultTableCellRenderer();
        centro.setHorizontalAlignment(SwingConstants.CENTER);

        tabela.getColumnModel().getColumn(0).setCellRenderer(centro);
        tabela.getColumnModel().getColumn(1).setCellRenderer(centro);
        tabela.getColumnModel().getColumn(5).setCellRenderer(centro);
        tabela.getColumnModel().getColumn(7).setCellRenderer(centro);

        tabela.getColumnModel().getColumn(0).setPreferredWidth(35);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(60);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(100);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(90);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(90);
        tabela.getColumnModel().getColumn(5).setPreferredWidth(50);
        tabela.getColumnModel().getColumn(6).setPreferredWidth(100);
        tabela.getColumnModel().getColumn(7).setPreferredWidth(60);

        JScrollPane scrollTabela = new JScrollPane(tabela);
        scrollTabela.setBorder(BorderFactory.createLineBorder(borda));
        scrollTabela.getViewport().setBackground(Color.WHITE);

        painelTabela.add(scrollTabela, BorderLayout.CENTER);
        painelPrincipal.add(painelTabela, BorderLayout.CENTER);

        // eventos dos botões
        btnSalvar.addActionListener(e -> salvarPet());
        btnEditar.addActionListener(e -> atualizarPet());
        btnExcluir.addActionListener(e -> excluirPet());
        btnLimpar.addActionListener(e -> limparCampos());

        // quando clica numa linha, joga os dados pros campos
        tabela.getSelectionModel().addListSelectionListener(e -> selecionarLinha());

        carregarTabela();
    }

    // cria os labels no mesmo estilo
    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        return label;
    }

    // cria os campos de texto padronizados
    private JTextField criarCampo(int colunas) {
        JTextField campo = new JTextField(colunas);
        campo.setPreferredSize(new Dimension(190, 30));
        campo.setFont(new Font("Arial", Font.PLAIN, 13));
        return campo;
    }

    // cria os botões com o mesmo tamanho
    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setFont(new Font("Arial", Font.BOLD, 12));
        botao.setPreferredSize(new Dimension(90, 36));
        return botao;
    }

    // quando clica num pet, ele preenche os campos
    private void selecionarLinha() {
        int linha = tabela.getSelectedRow();

        if (linha != -1) {
            txtId.setText(tableModel.getValueAt(linha, 0).toString());

            // procura no combo qual tutor tem o mesmo id do pet
            int tutorId = (int) tableModel.getValueAt(linha, 1);

            for (int i = 0; i < cbTutores.getItemCount(); i++) {
                if (cbTutores.getItemAt(i).getId() == tutorId) {
                    cbTutores.setSelectedIndex(i);
                    break;
                }
            }

            txtNome.setText(tableModel.getValueAt(linha, 2).toString());
            txtEspecie.setText(tableModel.getValueAt(linha, 3).toString());

            txtRaca.setText(
                    tableModel.getValueAt(linha, 4) != null
                            ? tableModel.getValueAt(linha, 4).toString()
                            : ""
            );

            txtSexo.setText(
                    tableModel.getValueAt(linha, 5) != null
                            ? tableModel.getValueAt(linha, 5).toString()
                            : ""
            );

            txtDataNasc.setText(
                    tableModel.getValueAt(linha, 6) != null
                            ? tableModel.getValueAt(linha, 6).toString()
                            : ""
            );

            txtPeso.setText(
                    tableModel.getValueAt(linha, 7) != null
                            ? tableModel.getValueAt(linha, 7).toString()
                            : ""
            );
        }
    }

    // pega os tutores do banco e coloca no combo
    private void carregarTutoresCombo() {
        cbTutores.removeAllItems();

        for (Tutor t : tutorDAO.listarTodos()) {
            cbTutores.addItem(t);
        }
    }

    // atualiza os dados que aparecem na tabela
    private void carregarTabela() {
        tableModel.setRowCount(0);

        for (Pet p : petDAO.listarTodos()) {
            tableModel.addRow(new Object[]{
                    p.getId(),
                    p.getTutorId(),
                    p.getNome(),
                    p.getEspecie(),
                    p.getRaca(),
                    p.getSexo(),
                    p.getDataNascimento(),
                    p.getPeso()
            });
        }
    }

    // cadastra um novo pet
    private void salvarPet() {
        Tutor tutorSelecionado = (Tutor) cbTutores.getSelectedItem();

        if (tutorSelecionado == null
                || txtNome.getText().trim().isEmpty()
                || txtEspecie.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Preencha os campos obrigatórios (Tutor, Nome e Espécie)!"
            );
            return;
        }

        try {
            // se o peso ficar vazio usa 0
            double peso = txtPeso.getText().trim().isEmpty()
                    ? 0.0
                    : Double.parseDouble(txtPeso.getText().replace(",", "."));

            // se não colocar data ele usa a data atual
            Date dataNasc = txtDataNasc.getText().trim().isEmpty()
                    ? new Date(System.currentTimeMillis())
                    : Date.valueOf(txtDataNasc.getText());

            Pet pet = new Pet(
                    tutorSelecionado.getId(),
                    txtNome.getText(),
                    txtEspecie.getText(),
                    txtRaca.getText(),
                    txtSexo.getText(),
                    dataNasc,
                    peso
            );

            if (petDAO.cadastrar(pet)) {
                JOptionPane.showMessageDialog(this, "Pet cadastrado com sucesso!");
                limparCampos();
                carregarTabela();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro nos dados inseridos. Verifique o peso e a data."
            );
        }
    }

    // atualiza o pet que foi selecionado
    private void atualizarPet() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um Pet na tabela para atualizar!");
            return;
        }

        Tutor tutorSelecionado = (Tutor) cbTutores.getSelectedItem();

        if (tutorSelecionado == null
                || txtNome.getText().trim().isEmpty()
                || txtEspecie.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Preencha os campos obrigatórios (Tutor, Nome e Espécie)!"
            );
            return;
        }

        try {
            double peso = txtPeso.getText().trim().isEmpty()
                    ? 0.0
                    : Double.parseDouble(txtPeso.getText().replace(",", "."));

            Date dataNasc = txtDataNasc.getText().trim().isEmpty()
                    ? new Date(System.currentTimeMillis())
                    : Date.valueOf(txtDataNasc.getText());

            // aqui vai o id porque é um pet que ja existe
            Pet pet = new Pet(
                    Integer.parseInt(txtId.getText()),
                    tutorSelecionado.getId(),
                    txtNome.getText(),
                    txtEspecie.getText(),
                    txtRaca.getText(),
                    txtSexo.getText(),
                    dataNasc,
                    peso
            );

            if (petDAO.atualizar(pet)) {
                JOptionPane.showMessageDialog(this, "Pet atualizado com sucesso!");
                limparCampos();
                carregarTabela();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar pet.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro nos dados inseridos. Verifique o peso e a data."
            );
        }
    }

    // exclui o pet selecionado
    private void excluirPet() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um Pet para excluir!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir este pet?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(txtId.getText());

            if (petDAO.excluir(id)) {
                JOptionPane.showMessageDialog(this, "Pet excluído!");
                limparCampos();
                carregarTabela();
            }
        }
    }

    // limpa os campos depois das ações
    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtEspecie.setText("");
        txtRaca.setText("");
        txtSexo.setText("");
        txtDataNasc.setText("");
        txtPeso.setText("");

        if (cbTutores.getItemCount() > 0) {
            cbTutores.setSelectedIndex(0);
        }

        tabela.clearSelection();
    }
}