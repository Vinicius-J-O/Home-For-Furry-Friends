package veterinaria.dao;

import veterinaria.Conexao;
import veterinaria.model.Exame;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExameDAO {

    // CREATE — cadastra um novo exame no catálogo.
    public boolean cadastrar(Exame exame) {
        String sql = "INSERT INTO exame (nome, descricao, valor) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, exame.getNome());
            stmt.setString(2, exame.getDescricao());
            stmt.setDouble(3, exame.getValor());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ — lista todos os exames cadastrados, em ordem alfabética pelo nome.
    public List<Exame> listarTodos() {
        List<Exame> exames = new ArrayList<>();
        String sql = "SELECT * FROM exame ORDER BY nome";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Exame e = new Exame(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("descricao"),
                    rs.getDouble("valor")
                );
                exames.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exames;
    }

    // DELETE — remove um exame do catálogo usando o seu id.
    public boolean excluir(int id) {
        String sql = "DELETE FROM exame WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
