package veterinaria.dao;

import veterinaria.Conexao;
import veterinaria.model.Tutor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TutorDAO {

    // CREATE - Esse método recebe um objeto Tutor (já preenchido com nome, cpf,
    // etc.) e salva ele como uma nova linha na tabela "tutor" do banco de dados.
    public boolean cadastrar(Tutor tutor) {

        // Esse é um comando SQL de inserção. As "?" são "espaços reservados"
        // (placeholders) que serão preenchidos com os valores reais logo abaixo.
        // Usar "?" em vez de colar o texto direto na string é uma boa prática de
        // segurança chamada "PreparedStatement", ela evita um tipo de ataque
        // chamado "SQL Injection" (quando alguém digita um texto malicioso
        // tentando manipular o comando SQL).
        String sql = "INSERT INTO tutor (nome, cpf, telefone, email, endereco) VALUES (?, ?, ?, ?, ?)";

        // O bloco "try (...)" abaixo é chamado de "try-with-resources": ele abre
        // a conexão com o banco (Connection) e o comando preparado (PreparedStatement),
        // e garante que os dois sejam fechados automaticamente no final, mesmo se
        // der algum erro no meio do caminho. Isso evita "vazamento" de conexões abertas.
        try (
            Connection conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql)
            ) {
                // Aqui preenchemos cada "?" da consulta SQL, na ordem em que aparecem
                // (1º "?" = nome, 2º "?" = cpf, e assim por diante).
                stmt.setString(1, tutor.getNome());
                stmt.setString(2, tutor.getCpf());
                stmt.setString(3, tutor.getTelefone());
                stmt.setString(4, tutor.getEmail());
                stmt.setString(5, tutor.getEndereco());

                // executeUpdate() executa de fato o comando SQL no banco
                // (usado para INSERT, UPDATE e DELETE, que são comandos que "mudam" dados).
                stmt.executeUpdate();
                return true;
             } catch (SQLException e) {
                // Se algo der errado (ex: banco fora do ar, CPF duplicado, etc.),
                // mostramos o erro no console para ajudar a entender o problema,
                // e devolvemos "false" para avisar quem chamou este método que falhou.
                e.printStackTrace();
                return false;
             }
    }

    // READ - Esse método pega todos os tutores cadastrados no banco de dados
    // e devolve como uma lista de objetos Tutor, prontos para serem mostrados
    // em uma tabela na tela.
    public List<Tutor> listarTodos() {
        List<Tutor> tutores = new ArrayList<>(); // Cria uma lista vazia, que vamos preenchendo depois
        String sql = "SELECT * FROM tutor ORDER BY id DESC"; // Pega tudo, do mais recente pro mais antigo

        try (
            Connection conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            // ResultSet é o "resultado" da consulta, como se fosse uma tabela
            // temporária com todas as linhas que o banco encontrou.
            ResultSet rs = stmt.executeQuery()
        ) {
            // rs.next() vai para a próxima linha do resultado, e devolve "false"
            // quando não tem mais linhas, fazendo esse "while" percorrer linha por linha.
            while (rs.next()) {
                // Para cada linha do banco, criamos um objeto Tutor correspondente,
                // lendo cada coluna pelo nome (rs.getInt("id"), rs.getString("nome"), etc.)
                Tutor tutor = new Tutor(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getString("endereco")
                );
                tutores.add(tutor); // Adiciona esse tutor na lista que vamos devolver
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tutores; // Devolve a lista completa (pode vir vazia, se não tiver tutores)
    }

    // UPDATE - Esse método atualiza os dados de um tutor que já existe no banco,
    // usando o "id" dele para saber qual linha da tabela deve ser alterada.
    public boolean atualizar (Tutor tutor) {
        String sql = "UPDATE tutor SET nome = ?, cpf = ?, telefone = ?, email = ?, endereco = ? WHERE id = ?";
        try (
            Connection conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, tutor.getNome());
            stmt.setString(2, tutor.getCpf());
            stmt.setString(3, tutor.getTelefone());
            stmt.setString(4, tutor.getEmail());
            stmt.setString(5, tutor.getEndereco());
            // O último "?" corresponde ao "WHERE id = ?", ou seja, aqui dizemos
            // qual tutor (pelo id) deve ser atualizado.
            stmt.setInt(6, tutor.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE - Esse método apaga o tutor com o "id" informado do banco de dados.
    public boolean excluir(int id) {
        String sql = "DELETE FROM tutor WHERE id = ?";
        try (
            Connection conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
