package com.volesh.animalshelter.dao;

import com.volesh.animalshelter.entity.Animal;
import com.volesh.animalshelter.entity.Sponsorship;
import com.volesh.animalshelter.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class SponsorshipDAO {
    public Long addSponsorship(Sponsorship sponsorship) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Long result = (Long) session.save(sponsorship);
        session.getTransaction().commit();
        return result;
    }
    public void updateSponsorsgip(Sponsorship sponsorship) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(sponsorship);
        session.getTransaction().commit();
    }
    public void deleteSponsorship(Sponsorship sponsorship) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.delete(sponsorship);
        session.getTransaction().commit();
    }
}
