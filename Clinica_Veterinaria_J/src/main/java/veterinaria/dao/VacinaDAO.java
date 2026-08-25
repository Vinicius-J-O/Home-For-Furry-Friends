package veterinaria.dao;

import veterinaria.Conexao;
import veterinaria.model.Vacina;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VacinaDAO {

    public boolean cadastrar(Vacina vacina) {
        String sql = "INSERT INTO vacina (pet_id, nome, data_aplicacao, proxima_dose) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, vacina.getPetId());
            stmt.setString(2, vacina.getNome());
            stmt.setDate(3, vacina.getDataAplicacao());
            stmt.setDate(4, vacina.getProximaDose());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Vacina> listarPorPet(int petId) {
        List<Vacina> vacinas = new ArrayList<>();
        String sql = "SELECT * FROM vacina WHERE pet_id = ? ORDER BY data_aplicacao DESC";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, petId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Vacina v = new Vacina(
                    rs.getInt("id"),
                    rs.getInt("pet_id"),
                    rs.getString("nome"),
                    rs.getDate("data_aplicacao"),
                    rs.getDate("proxima_dose")
                );
                vacinas.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vacinas;
    }
}