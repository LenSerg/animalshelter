package com.volesh.animalshelter.dao;

import com.volesh.animalshelter.entity.AnimalStatus;
import com.volesh.animalshelter.utils.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;

public class StatusDAO {
    public Long addStaus(AnimalStatus status) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Long result = (Long) session.save(status);
        session.getTransaction().commit();
        return result;
    }
}
