package com.volesh.animalshelter.dao;

import com.volesh.animalshelter.entity.Animal;
import com.volesh.animalshelter.entity.AnimalStatus;
import com.volesh.animalshelter.entity.Person;
import com.volesh.animalshelter.utils.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import java.util.List;

public class PersonDAO {
    public Long addPerson(Person person) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Long result = (Long) session.save(person);
        session.getTransaction().commit();
        return result;
    }
    public void updatePerson(Person person) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(person);
        session.getTransaction().commit();
    }
    public Person findPersonById(Long id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Person result = session.load(Person.class, id);
        initializeStatusList(result);
        session.getTransaction().commit();
        return  result;
    }
    public List<Person> findPerson() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Person> result = session.createQuery("from Person order by name").list();
        for (Person p : result) {
            initializeStatusList(p);
        }
        session.getTransaction().commit();
        return result;
    }
    public List<Person> findClient() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Person> result = session.createQuery("from Person where role like '%client%' " +
                "or role like '%клиент%'").list();

        for (Person p : result) {
            initializeStatusList(p);
        }
        session.getTransaction().commit();
        return result;
    }
    public List<Person> findClientOfOverexposure() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Person> result = session.createQuery("from Person where role like '%overex%' " +
                "or role like '%передерж%'").list();
        for (Person p : result) {
            initializeStatusList(p);
        }
        session.getTransaction().commit();
        return result;
    }
    private void initializeStatusList(Person p) {
        Hibernate.initialize(p.getStatusList());
        for (AnimalStatus as : p.getStatusList()) {
            Hibernate.initialize(as.getAnimal());
        }
    }

}
