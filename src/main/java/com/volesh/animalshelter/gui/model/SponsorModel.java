package com.volesh.animalshelter.gui.model;

import com.volesh.animalshelter.entity.Sponsor;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class SponsorModel extends AbstractTableModel {

    String[] columnName = {"№", "ФИО", "Адрес", "Телефон", "Email"};
    List<Sponsor> sponsors;

    public SponsorModel(List<Sponsor> sponsors, int mode) {
        if (mode == 1)
            columnName[1] = "ФИО";
        else
            columnName[1] = "Название организации";
        this.sponsors = sponsors;
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
        return sponsors.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Sponsor sponsor = sponsors.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return sponsor.getName();
            case 2:
                return sponsor.getAddress();
            case 3:
                return sponsor.getPhone();
            default:
                return sponsor.getEmail();
        }
    }
}
