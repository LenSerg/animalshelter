package com.volesh.animalshelter.gui.model;

import com.volesh.animalshelter.entity.AnimalType;

import javax.swing.*;
import java.util.List;

public class TypeComboboxModel extends AbstractListModel<AnimalType> implements ComboBoxModel<AnimalType>{

    List<AnimalType> types;
    AnimalType selection;


    public TypeComboboxModel(List<AnimalType> types) {
        types.add(new AnimalType("Добавить..."));
        this.types = types;
        this.selection = null;
    }

    @Override
    public int getSize() {
        return types.size();
    }

    @Override
    public void setSelectedItem(Object anItem) {
        selection = (AnimalType) anItem;
    }

    @Override
    public AnimalType getSelectedItem() {
        return selection;
    }

    @Override
    public AnimalType getElementAt(int index) {
        return types.get(index);
    }

}
