package com.volesh.animalshelter.gui;

import com.volesh.animalshelter.entity.Animal;

import javax.swing.table.AbstractTableModel;
import java.text.SimpleDateFormat;
import java.util.List;

public class AnimalModel extends AbstractTableModel {

    private static String[] headers = {"№", "Кличка", "Вид", "Пол", "Порода", "Дата регистрации",
            "Статус"};

    private List<Animal> animals;

    public AnimalModel(List<Animal> animals) {
        this.animals = animals;
    }

    public int getRowCount() {
        return animals.size();
    }

    @Override
    public String getColumnName(int column) {
        return headers[column];
    }

    public int getColumnCount() {
        return headers.length;
    }

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
                switch (animal.getSex()) {
                    case 1:
                        return "Муж";
                    case 2:
                        return "Жен";
                    default:
                        return "Неопределен";
                }
            case 4:
                return animal.getBreed();
            case 5:
                return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(animal.getRegistrationDate());
            default:
                return animal.getStatus() > 0 ? "Здоров" : "Болен";
        }
    }
}
