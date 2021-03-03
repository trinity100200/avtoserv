package ru.sapteh.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sapteh.DAO.DAO;
import ru.sapteh.model.Client;

import java.util.List;

public class ClietnDaoImpl implements DAO<Client, Integer> {
    final SessionFactory factory;

    public ClietnDaoImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void create(Client client) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.save(client);
            session.getTransaction().commit();
        }
    }

    @Override
    public Client read(Integer integer) {
        try(Session session = factory.openSession()){
            Client client = session.get(Client.class, integer);
            return client;
        }
    }

    @Override
    public List<Client> readAll() {
        try(Session session = factory.openSession()){
            Query<Client> result = session.createQuery("FROM Client");
            return result.list();
        }
    }

    @Override
    public void update(Client client) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.update(client);
            session.getTransaction().commit();

        }

    }
}
