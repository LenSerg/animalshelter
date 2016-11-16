package com.volesh.animalshelter.gui;

import com.volesh.animalshelter.dao.AnimalDAO;
import com.volesh.animalshelter.entity.SickAnimal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SickEditDialog extends JDialog implements ActionListener {
    private static final String SAVE = "SAVE";
    private static final String CANCEL = "CANCEL";
    private boolean save;

    private GridBagLayout gridBag = new GridBagLayout();
    private GridBagConstraints gbc = new GridBagConstraints();
    private SickAnimal sick;

    private JTextField sickNameField;
    private JTextField dateBeginField;
    private JTextField dateEndField;
    private JTextField moneyField;

    public SickEditDialog(String title, SickAnimal sick) {

        this.sick = sick;
        setTitle(title);
        setLayout(gridBag);

        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.EAST;
        sickNameField = new JTextField();
        createField("Наименование болезни*: ", sickNameField);
        dateBeginField = new JTextField();
        createField("Начало болезни*: ", dateBeginField);
        dateEndField = new JTextField();
        createField("Конец болезни: ", dateEndField);
        moneyField = new JTextField();
        createField("Затраты: ", moneyField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createButton("Сохранить", SAVE));
        buttonPanel.add(createButton("Отменить", CANCEL));

        gbc.anchor = GridBagConstraints.CENTER;
        gridBag.setConstraints(buttonPanel, gbc);
        add(buttonPanel);

        if (sick != null) {
            initField();
        }

        setModal(true);
        setBounds(200, 200, 500, 250);
        setResizable(false);
        setVisible(true);

    }

    private void initField() {
        sickNameField.setText(sick.getName());
        dateBeginField.setText(sick.getBeginningDateString());
        dateEndField.setText(sick.getEndingDateString());
        moneyField.setText(sick.getTratmentCostsString());
    }

    private JButton createButton(String name, String action) {
        JButton button = new JButton(name);
        button.addActionListener(this);
        button.setActionCommand(action);
        return button;
    }

    private void createField(String labelName, JTextField field) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel(labelName);
        field.setPreferredSize(new Dimension(250, 25));
        panel.add(label);
        panel.add(field);
        gridBag.setConstraints(panel, gbc);
        add(panel);
    }

    public SickAnimal getNewSick() {
        SickAnimal result = new SickAnimal(null, sickNameField.getText(), "",
                    getValidDate(dateBeginField.getText().trim()),
                    getValidDate(dateEndField.getText().trim()),
                    getValidInt(moneyField.getText()));
        if (dateEndField.getText().trim().isEmpty())
            result.setEndingDate(new Date(0));
        return result;
    }

    public SickAnimal getUpdatedSick() {
        sick.setName(sickNameField.getText());
        sick.setBeginningDate(getValidDate(dateBeginField.getText().trim()));
        sick.setEndingDate(getValidDate(dateEndField.getText().trim()));
        sick.setTratmentCosts(getValidInt(moneyField.getText()));
        if (dateEndField.getText().trim().isEmpty())
            sick.setEndingDate(new Date(0));
        return sick;
    }

    private Date getValidDate(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date result = null;
        try {
            result = dateFormat.parse(dateStr);
        } catch(ParseException ex) {

        }
        return result;
    }

    public boolean isSave() {
        return save;
    }

    private int getValidInt(String intStr) {
        int result = -2;
        intStr = intStr.trim();
        if (intStr.isEmpty())
            result = -1;
        else {
            try {
                result = Integer.parseInt(intStr);
            } catch (NumberFormatException ex) {

            }
        }
        return result;
    }

    private boolean isEmptyFields() {
        return sickNameField.getText().trim().isEmpty() || dateBeginField.getText().trim().isEmpty();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case SAVE:
                if (isEmptyFields()) {
                    JOptionPane.showMessageDialog(this, "Поля, помеченные * обязательны для заполнения",
                            "Внимание", JOptionPane.WARNING_MESSAGE);
                    break;
                }
                if (getValidDate(dateBeginField.getText().trim()) == null ||
                        (!dateEndField.getText().trim().isEmpty() &&
                                getValidDate(dateEndField.getText().trim()) == null)) {
                    JOptionPane.showMessageDialog(this, "Поля дат должны быть заполнены в формате 'дд-мм-гггг'",
                            "Ошибка", JOptionPane.ERROR_MESSAGE);
                    break;
                }
                if (getValidInt(moneyField.getText()) == -2) {
                    JOptionPane.showMessageDialog(this, "Поле 'Затраты' должно быть числом (в рублях)",
                            "Ошибка", JOptionPane.ERROR_MESSAGE);
                    break;
                }
                save = true;
            default:
                setVisible(false);
        }
    }
}
