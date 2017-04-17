package hu.alkfejl.hermanNote.view.panels;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import hu.alkfejl.hermanNote.model.bean.Student;
import hu.alkfejl.hermanNote.view.HermanNoteGUI;
import hu.alkfejl.hermanNote.view.Labels;
import hu.alkfejl.hermanNote.view.tablemodels.StudentTableModel;

public class StudentTablePanel extends JPanel implements ActionListener {
	
	private JLabel deleteLabel;
	private JLabel editLabel;
	private JButton editButton;
	private JButton deleteButton;
	Color settingsColor = new Color(224, 224, 224);
	private HermanNoteGUI gui;
	
	    
	public StudentTablePanel(HermanNoteGUI gui) {
		this.gui=gui;
		
		
        // A gombokat tartalmazó panel gyártása
        JPanel buttonPanel = createButtonPanel();

        // Az elõzõ két panelt egy panelre rakjuk
        JPanel MainPanel = createPanel(createTable(), buttonPanel); 
        
        add(MainPanel);
        
        setBackground(settingsColor);
        // Dialogus megjelenítése
        setVisible(true);
    }
	
	public StudentTablePanel(HermanNoteGUI gui, Student student) {
		this.gui=gui;
		
		
        // A gombokat tartalmazó panel gyártása
        JPanel buttonPanel = createButtonPanel();

        // Az elõzõ két panelt egy panelre rakjuk
        JPanel MainPanel = createPanel(createTable(student), buttonPanel); 
        
        add(MainPanel);
        
        setBackground(settingsColor);
        // Dialogus megjelenítése
        setVisible(true);
    }
	
	private JTable createTable() {
			
	        List<Student> students = gui.getController().getStudents();
	        
	        JTable table = new JTable(new StudentTableModel(students));
	        //table.setBackground(Color.lightGray);	        
	        return table;
	}

	private JTable createTable(Student student) {
        
        List<Student> students = gui.getController().searchStudent(student);
        System.out.println("createTable " + students.size());
        JTable table = new JTable(new StudentTableModel(students));
        //table.setBackground(Color.lightGray);	        
        return table;
    }
	 
	private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();

        // A panel elrendezése folytonos, középre igazítva
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Hozzáadjuk az edit gombot, és figyelünk rá  
        ImageIcon editIcon = new ImageIcon("src/img/edit.png");
        editButton = new JButton();
        editButton.setBackground(settingsColor);
        editButton.setIcon(editIcon);
        editButton.addActionListener(this);
        buttonPanel.add(editButton);

        // Hozzáadjuk a cancel gombot, és figyelünk rá
        ImageIcon deleteIcon = new ImageIcon("src/img/delete.png");
        deleteButton = new JButton();
        deleteButton.setBackground(settingsColor);
        deleteButton.setIcon(deleteIcon);
        deleteButton.addActionListener(this);
        buttonPanel.add(deleteButton);
        
        buttonPanel.setBackground(settingsColor);
        
        return buttonPanel;
    }

    private JPanel createPanel(JTable table, JPanel buttonPanel) {
        JPanel dialogPanel = new JPanel();

        // A panel elrendezése BorderLayout
        dialogPanel.setLayout(new BorderLayout());

        
        // Középen lesz a settingPanel
        dialogPanel.add(table, BorderLayout.CENTER);

        // Alul pedig a gombok
        buttonPanel.setPreferredSize(new Dimension(230, 100));
        buttonPanel.setMaximumSize(buttonPanel.getPreferredSize());
        dialogPanel.add(buttonPanel, BorderLayout.NORTH);

        //dialogPanel.setPreferredSize(new Dimension(240, 200));
        //dialogPanel.setMaximumSize(dialogPanel.getPreferredSize());
        dialogPanel.setBackground(Color.darkGray);
        return dialogPanel;
    }
    
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (deleteButton == e.getSource()){
			System.out.println("click on deleteButton");
		}
		if (editButton == e.getSource()){
			System.out.println("click on editButton");
		}
		
	}
}
