package ru.sapteh.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sapteh.DAO.DAO;
import ru.sapteh.model.Service;

import java.util.List;

public class ServiceDaoImpl implements DAO<Service, Integer> {
    final SessionFactory factory;

    public ServiceDaoImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void create(Service service) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.save(service);
            session.getTransaction().commit();
        }

    }

    @Override
    public Service read(Integer integer) {
        try(Session session = factory.openSession()){
            Service service = session.get(Service.class, integer);
            return service;
        }
    }

    @Override
    public List<Service> readAll() {
        try(Session session = factory.openSession()){
            Query<Service> result =session.createQuery("FROM Service");
            return result.list();
        }
    }

    @Override
    public void update(Service service) {
        try (Session session = factory.openSession()){
            session.beginTransaction();
            session.update(service);
            session.getTransaction().commit();

        }

    }
}
