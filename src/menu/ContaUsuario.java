package menu;

import java.sql.*;
import java.util.Scanner;

public class ContaUsuario {

    public static void main(String[] args) {
        // Dados de conexão
        String url = "jdbc:postgresql://localhost:5432/biblioteca"; // URL do banco de dados
        String usuario = "postgres"; // Nome de usuário do PostgreSQL
        String senha = "ronaldinhogoat"; // Senha do PostgreSQL

        Scanner scan = new Scanner(System.in);

        // Connection
        try (Connection connection = DriverManager.getConnection(url, usuario, senha)) {
            System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
            // Menu
            while (true) {
                System.out.println("\n--MENU--");
                System.out.println("Escolha uma opção:");
                System.out.println("1 - Criar conta");
                System.out.println("2 - Listar contas");
                System.out.println("3 - Excluir conta");
                System.out.println("4 - Sair");
                System.out.print("Opção: ");
                int op = scan.nextInt();
                scan.nextLine(); // Consumir a nova linha

                switch (op) {
                    case 1:
                        creatAccount(connection, scan);
                        break;
                    case 2:
                        listAccounts(connection);
                        break;
                    case 3:
                        deleteAccount(connection, scan);
                        break;
                    case 4:
                        System.out.println("Saindo...");
                        return;
                    default:
                        System.out.println("Opção inválida!");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    private static void creatAccount(Connection connection, Scanner scan) throws SQLException{
        System.out.print("Digite o seu nome: ");
        String nome = scan.nextLine();
        System.out.print("Digite o email: ");
        String email = scan.nextLine();
        System.out.print("Digite a senha: ");
        String senha = scan.nextLine();
        System.out.print("Digite o telefone: ");
        String telefone = scan.nextLine();
        System.out.print("Digite o endereço: ");
        String endereco = scan.nextLine();

        // sql
        String sql = "INSERT INTO usuario(nome, email, senha, telefone, endereco) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, nome);
            statement.setString(2, email);
            statement.setString(3, senha);
            statement.setString(4, telefone);
            statement.setString(5, endereco);
            statement.executeUpdate();
            System.out.println("Conta criada com sucesso!");
        }
    }

    private static void deleteAccount(Connection connection, Scanner scan) throws SQLException{
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";

        System.out.println("Digite o ID da conta que deseja deletar: ");
        int id = scan.nextInt();

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, id);

            // Executa a declaração SQL
            int rowsAffected = statement.executeUpdate();

            // Verifica se a exclusão foi bem-sucedida
            if (rowsAffected > 0) {
                System.out.println("Conta excluída com sucesso!");
            } else {
                System.out.println("Nenhuma conta foi excluída. Verifique o ID informado.");
            }
        }
    }

    private static void listAccounts(Connection connection) throws SQLException {
        String sql = "SELECT id_usuario, nome, email FROM usuario";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            ResultSet resultSet = statement.executeQuery();

            System.out.println("---CONTAS---");

            while (resultSet.next()){
                System.out.println("ID: " + resultSet.getInt("id_usuario"));
                System.out.println("Nome: " + resultSet.getString("nome"));
                System.out.println("Email: " + resultSet.getString("email"));
                System.out.println("________________________");
            }
        }
    }
}
