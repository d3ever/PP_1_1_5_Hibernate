package sexy.criss.task.core.jdbc.service;

import sexy.criss.task.core.jdbc.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    void createUsersTable();
    void dropUsersTable();
    void saveUser(String name, String lastName, byte age);
    void removeUserById(long id);
    void cleanUsersTable();
}