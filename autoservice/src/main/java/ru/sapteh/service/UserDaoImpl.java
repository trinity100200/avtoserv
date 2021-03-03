package ru.sapteh.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sapteh.DAO.DAO;
import ru.sapteh.model.User;

import java.util.List;

public class UserDaoImpl implements DAO<User, Integer> {
    final SessionFactory factory;

    public UserDaoImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void create(User user) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();

        }
    }

    @Override
    public User read(Integer integer) {
        try(Session session = factory.openSession()){
            User user = session.get(User.class, integer);
            return  user;
        }
    }

    @Override
    public List<User> readAll() {
        try(Session session = factory.openSession()){
            Query<User> result = session.createQuery("FROM User");
            return result.list();

        }
    }

    @Override
    public void update(User user) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        }

    }
}
