package hu.alkfejl.hermanNote.view.tablemodels;

import hu.alkfejl.hermanNote.model.bean.Purchase;
import hu.alkfejl.hermanNote.view.Labels;

import java.util.List;

import javax.swing.table.AbstractTableModel;

public class PurchaseTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 2093252961204461838L;

    private String[] columnNames = new String[] {
            Labels.customer, Labels.gender, Labels.book, Labels.price };

    List<Purchase> purchases;

    public PurchaseTableModel(List<Purchase> purchases) {
        super();

        this.purchases = purchases;
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
        return purchases.size();
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
        Purchase purchase = purchases.get(row);
        String askedColumnName = columnNames[col];

        if (askedColumnName.equals(Labels.customer)) {
            return purchase.getCustomer().getName();
        } else if (askedColumnName.equals(Labels.gender)) {
            return purchase.getCustomer().isFemale() ? Labels.female : Labels.male;
        } else if (askedColumnName.equals(Labels.book)) {
            return purchase.getBook().getTitle();
        } else if (askedColumnName.equals(Labels.price)) {
            return purchase.getBook().getPrice();
        }

        return Labels.unknown;
    }

    // Nagyon fontos! Eredetileg egy JTable-ben minden sztring
    // �gy viszont ami boolean, az checkboxkent jelenik meg.
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
