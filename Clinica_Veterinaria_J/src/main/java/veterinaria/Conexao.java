package veterinaria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL = "jdbc:mysql://localhost:3306/veterinaria";
    private static final String USUARIO = "root";
    private static final String SENHA = "";

    public static Connection getConexao() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver do MySQL não encontrado no projeto. Adicione o JAR do Connector/J.", e);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar no banco de dados. Verifique a URL, usuário e senha.", e);
        }
    }
}
