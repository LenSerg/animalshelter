package com.volesh.animalshelter.dao;

import com.volesh.animalshelter.entity.Animal;
import com.volesh.animalshelter.entity.Person;
import com.volesh.animalshelter.entity.Sponsor;
import com.volesh.animalshelter.entity.Sponsorship;
import com.volesh.animalshelter.utils.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import java.util.List;

public class SponsorDAO {
    public Long addSponsor(Sponsor sponsor) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Long result = (Long) session.save(sponsor);
        session.getTransaction().commit();
        return result;
    }
    public Sponsor findSponsorById(Long sponsorId) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Sponsor> list = session.createQuery("from Sponsor where id=" + sponsorId).list();
        Sponsor result = list.get(0);
        initSponsor(result);
        session.getTransaction().commit();
        return result;
    }

    public List<Sponsor> findIndividual(String name) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Sponsor> result = session.createQuery("from Sponsor where type=1 and name like '%"+name+"%'").list();
        for (Sponsor s : result) {
            initSponsor(s);
        }
        session.getTransaction().commit();
        return result;
    }

    public List<Sponsor> findEntity(String name) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List<Sponsor> result = session.createQuery("from Sponsor where type=2 and name like '%"+name+"%'").list();
        for (Sponsor s : result) {
            initSponsor(s);
        }
        session.getTransaction().commit();
        return result;
    }

    private void initSponsor(Sponsor sponsor) {
        Hibernate.initialize(sponsor.getSponsorshipList());
        for (Sponsorship s : sponsor.getSponsorshipList()) {
            Hibernate.initialize(s.getResource());
        }
    }


    public void deleteSponsor(Sponsor sponsor) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.delete(sponsor);
        session.getTransaction().commit();
    }
    public void updateSponsor(Sponsor sponsor) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(sponsor);
        session.getTransaction().commit();
    }
}
