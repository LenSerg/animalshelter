package com.volesh.animalshelter;

import com.volesh.animalshelter.dao.AnimalDAO;
import com.volesh.animalshelter.entity.*;
import com.volesh.animalshelter.utils.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import java.util.List;

public class App
{
    public static void main( String[] args )
    {
        AnimalDAO aDao = new AnimalDAO();
        Animal barsik = aDao.findAnimalById(1L );
        System.out.println("Name: " + barsik.getName());
        System.out.println("Photos:");
        for (Photo p : barsik.getPhotoList()) {
            System.out.println("   " + p.getUrl());
        }
        System.out.println("Sickness:");
        for (SickAnimal sa : barsik.getSickList()) {
            System.out.println("   " + sa.getName());
        }
        System.out.println("History:");
        for (AnimalStatus as : barsik.getStatusList()) {
            System.out.println("   " + as.getStatusString() + "("
                    + as.getPerson().getName() + ")");
        }
        System.out.println();
    }
}
