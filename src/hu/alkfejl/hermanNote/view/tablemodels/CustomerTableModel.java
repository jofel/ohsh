package hu.alkfejl.hermanNote.view.tablemodels;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import hu.alkfejl.hermanNote.model.bean.Customer;
import hu.alkfejl.hermanNote.view.Labels;

/**
 * Egyedi table modelt az AbstractTableModelb�l tudunk sz�rmaztatni.
 */
public class CustomerTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 2093252961204461838L;

    // Az egyes oszlop fejl�cek nevei
    private String[] columnNames = new String[] {
            Labels.name, Labels.age, Labels.female,
            Labels.rented, Labels.student, Labels.grantee,
            Labels.qualification };

    // A v�s�rl�kat tartalmaz� objektum (a DAO-t�l kapott �tstrukt�r�lva)
    Map<Integer, Customer> customers = new HashMap<Integer, Customer>();

    public CustomerTableModel(List<Customer> customers) {
        super();

        prepareDataStructure(customers);
    }

	/**
     * A controlleren kereszt�l megkapott customers strukt�r�j�t (lista) a view
     * sz�ks�gleteinek megfelel�en �talak�tja.
     * <p>
     * Egy map-be tessz�k �ket, kulcsk�nt a sorsz�mot haszn�ljuk, �gy a
     * lenti {@ #getValueAt(int,int)} f�ggv�nyt tudjuk implement�lni.
     * </p>
     *
     * @param customers A {@link Customers}-ek list�ja.
     */
    private void prepareDataStructure(List<Customer> customers) {
        int row = 0;

        for (Customer customer : customers) {
            this.customers.put(row, customer);
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
        return customers.entrySet().size();
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
        Customer customer = customers.get(row);
        String askedColumnName = columnNames[col];

        if (askedColumnName.equals(Labels.name)) {
            return customer.getName();
        } else if (askedColumnName.equals(Labels.age)) {
            return customer.getAge();
        } else if (askedColumnName.equals(Labels.female)) {
            return customer.isFemale();
        } else if (askedColumnName.equals(Labels.rented)) {
            return customer.isRented();
        } else if (askedColumnName.equals(Labels.student)) {
            return customer.isStudent();
        } else if (askedColumnName.equals(Labels.grantee)) {
            return customer.isGrantee();
        } else if (askedColumnName.equals(Labels.qualification)) {
            return customer.getQualification();
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

