package hu.alkfejl.hermanNote.view.tablemodels;

import hu.alkfejl.hermanNote.model.bean.Student;
import hu.alkfejl.hermanNote.view.Labels;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

/**
 * Egyedi table modelt az AbstractTableModelb�l tudunk sz�rmaztatni.
 */
public class StudentTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 2093252961204461838L;

    // Az egyes oszlop fejl�cek nevei
    private String[] columnNames = new String[] {
            Labels.student_eha, Labels.student_name
           // , Labels.student_point, Labels.student_kb, Labels.student_admin, Labels.student_user
            };

    // A v�s�rl�kat tartalmaz� objektum (a DAO-t�l kapott �tstrukt�r�lva)
    Map<Integer, Student> students = new HashMap<Integer, Student>();

    public StudentTableModel(List<Student> students) {
    	super();

        prepareDataStructure(students);
	}
    /**
     * A controlleren kereszt�l megkapott users strukt�r�j�t (lista) a view
     * sz�ks�gleteinek megfelel�en �talak�tja.
     * <p>
     * Egy map-be tessz�k �ket, kulcsk�nt a sorsz�mot haszn�ljuk, �gy a
     * lenti {@ #getValueAt(int,int)} f�ggv�nyt tudjuk implement�lni.
     * </p>
     *
     * @param users A {@link Customers}-ek list�ja.
     */
	private void prepareDataStructure(List<Student> students) {
		int row = 0;

        for (Student student : students) {
            this.students.put(row, student);
            row++;
        }
		
	}

	
    /* A table model megval�s�t�s�hoz fel�l kell �rni n�h�ny fontos met�dust!
     */

    /**
     * Megadja, hogy h�ny oszlopa van a t�bl�zatnak.
     */
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Megadja, hogy h�ny sora van a t�bl�zatnak.
     */
    @Override
    public int getRowCount() {
        return students.entrySet().size();
    }

    /**
     * Megadja, hogy adott oszlopnak mi a neve.
     */
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    /**
     * Megadja, hogy adott sorban �s oszlopban milyen �rt�k szerepel.
     */
    @Override
    public Object getValueAt(int row, int col) {
        Student student = students.get(row);
        String askedColumnName = columnNames[col];

        if (askedColumnName.equals(Labels.student_eha)) {
            return student.getEha();
        } else if (askedColumnName.equals(Labels.student_name)) {
            return student.getName();
        } else if (askedColumnName.equals(Labels.student_point)) {
            return student.getPoint();
        } else if (askedColumnName.equals(Labels.student_kb)) {
        	//System.out.println("Student kollbizes?"+ student.isKb());
            return student.isKb();
        } else if (askedColumnName.equals(Labels.student_admin)) {
            return student.isAdmin();
        } else if (askedColumnName.equals(Labels.student_user)) {
            return student.isUser();
        } 


        return Labels.unknown;
    }

    // Nagyon fontos! Eredetileg egy JTable-ben minden String
    // �gy viszont, ami boolean, az checkboxkent jelenik meg.
    @Override
    public Class<?> getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    // Nagyon fontos! Eredetileg egy JTable minden mez�je szerkeszthet�
    // Jelenleg ezt letiltjuk, a szerkeszt�shez a kontrolleren kereszt�l az
    // adatb�zis kommunik�ci�t is implementalni kell!
    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

}

