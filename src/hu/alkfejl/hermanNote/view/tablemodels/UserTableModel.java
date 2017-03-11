package hu.alkfejl.hermanNote.view.tablemodels;

import hu.alkfejl.hermanNote.model.bean.User;
import hu.alkfejl.hermanNote.view.Labels;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

/**
 * Egyedi table modelt az AbstractTableModelbõl tudunk származtatni.
 */
public class UserTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 2093252961204461838L;

    // Az egyes oszlop fejlécek nevei
    private String[] columnNames = new String[] {
            Labels.user_name, Labels.user_eha, Labels.user_room,
            Labels.user_kb, Labels.user_admin};

    // A vásárlókat tartalmazó objektum (a DAO-tól kapott átstruktúrálva)
    Map<Integer, User> users = new HashMap<Integer, User>();

    public UserTableModel(List<User> users) {
    	super();

        prepareDataStructure(users);
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
	private void prepareDataStructure(List<User> users) {
		int row = 0;

        for (User user : users) {
            this.users.put(row, user);
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
        return users.entrySet().size();
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
        User user = users.get(row);
        String askedColumnName = columnNames[col];

        if (askedColumnName.equals(Labels.user_name)) {
            return user.getName();
        } else if (askedColumnName.equals(Labels.user_eha)) {
            return user.getEha();
        } else if (askedColumnName.equals(Labels.user_room)) {
            return user.getRoom();
        } else if (askedColumnName.equals(Labels.user_kb)) {
            return user.isKb();
        } else if (askedColumnName.equals(Labels.user_admin)) {
            return user.isAdmin();
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

