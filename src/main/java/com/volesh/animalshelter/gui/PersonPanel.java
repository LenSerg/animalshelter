package com.volesh.animalshelter.gui;

import com.volesh.animalshelter.dao.PersonDAO;
import com.volesh.animalshelter.entity.Person;
import com.volesh.animalshelter.gui.model.PersonModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PersonPanel extends JPanel implements ActionListener {
    private static final String VIEW = "VIEW";
    private static final String ADD = "ADD";
    private static final String EDIT = "EDIT";
    private static final String DELETE = "DELETE";
    private static final String COMPLETE = "COMPLETE";

    private PersonDAO personManager;
    private List<Person> persons;

    private JTable personTable = new JTable();;
    private JTextField nameField = new JTextField();
    private JTextField surnameField = new JTextField();
    private JCheckBox clientCheck = new JCheckBox("Клиенты");
    private JCheckBox overexCheck = new JCheckBox("Передержка");
    private final GridBagLayout filterGridBag = new GridBagLayout();
    private final GridBagConstraints gbc = new GridBagConstraints();
    private final JPanel filterPanel = new JPanel();

    public PersonPanel() {
        personManager = new PersonDAO();

        setLayout(new BorderLayout());

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        filterPanel.setLayout(filterGridBag);
        filterPanel.setBorder(new TitledBorder(new EtchedBorder(), "Фильтр:"));
        addFilterField(new JLabel("Имя"), nameField, 200, 25);
        addFilterField(new JLabel("Фамилия"), surnameField, 200, 25);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 35, 0, 0);
        clientCheck.setSelected(true);
        filterGridBag.setConstraints(clientCheck, gbc);
        filterPanel.add(clientCheck);
        overexCheck.setSelected(true);
        filterGridBag.setConstraints(overexCheck, gbc);
        filterPanel.add(overexCheck);
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        JButton completeButton = createButton("Применить", COMPLETE);
        filterGridBag.setConstraints(completeButton, gbc);
        filterPanel.add(completeButton);

        JPanel leftPanel = new JPanel();
        leftPanel.add(filterPanel);

        personTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        personTable.getTableHeader().setReorderingAllowed(false);
        personTable.setAutoCreateRowSorter(true);
        JScrollPane scrollTable = new JScrollPane(personTable);
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

        loadModel();
    }

    private void rebuildTableColumnModel() {
        TableColumnModel tcm = personTable.getColumnModel();
        tcm.getColumn(0).setPreferredWidth(15);
        tcm.getColumn(1).setPreferredWidth(100);
    }


    private void loadModel() {
        String[] params = {"", ""};
        if (clientCheck.isSelected())
            params[0] = "клиент";
        if (overexCheck.isSelected()) {
            params[1] = "передерж";
        }
        persons = personManager.findPersonByFilter(nameField.getText(), surnameField.getText(), params);
        PersonModel model = new PersonModel(persons);
        personTable.setModel(model);
        rebuildTableColumnModel();
    }

    private Person getSelectedPerson() {
        int selectedRowIndex = personTable.getSelectedRow();
        if (selectedRowIndex != -1) {
            return persons.get((int) personTable.getValueAt(selectedRowIndex, 0) - 1);
        } else {
            JOptionPane.showMessageDialog(this, "Необходимо выделить запись в таблице", "Внимание", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    private JButton createButton(String title, String action) {
        JButton button = new JButton(title);
        button.setIcon(new ImageIcon(action + ".png"));
        button.setActionCommand(action);
        button.addActionListener(this);
        return button;
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


    private void addPerson() {
        PersonEditDialog personEditDialog = new PersonEditDialog(null, "Добавление клиента");
        if (personEditDialog.isSave()) {
            Person person = personEditDialog.getNewPerson();
            personManager.addPerson(person);
            loadModel();
        }
    }

    private void editPerson() {
        Person person = getSelectedPerson();
        if (person != null) {
            PersonEditDialog personEditDialog = new PersonEditDialog(person, "Редактирование информации о клиенте");
            if (personEditDialog.isSave()) {
                Person updatedPerson= personEditDialog.getUpdatedPerson();
                personManager.updatePerson(updatedPerson);
                loadModel();
            }
        }
    }

    private void deletePerson() {
        Person person= getSelectedPerson();
        if (person != null) {
            int reply = JOptionPane.showConfirmDialog(this, "Вы уверены что хотите удалить запись?", "Удалить?", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                personManager.deletePerson(person);
                loadModel();
            }
        }
    }

    private void viewPerson() {
        Person person = getSelectedPerson();
        if (person != null) {
            new PersonShowDialog(person);
        }
    }

    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch(action) {
            case COMPLETE:
                loadModel();
                break;
            case VIEW:
                viewPerson();
                break;
            case ADD:
                addPerson();
                break;
            case EDIT:
                editPerson();
                break;
            case DELETE:
                deletePerson();
                break;
        }
    }
}
