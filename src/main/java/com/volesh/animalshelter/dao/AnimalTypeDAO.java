package com.volesh.animalshelter.dao;

import com.volesh.animalshelter.entity.AnimalType;
import com.volesh.animalshelter.utils.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import java.util.List;

public class AnimalTypeDAO {
    public List<AnimalType> findType() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<AnimalType> result = session.createQuery("from AnimalType order by id").list();
        session.getTransaction().commit();
        return  result;
    }

    public Long addType(AnimalType type) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Long result = (Long) session.save(type);
        session.getTransaction().commit();
        return result;
    }

}
