package hu.alkfejl.hermanNote.view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import hu.alkfejl.hermanNote.model.bean.Student;
import hu.alkfejl.hermanNote.view.HermanNoteGUI;
import hu.alkfejl.hermanNote.view.Labels;

/**
 * Az osztály az új ügyfél felvételénél megjelenõ dialógus.
 */
public class StudentEditPanel extends JPanel implements ActionListener {


    private static final long serialVersionUID = 3382073646421158018L;

	private HermanNoteGUI gui;

    // A dialógus azon vezérlõit melyekre szükség lesz az eseménykezelés során
    // osztályváltozóként definiáljuk
    private JTextField ehaTextfield = new JTextField(10);
    private JTextField nameTextfield = new JTextField(10);
    private JSpinner pointSpinner = new JSpinner();
    private JCheckBox kbCheck = new JCheckBox();
    private JCheckBox adminCheck = new JCheckBox();
    private JCheckBox userCheck = new JCheckBox();
    private JButton saveButton = new JButton(Labels.save);
    private JButton printButton = new JButton(Labels.print);
    private JButton refreshButton = new JButton(Labels.cancel);
    
    Color settingsColor = new Color(224, 224, 224);

    public StudentEditPanel(HermanNoteGUI gui) {
    	this.gui=gui;
    	
    	 // A beállításokat tartalmazó panel gyártása
        JPanel settingPanel = createSettingPanel();

        // A gombokat tartalmazó panel gyártása
        JPanel buttonPanel = createButtonPanel();

        // Az elõzõ két panelt egy panelre rakjuk
        JPanel MainPanel = createPanel(settingPanel, buttonPanel); 
        
        add(MainPanel);
        
        // Dialogus megjelenítése
        setVisible(true);
    }

    private JPanel createSettingPanel() {
        JPanel settingPanel = new JPanel();

        // A panel elrendezése mátrix, 5 sor és 2 oszlop, a cellák egyforma méretûek
        settingPanel.setLayout(new GridLayout(6,2));

        // Az 1. sorban egy kor címke és egy spinner lesz
        settingPanel.add(new JLabel(Labels.student_eha));
        settingPanel.add(this.ehaTextfield);
        
        // A 2. sorban egy név címke és egy szövegmezõ lesz
        settingPanel.add(new JLabel(Labels.student_name));
        settingPanel.add(this.nameTextfield);

        // A 3. sorban egy kor címke és egy spinner lesz
        settingPanel.add(new JLabel(Labels.student_point));
        settingPanel.add(this.pointSpinner);

        // A 4. sorban lesz a kedvezményezettség
        settingPanel.add(new JLabel(Labels.student_kb));
        settingPanel.add(kbCheck);

        // A 5. sorban lesz a végzettség
        settingPanel.add(new JLabel(Labels.student_admin));
        settingPanel.add(adminCheck);
        
     	// A 6. sorban lesz a végzettség
        settingPanel.add(new JLabel(Labels.student_user));
        settingPanel.add(userCheck);

        return settingPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        
        // A panel elrendezése folytonos, középre igazítva
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Hozzáadjuk az save gombot, és figyelünk rá
        ImageIcon saveIcon = new ImageIcon("src/img/save.png");
        saveButton = new JButton();
        saveButton.setBackground(settingsColor);
        saveButton.setIcon(saveIcon);
        saveButton.addActionListener(this);
        buttonPanel.add(saveButton);

        // Hozzáadjuk a refresh gombot, és figyelünk rá
        ImageIcon printIcon = new ImageIcon("src/img/print.png");
        printButton = new JButton();
        printButton.setBackground(settingsColor);
        printButton.setIcon(printIcon);
        printButton.addActionListener(this);
        buttonPanel.add(printButton);
        
        // Hozzáadjuk a refresh gombot, és figyelünk rá
        ImageIcon refreshIcon = new ImageIcon("src/img/refresh.png");
        refreshButton = new JButton();
        refreshButton.setBackground(settingsColor);
        refreshButton.setIcon(refreshIcon);
        refreshButton.addActionListener(this);
        buttonPanel.add(refreshButton);
        
        return buttonPanel;
    }

    private JPanel createPanel(JPanel settingPanel, JPanel buttonPanel) {
        JPanel dialogPanel = new JPanel();

        // A panel elrendezése BorderLayout
        dialogPanel.setLayout(new BorderLayout());

        // Középen lesz a settingPanel
        dialogPanel.add(settingPanel, BorderLayout.CENTER);

        // Alul pedig a gombok
        dialogPanel.add(buttonPanel, BorderLayout.SOUTH);

        return dialogPanel;
    }

    public void actionPerformed(ActionEvent e) {
        if (saveButton == e.getSource()) {
            // Ha az OK gombot nyomták meg, akkor megpróbáljuk felvenni az
            // ügyfelet
        	System.out.println("click on refreshButton");
        	
            if (nameTextfield.getText().isEmpty()) {
                // Ha nem adtak meg nevet, akkor egy hibaüzenetet írunk ki egy
                // error dialogra (JOptionPane.ERROR_MESSAGE)
                JOptionPane.showMessageDialog(
                        gui.getWindow(),
                        Labels.student_name_is_required,
                        Labels.error,
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // létrehozzuk a customert
            Student student = new Student();
            
            student.setEha(ehaTextfield.getText());
            student.setName(nameTextfield.getText());
            student.setPoint((Integer)pointSpinner.getValue());
            student.setKb(kbCheck.isSelected());
            student.setAdmin(adminCheck.isSelected());
            student.setUser(userCheck.isSelected());

            if (!gui.getController().addStudent(student)) {
                // Ha az addCustomer false-t ad vissza akkor egy hibaüzenetet
                // írunk ki egy error dialogra(JOptionPane.ERROR_MESSAGE)
                JOptionPane.showMessageDialog(
                        gui.getWindow(),
                        Labels.student_exists,
                        Labels.error,
                        JOptionPane.ERROR_MESSAGE);
            } else {
                // Ha az addCustomer true-t ad vissza akkor bezárjuk a dialógust
                //setVisible(false);
            	//StudentPane.table.repaint();
            }
        } else if (refreshButton == e.getSource()) {
        	System.out.println("click on refreshButton");
        	ehaTextfield.setText("");
        	nameTextfield.setText("");
        	pointSpinner.setValue(0);
        	kbCheck.setSelected(false);
        	adminCheck.setSelected(false);
        	userCheck.setSelected(false);
        	
        	nameTextfield.setText("");
        	nameTextfield.setText("");
        	nameTextfield.setText("");
        } else if (printButton == e.getSource()) {
        	System.out.println("click on printButton");
        }
    }

}

