package com.volesh.animalshelter.gui;

import com.volesh.animalshelter.dao.AnimalDAO;
import com.volesh.animalshelter.dao.PersonDAO;
import com.volesh.animalshelter.dao.PhotoDAO;
import com.volesh.animalshelter.dao.StatusDAO;
import com.volesh.animalshelter.entity.Animal;
import com.volesh.animalshelter.entity.AnimalStatus;
import com.volesh.animalshelter.entity.Photo;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class AnimalPanel extends JPanel implements ActionListener {
    private static final String VIEW = "VIEW";
    private static final String ADD = "ADD";
    private static final String DELETE = "DELETE";
    private static final String EDIT = "EDIT";
    private static final String COMPLETE = "COMPLETE";

    private final AnimalDAO animalManager = new AnimalDAO();
    private final StatusDAO statusManager = new StatusDAO();
    private final PhotoDAO photoManager = new PhotoDAO();
    private final PersonDAO personManager = new PersonDAO();
    private final JTable animalTable = new JTable();
    private final GridBagLayout filterGridBag = new GridBagLayout();
    private final GridBagConstraints gbc = new GridBagConstraints();
    private final JPanel filterPanel = new JPanel();
    private final JTextField typeField = new JTextField();
    private final JTextField breedField = new JTextField();
    private final JCheckBox shelterCheck = new JCheckBox("В приюте");
    private final JCheckBox overexCheck = new JCheckBox("На передержке");
    private final JCheckBox homeCheck = new JCheckBox("На руках");

    private java.util.List<Animal> animals;

    public AnimalPanel() {

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        filterPanel.setLayout(filterGridBag);
        filterPanel.setBorder(new TitledBorder(new EtchedBorder(), "Фильтр:"));
        addFilterField(new JLabel("Вид"), typeField, 200, 25);
        addFilterField(new JLabel("Порода"), breedField, 200, 25);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 35, 0, 0);
        shelterCheck.setSelected(true);
        filterGridBag.setConstraints(shelterCheck, gbc);
        filterPanel.add(shelterCheck);
        overexCheck.setSelected(true);
        filterGridBag.setConstraints(overexCheck, gbc);
        filterPanel.add(overexCheck);
        filterGridBag.setConstraints(homeCheck, gbc);
        filterPanel.add(homeCheck);
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        JButton completeButton = createButton("Применить", COMPLETE);
        filterGridBag.setConstraints(completeButton, gbc);
        filterPanel.add(completeButton);

        JPanel leftPanel = new JPanel();
        leftPanel.add(filterPanel);

        animalTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        animalTable.getTableHeader().setReorderingAllowed(false);
        animalTable.setAutoCreateRowSorter(true);
        JScrollPane scrollTable = new JScrollPane(animalTable);
        JPanel centerPanel = new JPanel();
        centerPanel.setBorder(new EmptyBorder(10, 10, 10, 20));
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(scrollTable, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createButton("Просмотр", VIEW));
        buttonPanel.add(createButton("Добавить", ADD));
        buttonPanel.add(createButton("Изменить", EDIT));
        buttonPanel.add(createButton("Удалить", DELETE));
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);

        loadAnimal();

    }

    private void addFilterField(JLabel label, JTextField field, int widthField, int heightField) {
        filterGridBag.setConstraints(label, gbc);
        filterPanel.add(label);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        field.setPreferredSize(new Dimension(widthField, heightField));
        filterGridBag.setConstraints(field, gbc);
        filterPanel.add(field);
        gbc.fill = GridBagConstraints.NONE;
    }

    private void rebuildTableColumnModel() {
        TableColumnModel tcm = animalTable.getColumnModel();
        tcm.getColumn(0).setPreferredWidth(30);
        tcm.getColumn(5).setPreferredWidth(80);
        tcm.getColumn(6).setPreferredWidth(120);

    }

    private JButton createButton(String title, String action) {
        JButton button = new JButton(title);
        button.setIcon(new ImageIcon(action + ".png"));
        button.setActionCommand(action);
        button.addActionListener(this);
        return button;
    }

    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch(action) {
            case COMPLETE:
                loadAnimal();
                break;
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
        Animal animal = getSelectedAnimal();
        if (animal != null) {
            AnimalShowDialog animalShowDialog = new AnimalShowDialog(animal);
            int changedStatus = animalShowDialog.getChangedStatus();
            if (animalShowDialog.getChangedStatus() != 0) {
                String statusStr = "";
                switch(changedStatus) {
                    case 1:
                        statusStr = "Возврат в приют";
                        break;
                    case 2:
                        statusStr = "Передача на передержку";
                        break;
                    case 3:
                        statusStr = "Передача клиенту";
                        break;
                }
                changedStatus = changedStatus < 0 ? -changedStatus : changedStatus;
                animal.setStatus(changedStatus);
                animalManager.updateAnimal(animal);
                statusManager.addStaus(new AnimalStatus(animal, personManager.findPersonById(animalShowDialog.getClientId()),
                        statusStr, new Date()));
                loadAnimal();
            }
        }
    }

    private void addAnimal() {
        AnimalEditDialog animalEditDialog = new AnimalEditDialog(null, "Добавление животного");
        if (animalEditDialog.isSave()) {
            Animal animal = animalEditDialog.getNewAnimal();
            animalManager.addAnimal(animal);
            statusManager.addStaus(new AnimalStatus(animal, personManager.findPersonById(1L),
                    "Поступление в приют", new Date()));
            String imageFilename = animalEditDialog.getImageFile();
            if (imageFilename != null)
                photoManager.addPhoto(new Photo(animal, imageFilename, new Date()));
            loadAnimal();
        }
    }

    private void editAnimal() {
        Animal animal = getSelectedAnimal();
        if (animal != null) {
            AnimalEditDialog animalEditDialog = new AnimalEditDialog(animal, "Редактирование карточки животного");
            if (animalEditDialog.isSave()) {
                Animal updatedAnimal = animalEditDialog.getUpdatedAnimal();
                animalManager.updateAnimal(updatedAnimal);
                statusManager.addStaus(new AnimalStatus(animal, personManager.findPersonById(1L),
                        "Редактирование информации", new Date()));
                String imageFilename = animalEditDialog.getImageFile();
                if (imageFilename != null)
                    photoManager.addPhoto(new Photo(updatedAnimal, imageFilename, new Date()));
                loadAnimal();
            }
        }
    }

    private Animal getSelectedAnimal() {
        int selectedRowIndex = animalTable.getSelectedRow();
        if (selectedRowIndex != -1) {
            return animals.get((int) animalTable.getValueAt(selectedRowIndex, 0) - 1);
        } else {
            JOptionPane.showMessageDialog(this, "Вы должны выделить строку", "Внимание", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    private void deleteAnimal() {
        Animal animal = getSelectedAnimal();
        if (animal != null) {
            int reply = JOptionPane.showConfirmDialog(this, "Вы уверены что хотите удалить запись?", "Удалить?", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                animalManager.deleteAnimal(animal);
                loadAnimal();
            }
        }
    }

    private void loadAnimal() {
        int[] params = {0,0,0,0,0,0};
        if (shelterCheck.isSelected()) {
            params[0] = 1;
            params[1] = -1;
        }
        if (overexCheck.isSelected()) {
            params[2] = 2;
            params[3] = -2;
        }
        if (homeCheck.isSelected()) {
            params[4] = 3;
            params[5] = -3;
        }
        animals = animalManager.findAnimalFilter(typeField.getText().trim(), breedField.getText().trim(),
                params);
        AnimalModel am = new AnimalModel(animals);
        animalTable.setModel(am);
        rebuildTableColumnModel();
    }
}
