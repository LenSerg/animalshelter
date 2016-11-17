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
        addField(new JLabel("Фамилия:"), new JTextField(person.getSurname()), 200, 25);
        addField(new JLabel("Имя:"), new JTextField(person.getName()), 200, 25);
        addField(new JLabel("Отчество:"), new JTextField(person.getPatronymic()), 200, 25);
        addField(new JLabel("Пасспорт:"), new JTextField(person.getPassport()), 200, 25);
        addField(new JLabel("Телефон:"), new JTextField(person.getPhone()), 200, 25);
        addField(new JLabel("Email:"), new JTextField(person.getEmail()), 200, 25);
        addField(new JLabel("Адрес:"), new JTextField(person.getAddress()), 200, 25);
        addField(new JLabel("Дата регистрации:"), new JTextField(person.getRegistrationDateString()), 200, 25);
        addField(new JLabel("Роль:"), new JTextField(person.getRole()), 200, 25);
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


    private void addField(JLabel jLabel, JTextField field, int width, int high) {
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gridBag.setConstraints(jLabel, gbc);
        fieldPanel.add(jLabel);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
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
