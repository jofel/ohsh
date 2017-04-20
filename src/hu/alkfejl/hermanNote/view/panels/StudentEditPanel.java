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
	
	private JTextField idTextfield = new JTextField(10);
	
    private JTextField nameTextfield = new JTextField(10);
    private JTextField roomTextfield = new JTextField(10);
    private JSpinner pointSpinner = new JSpinner();
    private JCheckBox kbCheck = new JCheckBox();
    private JCheckBox adminCheck = new JCheckBox();
    private JCheckBox userCheck = new JCheckBox();
    private JButton saveButton = new JButton(Labels.save);
    private JButton printButton = new JButton(Labels.print);
    private JButton refreshButton = new JButton(Labels.cancel);
    
    Student editStudent;
    
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

    public StudentEditPanel(HermanNoteGUI gui, Student student) {
    	this.gui=gui;
    	
   	 	// A beállításokat tartalmazó panel gyártása
       JPanel settingPanel = createSettingPanel(student);

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
        
        idTextfield.setVisible(false);
        idTextfield.setText("");
        
        // A 2. sorban egy név címke és egy szövegmezõ lesz
        settingPanel.add(new JLabel(Labels.student_name));
        settingPanel.add(this.nameTextfield);
        
     // Az 1. sorban egy kor címke és egy spinner lesz
        settingPanel.add(new JLabel(Labels.student_room));
        settingPanel.add(this.roomTextfield);

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

	private JPanel createSettingPanel(Student student) {
		editStudent = student;
        JPanel settingPanel = new JPanel();
        
        // A panel elrendezése mátrix, 5 sor és 2 oszlop, a cellák egyforma méretûek
        settingPanel.setLayout(new GridLayout(6,2));
        
        idTextfield.setVisible(false);
        idTextfield.setText(editStudent.getId()+"");
        
        // A 2. sorban egy név címke és egy szövegmezõ lesz
        settingPanel.add(new JLabel(Labels.student_name));
        settingPanel.add(this.nameTextfield);
        nameTextfield.setText(editStudent.getName());
        
     // Az 1. sorban egy kor címke és egy spinner lesz
        settingPanel.add(new JLabel(Labels.student_room));
        settingPanel.add(this.roomTextfield);
        roomTextfield.setText(editStudent.getRoom() + "");

        // A 3. sorban egy kor címke és egy spinner lesz
        settingPanel.add(new JLabel(Labels.student_point));
        settingPanel.add(this.pointSpinner);
        pointSpinner.setValue(editStudent.getPoint());

        // A 4. sorban lesz a kedvezményezettség
        settingPanel.add(new JLabel(Labels.student_kb));
        settingPanel.add(kbCheck);
        kbCheck.setSelected(editStudent.isKb());

        // A 5. sorban lesz a végzettség
        settingPanel.add(new JLabel(Labels.student_admin));
        settingPanel.add(adminCheck);
        adminCheck.setSelected(editStudent.isAdmin());
        
     	// A 6. sorban lesz a végzettség
        settingPanel.add(new JLabel(Labels.student_user));
        settingPanel.add(userCheck);
        userCheck.setSelected(editStudent.isUser());

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
        	System.out.println("click on refreshButton " + idTextfield.getText());
        	
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
            if (roomTextfield.getText().equals("")) {
                // Ha nem adtak meg nevet, akkor egy hibaüzenetet írunk ki egy
                // error dialogra (JOptionPane.ERROR_MESSAGE)
                JOptionPane.showMessageDialog(
                        gui.getWindow(),
                        Labels.student_room_is_required,
                        Labels.error,
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!idTextfield.getText().equals("")){
            	
            	 if (!gui.getController().editStudent(editStudent)) {
 	                // Ha az addCustomer false-t ad vissza akkor egy hibaüzenetet
 	                // írunk ki egy error dialogra(JOptionPane.ERROR_MESSAGE)
 	                JOptionPane.showMessageDialog(
 	                        gui.getWindow(),
 	                        Labels.student_edit_exists,
 	                        Labels.error,
 	                        JOptionPane.ERROR_MESSAGE);
            	 } else System.out.println("sikeres mósodítás ");
            	 
            	
            } else {
	            // létrehozzuk a customert
	            Student newStudent = new Student();
	            
	            
	            newStudent.setName(nameTextfield.getText().toUpperCase());
	            newStudent.setRoom(Integer.parseInt(roomTextfield.getText()));
	            newStudent.setPoint((Integer)pointSpinner.getValue());
	            newStudent.setKb(kbCheck.isSelected());
	            newStudent.setAdmin(adminCheck.isSelected());
	            newStudent.setUser(userCheck.isSelected());
	
	            if (!gui.getController().addStudent(newStudent)) {
	                // Ha az addCustomer false-t ad vissza akkor egy hibaüzenetet
	                // írunk ki egy error dialogra(JOptionPane.ERROR_MESSAGE)
	                JOptionPane.showMessageDialog(
	                        gui.getWindow(),
	                        Labels.student_exists,
	                        Labels.error,
	                        JOptionPane.ERROR_MESSAGE);
	            } else {
	                // Ha az addCustomer true-t ad vissza akkor bezárjuk a dialógust
	            	 System.out.println(newStudent.getName() + " hallgató sikeresen hozzáadva");
	                //setVisible(false);
	            	//StudentPane.table.repaint();
	            }
            }
        } else if (refreshButton == e.getSource()) {
        	System.out.println("click on refreshButton");
        	nameTextfield.setText("");
        	roomTextfield.setText("");
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

	public JTextField getNameTextfield() {
		return nameTextfield;
	}

	public void setNameTextfield(JTextField nameTextfield) {
		this.nameTextfield = nameTextfield;
	}

	public JTextField getRoomTextfield() {
		return roomTextfield;
	}

	public void setRoomTextfield(JTextField roomTextfield) {
		this.roomTextfield = roomTextfield;
	}

	public JSpinner getPointSpinner() {
		return pointSpinner;
	}

	public void setPointSpinner(JSpinner pointSpinner) {
		this.pointSpinner = pointSpinner;
	}

	public JCheckBox getKbCheck() {
		return kbCheck;
	}

	public void setKbCheck(JCheckBox kbCheck) {
		this.kbCheck = kbCheck;
	}

	public JCheckBox getAdminCheck() {
		return adminCheck;
	}

	public void setAdminCheck(JCheckBox adminCheck) {
		this.adminCheck = adminCheck;
	}

	public JCheckBox getUserCheck() {
		return userCheck;
	}

	public void setUserCheck(JCheckBox userCheck) {
		this.userCheck = userCheck;
	}

	public JButton getSaveButton() {
		return saveButton;
	}

	public void setSaveButton(JButton saveButton) {
		this.saveButton = saveButton;
	}

	public JButton getPrintButton() {
		return printButton;
	}

	public void setPrintButton(JButton printButton) {
		this.printButton = printButton;
	}

	public JButton getRefreshButton() {
		return refreshButton;
	}

	public void setRefreshButton(JButton refreshButton) {
		this.refreshButton = refreshButton;
	}
    

}

