package com.volesh.animalshelter.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Животные", new AnimalPanel());
        tabbedPane.add("Клиенты/Передержка", new PersonPanel());
        tabbedPane.add("Спонсоры/Спонсорская помощь", new SponsorPanel());
        add(tabbedPane);


        setTitle("Приют для животных");
        setMinimumSize(new Dimension(900, 400));
        setBounds(100, 200, 900, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
