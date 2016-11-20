package com.volesh.animalshelter.gui;

import com.volesh.animalshelter.entity.Person;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PersonShowDialog extends JDialog implements ActionListener{
    private static final String CANCEL = "CANCEL";


    Person person;
    private GridBagLayout gridBag = new GridBagLayout();
    private GridBagConstraints gbc = new GridBagConstraints();
    private JPanel fieldPanel = new JPanel();

    public PersonShowDialog(Person person) {
        this.person = person;

        setTitle("Информация о клиенте");

        setLayout(new BorderLayout());

        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5, 5, 5, 5);
        fieldPanel.setLayout(gridBag);
        addField("Фамилия:", person.getSurname(), 200, 25);
        addField("Имя:", person.getName(), 200, 25);
        addField("Отчество:", person.getPatronymic(), 200, 25);
        addField("Пасспорт:", person.getPassport(), 200, 25);
        addField("Телефон:", person.getPhone(), 200, 25);
        addField("Email:", person.getEmail(), 200, 25);
        addField("Адрес:", person.getAddress(), 200, 25);
        addField("Дата регистрации:", person.getRegistrationDateString(), 200, 25);
        addField("Роль:", person.getRole(), 200, 25);
        add(fieldPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createButton("Закрыть", CANCEL));
        buttonPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        add(buttonPanel, BorderLayout.SOUTH);


        setModal(true);
        setResizable(false);
        setBounds(450, 220, 400, 420);
        setVisible(true);


    }


    private JButton createButton(String name, String action) {
        JButton jButton = new JButton(name);
        jButton.setIcon(new ImageIcon(action + ".png"));
        jButton.setActionCommand(action);
        jButton.addActionListener(this);
        return jButton;
    }


    private void addField(String labelText, String fieldText, int width, int high) {
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel label = new JLabel(labelText);
        gridBag.setConstraints(label, gbc);
        fieldPanel.add(label);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField field = new JTextField(fieldText);
        field.setEditable(false);
        field.setBorder(new EtchedBorder());
        field.setPreferredSize(new Dimension(width, high));
        gridBag.setConstraints(field, gbc);
        fieldPanel.add(field);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case CANCEL:
                setVisible(false);
                break;
        }
    }
}
