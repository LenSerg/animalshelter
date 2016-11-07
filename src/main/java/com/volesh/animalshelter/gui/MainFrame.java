package com.volesh.animalshelter.gui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        panel1.add(new AnimalPanel(), BorderLayout.CENTER);
        tabbedPane.add("Животные", panel1);
        tabbedPane.add("Передержка", new AnimalPanel());
        add(tabbedPane);


        setTitle("Приют для животных");
        setMinimumSize(new Dimension(900, 400));
        setBounds(100, 200, 900, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}