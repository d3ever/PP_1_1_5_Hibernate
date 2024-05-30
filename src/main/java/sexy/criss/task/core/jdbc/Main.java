package sexy.criss.task.core.jdbc;

import sexy.criss.task.core.jdbc.service.UserServiceImpl;

public class Main {

    public static void main(String[] args) {
        UserServiceImpl service = new UserServiceImpl();
        service.createUsersTable();

        service.saveUser("Vadim", "Zlobin", (byte) 21);
        service.saveUser("Nur", "Zaliev", (byte) 27);
        service.saveUser("Denis", "Agaho", (byte) 23);
        service.saveUser("Sergey", "Vakulenko", (byte) 22);

        service.getAllUsers();
        service.cleanUsersTable();
        service.dropUsersTable();
    }

}
