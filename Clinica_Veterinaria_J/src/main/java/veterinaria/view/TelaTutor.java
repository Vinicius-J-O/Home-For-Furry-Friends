package veterinaria.view;

import veterinaria.dao.TutorDAO;
import veterinaria.model.Tutor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaTutor extends JFrame {
    private JTextField txtId, txtNome, txtCpf, txtTelefone, txtEmail, txtEndereco;

    private JTable tabela;

    // O "tableModel" é quem realmente guarda os dados da tabela (linhas e colunas).
    // A JTable apenas "desenha" o que está no tableModel.
    private DefaultTableModel tableModel;

    // Um tipo de "canal" de comunicação com o banco de dados para tudo que for relacionado ao Tutor.
    private TutorDAO tutorDAO;

    public TelaTutor() {
        tutorDAO = new TutorDAO();

        setTitle("Gerenciador de Tutores - Home For Furry Friends");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Header
        JPanel painelHeader = new JPanel();
        painelHeader.setBackground(Color.decode("#2E7D6B"));
        JLabel lblTitulo = new JLabel("Home for Furry Friends - Cadastro de Tutor");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        painelHeader.add(lblTitulo);
        add(painelHeader, BorderLayout.NORTH);

        // Formulário
        JPanel painelForm = new JPanel(new GridLayout(6, 2, 5, 5));
        painelForm.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        painelForm.add(new JLabel("ID:"));
        txtId = new JTextField();
        txtId.setEditable(false); // O ID vai ser gerado pelo banco e o usuário não poderá digitar nessa área
        painelForm.add(txtId);

        painelForm.add(new JLabel("Nome*:")); // O "*" indica que o campo é obrigatório
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

        // Botões
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

        // Painel que junta o formulário (deixando em cima) com os botões (deixando embaixo).
        JPanel painelCentral = new JPanel(new BorderLayout());
        painelCentral.add(painelForm, BorderLayout.NORTH);
        painelCentral.add(painelBotoes, BorderLayout.SOUTH);

        add(painelCentral, BorderLayout.WEST); // Faz o painelCentral ficar do lado esquerdo da janela

        // Tabela de dados
        tableModel = new DefaultTableModel(new Object[]{"ID", "Nome", "CPF", "Telefone", "Email", "Endereço"}, 0);
        tabela = new JTable(tableModel);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Adiciona ações para os botões
        btnSalvar.addActionListener(e -> salvarTutor());
        btnEditar.addActionListener(e -> atualizarTutor());
        btnExcluir.addActionListener(e -> excluirTutor());
        btnLimpar.addActionListener(e -> limparCampos());

        // Este listener meio que "escuta" quando o usuário clica em uma linha da tabela,
        // e chama o selecionarLinha() para preencher o formulário com aqueles dados.
        tabela.getSelectionModel().addListSelectionListener(e -> selecionarLinha());

        carregarTabela();
    }

    // Busca todos os tutores no banco (via DAO) e preenche a tabela com eles.
    private void carregarTabela() {
        tableModel.setRowCount(0); // Isso limpa a tabela antes de recarregar, para não duplicar as linhas
        for (Tutor t : tutorDAO.listarTodos()) {
            // Para cada Tutor devolvido pelo banco, adiciona uma linha na tabela
            // com os valores na mesma ordem das colunas definidas abaixo.
            tableModel.addRow(new Object[]{t.getId(), t.getNome(), t.getCpf(), t.getTelefone(), t.getEmail(), t.getEndereco()});
        }
    }

    // Chamado quando clicam em "Salvar" e serve para criar um novo tutor com os dados do formulário.
    private void salvarTutor() {
        // Validação simples: nome e CPF são obrigatórios.
        // .trim() Remove os espaços do início e do fim do texto,
        // .isEmpty() Verifica se o texto está vazio ou se tem algo escrito.
        if (txtNome.getText().trim().isEmpty() || txtCpf.getText().trim().isEmpty()) {                
            JOptionPane.showMessageDialog(this, "Preencha os campos obrigatórios (Nome e CPF)!");
            return; // Para a execução do método sem salvar nada
        }

        // Cria um objeto Tutor com os dados digitados, ele está sem id pois é um cadastro novo.
        Tutor t = new Tutor(txtNome.getText(), txtCpf.getText(), txtTelefone.getText(), txtEmail.getText(), txtEndereco.getText());

        // Pede para o DAO salvar esse tutor no banco de dados.
        if (tutorDAO.cadastrar(t)) {
            JOptionPane.showMessageDialog(this, "Tutor cadastrado com sucesso!");
            limparCampos();   // Limpa o formulário
            carregarTabela(); // Recarrega a tabela para mostrar o novo tutor
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar tutor.");
        }
    }

    // Chamado quando clicam em "Atualizar" e serve para editar um tutor que já existe
    // (precisa ter selecionado uma linha da tabela antes para saber qual tutor editar).
    private void atualizarTutor() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um tutor na tabela para atualizar!");
            return;
        }
        // Aqui nós criamos o Tutor com o id porque nós precisamos dizer ao banco qual registro vai ser alterado.
        Tutor t = new Tutor(Integer.parseInt(txtId.getText()), txtNome.getText(), txtCpf.getText(), txtTelefone.getText(), txtEmail.getText(), txtEndereco.getText());
        if (tutorDAO.atualizar(t)) {
            JOptionPane.showMessageDialog(this, "Tutor atualizado com sucesso!");
            limparCampos();
            carregarTabela();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar tutor.");
        }
    }

    // Chamado quando clicam em "Excluir" e serve para apagar o tutor selecionado da tabela.
    private void excluirTutor() {
        if (txtId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Selecione um tutor na tabela para excluir!");
            return;
        }
        // Antes de excluir de verdade, pedimos uma confirmação para o usuário,
        // isso evita que algo seja excluído acidentalmente.
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

    // Chamado sempre que o usuário clica em uma linha da tabela e serve para copiar os
    // dados daquela linha para dentro dos campos do formulário, permitindo que ao usuário
    // editar (Atualizar) ou excluir aquele registro específico.
    private void selecionarLinha() {
        int linha = tabela.getSelectedRow();
        if (linha != -1) {
            // tableModel.getValueAt(linha, coluna) lê o valor de algo específico da tabela.
            // As colunas são: 0=ID, 1=Nome, 2=CPF, 3=Telefone, 4=Email, 5=Endereço
            txtId.setText(tableModel.getValueAt(linha, 0).toString());
            txtNome.setText(tableModel.getValueAt(linha, 1).toString());
            txtCpf.setText(tableModel.getValueAt(linha, 2).toString());
            // Aqui ele checa usando "!= null" antes de chamar .toString(), pois o telefone/email/endereço
            // são campos opcionais e podem estar vazios (null) no banco de dados.
            txtTelefone.setText(tableModel.getValueAt(linha, 3) != null ? tableModel.getValueAt(linha, 3).toString() : "");
            txtEmail.setText(tableModel.getValueAt(linha, 4) != null ? tableModel.getValueAt(linha, 4).toString() : "");
            txtEndereco.setText(tableModel.getValueAt(linha, 5) != null ? tableModel.getValueAt(linha, 5).toString() : "");
        }
    }

    // Ele limpa todos os campos do formulário e desmarca qualquer linha selecionada
    // na tabela, ele é usado depois de salvar/atualizar/excluir, e também quando
    // o botão "Limpar" é clicado diretamente.
    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtEndereco.setText("");
        tabela.clearSelection();
    }

    // Teste para a tela sozinha, sem passar pelo menu principal.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaTutor().setVisible(true));
    }
}
