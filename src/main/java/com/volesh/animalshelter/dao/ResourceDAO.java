package com.volesh.animalshelter.dao;

import com.volesh.animalshelter.entity.Resource;
import com.volesh.animalshelter.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class ResourceDAO {
    public Long addResource(Resource resource) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Long result = (Long) session.save(resource);
        session.getTransaction().commit();
        return result;
    }
    public List<Resource> findResource() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Resource> result = session.createQuery("from Resource").list();
        session.getTransaction().commit();
        return result;
    }

}
