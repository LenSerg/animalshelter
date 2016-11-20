package com.volesh.animalshelter.gui;

import com.volesh.animalshelter.dao.PersonDAO;
import com.volesh.animalshelter.entity.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PersonGetDialog extends JDialog implements ActionListener {

    private boolean save;
    private List<Person> persons;
    private final PersonDAO personManager = new PersonDAO();
    private final String COMPLETE = "COMPLETE";
    private final String CANCEL = "CANCEL";

    private JTable personTable;

    public PersonGetDialog(int mode) {

        setLayout(new BorderLayout());

        personTable = initTable(mode);
        personTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        personTable.getTableHeader().setReorderingAllowed(false);
        personTable.setAutoCreateRowSorter(true);
        personTable.getColumnModel().getColumn(0).setPreferredWidth(30);
        personTable.getColumnModel().getColumn(4).setPreferredWidth(140);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JScrollPane(personTable), BorderLayout.CENTER);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 0, 15));
        add(tablePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createButton("Принять", COMPLETE));
        buttonPanel.add(createButton("Оменить", CANCEL));
        add(buttonPanel, BorderLayout.SOUTH);

        setTitle("Выберите клиента");
        setBounds(200, 200, 600, 300);
        setModal(true);
        setResizable(false);
        setVisible(true);
    }

    private JButton createButton(String title, String action) {
        JButton button = new JButton(title);
        button.setIcon(new ImageIcon(action + ".png"));
        button.setActionCommand(action);
        button.addActionListener(this);
        return button;
    }

    private JTable initTable(int mode) {
        if (mode == 2)
            persons = personManager.findClientOfOverexposure();
        else if (mode == 3)
            persons = personManager.findClient();
        else
            persons = personManager.findPerson();
        String[] columnNames = {"№", "Имя", "Фамилия", "Отчество", "Адрес", "Паспорт"};
        Object[][] tableInit = new Object[persons.size()][columnNames.length];
        for (int i = 0; i < persons.size(); i++) {
            Person person = persons.get(i);
            tableInit[i][0] = i + 1;
            tableInit[i][1] = person.getName();
            tableInit[i][2] = person.getSurname();
            tableInit[i][3] = person.getPatronymic();
            tableInit[i][4] = person.getAddress();
            tableInit[i][5] = person.getPassport();
        }
        return new JTable(tableInit, columnNames);
    }

    private boolean complete() {
        if (personTable.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Необходимо выбрать клиента", "Внимание", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        int reply = JOptionPane.showConfirmDialog(this, "Вы уверены что хотите оформить\nпередачу животного данному клиенту?",
                "Уверены?", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.NO_OPTION) {
            return  false;
        }
        return true;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case COMPLETE:
                if (complete())
                    save = true;
            default:
                setVisible(false);
        }
    }

    public Long getSelectedPersonId() {
        int seletedRowIndex = personTable.getSelectedRow();
        return persons.get(((int) personTable.getValueAt(seletedRowIndex, 0)) - 1).getId();
    }

    public boolean isSave() {
        return save;
    }
}
