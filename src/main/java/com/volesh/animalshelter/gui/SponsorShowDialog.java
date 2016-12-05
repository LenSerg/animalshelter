package com.volesh.animalshelter.gui;

import com.volesh.animalshelter.dao.SponsorshipDAO;
import com.volesh.animalshelter.dao.SponsorDAO;
import com.volesh.animalshelter.entity.Sponsor;
import com.volesh.animalshelter.entity.Sponsorship;
import com.volesh.animalshelter.gui.model.SponsorshipModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SponsorShowDialog extends JDialog implements ActionListener {
    private static final String CANCEL = "CANCEL";
    private static final String ADD = "ADD";
    private static final String EDIT = "EDIT";
    private static final String DELETE = "DELETE";

    private JTable sponsorshipTable = new JTable();;
    private SponsorshipDAO sponsorshipManager = new SponsorshipDAO();
    private GridBagLayout gridBag = new GridBagLayout();
    private GridBagConstraints gbc = new GridBagConstraints();
    private JPanel fieldPanel = new JPanel();
    private Sponsor sponsor;

    public SponsorShowDialog(Sponsor sponsor) {
        setTitle("Информация о спонсоре");
        this.sponsor = sponsor;

        setLayout(new BorderLayout());

        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5, 5, 5, 5);
        fieldPanel.setLayout(gridBag);
        if (sponsor.getType() == 1)
            addField("ФИО:", sponsor.getName(), 200, 25);
        else {
            addField("Организация:", sponsor.getName(), 200, 25);
            addField("Контактное лицо:", sponsor.getContactPerson(), 200, 25);
        }
        addField("Адрес:", sponsor.getAddress(), 200, 25);
        addField("Телефон:", sponsor.getPhone(), 200, 25);
        addField("Email:", sponsor.getEmail(), 200, 25);
        addField("Дата регистрации:", sponsor.getRegistrationDateString(), 200, 25);
        fieldPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        add(fieldPanel, BorderLayout.WEST);

        sponsorshipTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sponsorshipTable.getTableHeader().setReorderingAllowed(false);
        sponsorshipTable.setAutoCreateRowSorter(true);

        JLabel tableName = new JLabel("Спонсорская помощь");
        tableName.setBorder(BorderFactory.createEmptyBorder(7, 150, 15, 0));
        tableName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JScrollPane(sponsorshipTable), BorderLayout.CENTER);
        tablePanel.add(tableName, BorderLayout.NORTH);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 17, 15));
        add(tablePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createButton("Добавить помощь", ADD));
        buttonPanel.add(createButton("Редактировать", EDIT));
        buttonPanel.add(createButton("Удалить", DELETE));
        buttonPanel.add(createButton("Закрыть", CANCEL));
        buttonPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        add(buttonPanel, BorderLayout.SOUTH);

        loadSponsorships();

        setModal(true);
        setResizable(false);
        setBounds(350, 220, 850, 400);
        setVisible(true);



    }


    private void rebuildTableColumnModel() {
        TableColumnModel tcm = sponsorshipTable.getColumnModel();
        tcm.getColumn(0).setPreferredWidth(30);
        tcm.getColumn(3).setPreferredWidth(120);

    }


    private void loadSponsorships() {
        sponsor = new SponsorDAO().findSponsorById(sponsor.getId());
        SponsorshipModel sm = new SponsorshipModel(sponsor.getSponsorshipList());
        sponsorshipTable.setModel(sm);
        rebuildTableColumnModel();
    }


    private JButton createButton(String name, String action) {
        JButton jButton = new JButton(name);
        jButton.setIcon(new ImageIcon(action + ".png"));
        jButton.setActionCommand(action);
        jButton.addActionListener(this);
        return jButton;
    }

    private void addField(String labelText, String fieldText, int width, int high) {
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel label = new JLabel(labelText);
        gridBag.setConstraints(label, gbc);
        fieldPanel.add(label);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField field = new JTextField(fieldText);
        field.setEditable(false);
        field.setBorder(new EtchedBorder());
        field.setPreferredSize(new Dimension(width, high));
        gridBag.setConstraints(field, gbc);
        fieldPanel.add(field);
    }

    private Sponsorship getSelectedSponsorship() {
        int selectedRowIndex = sponsorshipTable.getSelectedRow();
        if (selectedRowIndex != -1) {
            return sponsor.getSponsorshipList().get((int) sponsorshipTable.getValueAt(selectedRowIndex, 0) - 1);
        } else {
            JOptionPane.showMessageDialog(this, "Необходимо выделить запись в таблице", "Внимание", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    private void editSponsorship() {
        Sponsorship sponsorship = getSelectedSponsorship();
        if (sponsorship != null) {
            SponsorshipEditDialog sponsorshipEditDialog = new SponsorshipEditDialog(sponsor,
                    sponsorship, "Редактирование информации о спонсорской помощи");
            if (sponsorshipEditDialog.isSave()) {
                Sponsorship updatedSponsorship = sponsorshipEditDialog.getUpdatedSponsorship();
                sponsorshipManager.updateSponsorsgip(updatedSponsorship);
                loadSponsorships();
            }
        }
    }

    private void addSponsorship() {
        SponsorshipEditDialog dialog = new SponsorshipEditDialog(sponsor, null, "Добавление спонсорской помощи");
        if (dialog.isSave()) {
            sponsorshipManager.addSponsorship(dialog.getNewSponsorship());
            loadSponsorships();
        }
    }

    private void deleteSponsorship() {
        Sponsorship sponsorship = getSelectedSponsorship();
        if (sponsorship != null) {
            int reply = JOptionPane.showConfirmDialog(this, "Вы уверены что хотите удалить запись?", "Удалить?", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                sponsorshipManager.deleteSponsorship(sponsorship);
                loadSponsorships();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        SponsorshipEditDialog dialog;
        switch (action) {
            case ADD:
                addSponsorship();
                break;
            case EDIT:
                editSponsorship();
                break;
            case DELETE:
                deleteSponsorship();
                break;
            case CANCEL:
                setVisible(false);
                break;
        }
    }
}
