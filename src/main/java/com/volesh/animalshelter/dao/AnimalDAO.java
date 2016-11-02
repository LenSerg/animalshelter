package com.volesh.animalshelter.dao;

import com.volesh.animalshelter.entity.Animal;
import com.volesh.animalshelter.entity.AnimalStatus;
import com.volesh.animalshelter.utils.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import java.util.List;

public class AnimalDAO {
    public Long addAnimal(Animal animal) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Long result = (Long) session.save(animal);
        session.getTransaction().commit();
        return result;
    }
    public void updateAnimal(Animal animal) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(animal);
        session.getTransaction().commit();
    }
    public Animal findAnimalById(Long animalId) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Animal result = session.load(Animal.class, animalId);
        Hibernate.initialize(result.getSickList());
        Hibernate.initialize(result.getPhotoList());
        Hibernate.initialize(result.getStatusList());
        for (AnimalStatus as : result.getStatusList()) {
            Hibernate.initialize(as.getPerson());
        }
        session.getTransaction().commit();
        return result;
    }
    public List<Animal> findAnimal() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Animal> result = session.createQuery("from Animal order by name").list();
        for (Animal a : result) {
            Hibernate.initialize(a.getSickList());
            Hibernate.initialize(a.getPhotoList());
            Hibernate.initialize(a.getStatusList());
            for (AnimalStatus as : a.getStatusList()) {
                Hibernate.initialize(as.getPerson());
            }
        }
        session.getTransaction().commit();
        return result;
    }

}
