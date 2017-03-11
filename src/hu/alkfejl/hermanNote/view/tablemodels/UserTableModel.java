package hu.alkfejl.hermanNote.view.tablemodels;

import hu.alkfejl.hermanNote.model.bean.User;
import hu.alkfejl.hermanNote.view.Labels;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

/**
 * Egyedi table modelt az AbstractTableModelb�l tudunk sz�rmaztatni.
 */
public class UserTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 2093252961204461838L;

    // Az egyes oszlop fejl�cek nevei
    private String[] columnNames = new String[] {
            Labels.user_name, Labels.user_eha, Labels.user_room,
            Labels.user_kb, Labels.user_admin};

    // A v�s�rl�kat tartalmaz� objektum (a DAO-t�l kapott �tstrukt�r�lva)
    Map<Integer, User> users = new HashMap<Integer, User>();

    public UserTableModel(List<User> users) {
    	super();

        prepareDataStructure(users);
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
	private void prepareDataStructure(List<User> users) {
		int row = 0;

        for (User user : users) {
            this.users.put(row, user);
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
     * Megadja, hogy adott sorban �s oszlopban milyen �rt�k szerepel.
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

