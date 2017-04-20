package hu.alkfejl.hermanNote.view.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hu.alkfejl.hermanNote.model.bean.Student;
import hu.alkfejl.hermanNote.view.Colors;
import hu.alkfejl.hermanNote.view.HermanNoteGUI;
import hu.alkfejl.hermanNote.view.Labels;
import hu.alkfejl.hermanNote.view.splitPanes.StudentSplitPane;

/**
 * Az osztály az új ügyfél felvételénél megjelenő dialógus.
 */
public class StudentSearchPanel extends JPanel implements ActionListener {


    private static final long serialVersionUID = 3382073646421158018L;

	private HermanNoteGUI gui;

    // A dialógus azon vezérlőit melyekre szükség lesz az eseménykezelés során
    // osztályváltozóként definiáljuk
    private JTextField nameTextfield = new JTextField(10);
    private JTextField roomTextfield = new JTextField(3);
    private JCheckBox kbCheck = new JCheckBox();
    private JCheckBox adminCheck = new JCheckBox();
    private JCheckBox userCheck = new JCheckBox();
    private JButton searchButton = new JButton(Labels.search);
    private JButton refreshButton = new JButton(Labels.cancel);
    private String sql;

	
    
    

    public StudentSearchPanel(HermanNoteGUI gui) {
    	this.gui=gui;
    	
    	 // A beállításokat tartalmazó panel gyártása
        JPanel settingPanel = createSettingPanel();

        // A gombokat tartalmazó panel gyártása
        JPanel buttonPanel = createButtonPanel();
        
        JPanel titlePanel = createTitlePanel();
        
        // Az előző két panelt egy panelre rakjuk
        JPanel MainPanel = createPanel(titlePanel, settingPanel, buttonPanel); 
        
        add(MainPanel);
        
        // Dialogus megjelenítése
        setVisible(true);
    }

    private JPanel createTitlePanel(){
    	JPanel settingPanel = new JPanel();
    	
    	settingPanel.add(new JLabel("Keresés"));
    	
    	return settingPanel;
    	
    }
    private JPanel createSettingPanel() {
        JPanel settingPanel = new JPanel();

        //ButtonGroup group = new ButtonGroup();
        
        // A panel elrendezése mátrix, 5 sor és 2 oszlop, a cellák egyforma méretűek
        settingPanel.setLayout(new GridLayout(6,2));

               
        // A 2. sorban egy név címke és egy szövegmező lesz
        settingPanel.add(new JLabel(Labels.student_name));
        settingPanel.add(this.nameTextfield);

        // A 3. sorban egy kor címke és egy spinner lesz
        settingPanel.add(new JLabel(Labels.student_room));
        settingPanel.add(this.roomTextfield);

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

        // Hozzáadjuk az ok gombot, és figyelünk rá
        ImageIcon searchIcon = new ImageIcon("src/img/search.png");
        ImageIcon searchTextIcon = new ImageIcon("src/img/searchText.png");
        
        searchButton = new JButton();
        searchButton.setBackground(Colors.searchColor);
        searchButton.setIcon(searchIcon);
        searchButton.addActionListener(this);
        
        searchButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	searchButton.setIcon(searchTextIcon);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	searchButton.setIcon(searchIcon);
            }
        });
        buttonPanel.add(searchButton);

        // Hozzáadjuk a refresh gombot, és figyelünk rá
        ImageIcon refreshIcon = new ImageIcon("src/img/refresh.png");
        ImageIcon refreshTextIcon = new ImageIcon("src/img/refreshText.png");
        refreshButton = new JButton();
        refreshButton.setBackground(Colors.refreshColor);
        refreshButton.setIcon(refreshIcon);
        refreshButton.addActionListener(this);
        refreshButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
            	refreshButton.setIcon(refreshTextIcon);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
            	refreshButton.setIcon(refreshIcon);
            }
        });
        buttonPanel.add(refreshButton);
        
        return buttonPanel;
    }

    private JPanel createPanel(JPanel titlePanel, JPanel settingPanel, JPanel buttonPanel) {
        JPanel dialogPanel = new JPanel();
        	
        // A panel elrendezése BorderLayout
        dialogPanel.setLayout(new BorderLayout());

        
        dialogPanel.add(titlePanel, BorderLayout.NORTH);
        
        // Középen lesz a settingPanel
        dialogPanel.add(settingPanel, BorderLayout.CENTER);

        // Alul pedig a gombok
        dialogPanel.add(buttonPanel, BorderLayout.SOUTH);

        return dialogPanel;
    }

    public void actionPerformed(ActionEvent e) {
        if (refreshButton == e.getSource()) {
        	System.out.println("click on refreshButton");
        	nameTextfield.setText("");
        	roomTextfield.setText("");
        	kbCheck.setSelected(false);
        	userCheck.setSelected(false);
        	adminCheck.setSelected(false);
        } else if (getSearchButton() == e.getSource()) {
			System.out.println("click on refreshButton2");
			Student student = new Student();
	            
            //student.setId(sp.getEhaTextfield().getText());
            student.setName(getNameTextfield().getText().toUpperCase());
            student.setRoom(Integer.parseInt(
            		getRoomTextfield().getText().equals("") ? 
            				"0" : getRoomTextfield().getText()));
            
            student.setKb(getKbCheck().isSelected());
            student.setAdmin(getAdminCheck().isSelected());
            student.setUser(getUserCheck().isSelected());
            student.toString();
            
            
            StudentSplitPane sp = new StudentSplitPane(gui, student);
	            
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

	public JButton getSearchButton() {
		return searchButton;
	}

	public void setSearchButton(JButton searchButton) {
		this.searchButton = searchButton;
	}

	public JButton getRefreshButton() {
		return refreshButton;
	}

	public void setRefreshButton(JButton refreshButton) {
		this.refreshButton = refreshButton;
	}
    
}



