package com.volesh.animalshelter.gui;

import com.volesh.animalshelter.entity.SickAnimal;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class SickModel extends AbstractTableModel{
    private static final String[] headers = {"№", "Название", "Дата начала", "Дата окончания", "Затраты"};

    private List<SickAnimal> sickList;

    public SickModel(List<SickAnimal> sickList) {
        this.sickList = sickList;
    }

    @Override
    public int getRowCount() {
        return sickList.size();
    }

    @Override
    public int getColumnCount() {
        return headers.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SickAnimal sick = sickList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return sick.getName();
            case 2:
                return sick.getBeginningDateString();
            case 3:
                return sick.getEndingDateString();
            default:
                return sick.getTratmentCostsString();
        }
    }

    @Override
    public String getColumnName(int column) {
        return headers[column];
    }
}

