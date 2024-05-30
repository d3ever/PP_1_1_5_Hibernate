package sexy.criss.task.core.jdbc.service;

import sexy.criss.task.core.jdbc.dao.UserDao;
import sexy.criss.task.core.jdbc.dao.UserDaoHibernateImpl;
import sexy.criss.task.core.jdbc.dao.UserDaoJDBCImpl;
import sexy.criss.task.core.jdbc.model.User;
import sexy.criss.task.core.jdbc.util.Util;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private final UserDao daoJDBC;
    private final Logger logger;

    public UserServiceImpl() {
        daoJDBC = new UserDaoHibernateImpl();
        logger = Logger.getGlobal();
    }

    public void createUsersTable() {
        daoJDBC.createUsersTable();
        logger.info("Таблица была создана");
    }

    public void dropUsersTable() {
        daoJDBC.dropUsersTable();
        logger.info("Таблица была удалена");
    }

    public void saveUser(String name, String lastName, byte age) {
        daoJDBC.saveUser(name, lastName, age);
        logger.info(Util.format("User %s был сохранен", name));
    }

    public void removeUserById(long id) {
        daoJDBC.removeUserById(id);
        logger.info(Util.format("User с номером %d был удален", id));
    }

    public List<User> getAllUsers() {
        return daoJDBC.getAllUsers().stream().filter(user -> {
            System.out.println(user.toString());
            return true;
        }).collect(Collectors.toList());
    }

    public void cleanUsersTable() {
        daoJDBC.cleanUsersTable();
        logger.info("Таблица была очищена");
    }
}