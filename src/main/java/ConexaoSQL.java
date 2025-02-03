import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class ConexaoSQL {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://wagnerweinert.com.br:3306/jdbc";
        String username = "tads24_lourenco";
        String password = "tads24_lourenco";

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
                        //inserirRegistro(connection, scanner);
                        break;
                    case 2:
                        //alterarRegistro(connection, scanner);
                        break;
                    case 3:
                        //excluirRegistro(connection, scanner);
                        break;
                    case 4:
                        //listarRegistros(connection);
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
}
