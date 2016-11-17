package com.volesh.animalshelter.gui.model;

import com.volesh.animalshelter.entity.Person;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PersonModel extends AbstractTableModel {

    private static final String[] columnName = {"№", "Фамилия", "Имя",  "Отчество", "№ пасспорта", "Дата регистрации", "Роль"};
    private List<Person> persons;

    public PersonModel(List<Person> persons) {
        this.persons = persons;
    }

    @Override
    public String getColumnName(int column) {
        return columnName[column];
    }

    @Override
    public int getColumnCount() {
        return columnName.length;
    }

    @Override
    public int getRowCount() {
        return persons.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Person person = persons.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return person.getSurname();
            case 2:
                return person.getName();
            case 3:
                return person.getPatronymic();
            case 4:
                return person.getPassport();
            case 5:
                return person.getRegistrationDateString();
            default:
                return person.getRole();
        }
    }
}
