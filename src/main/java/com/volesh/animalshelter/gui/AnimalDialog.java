package com.volesh.animalshelter.gui;

import com.volesh.animalshelter.entity.Animal;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class AnimalDialog extends JDialog implements ActionListener {
    private static final String SAVE = "SAVE";
    private static final String CANCEL = "CANCEL";
    private static final String[] sex = {"неопред", "муж", "жен"};
    private Animal animal;
    private boolean save = false;

    private final JTextField nameTF = new JTextField();
    private final JTextField typeTF = new JTextField();
    private final JComboBox sexCB = new JComboBox<>(sex);
    private final JTextField breedTF = new JTextField();
    private final JTextField colorTF = new JTextField();
    private final JTextArea specSignsTA = new JTextArea();
    private final JTextField ageTF = new JTextField();
    private JPanel fieldPanel = new JPanel();


    private GridBagLayout gridBag = new GridBagLayout();
    private GridBagConstraints gbc = new GridBagConstraints();

    public AnimalDialog(Animal animal, String title) {
        setTitle(title);

        setLayout(new BorderLayout());

        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5, 5, 5, 5);

        fieldPanel.setLayout(gridBag);
        addField(new JLabel("Кличка:"), nameTF, 200, 25);
        addField(new JLabel("Вид:"), typeTF, 200, 25);
        addField(new JLabel("Пол:"), sexCB, 100, 25);
        addField(new JLabel("Порода:"), breedTF, 200, 25);
        addField(new JLabel("Цвет:"), colorTF, 200, 25);
        addField(new JLabel("Возраст:"), ageTF, 200, 25);
        addField(new JLabel("Особые приметы:"), new JScrollPane(specSignsTA), 200, 100);
        add(fieldPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createButton("Сохранить", SAVE));
        buttonPanel.add(createButton("Отменить", CANCEL));
        buttonPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        add(buttonPanel, BorderLayout.SOUTH);

        if (animal != null) {
            this.animal = animal;
            initField();
        }

        setModal(true);
        setResizable(false);
        setBounds(450, 220, 400, 420);
        setVisible(true);
    }

    private void initField() {
        nameTF.setText(animal.getName());
        typeTF.setText(animal.getType());
        sexCB.setSelectedIndex(animal.getSex());
        breedTF.setText(animal.getBreed());
        colorTF.setText(animal.getColor());
        ageTF.setText(animal.getAge() + "");
        specSignsTA.setText(animal.getSpecialSigns());
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
        jButton.setActionCommand(action);
        jButton.addActionListener(this);
        return jButton;
    }

    public boolean isSave() {
        return save;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        save = e.getActionCommand().equals(SAVE);
        setVisible(false);
    }

    public Animal getUpdatedAnimal() {
        animal.setName(nameTF.getText());
        animal.setType(typeTF.getText());
        animal.setSex(sexCB.getSelectedIndex());
        animal.setBreed(breedTF.getText());
        animal.setAge(Integer.parseInt(ageTF.getText()));
        animal.setColor(colorTF.getText());
        animal.setSpecialSigns(specSignsTA.getText());
        return animal;
    }

    public Animal getNewAnimal() {
        return new Animal(nameTF.getText(), typeTF.getText(), breedTF.getText(),
                Integer.parseInt(ageTF.getText()), colorTF.getText(), sexCB.getSelectedIndex(),
                specSignsTA.getText(), new Date(), 1);
    }

}
