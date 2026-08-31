package veterinaria.dao;

import veterinaria.Conexao;
import veterinaria.model.Procedimento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProcedimentoDAO {

    // CREATE — cadastra um novo procedimento no catálogo.
    public boolean cadastrar(Procedimento proc) {
        String sql = "INSERT INTO procedimento (nome, descricao, valor) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, proc.getNome());
            stmt.setString(2, proc.getDescricao());
            stmt.setDouble(3, proc.getValor());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ — lista todos os procedimentos cadastrados, em ordem alfabética pelo nome.
    public List<Procedimento> listarTodos() {
        List<Procedimento> procs = new ArrayList<>();
        String sql = "SELECT * FROM procedimento ORDER BY nome";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Procedimento p = new Procedimento(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("descricao"),
                    rs.getDouble("valor")
                );
                procs.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return procs;
    }
}
