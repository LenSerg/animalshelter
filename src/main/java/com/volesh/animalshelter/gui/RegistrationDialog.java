package com.volesh.animalshelter.gui;

import com.volesh.animalshelter.entity.Person;
import com.volesh.animalshelter.entity.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.security.*;

public class RegistrationDialog extends JDialog implements ActionListener{

    private static final String SAVE = "SAVE";
    private static final String CANCEL = "CANCEL";
    private User user;
    private boolean save = false;

    private final JTextField nameTF = new JTextField();
    private final JTextField surnameTF = new JTextField();
    private final JTextField patronymicTF = new JTextField();
    private final JTextField loginTF  = new JTextField();
    private final JPasswordField passwordTF = new JPasswordField();
    private final JPasswordField confirmpassTF = new JPasswordField();
    private final JTextField phoneTF = new JTextField();
    private final JTextField emailTF = new JTextField();
    private JPanel fieldPanel = new JPanel();

    private GridBagLayout gridBag = new GridBagLayout();
    private GridBagConstraints gbc = new GridBagConstraints();

    public RegistrationDialog(User user, String title) {
        setTitle(title);

        setLayout(new BorderLayout());

        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(5, 5, 5, 5);
        fieldPanel.setLayout(gridBag);
        addField(new JLabel("Фамилия*:"), surnameTF, 200, 25);
        addField(new JLabel("Имя*:"), nameTF, 200, 25);
        addField(new JLabel("Отчество:"), patronymicTF, 200, 25);
        addField(new JLabel("Логин*:"), loginTF, 200, 25);
        addField(new JLabel("Пароль*:"), passwordTF, 200, 25);
        addField(new JLabel("Повторите пароль*:"), confirmpassTF, 200, 25);
        addField(new JLabel("Email:"), emailTF, 200, 25);
        addField(new JLabel("Телефон:"), phoneTF, 200, 25);
        add(fieldPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createButton("Сохранить", SAVE));
        buttonPanel.add(createButton("Отменить", CANCEL));
        buttonPanel.setBorder(new EmptyBorder(0, 0, 20, 0));
        add(buttonPanel, BorderLayout.SOUTH);

        if (user != null) {
            this.user  = user;
            initField();
        }

        setModal(true);
        setResizable(false);
        setBounds(450, 220, 400, 400);
        setVisible(true);
    }

    private boolean stringIsEmpty(String str) {
        return str.trim().equals("");
    }

    private JButton createButton(String title, String action) {
        JButton button = new JButton(title);
        button.setIcon(new ImageIcon(action + ".png"));
        button.setActionCommand(action);
        button.addActionListener(this);
        return button;
    }

    private boolean validatePhone(String str) {
        if (!stringIsEmpty(str)) {
            if (str.length() != 11)
                return false;
            char[] strArray = str.toCharArray();
            if (strArray[0] != '8')
                return false;
            for (int i = 1; i < strArray.length; i++) {
                if (!Character.isDigit(strArray[i]))
                    return false;
            }
        }
        return true;
    }

    private boolean validateMail(String str) {
        if (stringIsEmpty(str))
            return true;
        Matcher matcher = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$").matcher(str);
        return matcher.matches();
    }
    private boolean validateLogin(String str) {
        if (stringIsEmpty(str))
            return true;
        Matcher matcher = Pattern.compile("^[A-Za-z]([.A-Za-z0-9-]{1,18})([A-Za-z0-9])$").matcher(str);
        return matcher.matches();
    }

    private void initField() {
        nameTF.setText(user.getName());
        surnameTF.setText(user.getSurname());
        patronymicTF.setText(user.getPatronymic());
        loginTF.setText(user.getLogin());
        phoneTF.setText(user.getPhone());
        emailTF.setText(user.getEmail());
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

    public boolean isSave() {
        return save;
    }

    private boolean fieldsValidate() {
        if (stringIsEmpty(nameTF.getText()) || stringIsEmpty(surnameTF.getText())
                || stringIsEmpty(loginTF.getText()) || stringIsEmpty(new String(passwordTF.getPassword()))
                || stringIsEmpty(new String(confirmpassTF.getPassword()))) {
            JOptionPane.showMessageDialog(this, "Поля, помеченные * обязательны для заполнения",
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!Arrays.equals(passwordTF.getPassword(), confirmpassTF.getPassword())) {
            JOptionPane.showMessageDialog(this, "Пароли не совпадают!\n",
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!validatePhone(phoneTF.getText())) {
            JOptionPane.showMessageDialog(this, "Некорректный номер телефона!\n" +
                            "Допустимы формат: '89876543210'",
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!validateMail(emailTF.getText())) {
            JOptionPane.showMessageDialog(this, "Некорректный email адрес!\n",
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!validateLogin(loginTF.getText())) {
            JOptionPane.showMessageDialog(this, "Некорректный логин!\n",
                    "Ошибка", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case SAVE:
                if (!fieldsValidate())
                    break;
                save = true;
            default:
                setVisible(false);
                break;
        }
    }
/*
    public Person getUpdatedPerson() {
        person.setName(nameTF.getText());
        person.setSurname(surnameTF.getText());
        person.setPatronymic(patronymicTF.getText());
        person.setPassport(passportTF.getText());
        person.setPhone(phoneTF.getText());
        person.setAddress(addressTF.getText());
        person.setEmail(emailTF.getText());
        switch(roleCB.getSelectedIndex()) {
            case 0:
                person.setRole("Клиент");
                break;
            case 1:
                person.setRole("Передержка");
                break;
        }
        return person;
    }*/

    public String getHash(char[] strMass) {
        String str = new String(strMass);

        MessageDigest md5 ;
        StringBuffer  hexString = new StringBuffer();

        try {
            md5 = MessageDigest.getInstance("md5");
            md5.reset();
            md5.update(str.getBytes());
            byte messageDigest[] = md5.digest();
            for (int i = 0; i < messageDigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }

        }
        catch (NoSuchAlgorithmException e) {
            return e.toString();
        }
        return hexString.toString();
    }


    public User getNewUser() {
        return new User(nameTF.getText(), surnameTF.getText(), patronymicTF.getText(),
                loginTF.getText(), getHash(passwordTF.getPassword()), phoneTF.getText(),
                emailTF.getText(), new Date());
    }

}
