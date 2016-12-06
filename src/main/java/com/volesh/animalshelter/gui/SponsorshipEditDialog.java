package com.volesh.animalshelter.gui;

import com.volesh.animalshelter.dao.ResourceDAO;
import com.volesh.animalshelter.entity.Resource;
import com.volesh.animalshelter.entity.Sponsor;
import com.volesh.animalshelter.entity.Sponsorship;
import com.volesh.animalshelter.gui.model.ResourceComboboxModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class SponsorshipEditDialog extends JDialog implements ActionListener {

    private static final String SAVE = "SAVE";
    private static final String CANCEL = "CANCEL";
    private final ResourceDAO resourceManager = new ResourceDAO();
    private boolean save = false;
    private Sponsorship sponsorship;
    private Sponsor sponsor;


    private final JTextField sponsorTF = new JTextField();
    private final JComboBox resourceCB = new JComboBox();
    private final JTextField amountTF = new JTextField();
    private JPanel fieldPanel = new JPanel();

    private GridBagLayout gridBag = new GridBagLayout();
    private GridBagConstraints gbc = new GridBagConstraints();

    public SponsorshipEditDialog(Sponsor sponsor, Sponsorship sponsorship, String title) {
        setTitle(title);
        this.sponsorship = sponsorship;
        this.sponsor = sponsor;

        setLayout(new BorderLayout());

        resourceCB.setModel(new ResourceComboboxModel(resourceManager.findResource()));
        resourceCB.addActionListener(this);

        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5, 5, 5, 5);
        fieldPanel.setLayout(gridBag);
        addField(new JLabel("Спонсор:"), sponsorTF, 300, 25);
        sponsorTF.setText(sponsor.getName());
        sponsorTF.setEditable(false);
        addField(new JLabel("Ресурс:"), resourceCB, 200, 25);
        addField(new JLabel("Количество*:"), amountTF, 200, 25);
        add(fieldPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createButton("Сохранить", SAVE));
        buttonPanel.add(createButton("Отменить", CANCEL));
        buttonPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        add(buttonPanel, BorderLayout.SOUTH);

        if (sponsorship != null) {
            this.sponsorship = sponsorship;
            initField();
        }

        setModal(true);
        setResizable(false);
        setBounds(450, 220, 500, 200);
        setVisible(true);
    }

    private boolean stringIsEmpty(String str) {
        return str.trim().equals("");
    }

    private boolean stringIsInteger(String str) {
        boolean result = false;
        if (!stringIsEmpty(str)) {
            try {
                Integer.parseInt(str);
                result = true;
            } catch (NumberFormatException ex) {
            }
        }
        return result;
    }

    private void initField() {
        resourceCB.getModel().setSelectedItem(sponsorship.getResource());
        amountTF.setText(sponsorship.getAmount());
    }

    private void addField(JLabel jLabel, JComponent component, int width, int high) {
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gridBag.setConstraints(jLabel, gbc);
        fieldPanel.add(jLabel);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        component.setPreferredSize(new Dimension(width, high));
        gridBag.setConstraints(component, gbc);
        fieldPanel.add(component);
    }

    private JButton createButton(String name, String action) {
        JButton jButton = new JButton(name);
        jButton.setIcon(new ImageIcon(action + ".png"));
        jButton.setActionCommand(action);
        jButton.addActionListener(this);
        return jButton;
    }

    public boolean isSave() {
        return save;
    }

    private void addType() {
        if (((Resource) resourceCB.getSelectedItem()).getId() == null) {
            String typeStr = JOptionPane.showInputDialog(
                    this,
                    "Введите строку для добавления:",
                    "Добавление ресурса", JOptionPane.PLAIN_MESSAGE);
            if (typeStr == null) {
                resourceCB.setSelectedIndex(-1);
            } else if (typeStr.length() > 0) {
                Resource resource = new Resource(typeStr, 0);
                resourceManager.addResource(resource);
                resourceCB.setModel(new ResourceComboboxModel(resourceManager.findResource()));
                resourceCB.getModel().setSelectedItem(resource);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "comboBoxChanged":
                addType();
                break;
            case SAVE:
                if (!fieldsValidation())
                    break;
                save = true;
            default:
                setVisible(false);
                break;
        }
    }


    private boolean fieldsValidation() {
        if (stringIsEmpty(amountTF.getText())) {
            JOptionPane.showMessageDialog(this, "Поля, помеченные * обязательны для заполнения",
                    "Внимание", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (!stringIsInteger(amountTF.getText())) {
            JOptionPane.showMessageDialog(this, "Поле 'Количество' должно быть числом",
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }


    public Sponsorship getUpdatedSponsorship() {
        sponsorship.setResource((Resource) resourceCB.getSelectedItem());
        sponsorship.setAmount(amountTF.getText());
        return sponsorship;
    }

    public Sponsorship getNewSponsorship() {
        Sponsorship result = new Sponsorship((Resource) resourceCB.getSelectedItem(), sponsor,
                amountTF.getText(), new Date());
        return result;
    }
}
