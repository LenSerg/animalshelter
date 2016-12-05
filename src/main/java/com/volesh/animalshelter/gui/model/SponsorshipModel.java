package com.volesh.animalshelter.gui.model;


import com.volesh.animalshelter.entity.Sponsorship;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class SponsorshipModel extends AbstractTableModel {

    private static String[] headers = {"№", "Ресурс", "Количество", "Дата спонсирования"};

    private List<Sponsorship> sponsorships;

    public SponsorshipModel(List<Sponsorship> sponsorships) {
        this.sponsorships = sponsorships;
    }

    @Override
    public int getRowCount() {
        return sponsorships.size();
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
        Sponsorship sponsorship = sponsorships.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return sponsorship.getResource().getName();
            case 2:
                return sponsorship.getAmount();
            default:
                return sponsorship.getAddingDateString();
        }
    }
}