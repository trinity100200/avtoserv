package ru.sapteh;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.DAO.DAO;
import ru.sapteh.model.*;
import ru.sapteh.service.ClientServiceDaoImpl;
import ru.sapteh.service.ClietnDaoImpl;
import ru.sapteh.service.ServiceDaoImpl;
import ru.sapteh.service.UserDaoImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();

        DAO<Client, Integer> clientDAO = new ClietnDaoImpl(factory);
        List<Client> list = new ArrayList<>();
        list.addAll(clientDAO.readAll());
        for (Client client : list){
            System.out.println(client);
        }
//
//
//        DAO<User, Integer> userIntegerDAO = new UserDaoImpl(factory);
//        List<User> list1 = new ArrayList<>();
//        list1.addAll(userIntegerDAO.readAll());
//        for (User user : list1){
//            System.out.println(user);
//        }





        factory.close();
    }
}
