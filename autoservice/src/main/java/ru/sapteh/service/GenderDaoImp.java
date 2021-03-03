package ru.sapteh.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sapteh.DAO.DAO;
import ru.sapteh.model.Gender;

import java.util.List;

public class GenderDaoImp implements DAO<Gender, Integer> {
    final SessionFactory factory;

    public GenderDaoImp(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public void create(Gender gender) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.save(gender);
            session.getTransaction().commit();
        }

    }

    @Override
    public Gender read(Integer integer) {
        try(Session session = factory.openSession()){
            Gender gender = session.get(Gender.class, integer);
            return gender;
        }
    }

    @Override
    public List<Gender> readAll() {
        try(Session session = factory.openSession()){
            Query<Gender> result = session.createQuery("FROM Gender");
            return result.list();
        }
    }

    @Override
    public void update(Gender gender) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.update(gender);
            session.getTransaction().commit();

        }

    }
}
