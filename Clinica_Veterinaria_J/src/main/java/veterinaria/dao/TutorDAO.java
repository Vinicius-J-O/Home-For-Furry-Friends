package veterinaria.dao;

import veterinaria.Conexao;
import veterinaria.model.Tutor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TutorDAO {

    public boolean cadastrar(Tutor tutor) {
        String sql = "INSERT INTO tutor (nome, cpf, telefone, email, endereco) VALUES (?, ?, ?, ?, ?)";
        try (
            Connection conn = Conexao.getConexao(); 
            PreparedStatement stmt = conn.prepareStatement(sql)
            ) {
                stmt.setString(1, tutor.getNome());
                stmt.setString(2, tutor.getCpf());
                stmt.setString(3, tutor.getTelefone());
                stmt.setString(4, tutor.getEmail());
                stmt.setString(5, tutor.getEndereco());
                stmt.executeUpdate();
                return true;
             } catch (SQLException e) {
                e.printStackTrace();
                return false;
             }
    }

    public List<Tutor> listarTodos() {
        List<Tutor> tutores = new ArrayList<>();
        String sql = "SELECT * FROM tutor ORDER BY id DESC";
        try (
            Connection conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                Tutor tutor = new Tutor(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getString("telefone"),
                    rs.getString("email"),
                    rs.getString("endereco")
                );
                tutores.add(tutor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tutores;
    }

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
            stmt.setInt(6, tutor.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

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