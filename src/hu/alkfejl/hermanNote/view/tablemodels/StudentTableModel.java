package hu.alkfejl.hermanNote.view.tablemodels;

import hu.alkfejl.hermanNote.model.bean.Student;
import hu.alkfejl.hermanNote.view.Labels;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

/**
 * Egyedi table modelt az AbstractTableModelbõl tudunk származtatni.
 */
public class StudentTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 2093252961204461838L;

    // Az egyes oszlop fejlécek nevei
    private String[] columnNames = new String[] {
            Labels.student_eha, Labels.student_name
           // , Labels.student_point, Labels.student_kb, Labels.student_admin, Labels.student_user
            };

    // A vásárlókat tartalmazó objektum (a DAO-tól kapott átstruktúrálva)
    Map<Integer, Student> students = new HashMap<Integer, Student>();

    public StudentTableModel(List<Student> students) {
    	super();

        prepareDataStructure(students);
	}
    /**
     * A controlleren keresztül megkapott users struktúráját (lista) a view
     * szükségleteinek megfelelõen átalakítja.
     * <p>
     * Egy map-be tesszük õket, kulcsként a sorszámot használjuk, így a
     * lenti {@ #getValueAt(int,int)} függvényt tudjuk implementálni.
     * </p>
     *
     * @param users A {@link Customers}-ek listája.
     */
	private void prepareDataStructure(List<Student> students) {
		int row = 0;

        for (Student student : students) {
            this.students.put(row, student);
            row++;
        }
		
	}

	
    /* A table model megvalósításához felül kell írni néhány fontos metódust!
     */

    /**
     * Megadja, hogy hány oszlopa van a táblázatnak.
     */
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Megadja, hogy hány sora van a táblázatnak.
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
     * Megadja, hogy adott sorban és oszlopban milyen érték szerepel.
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
    // Így viszont, ami boolean, az checkboxkent jelenik meg.
    @Override
    public Class<?> getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    // Nagyon fontos! Eredetileg egy JTable minden mezõje szerkeszthetõ
    // Jelenleg ezt letiltjuk, a szerkesztéshez a kontrolleren keresztül az
    // adatbázis kommunikációt is implementalni kell!
    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

}

