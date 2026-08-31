package veterinaria.dao;

import veterinaria.Conexao;
import veterinaria.model.Pet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PetDAO {

    // CREATE — cadastra um novo pet no banco de dados, vinculado a um tutor (tutor_id).
    public boolean cadastrar(Pet pet) {
        String sql = "INSERT INTO pet (tutor_id, nome, especie, raca, sexo, data_nascimento, peso) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Completa cada "?" da consulta, na mesma ordem das colunas abaixo.
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

    // READ — pega todos os pets cadastrados, do mais recente para o mais antigo.
    public List<Pet> listarTodos() {
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT * FROM pet ORDER BY id DESC";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            // Percorre cada linha encontrada no banco e transforma em um objeto Pet.
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

    // UPDATE — atualiza os dados de um pet já existente, identificado pelo id.
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
            stmt.setInt(8, pet.getId()); // define qual pet (WHERE id = ?) deve ser atualizado
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE — remove um pet do banco de dados através do seu id.
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
