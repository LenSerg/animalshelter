package com.volesh.animalshelter.dao;

import com.volesh.animalshelter.entity.Photo;
import com.volesh.animalshelter.utils.HibernateUtil;
import org.hibernate.Session;

public class PhotoDAO {
    public Long addPhoto(Photo photo) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Long result = (Long) session.save(photo);
        session.getTransaction().commit();
        return result;
    }
}
