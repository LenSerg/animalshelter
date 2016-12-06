package com.volesh.animalshelter;

import com.volesh.animalshelter.dao.AnimalDAO;
import com.volesh.animalshelter.dao.PersonDAO;
import com.volesh.animalshelter.entity.*;
import com.volesh.animalshelter.gui.AuthDialog;
import com.volesh.animalshelter.gui.MainFrame;
import com.volesh.animalshelter.utils.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import javax.swing.*;
import java.util.List;

public class App
{
    public static void main( String[] args )
    {
        AuthDialog dialog = new AuthDialog();
        if (dialog.isAuthorization()) {
            MainFrame mf = new MainFrame();
            mf.setVisible(true);
        }
    }
}
