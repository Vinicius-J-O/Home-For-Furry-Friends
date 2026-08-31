package veterinaria.dao;

import veterinaria.Conexao;
import veterinaria.model.Veterinario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeterinarioDAO {

    // CREATE — cadastra um novo veterinário no banco.
    public boolean cadastrar(Veterinario vet) {
        String sql = "INSERT INTO veterinario (nome, crmv, telefone, especialidade) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, vet.getNome());
            stmt.setString(2, vet.getCrmv());
            stmt.setString(3, vet.getTelefone());
            stmt.setString(4, vet.getEspecialidade());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ — busca todos os veterinários cadastrados.
    public List<Veterinario> listarTodos() {
        List<Veterinario> vets = new ArrayList<>();
        String sql = "SELECT * FROM veterinario ORDER BY id DESC";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Veterinario vet = new Veterinario(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("crmv"),
                    rs.getString("telefone"),
                    rs.getString("especialidade")
                );
                vets.add(vet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vets;
    }

    // UPDATE — atualiza os dados de um veterinário já existente.
    public boolean atualizar(Veterinario vet) {
        String sql = "UPDATE veterinario SET nome = ?, crmv = ?, telefone = ?, especialidade = ? WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, vet.getNome());
            stmt.setString(2, vet.getCrmv());
            stmt.setString(3, vet.getTelefone());
            stmt.setString(4, vet.getEspecialidade());
            stmt.setInt(5, vet.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE — remove um veterinário do banco, usando o seu id.
    public boolean excluir(int id) {
        String sql = "DELETE FROM veterinario WHERE id = ?";
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
