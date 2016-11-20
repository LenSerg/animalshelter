package com.volesh.animalshelter.gui;

import com.volesh.animalshelter.entity.Animal;
import com.volesh.animalshelter.entity.AnimalStatus;
import com.volesh.animalshelter.entity.Person;
import com.volesh.animalshelter.entity.Photo;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class AnimalShowDialog extends JDialog implements ActionListener {

    private final String CANCEL = "CANCEL";
    private final String TOOVEREX = "TOOVEREX";
    private final String TOHOME = "TOHOME";
    private final String RETURN = "RETURN";
    private final String SICKLIST = "SICKLIST";
    private final String ABOUT = "ABOUT";

    private int changedStatus = 0;
    private Long clientId = 0L;
    private Animal animal;
    private Person person;
    private GridBagLayout gridBag = new GridBagLayout();
    private GridBagConstraints gbc = new GridBagConstraints();

    public AnimalShowDialog(Animal animal) {

        this.animal = animal;

        setTitle("Карточка животного");
        setLayout(gridBag);

        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5, 0, 0, 3);
        addFieldPanel("Кличка: ", animal.getName());
        gbc.insets = new Insets(0, 0, 0, 3);
        addFieldPanel("Тип: ", animal.getType().toString());
        addFieldPanel("Порода: ", animal.getBreed());
        addFieldPanel("Пол: ", animal.getSexString());
        addFieldPanel("Возраст: ", animal.getAgeString());
        addFieldPanel("Цвет: ", animal.getColor());
        addFieldPanel("Дата регистрации: ", animal.getDateString());
        addFieldPanel("Статус: ", animal.getStatusString());
        if (Math.abs(animal.getStatus()) == 1)
            addFieldPanel("№ вольера: ", animal.getCageNumber()+"");
        else {
            java.util.List<AnimalStatus> history = animal.getStatusList();
            person = history.get(history.size() - 1).getPerson();
            addFieldPanel("Владелец: ", person.getSurname()+" "+person.getName());
        }
        JPanel specSignsPanel = new JPanel();
        specSignsPanel.add(new JLabel("Особые приметы: "));
        JTextArea specSignsarea = new JTextArea(animal.getSpecialSigns());
        specSignsarea.setEditable(false);
        specSignsarea.setBackground(specSignsPanel.getBackground());
        JScrollPane scrollSigns = new JScrollPane(specSignsarea);
        scrollSigns.setPreferredSize(new Dimension(280, 100));
        scrollSigns.setBorder(new EtchedBorder());
        specSignsPanel.add(scrollSigns);
        gbc.gridx = 0;
        gbc.gridy = 11;
        gridBag.setConstraints(specSignsPanel, gbc);
        add(specSignsPanel);

        JLabel imageLabel = new JLabel(getImage());
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
        JPanel imagePanel = new JPanel();
        imagePanel.add(imageLabel);

        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 8, 0, 0);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 10;
        gridBag.setConstraints(imagePanel, gbc);
        add(imagePanel);

        gbc.gridx = 1;
        gbc.gridy = 11;
        gbc.gridheight = 1;
        JTextArea statusArea = new JTextArea();
        statusArea.setEditable(false);
        statusArea.setBackground(specSignsPanel.getBackground());
        for (AnimalStatus status : animal.getStatusList()) {
            String actualStatusString = "("+status.getStatusDateString()+") "+status.getStatusString()+"\n";
            statusArea.setText(statusArea.getText() + actualStatusString);
        }
        JScrollPane scrollStatus = new JScrollPane(statusArea);
        scrollStatus.setBorder(BorderFactory.createTitledBorder(new EtchedBorder(), " История: "));
        scrollStatus.setPreferredSize(new Dimension(420, 105));
        gridBag.setConstraints(scrollStatus, gbc);
        add(scrollStatus);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createButton("История болезни", SICKLIST));

        int absStatus = Math.abs(animal.getStatus());
        if (absStatus == 1)
            buttonPanel.add(createButton("На передержку", TOOVEREX));
        if (absStatus > 0 && absStatus < 3)
            buttonPanel.add(createButton("Передача клиенту", TOHOME));
        if (absStatus > 1) {
            buttonPanel.add(createButton("Возврат", RETURN));
            buttonPanel.add(createButton("Владелец", ABOUT));
        }
        buttonPanel.add(createButton("Закрыть", CANCEL));

        gbc.gridx = 0;
        gbc.gridy = 12;
        gbc.gridwidth = 2;
        gridBag.setConstraints(buttonPanel, gbc);
        add(buttonPanel);

        setModal(true);
        setResizable(false);
        setBounds(300, 100, 930, 520);
        setVisible(true);
    }

    private JButton createButton(String title, String action) {
        JButton button = new JButton(title);
        button.setIcon(new ImageIcon(action + ".png"));
        button.setActionCommand(action);
        button.addActionListener(this);
        return button;
    }

    private void addFieldPanel(String labelName, String fieldName) {
        JPanel panel = new JPanel();
        panel.add(new JLabel(labelName));
        JTextField field = new JTextField(fieldName);
        field.setPreferredSize(new Dimension(280, 25));
        field.setBorder(new EtchedBorder());
        field.setEditable(false);
        panel.add(field);
        gridBag.setConstraints(panel, gbc);
        add(panel);
    }

    private ImageIcon getImage() {
        java.util.List<Photo> photos = animal.getPhotoList();
        String filename;
        if (photos.size() > 0) {
            filename = photos.get(photos.size()-1).getUrl();
        } else {
            filename = "src/main/resources/img/anonim.jpg";
        }
        ImageIcon result = null;
        try {
            result = new ImageIcon(ImageIO.read(new File(filename)));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        PersonGetDialog p;
        switch (action) {
            case RETURN:
                int reply = JOptionPane.showConfirmDialog(this, "Вы уверены что хотите оформить возврат данного животного?", "Уверены?", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    changedStatus = 1;
                    clientId = 1L;
                    setVisible(false);
                }
                break;
            case TOOVEREX:
                p = new PersonGetDialog(2);
                if (p.isSave()) {
                    changedStatus = 2;
                    clientId = p.getSelectedPersonId();
                    setVisible(false);
                }
                break;
            case TOHOME:
                p = new PersonGetDialog(3);
                if (p.isSave()) {
                    changedStatus = 3;
                    clientId = p.getSelectedPersonId();
                    setVisible(false);
                }
                break;
            case SICKLIST:
                new SickListDialog(animal.getId());
                break;
            case ABOUT:
                new PersonShowDialog(person);
                break;
            case CANCEL:
                setVisible(false);
                break;
        }
    }

    public Long getClientId() {
        return clientId;
    }

    public int getChangedStatus() {
        return changedStatus;
    }
}
