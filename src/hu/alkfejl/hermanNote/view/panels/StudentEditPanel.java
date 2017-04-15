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
 * Az oszt�ly az �j �gyf�l felv�tel�n�l megjelen� dial�gus.
 */
public class StudentEditPanel extends JPanel implements ActionListener {


    private static final long serialVersionUID = 3382073646421158018L;

	private HermanNoteGUI gui;

    // A dial�gus azon vez�rl�it melyekre sz�ks�g lesz az esem�nykezel�s sor�n
    // oszt�lyv�ltoz�k�nt defini�ljuk
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
    	
    	 // A be�ll�t�sokat tartalmaz� panel gy�rt�sa
        JPanel settingPanel = createSettingPanel();

        // A gombokat tartalmaz� panel gy�rt�sa
        JPanel buttonPanel = createButtonPanel();

        // Az el�z� k�t panelt egy panelre rakjuk
        JPanel MainPanel = createPanel(settingPanel, buttonPanel); 
        
        add(MainPanel);
        
        // Dialogus megjelen�t�se
        setVisible(true);
    }

    private JPanel createSettingPanel() {
        JPanel settingPanel = new JPanel();

        // A panel elrendez�se m�trix, 5 sor �s 2 oszlop, a cell�k egyforma m�ret�ek
        settingPanel.setLayout(new GridLayout(6,2));

        // Az 1. sorban egy kor c�mke �s egy spinner lesz
        settingPanel.add(new JLabel(Labels.student_eha));
        settingPanel.add(this.ehaTextfield);
        
        // A 2. sorban egy n�v c�mke �s egy sz�vegmez� lesz
        settingPanel.add(new JLabel(Labels.student_name));
        settingPanel.add(this.nameTextfield);

        // A 3. sorban egy kor c�mke �s egy spinner lesz
        settingPanel.add(new JLabel(Labels.student_point));
        settingPanel.add(this.pointSpinner);

        // A 4. sorban lesz a kedvezm�nyezetts�g
        settingPanel.add(new JLabel(Labels.student_kb));
        settingPanel.add(kbCheck);

        // A 5. sorban lesz a v�gzetts�g
        settingPanel.add(new JLabel(Labels.student_admin));
        settingPanel.add(adminCheck);
        
     	// A 6. sorban lesz a v�gzetts�g
        settingPanel.add(new JLabel(Labels.student_user));
        settingPanel.add(userCheck);

        return settingPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        
        // A panel elrendez�se folytonos, k�z�pre igaz�tva
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Hozz�adjuk az save gombot, �s figyel�nk r�
        ImageIcon saveIcon = new ImageIcon("src/img/save.png");
        saveButton = new JButton();
        saveButton.setBackground(settingsColor);
        saveButton.setIcon(saveIcon);
        saveButton.addActionListener(this);
        buttonPanel.add(saveButton);

        // Hozz�adjuk a refresh gombot, �s figyel�nk r�
        ImageIcon printIcon = new ImageIcon("src/img/print.png");
        printButton = new JButton();
        printButton.setBackground(settingsColor);
        printButton.setIcon(printIcon);
        printButton.addActionListener(this);
        buttonPanel.add(printButton);
        
        // Hozz�adjuk a refresh gombot, �s figyel�nk r�
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

        // A panel elrendez�se BorderLayout
        dialogPanel.setLayout(new BorderLayout());

        // K�z�pen lesz a settingPanel
        dialogPanel.add(settingPanel, BorderLayout.CENTER);

        // Alul pedig a gombok
        dialogPanel.add(buttonPanel, BorderLayout.SOUTH);

        return dialogPanel;
    }

    public void actionPerformed(ActionEvent e) {
        if (saveButton == e.getSource()) {
            // Ha az OK gombot nyomt�k meg, akkor megpr�b�ljuk felvenni az
            // �gyfelet
        	System.out.println("click on refreshButton");
        	
            if (nameTextfield.getText().isEmpty()) {
                // Ha nem adtak meg nevet, akkor egy hiba�zenetet �runk ki egy
                // error dialogra (JOptionPane.ERROR_MESSAGE)
                JOptionPane.showMessageDialog(
                        gui.getWindow(),
                        Labels.student_name_is_required,
                        Labels.error,
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // l�trehozzuk a customert
            Student student = new Student();
            
            student.setEha(ehaTextfield.getText());
            student.setName(nameTextfield.getText());
            student.setPoint((Integer)pointSpinner.getValue());
            student.setKb(kbCheck.isSelected());
            student.setAdmin(adminCheck.isSelected());
            student.setUser(userCheck.isSelected());

            if (!gui.getController().addStudent(student)) {
                // Ha az addCustomer false-t ad vissza akkor egy hiba�zenetet
                // �runk ki egy error dialogra(JOptionPane.ERROR_MESSAGE)
                JOptionPane.showMessageDialog(
                        gui.getWindow(),
                        Labels.student_exists,
                        Labels.error,
                        JOptionPane.ERROR_MESSAGE);
            } else {
                // Ha az addCustomer true-t ad vissza akkor bez�rjuk a dial�gust
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

