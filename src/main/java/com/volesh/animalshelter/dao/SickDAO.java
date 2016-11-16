package com.volesh.animalshelter.dao;

import com.volesh.animalshelter.entity.SickAnimal;
import com.volesh.animalshelter.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class SickDAO {
    public Long addSick(SickAnimal sick) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Long result = (Long) session.save(sick);
        session.getTransaction().commit();
        return result;
    }
    public List<SickAnimal> findSickById(Long id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<SickAnimal> result = session.createQuery("from SickAnimal where animal_id=" + id).list();
        session.getTransaction().commit();
        return result;
    }

    public void deleteSick(SickAnimal sick) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.delete(sick);
        session.getTransaction().commit();
    }
    public void updateSick(SickAnimal sick) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(sick);
        session.getTransaction().commit();
    }
}
