package hu.alkfejl.hermanNote.view.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;

import hu.alkfejl.hermanNote.model.bean.Student;
import hu.alkfejl.hermanNote.view.HermanNoteGUI;
import hu.alkfejl.hermanNote.view.Labels;
import hu.alkfejl.hermanNote.view.splitPanes.StudentSplitPane;
import hu.alkfejl.hermanNote.view.tablemodels.StudentTableModel;

public class StudentTablePanel extends JPanel implements ActionListener {
	
	private JLabel deleteLabel;
	private JLabel editLabel;
	private JButton editButton;
	private JButton deleteButton;
	private JTable table;
	List<Student> students;
	Color settingsColor = new Color(224, 224, 224);
	private HermanNoteGUI gui;
	
	    
	public StudentTablePanel(HermanNoteGUI gui) {
		this.gui=gui;
		
		
        // A gombokat tartalmazó panel gyártása
        JPanel buttonPanel = createButtonPanel();
        
        JPanel titlePanel = createTitlePanel(); 

        // Az előző két panelt egy panelre rakjuk
        JPanel MainPanel = createPanel(createTitlePanel(), createTable(), buttonPanel); 
        
        add(MainPanel);
        
        setBackground(settingsColor);
        // Dialogus megjelenítése
        setVisible(true);
    }
	
	private JPanel createTitlePanel(){
    	JPanel settingPanel = new JPanel();
    	
    	settingPanel.add(new JLabel("Keresési találatok, szerkesztés"));
    	
    	return settingPanel;
    	
    }
	
	
	public StudentTablePanel(HermanNoteGUI gui, Student student) {
		this.gui=gui;
		
		JPanel titlePanel = createTitlePanel();
		
        // A gombokat tartalmazó panel gyártása
        JPanel buttonPanel = createButtonPanel();

        // Az előző két panelt egy panelre rakjuk
        JPanel MainPanel = createPanel(createTitlePanel(), createTable(student), buttonPanel); 
        
        add(MainPanel);
        
        setBackground(settingsColor);
        // Dialogus megjelenítése
        setVisible(true);
    }
	
	private JTable createTable() {
			
	        List<Student> students = gui.getController().getStudents();
	        System.out.println(students.get(0).getRoom());
	        JTable table = new JTable(new StudentTableModel(students));
	        //table.setBackground(Color.lightGray);	        
	        return table;
	}

	private JTable createTable(Student student) {
        
        students = gui.getController().searchStudent(student);
        table = new JTable(new StudentTableModel(students));
        
        TableColumn column = null;
        for (int i = 0; i < 3; i++) {
            column = table.getColumnModel().getColumn(i);
            if (i == 1) {
                column.setPreferredWidth(150); //sport column is bigger
            } else {
                column.setPreferredWidth(10);
            }
        }
        
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                // do some actions here, for example
                // print first column value from selected row
                System.out.println(table.getValueAt(table.getSelectedRow(), 0).toString());
            }
        });
        
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

    private JPanel createPanel(JPanel titlePanel, JTable table, JPanel buttonPanel) {
        JPanel dialogPanel = new JPanel();

        // A panel elrendezése BorderLayout
        dialogPanel.setLayout(new BorderLayout());
        
        dialogPanel.add(titlePanel, BorderLayout.NORTH);
        
        // Középen lesz a settingPanel
        dialogPanel.add(table, BorderLayout.SOUTH);

        // Alul pedig a gombok
        buttonPanel.setPreferredSize(new Dimension(230, 100));
        buttonPanel.setMaximumSize(buttonPanel.getPreferredSize());
        dialogPanel.add(buttonPanel, BorderLayout.CENTER);

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
			String tableId =  table.getValueAt(table.getSelectedRow(), 0).toString();
			for (Student student : students){
				String id = student.getId() + "";
				if (tableId.equals(id)){
					
					if (!gui.getController().removeStudent(student)) {
		                // Ha az addCustomer false-t ad vissza akkor egy hibaüzenetet
		                // írunk ki egy error dialogra(JOptionPane.ERROR_MESSAGE)
		                JOptionPane.showMessageDialog(
		                        gui.getWindow(),
		                        Labels.student_remove_fail,
		                        Labels.error,
		                        JOptionPane.ERROR_MESSAGE);
		            } else {
		            	JOptionPane.showMessageDialog(
		                        gui.getWindow(),
		                        Labels.student_remove_success,
		                        Labels.error,
		                        JOptionPane.INFORMATION_MESSAGE);
		            	StudentSplitPane sp = new StudentSplitPane(gui, student, student);
		            	
		            }
				}
			}
		}
		if (editButton == e.getSource()){
			System.out.println("click on editButton");
			String tableId =  table.getValueAt(table.getSelectedRow(), 0).toString();
			for (Student student : students){
				String id = student.getId() + "";
				System.out.println(tableId + "==" + id);
				if (tableId.equals(id)){
					System.out.println(tableId + "==" + id);
					StudentSplitPane sp = new StudentSplitPane(gui, student, student);
				}
			}
			for(int i=0; i<3; i++){
			System.out.print(table.getValueAt(table.getSelectedRow(), i)+"  ");
			}
			
		}
		
	}
}
