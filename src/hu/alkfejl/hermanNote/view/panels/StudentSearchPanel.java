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
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import hu.alkfejl.hermanNote.view.HermanNoteGUI;
import hu.alkfejl.hermanNote.view.Labels;

/**
 * Az osztály az új ügyfél felvételénél megjelenõ dialógus.
 */
public class StudentSearchPanel extends JPanel implements ActionListener {


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
    private JButton searchButton = new JButton(Labels.search);
    private JButton refreshButton = new JButton(Labels.cancel);
    private String sql;
    
    Color settingsColor = new Color(224, 224, 224);

    public StudentSearchPanel(HermanNoteGUI gui) {
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

        // Hozzáadjuk az ok gombot, és figyelünk rá
        ImageIcon searchIcon = new ImageIcon("src/img/search.png");
        ImageIcon searchTextIcon = new ImageIcon("src/img/searchText.png");
        
        searchButton = new JButton();
        searchButton.setBackground(settingsColor);
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
        refreshButton.setBackground(settingsColor);
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
        if (searchButton == e.getSource()) {
            // Ha az OK gombot nyomták meg, akkor megpróbáljuk felvenni az
            // ügyfelet
        	//System.out.println("click on refreshButton1");
        	
        } else if (refreshButton == e.getSource()) {
        	System.out.println("click on refreshButton");
        	ehaTextfield.setText("");
        	nameTextfield.setText("");
        	pointSpinner.setValue(0);
        	kbCheck.setSelected(false);
        	adminCheck.setSelected(false);
        	userCheck.setSelected(false);
        	
        }
    }

	public JTextField getEhaTextfield() {
		return ehaTextfield;
	}

	public void setEhaTextfield(JTextField ehaTextfield) {
		this.ehaTextfield = ehaTextfield;
	}

	public JTextField getNameTextfield() {
		return nameTextfield;
	}

	public void setNameTextfield(JTextField nameTextfield) {
		this.nameTextfield = nameTextfield;
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



