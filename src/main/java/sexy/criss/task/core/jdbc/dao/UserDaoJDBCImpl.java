package sexy.criss.task.core.jdbc.dao;

import sexy.criss.task.core.jdbc.model.User;
import sexy.criss.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        Util.execute("CREATE TABLE IF NOT EXISTS Users (%s, %s, %s, %s)",
                "id INT AUTO_INCREMENT PRIMARY KEY",
                "name VARCHAR(100)",
                "lastName VARCHAR(100)",
                "age TINYINT");
    }

    public void dropUsersTable() {
        Util.execute("DROP TABLE IF EXISTS Users");
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement ps = Util.prepared("INSERT INTO Users (name, lastName, age) VALUES (?, ?, ?)")) {
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setByte(3, age);
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        Util.execute("DELETE FROM Users WHERE ID=%d", id);
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (ResultSet rs = Util.prepared("SELECT * FROM Users").executeQuery()) {
            while (rs.next()) {
                list.add(User.builder().id(rs.getLong("id")).name(rs.getString("name")).lastName(rs.getString("lastName")).age(rs.getByte("age")).build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void cleanUsersTable() {
        Util.execute("TRUNCATE TABLE Users");
    }
}