package com.volesh.animalshelter.gui;

import com.volesh.animalshelter.entity.Animal;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ChangeListener {

    JTabbedPane tabbedPane = new JTabbedPane();

    AnimalPanel animal =  new AnimalPanel();
    PersonPanel person = new PersonPanel();
    SponsorPanel sponsor = new SponsorPanel();

    public MainFrame() {

        tabbedPane.addChangeListener(this);
        tabbedPane.add("Животные", animal);
        tabbedPane.add("Клиенты/Передержка", person);
        tabbedPane.add("Спонсоры/Спонсорская помощь", sponsor);
        add(tabbedPane);

        setTitle("Приют для животных");
        setMinimumSize(new Dimension(900, 400));
        setBounds(100, 200, 900, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (tabbedPane.getSelectedIndex() == 1) {
            person.loadModel();
        }
    }
}
