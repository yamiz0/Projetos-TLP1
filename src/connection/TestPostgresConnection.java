package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestPostgresConnection {
    public static void main(String[] args) {
        // Configurações de conexão
        String url = "jdbc:postgresql://localhost:5432/biblioteca"; // URL do banco de dados
        String usuario = "postgres"; // Nome de usuário do PostgreSQL
        String senha = "postgresql"; // Senha do PostgreSQL

        // Tenta estabelecer a conexão
        try (Connection connection = DriverManager.getConnection(url, usuario, senha)) {
            System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }
}
