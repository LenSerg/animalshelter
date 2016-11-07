package com.volesh.animalshelter;

import com.volesh.animalshelter.dao.AnimalDAO;
import com.volesh.animalshelter.dao.PersonDAO;
import com.volesh.animalshelter.entity.*;
import com.volesh.animalshelter.gui.MainFrame;
import com.volesh.animalshelter.utils.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import java.util.List;

public class App
{
    public static void main( String[] args )
    {
        MainFrame mf = new MainFrame();
        mf.setVisible(true);
    }
}
