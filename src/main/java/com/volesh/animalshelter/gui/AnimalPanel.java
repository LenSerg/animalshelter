package com.volesh.animalshelter.gui;

import com.volesh.animalshelter.dao.AnimalDAO;
import com.volesh.animalshelter.entity.Animal;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimalPanel extends JPanel implements ActionListener {
    private static final String VIEW = "VIEW";
    private static final String ADD = "ADD";
    private static final String DELETE = "DELETE";
    private static final String EDIT = "EDIT";

    private final AnimalDAO animalManager = new AnimalDAO();
    private final JTable animalTable = new JTable();

    private java.util.List<Animal> animals;

    public AnimalPanel() {

        GridBagLayout filterGridBag = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        JPanel filterPanel = new JPanel();
        JLabel typeLabel = new JLabel("Тип:");
        JTextField typeField = new JTextField();
        JLabel breedLabel = new JLabel("Порода:");
        JTextField breedField = new JTextField();
        JButton comleteButton = new JButton("Применить");
        JPanel leftPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel buttonPanel = new JPanel();

        filterPanel.setLayout(filterGridBag);
        filterPanel.setBorder(new TitledBorder(new EtchedBorder(), "Фильтр:"));

        filterGridBag.setConstraints(typeLabel, gbc);
        filterPanel.add(typeLabel);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        typeField.setPreferredSize(new Dimension(200, 25));
        filterGridBag.setConstraints(typeField, gbc);
        filterPanel.add(typeField);
        gbc.fill = GridBagConstraints.NONE;

        filterGridBag.setConstraints(breedLabel, gbc);
        filterPanel.add(breedLabel);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        breedField.setPreferredSize(new Dimension(200, 25));
        filterGridBag.setConstraints(breedField, gbc);
        filterPanel.add(breedField);
        gbc.fill = GridBagConstraints.NONE;

        filterGridBag.setConstraints(comleteButton, gbc);
        filterPanel.add(comleteButton);

        leftPanel.add(filterPanel);

        setLayout(new BorderLayout());
        add(leftPanel, BorderLayout.WEST);


        animalTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        animalTable.getTableHeader().setReorderingAllowed(false);
        animalTable.setAutoCreateRowSorter(true);
        JScrollPane scrollTable = new JScrollPane(animalTable);
        centerPanel.setBorder(new EmptyBorder(10, 10, 10, 20));
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(scrollTable, BorderLayout.CENTER);


        buttonPanel.add(createButton("Просмотр", VIEW));
        buttonPanel.add(createButton("Добавить", ADD));
        buttonPanel.add(createButton("Изменить", EDIT));
        buttonPanel.add(createButton("Удалить", DELETE));
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);

        loadAnimal();


    }

    private void rebuildTableColumnModel() {
        TableColumnModel tcm = animalTable.getColumnModel();
        tcm.getColumn(0).setPreferredWidth(30);
        tcm.getColumn(3).setPreferredWidth(100);
        tcm.getColumn(4).setPreferredWidth(100);
        tcm.getColumn(5).setPreferredWidth(150);

    }

    private JButton createButton(String title, String action) {
        JButton button = new JButton(title);
        button.setActionCommand(action);
        button.addActionListener(this);
        return button;
    }

    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch(action) {
            case VIEW:
                viewAnimal();
                break;
            case ADD:
                addAnimal();
                break;
            case EDIT:
                editAnimal();
                break;
            case DELETE:
                deleteAnimal();
                break;
        }
    }

    private void viewAnimal() {
        System.out.println(animalTable.getModel().getValueAt(animalTable.getSelectedRow(), 0).toString());

    }

    private void addAnimal() {
        AnimalDialog animalDialog = new AnimalDialog(null, "Добавление животного");
        if (animalDialog.isSave()) {
            animalManager.addAnimal(animalDialog.getNewAnimal());
            loadAnimal();
        }
    }

    private void editAnimal() {
        int selectedRowIndex = animalTable.getSelectedRow();
        if (selectedRowIndex != -1) {
            Animal animal = animals.get((int) animalTable.getValueAt(selectedRowIndex, 0) - 1);
            AnimalDialog animalDialog = new AnimalDialog(animal, "Редактирование карточки животного");
            if (animalDialog.isSave()) {
                animalManager.updateAnimal(animalDialog.getUpdatedAnimal());
                loadAnimal();
            }
        }
        else {
            JOptionPane.showMessageDialog(this, "Вы должны выделить строку для редактирования", "Внимание", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteAnimal() {
        int selectedRowIndex = animalTable.getSelectedRow();
        if (selectedRowIndex != -1) {
            Animal animal = animals.get((int) animalTable.getValueAt(selectedRowIndex, 0) - 1);
            animalManager.deleteAnimal(animal);
            loadAnimal();
        }
        else {
            JOptionPane.showMessageDialog(this, "Вы должны выделить строку для удаления", "Внимание", JOptionPane.WARNING_MESSAGE);
        }
    }


    private void loadAnimal() {
        animals = animalManager.findAnimal();
        AnimalModel am = new AnimalModel(animals);
        animalTable.setModel(am);
        rebuildTableColumnModel();
    }
}
