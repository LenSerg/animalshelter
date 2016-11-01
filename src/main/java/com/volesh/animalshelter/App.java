package com.volesh.animalshelter;

import com.volesh.animalshelter.entity.Animal;
import com.volesh.animalshelter.utils.HibernateUtil;
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
    }

    public List<Animal> listAnimal() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Animal> result = session.createQuery("from animal order by name").list();
        session.getTransaction().commit();
        return result;
    }
}
