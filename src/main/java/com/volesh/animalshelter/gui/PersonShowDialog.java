package com.volesh.animalshelter.gui;

import com.volesh.animalshelter.dao.AnimalDAO;
import com.volesh.animalshelter.entity.Animal;
import com.volesh.animalshelter.entity.Person;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class PersonShowDialog extends JDialog implements ActionListener{
    private static final String CANCEL = "CANCEL";

    private AnimalDAO animalManager = new AnimalDAO();
    private GridBagLayout gridBag = new GridBagLayout();
    private GridBagConstraints gbc = new GridBagConstraints();
    private JPanel fieldPanel = new JPanel();
    private JTable animalTable;
    private Person person;

    public PersonShowDialog(Person person) {

        setTitle("Информация о клиенте");
        this.person = person;

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
        fieldPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        add(fieldPanel, BorderLayout.WEST);

        animalTable = initTable();
        animalTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        animalTable.getTableHeader().setReorderingAllowed(false);
        animalTable.setAutoCreateRowSorter(true);
        animalTable.getColumnModel().getColumn(0).setPreferredWidth(30);
        animalTable.getColumnModel().getColumn(4).setPreferredWidth(140);

        JLabel tableName = new JLabel("Переданные животные");
        tableName.setBorder(BorderFactory.createEmptyBorder(7, 150, 15, 0));
        tableName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JScrollPane(animalTable), BorderLayout.CENTER);
        tablePanel.add(tableName, BorderLayout.NORTH);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 17, 15));
        add(tablePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createButton("Закрыть", CANCEL));
        buttonPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        add(buttonPanel, BorderLayout.SOUTH);


        setModal(true);
        setResizable(false);
        setBounds(450, 220, 850, 420);
        setVisible(true);


    }

    private JTable initTable() {
        java.util.List<Animal> animals = person.getAnimalList();
        String[] columnNames = {"№", "Кличка", "Пол", "Вид", "Порода", "Возраст"};
        Object[][] tableInit = new Object[animals.size()][columnNames.length];
        for (int i = 0; i < animals.size(); i++) {
            Animal animal = animals.get(i);
            tableInit[i][0] = i + 1;
            tableInit[i][1] = animal.getName();
            tableInit[i][2] = animal.getSexString();
            tableInit[i][3] = animal.getType().getName();
            tableInit[i][4] = animal.getBreed();
            tableInit[i][5] = animal.getAge();
        }
        return new JTable(tableInit, columnNames);
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
