package veterinaria;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL = "jdbc:mysql://localhost:3306/veterinaria";
    private static final String USUARIO = "root";
    private static final String SENHA = "";

    // Este método devolve uma "Connection" (conexão) pronta para uso.
    // "static" significa que não precisa criar um objeto Conexao para usar,
    // é só chamar Conexao.getConexao() de qualquer lugar do sistema.
    public static Connection getConexao() {
        try {
            // Esta linha "carrega" o Driver do MySQL, ele é um componente que sabe
            // como o Java deve conversar especificamente com um banco MySQL
            // (bancos diferentes, como PostgreSQL ou SQL Server, usam drivers diferentes).
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Aqui, nós abrimos a conexão com o banco, usando o endereço,
            // usuário e senha definidos, e devolvemos a conexão pronta
            // para quem chamou o método poder usar.
            return DriverManager.getConnection(URL, USUARIO, SENHA);

        } catch (ClassNotFoundException e) {
            // Isso acontece se o driver do MySQL não estiver disponível no projeto
            // (por exemplo, se a dependência não foi baixada corretamente pelo Maven).
            throw new RuntimeException("Driver do MySQL não encontrado no projeto. Adicione o JAR do Connector/J.", e);

        } catch (SQLException e) {
            // Isso acontece se o MySQL não estiver rodando, se a URL/usuário/senha
            // estiverem errados, ou se o banco "veterinaria" ainda não tiver sido criado.
            throw new RuntimeException("Erro ao conectar no banco de dados. Verifique a URL, usuário e senha.", e);
        }
    }
}
