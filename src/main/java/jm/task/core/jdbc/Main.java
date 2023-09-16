package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        UserService serv = new UserServiceImpl();
        serv.createUsersTable();
        String[] names = new String[]{"Sasha", "Sanya", "Aleksandr", "Sancho"};
        String[] lastNames = new String[] {"Sashov", "Sanyov", "Aleksandrov", "Sanchov"};
        int[] ages = new int[] {18, 14, 35, 70};
        for (int i = 0; i < 4; i++) {
            serv.saveUser(names[i], lastNames[i], (byte)ages[i]);
            System.out.printf("User с именем - %s добавлен в базу данных \n", names[i]);
        }
        System.out.println(serv.getAllUsers());
        serv.cleanUsersTable();;
        serv.dropUsersTable();
    }
}
