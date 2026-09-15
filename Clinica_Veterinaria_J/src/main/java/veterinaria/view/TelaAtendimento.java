package veterinaria.view;

import veterinaria.dao.AtendimentoDAO;
import veterinaria.dao.PetDAO;
import veterinaria.dao.VeterinarioDAO;
import veterinaria.model.Atendimento;
import veterinaria.model.Pet;
import veterinaria.model.Veterinario;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.Time;

public class TelaAtendimento extends JFrame {

    private JTextField txtId, txtDescricao, txtDiagnostico, txtValor;
    private JComboBox<Pet> cbPets;
    private JComboBox<Veterinario> cbVets;
    private JTable tabela;
    private DefaultTableModel tableModel;

    private AtendimentoDAO atendimentoDAO = new AtendimentoDAO();
    private PetDAO petDAO = new PetDAO();
    private VeterinarioDAO veterinarioDAO = new VeterinarioDAO();

    // cores usadas na tela
    private final Color verdeEscuro = Color.decode("#245B52");
    private final Color verde = Color.decode("#3D8B7D");
    private final Color azul = Color.decode("#4F9DAC");
    private final Color fundo = Color.decode("#F3F5F4");
    private final Color branco = Color.WHITE;
    private final Color vermelho = Color.decode("#D9534F");
    private final Color cinza = Color.decode("#757575");
    private final Color borda = Color.decode("#D5DDDA");

    public TelaAtendimento() {
        setTitle("Home for Furry Friends - Cadastro de Atendimentos");
        setSize(1100, 620);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(fundo);
        setContentPane(painelPrincipal);

        // cabeçalho
        JPanel header = new JPanel();
        header.setBackground(verdeEscuro);
        header.setBorder(new EmptyBorder(12, 10, 12, 10));

        JLabel titulo = new JLabel("Cadastro de Atendimentos");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial", Font.BOLD, 21));

        header.add(titulo);
        painelPrincipal.add(header, BorderLayout.NORTH);

        // lado esquerdo da tela
        JPanel esquerda = new JPanel(new BorderLayout());
        esquerda.setBackground(fundo);
        esquerda.setPreferredSize(new Dimension(460, 500));
        esquerda.setBorder(new EmptyBorder(25, 30, 20, 20));

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(branco);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borda),
                new EmptyBorder(20, 22, 20, 22)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 8, 7, 8);
        gbc.anchor = GridBagConstraints.WEST;

        // ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        form.add(criarLabel("ID"), gbc);

        txtId = criarCampo(18);
        txtId.setEditable(false);

        gbc.gridx = 1;
        form.add(txtId, gbc);

        // Pet
        gbc.gridx = 0;
        gbc.gridy = 1;
        form.add(criarLabel("Pet *"), gbc);

        cbPets = new JComboBox<>();
        cbPets.setPreferredSize(new Dimension(200, 30));
        carregarPets();

        gbc.gridx = 1;
        form.add(cbPets, gbc);

        // Veterinário
        gbc.gridx = 0;
        gbc.gridy = 2;
        form.add(criarLabel("Veterinário *"), gbc);

        cbVets = new JComboBox<>();
        cbVets.setPreferredSize(new Dimension(200, 30));
        carregarVeterinarios();

        gbc.gridx = 1;
        form.add(cbVets, gbc);

        // Descrição
        gbc.gridx = 0;
        gbc.gridy = 3;
        form.add(criarLabel("Descrição"), gbc);

        txtDescricao = criarCampo(18);

        gbc.gridx = 1;
        form.add(txtDescricao, gbc);

        // Diagnóstico
        gbc.gridx = 0;
        gbc.gridy = 4;
        form.add(criarLabel("Diagnóstico"), gbc);

        txtDiagnostico = criarCampo(18);

        gbc.gridx = 1;
        form.add(txtDiagnostico, gbc);

        // Valor
        gbc.gridx = 0;
        gbc.gridy = 5;
        form.add(criarLabel("Valor (R$) *"), gbc);

        txtValor = criarCampo(18);

        gbc.gridx = 1;
        form.add(txtValor, gbc);

        esquerda.add(form, BorderLayout.CENTER);

        // botões
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 15));
        botoes.setBackground(fundo);

        JButton btnSalvar = criarBotao("Salvar", verde);
        JButton btnAtualizar = criarBotao("Atualizar", azul);
        JButton btnExcluir = criarBotao("Excluir", vermelho);
        JButton btnLimpar = criarBotao("Limpar", cinza);

        botoes.add(btnSalvar);
        botoes.add(btnAtualizar);
        botoes.add(btnExcluir);
        botoes.add(btnLimpar);

        esquerda.add(botoes, BorderLayout.SOUTH);
        painelPrincipal.add(esquerda, BorderLayout.WEST);

        // tabela
        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.setBackground(fundo);
        painelTabela.setBorder(new EmptyBorder(25, 0, 25, 25));

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Pet ID", "Vet ID", "Data", "Diagnóstico", "Valor"}, 0
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
        tabela.getColumnModel().getColumn(2).setCellRenderer(centro);
        tabela.getColumnModel().getColumn(3).setCellRenderer(centro);
        tabela.getColumnModel().getColumn(5).setCellRenderer(centro);

        tabela.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(60);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(60);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(100);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(180);
        tabela.getColumnModel().getColumn(5).setPreferredWidth(80);

        JScrollPane scrollTabela = new JScrollPane(tabela);
        scrollTabela.setBorder(BorderFactory.createLineBorder(borda));
        scrollTabela.getViewport().setBackground(Color.WHITE);

        painelTabela.add(scrollTabela, BorderLayout.CENTER);
        painelPrincipal.add(painelTabela, BorderLayout.CENTER);

        // eventos
        btnSalvar.addActionListener(e -> salvarAtendimento());
        btnAtualizar.addActionListener(e -> atualizarAtendimento());
        btnExcluir.addActionListener(e -> excluirAtendimento());
        btnLimpar.addActionListener(e -> limparCampos());

        tabela.getSelectionModel().addListSelectionListener(e -> selecionarLinha());

        carregarTabela();
    }

    // cria os labels iguais
    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        return label;
    }

    // cria os campos de texto no mesmo tamanho
    private JTextField criarCampo(int colunas) {
        JTextField campo = new JTextField(colunas);
        campo.setPreferredSize(new Dimension(200, 30));
        campo.setFont(new Font("Arial", Font.PLAIN, 13));
        return campo;
    }

    // cria os botões já com o estilo
    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setFont(new Font("Arial", Font.BOLD, 12));
        botao.setPreferredSize(new Dimension(90, 36));
        return botao;
    }

    // coloca os pets do banco no combo
    private void carregarPets() {
        cbPets.removeAllItems();

        for (Pet p : petDAO.listarTodos()) {
            cbPets.addItem(p);
        }
    }

    // coloca os veterinarios do banco no combo
    private void carregarVeterinarios() {
        cbVets.removeAllItems();

        for (Veterinario v : veterinarioDAO.listarTodos()) {
            cbVets.addItem(v);
        }
    }

    // carrega os atendimentos na tabela
    private void carregarTabela() {
        tableModel.setRowCount(0);

        for (Atendimento a : atendimentoDAO.listarTodos()) {
            tableModel.addRow(new Object[]{
                    a.getId(),
                    a.getPetId(),
                    a.getVeterinarioId(),
                    a.getDataAtendimento(),
                    a.getDiagnostico(),
                    String.format("R$ %.2f", a.getValor())
            });
        }
    }

    // quando clica numa linha, preenche os campos
    private void selecionarLinha() {
        int linha = tabela.getSelectedRow();

        if (linha == -1) {
            return;
        }

        int id = Integer.parseInt(tableModel.getValueAt(linha, 0).toString());
        int petId = Integer.parseInt(tableModel.getValueAt(linha, 1).toString());
        int vetId = Integer.parseInt(tableModel.getValueAt(linha, 2).toString());

        txtId.setText(String.valueOf(id));

        // procura o pet pelo ID dentro do combo
        for (int i = 0; i < cbPets.getItemCount(); i++) {
            if (cbPets.getItemAt(i).getId() == petId) {
                cbPets.setSelectedIndex(i);
                break;
            }
        }

        // procura o veterinario pelo ID
        for (int i = 0; i < cbVets.getItemCount(); i++) {
            if (cbVets.getItemAt(i).getId() == vetId) {
                cbVets.setSelectedIndex(i);
                break;
            }
        }

        // pega o atendimento completo para mostrar os campos que não estão na tabela
        for (Atendimento a : atendimentoDAO.listarTodos()) {
            if (a.getId() == id) {
                txtDescricao.setText(a.getDescricao() == null ? "" : a.getDescricao());
                txtDiagnostico.setText(a.getDiagnostico() == null ? "" : a.getDiagnostico());
                txtValor.setText(String.valueOf(a.getValor()));
                break;
            }
        }
    }

    // cadastra um novo atendimento
    private void salvarAtendimento() {
        Pet pet = (Pet) cbPets.getSelectedItem();
        Veterinario vet = (Veterinario) cbVets.getSelectedItem();

        if (pet == null || vet == null || txtValor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha os campos obrigatórios!");
            return;
        }

        try {
            double valor = Double.parseDouble(txtValor.getText().replace(",", "."));

            Atendimento a = new Atendimento(
                    pet.getId(),
                    vet.getId(),
                    new Date(System.currentTimeMillis()),
                    new Time(System.currentTimeMillis()),
                    txtDescricao.getText(),
                    txtDiagnostico.getText(),
                    valor
            );

            if (atendimentoDAO.registrar(a)) {
                JOptionPane.showMessageDialog(this, "Atendimento cadastrado com sucesso!");
                limparCampos();
                carregarTabela();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Informe um valor válido!");
        }
    }

    // atualiza o atendimento selecionado
    private void atualizarAtendimento() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um atendimento para atualizar!");
            return;
        }

        Pet pet = (Pet) cbPets.getSelectedItem();
        Veterinario vet = (Veterinario) cbVets.getSelectedItem();

        if (pet == null || vet == null || txtValor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha os campos obrigatórios!");
            return;
        }

        try {
            double valor = Double.parseDouble(txtValor.getText().replace(",", "."));

            Atendimento a = new Atendimento(
                    Integer.parseInt(txtId.getText()),
                    pet.getId(),
                    vet.getId(),
                    null,
                    null,
                    txtDescricao.getText(),
                    txtDiagnostico.getText(),
                    valor
            );

            if (atendimentoDAO.atualizar(a)) {
                JOptionPane.showMessageDialog(this, "Atendimento atualizado com sucesso!");
                limparCampos();
                carregarTabela();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Verifique os dados informados!");
        }
    }

    // exclui o atendimento selecionado
    private void excluirAtendimento() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um atendimento para excluir!");
            return;
        }

        int resposta = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente excluir este atendimento?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION
        );

        if (resposta == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(txtId.getText());

            if (atendimentoDAO.excluir(id)) {
                JOptionPane.showMessageDialog(this, "Atendimento excluído!");
                limparCampos();
                carregarTabela();
            }
        }
    }

    // limpa os campos e a seleção
    private void limparCampos() {
        txtId.setText("");
        txtDescricao.setText("");
        txtDiagnostico.setText("");
        txtValor.setText("");

        tabela.clearSelection();

        if (cbPets.getItemCount() > 0) {
            cbPets.setSelectedIndex(0);
        }

        if (cbVets.getItemCount() > 0) {
            cbVets.setSelectedIndex(0);
        }
    }
}