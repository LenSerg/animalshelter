package com.volesh.animalshelter.gui;

import com.volesh.animalshelter.dao.AnimalTypeDAO;
import com.volesh.animalshelter.entity.Animal;
import com.volesh.animalshelter.entity.AnimalType;
import com.volesh.animalshelter.entity.Photo;
import com.volesh.animalshelter.gui.model.TypeComboboxModel;
import net.coobird.thumbnailator.Thumbnails;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class AnimalEditDialog extends JDialog implements ActionListener {
    private static final String SAVE = "SAVE";
    private static final String CANCEL = "CANCEL";
    private static final String CHOOSE = "CHOOSE";
    private static final String[] sex = {"неопред", "муж", "жен"};
    private final AnimalTypeDAO typeManager = new AnimalTypeDAO();
    private boolean save = false;
    private String photoPathname = "";
    private Animal animal;

    private final JTextField nameTF = new JTextField();
    private final JTextField cageNumberTF = new JTextField();
    private final JComboBox typeCB = new JComboBox();
    private final JComboBox sexCB = new JComboBox<>(sex);
    private final JTextField breedTF = new JTextField();
    private final JTextField colorTF = new JTextField();
    private final JTextField chooseTF = new JTextField();
    private final JTextArea specSignsTA = new JTextArea();
    private final JTextField ageTF = new JTextField();
    private JPanel fieldPanel = new JPanel();

    private GridBagLayout gridBag = new GridBagLayout();
    private GridBagConstraints gbc = new GridBagConstraints();

    public AnimalEditDialog(Animal animal, String title) {
        setTitle(title);

        setLayout(new BorderLayout());

        JPanel choosePanel = new JPanel(new BorderLayout());
        chooseTF.setPreferredSize(new Dimension(200, 25));
        JButton chooseButton = createButton("Обзор...", CHOOSE);
        choosePanel.add(chooseTF, BorderLayout.WEST);
        choosePanel.add(chooseButton, BorderLayout.EAST);

        typeCB.setModel(new TypeComboboxModel(typeManager.findType()));
        typeCB.addActionListener(this);
        typeCB.setSelectedIndex(0);

        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5, 5, 5, 5);
        fieldPanel.setLayout(gridBag);
        addField(new JLabel("Фото:"), choosePanel, 320, 25);
        addField(new JLabel("Кличка:"), nameTF, 200, 25);
        addField(new JLabel("№ вольера*:"), cageNumberTF, 200, 25);
        addField(new JLabel("Вид*:"), typeCB, 100, 25);
        addField(new JLabel("Пол:"), sexCB, 100, 25);
        addField(new JLabel("Порода:"), breedTF, 200, 25);
        addField(new JLabel("Цвет*:"), colorTF, 200, 25);
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
        setBounds(450, 220, 500, 480);
        setVisible(true);
    }

    private boolean stringIsEmpty(String str) {
        return str.trim().equals("");
    }

    public String getPathname() {
        String pathname = chooseTF.getText();
        if (stringIsEmpty(pathname)) {
            photoPathname = "";
        } else if (pathname.equals(photoPathname)) {
            photoPathname = null;
        }
        else {
            photoPathname = "src/main/resources/img/" + UUID.randomUUID().toString().substring(24) + ".jpg";
            try {
                Thumbnails.of(pathname).size(400, 300).toFile(photoPathname);
            } catch (IOException ex) {
            }
        }
        return photoPathname;
    }

    private boolean stringIsInteger(String str) {
        boolean result = false;
        if (!stringIsEmpty(str)) {
            try {
                Integer.parseInt(str);
                result = true;
            } catch(NumberFormatException ex) {
            }
        }
        return result;
    }

    private void initField() {
        if (animal.getPhotoList().size() > 0) {
            java.util.List<Photo> photos = animal.getPhotoList();
            photoPathname =  photos.get(photos.size()-1).getUrl();
            chooseTF.setText(photoPathname);
        }
        nameTF.setText(animal.getName());
        typeCB.getModel().setSelectedItem(animal.getType());
        cageNumberTF.setText(animal.getCageNumber() + "");
        sexCB.setSelectedIndex(animal.getSex());
        breedTF.setText(animal.getBreed());
        colorTF.setText(animal.getColor());
        ageTF.setText(animal.getAgeString());
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
        jButton.setIcon(new ImageIcon(action + ".png"));
        jButton.setActionCommand(action);
        jButton.addActionListener(this);
        return jButton;
    }

    public boolean isSave() {
        return save;
    }

    private void addType() {
        if (((AnimalType) typeCB.getSelectedItem()).getId() == null) {
            String typeStr = JOptionPane.showInputDialog(
                    this,
                    "Введите строку для добавления:",
                    "Добавление типа", JOptionPane.PLAIN_MESSAGE);
            if (typeStr == null)  {
                typeCB.setSelectedIndex(0);
            }
            else if (typeStr.length() > 0) {
                AnimalType type = new AnimalType(typeStr);
                typeManager.addType(type);
                typeCB.setModel(new TypeComboboxModel(typeManager.findType()));
                typeCB.getModel().setSelectedItem(type);
            }
        }
    }

    private void selectFile() {
        JFileChooser chooseDialog = new JFileChooser();
        chooseDialog.setApproveButtonText("Select");
        chooseDialog.setDialogTitle("Выберите фото для загрузки");
        chooseDialog.showOpenDialog(this);
        File file = chooseDialog.getSelectedFile();
        if (file != null)
            chooseTF.setText(file.getAbsolutePath());
    }

    private boolean fieldsValidation() {
        if (!stringIsEmpty(chooseTF.getText()) && !(new File(chooseTF.getText())).exists()) {
            JOptionPane.showMessageDialog(this, "Некорректное имя файла!",
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (stringIsEmpty(cageNumberTF.getText()) || stringIsEmpty(colorTF.getText())) {
            JOptionPane.showMessageDialog(this, "Поля, помеченные * обязательны для заполнения",
                    "Внимание", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (!stringIsInteger(cageNumberTF.getText())) {
            JOptionPane.showMessageDialog(this, "Поле '№ вольера' должно быть числом",
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!stringIsEmpty(ageTF.getText()) && !stringIsInteger(ageTF.getText())) {
            JOptionPane.showMessageDialog(this, "Поле 'Возраст' должно быть числом (в месяцах)",
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "comboBoxChanged":
                addType();
                break;
            case CHOOSE:
                selectFile();
                break;
            case SAVE:
                if (fieldsValidation())
                    save = true;
                else
                    break;
            default:
                setVisible(false);
                break;
        }
    }

    public Animal getUpdatedAnimal() {
        animal.setName(nameTF.getText());
        animal.setType((AnimalType) typeCB.getSelectedItem());
        animal.setSex(sexCB.getSelectedIndex());
        animal.setCageNumber(Integer.parseInt(cageNumberTF.getText()));
        animal.setBreed(breedTF.getText());
        try {
            animal.setAge(Integer.parseInt(ageTF.getText()));
        } catch (NumberFormatException ex) {
            animal.setAge(0);
        }
        animal.setColor(colorTF.getText());
        animal.setSpecialSigns(specSignsTA.getText());
        return animal;
    }

    public Animal getNewAnimal() {
        Animal result = new Animal(nameTF.getText(), null, (AnimalType) typeCB.getSelectedItem(), breedTF.getText(),
                0, colorTF.getText(), Integer.parseInt(cageNumberTF.getText()), sexCB.getSelectedIndex(),
                specSignsTA.getText(), new Date(), 1);
        if (!stringIsEmpty(ageTF.getText()))
            result.setAge(Integer.parseInt(ageTF.getText()));
        return result;
    }

}
