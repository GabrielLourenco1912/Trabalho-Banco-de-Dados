
// Debian 12
// apt-get install openjdk-17-jdk
// wget https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-j-9.1.0.zip


// fazer o download de mysql-connector-j-9.1.0.jar e deixar no mesmo diretório
// javac -cp .:mysql-connector-j-9.1.0.jar ConexaoMySQL.java
// java -cp .:mysql-connector-j-9.1.0.jar ConexaoMySQL


// servidor: wagnerweinert.com.br
// porta: 3306
// usuario: jdbc
// senha: jdbc
// banco: jdbc

// create table pessoas(id int auto_increment not null primary key, nome varchar(255) not null, idade int not null);


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class ConexaoMySQL{

    public static void main(String[] args) {
        // Configurações da URL de Conexão
        String jdbcUrl = "jdbc:mysql://wagnerweinert.com.br:3306/jdbc";
        String username = "jdbc";
        String password = "jdbc";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            System.out.println("Conexão estabelecida com sucesso!");

            Scanner scanner = new Scanner(System.in);
            int opcao;

            do {
                System.out.println("Menu:");
                System.out.println("1. Inserir Registro");
                System.out.println("2. Alterar Registro");
                System.out.println("3. Excluir Registro");
                System.out.println("4. Listar Registros");
                System.out.println("5. Sair");
                System.out.print("Escolha uma opção: ");
                opcao = scanner.nextInt();
                scanner.nextLine(); // Consumir a nova linha

                switch (opcao) {
                    case 1:
                        inserirRegistro(connection, scanner);
                        break;
                    case 2:
                        alterarRegistro(connection, scanner);
                        break;
                    case 3:
                        excluirRegistro(connection, scanner);
                        break;
                    case 4:
                        listarRegistros(connection);
                        break;
                    case 5:
                        System.out.println("Encerrando o programa.");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        break;
                }
            } while (opcao != 5);

            scanner.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void inserirRegistro(Connection connection, Scanner scanner) {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Idade: ");
        int idade = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha

        String sql = "INSERT INTO pessoas (nome, idade) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setInt(2, idade);
            pstmt.executeUpdate();
            System.out.println("Registro inserido com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void alterarRegistro(Connection connection, Scanner scanner) {
        System.out.print("ID do registro a alterar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha
        System.out.print("Novo Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Nova Idade: ");
        int idade = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha

        String sql = "UPDATE pessoas SET nome = ?, idade = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setInt(2, idade);
            pstmt.setInt(3, id);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Registro atualizado com sucesso!");
            } else {
                System.out.println("Nenhum registro encontrado com o ID fornecido.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void excluirRegistro(Connection connection, Scanner scanner) {
        System.out.print("ID do registro a excluir: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha

        String sql = "DELETE FROM pessoas WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Registro excluído com sucesso!");
            } else {
                System.out.println("Nenhum registro encontrado com o ID fornecido.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void listarRegistros(Connection connection) {
        String sql = "SELECT id, nome, idade FROM pessoas";
        try (Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                int idade = resultSet.getInt("idade");
                System.out.println("ID: " + id + ", Nome: " + nome + ", Idade: " + idade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}