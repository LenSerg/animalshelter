package com.volesh.animalshelter.dao;

import com.volesh.animalshelter.entity.User;
import com.volesh.animalshelter.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class UserDAO {
    public User findUserForAuth(String login, String password) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<User> users = session.createQuery("from User where login='"+login+"' and password='"+password+"'").list();
        session.getTransaction().commit();
        return (users.isEmpty()) ? null : users.get(0);
    }
    public void findUser() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.createQuery("from User").list();
        session.getTransaction().commit();
    }
    public Long addUser(User user) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Long result = (Long) session.save(user);
        session.getTransaction().commit();
        return result;
    }
}
