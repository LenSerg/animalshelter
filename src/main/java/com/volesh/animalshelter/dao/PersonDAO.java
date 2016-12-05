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
        initializeAnimalList(result);
        session.getTransaction().commit();
        return  result;
    }
    public List<Person> findPerson() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Person> result = session.createQuery("from Person order by name").list();
        for (Person p : result) {
            initializeAnimalList(p);
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
            initializeAnimalList(p);
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
            initializeAnimalList(p);
        }
        session.getTransaction().commit();
        return result;
    }
    public List<Person> findPersonByFilter(String name, String surname, String[] params) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String query = "from Person where name like '%"+name+"%' and surname like '%"+surname+"%'";
        if (!params[0].isEmpty() && !params[1].isEmpty())
            query += " and (role like '%"+params[0]+"%' or role like '%"+params[1]+"%')";
        else if (!params[0].isEmpty())
            query += " and role like '%"+params[0]+"%'";
        else if (!params[1].isEmpty())
            query += " and role like '%"+params[1]+"%'";
        else
            query += "and role like 'error'";
        List<Person> result = session.createQuery(query).list();
        for (Person p : result) {
            initializeAnimalList(p);
        }
        session.getTransaction().commit();
        return result;
    }
    private void initializeAnimalList(Person p) {
        Hibernate.initialize(p.getAnimalList());
        for (Animal animal : p.getAnimalList()) {
            Hibernate.initialize(animal.getType());
        }
    }

    public void deletePerson(Person person) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.delete(person);
        session.getTransaction().commit();
    }

}
