package com.volesh.animalshelter.gui;

import com.volesh.animalshelter.entity.Animal;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class AnimalModel extends AbstractTableModel {

    private static String[] headers = {"№", "Кличка", "Вид", "Пол", "Порода", "№ вольера", "Дата регистрации"};

    private List<Animal> animals;

    public AnimalModel(List<Animal> animals) {
        this.animals = animals;
    }

    @Override
    public int getRowCount() {
        return animals.size();
    }

    @Override
    public String getColumnName(int column) {
        return headers[column];
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Animal animal = animals.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return animal.getName();
            case 2:
                return animal.getType();
            case 3:
                return animal.getSexString();
            case 4:
                return animal.getBreed();
            case 5:
                return animal.getCageNumber();
            default:
                return animal.getDateString();
        }
    }
}
