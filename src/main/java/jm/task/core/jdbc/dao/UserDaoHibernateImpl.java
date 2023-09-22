package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private SessionFactory sf = Util.getConnection();
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = sf.openSession()) {
            Transaction tran = session.beginTransaction();
            session.createNativeQuery("create table if not exists user (id BIGINT Primary Key Auto_increment not null, name VARCHAR(64), lastname VARCHAR(64), age TINYINT)").executeUpdate();
            tran.commit();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sf.openSession()) {
            Transaction tran = session.beginTransaction();
            session.createNativeQuery("drop table if exists user").executeUpdate();
            tran.commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sf.openSession()) {
            Transaction tran = session.beginTransaction();
            session.save(new User(name, lastName, age));
            tran.commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sf.openSession()){
            Transaction tran = session.beginTransaction();
            //session.createQuery("delete from User u where u.id = :id").setParameter("id", id).executeUpdate();
            session.remove(session.get(User.class, id));
            tran.commit();
        }

    }

    @Override
    public List<User> getAllUsers() {
        try(Session session = sf.openSession()) {
            return session.createQuery("from User", User.class).list();
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sf.openSession()){
            Transaction tran = session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            tran.commit();
        }
    }
}
