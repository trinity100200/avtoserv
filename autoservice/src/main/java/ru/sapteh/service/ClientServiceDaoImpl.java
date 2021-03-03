package ru.sapteh.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sapteh.DAO.DAO;
import ru.sapteh.model.ClientService;

import java.util.List;

public class ClientServiceDaoImpl implements DAO<ClientService, Integer> {
    final SessionFactory factory;

    public ClientServiceDaoImpl(SessionFactory factory) {
        this.factory = factory;
    }

    public void create(ClientService clientService) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.save(clientService);
            session.getTransaction().commit();

        }

    }

    public ClientService read(Integer integer) {
        try(Session session = factory.openSession()){
            ClientService clientService = session.get(ClientService.class, integer);
            return  clientService;
        }
    }

    public List<ClientService> readAll() {
        try(Session session = factory.openSession()){
            Query<ClientService> result =session.createQuery("FROM ClientService");
            return result.list();
        }
    }

    public void update(ClientService clientService) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.update(clientService);
            session.getTransaction().commit();

        }

    }
}
