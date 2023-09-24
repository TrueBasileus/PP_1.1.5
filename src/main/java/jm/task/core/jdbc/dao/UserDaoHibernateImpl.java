package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sf.openSession()) {
            Transaction tran = session.beginTransaction();
            session.createNativeQuery("drop table if exists user").executeUpdate();
            tran.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = sf.openSession();
        Transaction tran = session.beginTransaction();
        try {
            session.save(new User(name, lastName, age));
            tran.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tran.rollback();
        }
        session.close();

    }

    @Override
    public void removeUserById(long id) {

        Session session = sf.openSession();
        Transaction tran = session.beginTransaction();
        try {
            session.remove(session.get(User.class, id));
            tran.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tran.rollback();
        }
        session.close();

    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sf.openSession()) {
            return session.createQuery("from User", User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void cleanUsersTable() {
        Session session = sf.openSession();
        Transaction tran = session.beginTransaction();
        try {
            session.createQuery("delete from User").executeUpdate();
            tran.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tran.rollback();
        }
        session.close();
    }
}
