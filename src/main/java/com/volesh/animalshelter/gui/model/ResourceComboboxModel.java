package com.volesh.animalshelter.gui.model;

import com.volesh.animalshelter.entity.Resource;

import javax.swing.*;
import java.util.List;

public class ResourceComboboxModel extends AbstractListModel<Resource> implements ComboBoxModel<Resource> {

    List<Resource> resources;
    Resource selection;


    public ResourceComboboxModel(List<Resource> resources) {
        resources.add(new Resource("Добавить...", 0));
        this.resources = resources;
        this.selection = null;
    }

    @Override
    public int getSize() {
        return resources.size();
    }

    @Override
    public void setSelectedItem(Object anItem) {
        selection = (Resource) anItem;
    }

    @Override
    public Resource getSelectedItem() {
        return selection;
    }

    @Override
    public Resource getElementAt(int index) {
        return resources.get(index);
    }
}

