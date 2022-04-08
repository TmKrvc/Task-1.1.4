package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            String createTable = "CREATE TABLE IF NOT EXIST users (" +
                    "Id INT PRIMARY KEY AUTO_INCREMENT," +
                    "    name VARCHAR(20)," +
                    "    lastname VARCHAR(20)," +
                    "age TINYINT(100))";
            statement.execute(createTable);
            System.out.println("Таблица создана");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            String dropTable = "DROP TABLE IF EXIST users ";
            statement.execute(dropTable);
            System.out.println("Таблица удалена");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String save = "INSERT INTO users(name,lastname,age) VALUES(?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(save)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Statement statement = connection.createStatement()) {
            String removeId = "DELETE FROM users WHERE id";
            statement.execute(removeId);
            System.out.println("User удален");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        User user = new User();
        try (Statement statement = connection.createStatement();) {
            String getAllUsers = "SELECT id,name,lastname,age FROM users";
            ResultSet resultSet = statement.executeQuery(getAllUsers);
            while (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                allUsers.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allUsers;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            String cleanUser = "TRUNCATE users";
            statement.executeUpdate(cleanUser);
            System.out.println("Таблица очищена");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
