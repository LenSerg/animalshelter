package com.volesh.animalshelter.gui;

import com.volesh.animalshelter.dao.AnimalDAO;
import com.volesh.animalshelter.dao.SickDAO;
import com.volesh.animalshelter.entity.SickAnimal;
import com.volesh.animalshelter.gui.model.SickModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SickListDialog extends JDialog implements ActionListener {

    private final SickDAO sickManager = new SickDAO();
    private final String ADD = "ADD";
    private final String EDIT = "EDIT";
    private final String DELETE = "DELETE";
    private final String CANCEL = "CANCEL";
    private List<SickAnimal> sicks;

    private JTable sickTable;
    private Long animalId;

    public SickListDialog(Long animalId) {

        setTitle("История болезни");
        setLayout(new BorderLayout());
        this.animalId = animalId;

        sickTable = new JTable();
        sickTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sickTable.getTableHeader().setReorderingAllowed(false);
        sickTable.setAutoCreateRowSorter(true);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JScrollPane(sickTable), BorderLayout.CENTER);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 0, 15));
        add(tablePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createButton("Добавить", ADD));
        buttonPanel.add(createButton("Изменить", EDIT));
        buttonPanel.add(createButton("Удалить", DELETE));
        buttonPanel.add(createButton("Закрыть", CANCEL));
        add(buttonPanel, BorderLayout.SOUTH);

        loadModel();
        setBounds(200, 200, 600, 300);
        setModal(true);
        setResizable(false);
        setVisible(true);
    }


    private JButton createButton(String name, String action) {
        JButton button = new JButton(name);
        button.setIcon(new ImageIcon(action + ".png"));
        button.addActionListener(this);
        button.setActionCommand(action);
        return button;
    }

    private void loadModel() {
        sicks = sickManager.findSickById(animalId);
        SickModel model = new SickModel(sicks);
        sickTable.setModel(model);
    }

    private SickAnimal getSelectedSick() {
        int selectedRow = sickTable.getSelectedRow();
        if (selectedRow != -1) {
            return sicks.get((int) sickTable.getValueAt(selectedRow, 0) - 1);
        }
        else {
            JOptionPane.showMessageDialog(this, "Необходимо выделить строку", "Внимание", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case ADD:
                SickEditDialog dialog = new SickEditDialog("Добавление болезни", null);
                if (dialog.isSave()) {
                    SickAnimal sick = dialog.getNewSick();
                    sick.setAnimal(new AnimalDAO().findAnimalById(animalId));
                    sickManager.addSick(sick);
                    loadModel();
                }
                break;
            case EDIT:
                SickAnimal selectedSick = getSelectedSick();
                if (selectedSick != null) {
                    dialog = new SickEditDialog("Изменение болезни", selectedSick);
                    if (dialog.isSave()) {
                        sickManager.updateSick(dialog.getUpdatedSick());
                        loadModel();
                    }
                }
                break;
            case DELETE:
                selectedSick = getSelectedSick();
                if (selectedSick != null) {
                    int reply = JOptionPane.showConfirmDialog(this, "Вы уверены, что хотите удалить эту запись?",
                            "Удалить?", JOptionPane.YES_NO_OPTION);
                    if (reply == JOptionPane.YES_OPTION) {
                        sickManager.deleteSick(selectedSick);
                        loadModel();
                    }
                }
                break;
            case CANCEL:
                setVisible(false);
        }
    }
}
