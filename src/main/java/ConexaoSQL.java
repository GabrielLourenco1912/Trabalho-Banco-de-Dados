import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Scanner;


public class ConexaoSQL {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://wagnerweinert.com.br:3306/tads24_lourenco";
        String username = "tads24_lourenco";
        String password = "tads24_lourenco";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            System.out.println("Conexão estabelecida com sucesso!");

            Scanner scanner = new Scanner(System.in);
            int opcao;

            do {
                System.out.println("Menu:");
                System.out.println("1. Inserir Material");
                System.out.println("2. Alterar Registro");
                System.out.println("3. Excluir Registro");
                System.out.println("4. Listar Materiais");
                System.out.println("5. Listar Material pelo ID");
                System.out.println("6. Procurar Material pelo Nome");
                System.out.println("7. Procurar preço pelo nome do paciente");
                System.out.println("8. Sair");
                System.out.print("Escolha uma opção: ");
                opcao = scanner.nextInt();
                scanner.nextLine(); // Consumir a nova linha

                switch (opcao) {
                    case 1:
                        inserirMaterial(connection, scanner);
                        break;
                    case 2:
                        alterarMaterial(connection, scanner);
                        break;
                    case 3:
                        excluirMaterial(connection, scanner);
                        break;
                    case 4:
                        listarMateriais(connection);
                        break;
                    case 5:
                        procurarMaterial(connection, scanner);
                        break;
                    case 6:
                        procurarMaterialPorNome(connection, scanner);
                        break;
                    case 7:
                        procurarPrecoPorCliente(connection, scanner);
                        break;
                    case 8:
                        System.out.println("Encerrando o programa.");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        break;
                }
            } while (opcao != 8);

            scanner.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void inserirMaterial(Connection connection, Scanner scanner) {
        System.out.print("Nome: ");
        String nomeMaterial = scanner.nextLine();
        System.out.print("Quantidade: ");
        int qntdMaterial = scanner.nextInt();
        System.out.print("Descrição do material: ");
        scanner.nextLine(); // Consumir a nova linha
        String descricaoMaterial = scanner.nextLine();

        String sql = "INSERT INTO Clinica_Material (nomeMaterial, qntdMaterial , descricaoMaterial) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nomeMaterial);
            pstmt.setInt(2, qntdMaterial);
            pstmt.setString(3, descricaoMaterial);
            pstmt.executeUpdate();
            System.out.println("Material inserido com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void alterarMaterial (Connection connection, Scanner scanner) {
        System.out.print("ID do Material a alterar: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha
        System.out.print("Novo Nome do Material: ");
        String nome = scanner.nextLine();
        System.out.print("Quantidade: ");
        int qntdMaterial = scanner.nextInt();
        System.out.print("Descrição do material: ");
        scanner.nextLine(); // Consumir a nova linha
        String descricaoMaterial = scanner.nextLine();

        String sql = "UPDATE Clinica_Material SET  nomeMaterial = ?, qntdMaterial = ?, descricaoMaterial = ? WHERE idMaterial = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            pstmt.setInt(2, qntdMaterial);
            pstmt.setString(3, descricaoMaterial);
            pstmt.setInt(4, id);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Material atualizado com sucesso!");
            } else {
                System.out.println("Nenhum material encontrado com o ID fornecido.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void excluirMaterial(Connection connection, Scanner scanner) {
        System.out.print("ID do Material a excluir: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir a nova linha

        String sql = "DELETE FROM Clinica_Material WHERE idMaterial = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Material excluído com sucesso!");
            } else {
                System.out.println("Nenhum material encontrado com o ID fornecido.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void listarMateriais(Connection connection) {
        String sql = "SELECT idMaterial, nomeMaterial, qntdMaterial, descricaoMaterial FROM Clinica_Material";
        try (Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(sql)) {
            while (resultSet.next()) {
                int ID = resultSet.getInt("idMaterial");
                String nome = resultSet.getString("nomeMaterial");
                int quantidade = resultSet.getInt("qntdMaterial");
                String descricao = resultSet.getString("descricaoMaterial");
                System.out.println("ID: " + ID + ", Nome: " + nome + ", Quantidade: " + quantidade + ", Descrição do Material: " + descricao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void procurarMaterial(Connection connection, Scanner scanner) {
        String sql = "SELECT * FROM Clinica_Material WHERE idMaterial = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            System.out.print("ID do Material a procurar: ");
            int idMaterial = scanner.nextInt();
            pstmt.setInt(1, idMaterial);

            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("idMaterial");
                    String nome = resultSet.getString("nomeMaterial");
                    int quantidade = resultSet.getInt("qntdMaterial");
                    String descricao = resultSet.getString("descricaoMaterial");

                    System.out.println("ID: " + id + ", Nome: " + nome + ", Quantidade: " + quantidade + ", Descrição: " + descricao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void procurarMaterialPorNome(Connection connection, Scanner scanner) {
        String sql = "SELECT * FROM Clinica_Material WHERE nomeMaterial = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            System.out.print("Nome do Material a procurar: ");
            String nomeMaterial = scanner.nextLine();
            pstmt.setString(1, nomeMaterial);

            try (ResultSet resultSet = pstmt.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("idMaterial");
                    String nome = resultSet.getString("nomeMaterial");
                    int quantidade = resultSet.getInt("qntdMaterial");
                    String descricao = resultSet.getString("descricaoMaterial");

                    System.out.println("ID: " + id + ", Nome: " + nome + ", Quantidade: " + quantidade + ", Descrição: " + descricao);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void procurarPrecoPorCliente(Connection connection, Scanner scanner) {
        String sql = "SELECT s.nomePaciente, n.preco\n" +
                "FROM Clinica_Servico s, Clinica_Nota_Fiscal n\n" +
                "WHERE s.nomePaciente LIKE ? AND s.idServico = n.Servico_idServico";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            System.out.print("Nome do cliente a procurar: ");
            String nomeCliente = scanner.nextLine();
            pstmt.setString(1, "%" + nomeCliente + "%");

            try (ResultSet resultSet = pstmt.executeQuery()) {
                boolean encontrou = false;
                while (resultSet.next()) {
                    double preco = resultSet.getDouble("preco");
                    String nome = resultSet.getString("nomePaciente");

                    System.out.println("Nome: " + nome + ", Preço: " + preco);
                    encontrou = true;
                }
                if (!encontrou) {
                    System.out.println("Nome do cliente não encontrado.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}