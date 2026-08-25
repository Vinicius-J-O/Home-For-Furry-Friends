package veterinaria.dao;

import veterinaria.Conexao;
import veterinaria.model.Pet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PetDAO {

    public boolean cadastrar(Pet pet) {
        String sql = "INSERT INTO pet (tutor_id, nome, especie, raca, sexo, data_nascimento, peso) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pet.getTutorId());
            stmt.setString(2, pet.getNome());
            stmt.setString(3, pet.getEspecie());
            stmt.setString(4, pet.getRaca());
            stmt.setString(5, pet.getSexo());
            stmt.setDate(6, pet.getDataNascimento());
            stmt.setDouble(7, pet.getPeso());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Pet> listarTodos() {
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT * FROM pet ORDER BY id DESC";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Pet pet = new Pet(
                    rs.getInt("id"),
                    rs.getInt("tutor_id"),
                    rs.getString("nome"),
                    rs.getString("especie"),
                    rs.getString("raca"),
                    rs.getString("sexo"),
                    rs.getDate("data_nascimento"),
                    rs.getDouble("peso")
                );
                pets.add(pet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pets;
    }

    public boolean atualizar(Pet pet) {
        String sql = "UPDATE pet SET tutor_id = ?, nome = ?, especie = ?, raca = ?, sexo = ?, data_nascimento = ?, peso = ? WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pet.getTutorId());
            stmt.setString(2, pet.getNome());
            stmt.setString(3, pet.getEspecie());
            stmt.setString(4, pet.getRaca());
            stmt.setString(5, pet.getSexo());
            stmt.setDate(6, pet.getDataNascimento());
            stmt.setDouble(7, pet.getPeso());
            stmt.setInt(8, pet.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM pet WHERE id = ?";
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