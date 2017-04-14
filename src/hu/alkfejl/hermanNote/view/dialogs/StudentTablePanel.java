package hu.alkfejl.hermanNote.view.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import hu.alkfejl.hermanNote.model.bean.Student;
import hu.alkfejl.hermanNote.view.HermanNoteGUI;
import hu.alkfejl.hermanNote.view.Labels;
import hu.alkfejl.hermanNote.view.tablemodels.StudentTableModel;

public class StudentTablePanel extends JPanel implements ActionListener {

	private JButton editButton = new JButton(Labels.edit);
	private JButton deleteButton = new JButton(Labels.delete);
	
	private HermanNoteGUI gui;

	    
	StudentTablePanel(HermanNoteGUI gui) {
		this.gui=gui;
		
		
        // A gombokat tartalmazó panel gyártása
        JPanel buttonPanel = createButtonPanel();

        // Az elõzõ két panelt egy panelre rakjuk
        JPanel MainPanel = createPanel(createTable(), buttonPanel); 
        
        add(MainPanel);
        
        // Dialogus megjelenítése
        setVisible(true);
    }
	
	 private JTable createTable() {
	        
	        List<Student> students = gui.getController().getStudents();
	        JTable table = new JTable(new StudentTableModel(students));
	        	        
	        return table;
	    }

	 
	private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();

        // A panel elrendezése folytonos, középre igazítva
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Hozzáadjuk az ok gombot, és figyelünk rá
        buttonPanel.add(editButton);
        editButton.addActionListener(this);

        // Hozzáadjuk a cancel gombot, és figyelünk rá
        buttonPanel.add(deleteButton);
        deleteButton.addActionListener(this);

        return buttonPanel;
    }

    private JPanel createPanel(JTable table, JPanel buttonPanel) {
        JPanel dialogPanel = new JPanel();
        JLabel title = new JLabel("valami");
        title.setPreferredSize(new Dimension(230, 20));
        title.setMaximumSize(title.getPreferredSize());

        // A panel elrendezése BorderLayout
        dialogPanel.setLayout(new BorderLayout());

        // Középen lesz a settingPanel
        dialogPanel.add(title, BorderLayout.NORTH);
        
        
        // Középen lesz a settingPanel
        dialogPanel.add(table, BorderLayout.CENTER);

        // Alul pedig a gombok
        dialogPanel.add(buttonPanel, BorderLayout.SOUTH);

        //dialogPanel.setPreferredSize(new Dimension(240, 200));
        //dialogPanel.setMaximumSize(dialogPanel.getPreferredSize());
        
        return dialogPanel;
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
