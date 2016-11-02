package com.volesh.animalshelter;

import com.volesh.animalshelter.entity.Animal;
import com.volesh.animalshelter.entity.Person;
import com.volesh.animalshelter.entity.Sponsor;
import com.volesh.animalshelter.utils.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import java.util.List;

public class App
{
    public static void main( String[] args )
    {
        App app = new App();
        List<Animal> animals = app.listAnimal();
        for (Animal animal : animals) {
            System.out.println(animal.getName());
        }
        List<Person> persons = app.listPerson();
        for (Person person : persons) {
            System.out.println(person.getName());
        }
        List<Sponsor> sponsors = app.listSponsor();
        for (Sponsor sponsor : sponsors) {
            System.out.println(sponsor.getName());
        }
    }

    private List<Animal> listAnimal() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Animal> result = session.createQuery("from Animal order by name").list();
        session.getTransaction().commit();
        return result;
    }
    private List<Person> listPerson() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Person> result = session.createQuery("from Person order by name").list();
        session.getTransaction().commit();
        return result;
    }
    private List<Sponsor> listSponsor() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Sponsor> result = session.createQuery("from Sponsor order by name").list();
        session.getTransaction().commit();
        return result;
    }
}
