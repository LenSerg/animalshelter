package com.volesh.animalshelter.gui;

import com.volesh.animalshelter.entity.Animal;
import com.volesh.animalshelter.entity.Photo;
import net.coobird.thumbnailator.Thumbnails;

import javax.swing.*;
import javax.swing.border.Border;
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
    private Animal animal;
    private String photoPathname = "";
    private boolean save = false;

    private final JTextField nameTF = new JTextField();
    private final JTextField cageNumberTF = new JTextField();
    private final JTextField typeTF = new JTextField();
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

        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5, 5, 5, 5);
        fieldPanel.setLayout(gridBag);
        addField(new JLabel("Фото:"), choosePanel, 320, 25);
        addField(new JLabel("Кличка:"), nameTF, 200, 25);
        addField(new JLabel("№ вольера*:"), cageNumberTF, 200, 25);
        addField(new JLabel("Вид*:"), typeTF, 200, 25);
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

    private boolean checkField(JTextField field) {
        return !field.getText().trim().equals("");
    }

    private boolean isChecked() {
        return checkField(typeTF) && checkField(cageNumberTF) && checkField(colorTF);
    }

    private void initField() {
        if (animal.getPhotoList().size() > 0) {
            java.util.List<Photo> photos = animal.getPhotoList();
            photoPathname =  photos.get(photos.size()-1).getUrl();
            chooseTF.setText(photoPathname);
        }
        nameTF.setText(animal.getName());
        typeTF.setText(animal.getType());
        cageNumberTF.setText(animal.getCageNumber() + "");
        sexCB.setSelectedIndex(animal.getSex());
        breedTF.setText(animal.getBreed());
        colorTF.setText(animal.getColor());
        ageTF.setText(animal.getAge() + "");
        specSignsTA.setText(animal.getSpecialSigns());
    }

    public String getImageFile() {
        String filePath = null;
        if (checkField(chooseTF) && !chooseTF.getText().equals(photoPathname)) {
            filePath = "src/main/resources/img/" + UUID.randomUUID().toString().substring(24) + ".jpg";
            try {
                Thumbnails.of(chooseTF.getText()).size(400, 300).toFile(filePath);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return filePath;
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

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case CHOOSE:
                JFileChooser chooseDialog = new JFileChooser();
                chooseDialog.setApproveButtonText("Select");
                chooseDialog.setDialogTitle("Выберите фото для загрузки");
                chooseDialog.showOpenDialog(this);
                File file = chooseDialog.getSelectedFile();
                if (file != null)
                    chooseTF.setText(file.getAbsolutePath());
                break;
            case SAVE:
                if (!isChecked()) {
                    JOptionPane.showMessageDialog(this, "Поля, помеченные * обязательны для заполнения",
                            "Внимание", JOptionPane.WARNING_MESSAGE);
                    break;
                }
                if (checkField(chooseTF) && !(new File(chooseTF.getText().trim()).exists())) {
                    JOptionPane.showMessageDialog(this, "Такого файла не существует!",
                            "Ошибка", JOptionPane.ERROR_MESSAGE);
                    break;
                }
                try {
                    Integer.parseInt(cageNumberTF.getText());
                } catch(NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Поле \"№ вольера\" должно быть числом",
                            "Ошибка", JOptionPane.ERROR_MESSAGE);
                    break;
                }
                save = true;
            default:
                setVisible(false);
                break;
        }
    }

    public Animal getUpdatedAnimal() {
        animal.setName(nameTF.getText());
        animal.setType(typeTF.getText());
        animal.setSex(sexCB.getSelectedIndex());
        animal.setCageNumber(Integer.parseInt(cageNumberTF.getText()));
        animal.setBreed(breedTF.getText());
        animal.setAge(ageTF.getText());
        animal.setColor(colorTF.getText());
        animal.setSpecialSigns(specSignsTA.getText());
        return animal;
    }

    public Animal getNewAnimal() {
        return new Animal(nameTF.getText(), typeTF.getText(), breedTF.getText(),
                ageTF.getText(), colorTF.getText(), Integer.parseInt(cageNumberTF.getText()), sexCB.getSelectedIndex(),
                specSignsTA.getText(), new Date(), 1);
    }

}
