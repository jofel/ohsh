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

import hu.alkfejl.hermanNote.view.Colors;
import hu.alkfejl.hermanNote.view.HermanNoteGUI;
import hu.alkfejl.hermanNote.view.Labels;

/**
 * Az oszt�ly az �j �gyf�l felv�tel�n�l megjelen� dial�gus.
 */
public class StudentSearchPanel extends JPanel implements ActionListener {


    private static final long serialVersionUID = 3382073646421158018L;

	private HermanNoteGUI gui;

    // A dial�gus azon vez�rl�it melyekre sz�ks�g lesz az esem�nykezel�s sor�n
    // oszt�lyv�ltoz�k�nt defini�ljuk
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

        //ButtonGroup group = new ButtonGroup();
        
        // A panel elrendez�se m�trix, 5 sor �s 2 oszlop, a cell�k egyforma m�ret�ek
        settingPanel.setLayout(new GridLayout(6,2));

               
        // A 2. sorban egy n�v c�mke �s egy sz�vegmez� lesz
        settingPanel.add(new JLabel(Labels.student_name));
        settingPanel.add(this.nameTextfield);

        // A 3. sorban egy kor c�mke �s egy spinner lesz
        settingPanel.add(new JLabel(Labels.student_room));
        settingPanel.add(this.roomTextfield);

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

        // Hozz�adjuk az ok gombot, �s figyel�nk r�
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

        // Hozz�adjuk a refresh gombot, �s figyel�nk r�
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
        if (refreshButton == e.getSource()) {
        	System.out.println("click on refreshButton");
        	nameTextfield.setText("");
        	roomTextfield.setText("");
        	kbCheck.setSelected(false);
        	adminCheck.setSelected(false);
        	userCheck.setSelected(false);
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



