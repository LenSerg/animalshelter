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
    public void deleteAnimal(Animal animal) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.delete(animal);
        session.getTransaction().commit();
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
        initializeAnimalOption(result);
        session.getTransaction().commit();
        return result;
    }
    public List<Animal> findAnimalFilter(String type, String breed, int[] params) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String query = "from Animal where type_id in (select id from AnimalType where " +
                "name like '%"+type+"%') and breed like '%"+breed+"%' and status in (";
        for (int i = 0; i < params.length; i++) {
            query += params[i];
            if (i != params.length - 1)
                query += ", ";
        }
        query += ")";
        List<Animal> result = session.createQuery(query).list();
        for (Animal a : result) {
            initializeAnimalOption(a);
        }
        session.getTransaction().commit();
        return result;
    }
    public void initializeAnimalOption(Animal animal) {
        Hibernate.initialize(animal.getSickList());
        Hibernate.initialize(animal.getPhotoList());
        Hibernate.initialize(animal.getStatusList());
        Hibernate.initialize(animal.getType());
        Hibernate.initialize(animal.getOwner());
        Hibernate.initialize(animal.getOwner().getAnimalList());
        for (AnimalStatus as : animal.getStatusList()) {
            Hibernate.initialize(as.getPerson());
        }
    }


}
