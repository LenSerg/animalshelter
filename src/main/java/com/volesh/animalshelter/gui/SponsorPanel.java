package com.volesh.animalshelter.gui;

import com.volesh.animalshelter.dao.*;
import com.volesh.animalshelter.entity.Person;
import com.volesh.animalshelter.entity.Sponsor;
import com.volesh.animalshelter.gui.model.SponsorModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SponsorPanel extends JPanel implements ActionListener {

    private static final String VIEW = "VIEW";
    private static final String ADD = "ADD";
    private static final String DELETE = "DELETE";
    private static final String EDIT = "EDIT";
    private static final String COMPLETE = "COMPLETE";
    private static final String[] typeString = {"Физические лица", "Юридические лица"};

    private final SponsorDAO sponsorManager = new SponsorDAO();
    private List<Sponsor> sponsors;
    private final JTable sponsorTable = new JTable();
    private final GridBagLayout filterGridBag = new GridBagLayout();
    private final GridBagConstraints gbc = new GridBagConstraints();
    private final JPanel filterPanel = new JPanel();
    private final JLabel nameL = new JLabel("ФИО");
    private final JTextField nameTF = new JTextField();
    private final JComboBox typeCB = new JComboBox(typeString);

    public SponsorPanel() {

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        filterPanel.setLayout(filterGridBag);
        filterPanel.setBorder(new TitledBorder(new EtchedBorder(), "Фильтр:"));
        typeCB.addActionListener(this);
        addFilterField(new JLabel("Тип"), typeCB, 200, 25);
        addFilterField(nameL, nameTF, 200, 25);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        JButton completeButton = createButton("Применить", COMPLETE);
        filterGridBag.setConstraints(completeButton, gbc);
        filterPanel.add(completeButton);

        JPanel leftPanel = new JPanel();
        leftPanel.add(filterPanel);

        sponsorTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sponsorTable.getTableHeader().setReorderingAllowed(false);
        sponsorTable.setAutoCreateRowSorter(true);
        JScrollPane scrollTable = new JScrollPane(sponsorTable);
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

        loadSponsor();

    }

    private JButton createButton(String title, String action) {
        JButton button = new JButton(title);
        button.setIcon(new ImageIcon(action + ".png"));
        button.setActionCommand(action);
        button.addActionListener(this);
        return button;
    }



    private void loadSponsor() {
        SponsorModel model;
        if (typeCB.getSelectedIndex() == 0) {
            sponsors = sponsorManager.findIndividual(nameTF.getText());
            model = new SponsorModel(sponsors, 1);
        }
        else {
            sponsors = sponsorManager.findEntity(nameTF.getText());
            model = new SponsorModel(sponsors, 2);
        }
        sponsorTable.setModel(model);
        rebuildColumn();
    }

    private void addFilterField(JLabel label, JComponent field, int widthField, int heightField) {
        filterGridBag.setConstraints(label, gbc);
        filterPanel.add(label);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        field.setPreferredSize(new Dimension(widthField, heightField));
        filterGridBag.setConstraints(field, gbc);
        filterPanel.add(field);
        gbc.fill = GridBagConstraints.NONE;
    }

    private void rebuildColumn() {
        TableColumnModel model = sponsorTable.getColumnModel();
        model.getColumn(0).setPreferredWidth(10);
        model.getColumn(1).setPreferredWidth(200);
    }


    private void addSponsor() {
        SponsorEditDialog sponsorEditDialog = new SponsorEditDialog(null, "Добавление спонсора", typeCB.getSelectedIndex()+1);
        if (sponsorEditDialog.isSave()) {
            Sponsor sponsor = sponsorEditDialog.getNewSponsor();
            sponsorManager.addSponsor(sponsor);
            loadSponsor();
        }
    }

    private void editSponsor() {
        Sponsor sponsor = getSelectedSponsor();
        if (sponsor != null) {
            SponsorEditDialog sponsorEditDialog = new SponsorEditDialog(sponsor,
                    "Редактирование информации о спонсоре", typeCB.getSelectedIndex()+1);
            if (sponsorEditDialog.isSave()) {
                Sponsor updatedSponsor= sponsorEditDialog.getUpdatedSponsor();
                sponsorManager.updateSponsor(updatedSponsor);
                loadSponsor();
            }
        }
    }

    private void deleteSponsor() {
        Sponsor sponsor = getSelectedSponsor();
        if (sponsor != null) {
            int reply = JOptionPane.showConfirmDialog(this, "Вы уверены что хотите удалить запись?", "Удалить?", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                sponsorManager.deleteSponsor(sponsor);
                loadSponsor();
            }
        }
    }

    private void viewSponsor() {
        Sponsor sponsor = getSelectedSponsor();
        if (sponsor != null) {
            new SponsorShowDialog(sponsor);
        }
    }

    private Sponsor getSelectedSponsor() {
        int selectedRowIndex = sponsorTable.getSelectedRow();
        if (selectedRowIndex != -1) {
            return sponsors.get((int) sponsorTable.getValueAt(selectedRowIndex, 0) - 1);
        } else {
            JOptionPane.showMessageDialog(this, "Необходимо выделить запись в таблице", "Внимание", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "comboBoxChanged":
                if (typeCB.getSelectedIndex() == 0)
                    nameL.setText("ФИО");
                else
                    nameL.setText("Название организации");
                loadSponsor();
                break;
            case COMPLETE:
                loadSponsor();
                break;
            case VIEW:
                viewSponsor();
                break;
            case ADD:
                addSponsor();
                break;
            case EDIT:
                editSponsor();
                break;
            case DELETE:
                deleteSponsor();
                break;
        }
    }
}
