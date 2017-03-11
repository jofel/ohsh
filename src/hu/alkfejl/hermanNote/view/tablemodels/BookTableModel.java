package hu.alkfejl.hermanNote.view.tablemodels;

import hu.alkfejl.hermanNote.model.bean.Book;
import hu.alkfejl.hermanNote.view.Labels;

import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * Egyedi table modelt az AbstractTableModelb�l tudunk sz�rmaztatni.
 */
public class BookTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 2093252961204461838L;

    // Az egyes oszlop fejl�cek nevei
    private String[] columnNames = new String[] {
            Labels.author, Labels.title, Labels.year, Labels.category,
            Labels.price, Labels.piece, Labels.ancient };

    List<Book> books;

    public BookTableModel(List<Book> books) {
        super();

        this.books = books;
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
        return books.size();
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
        Book book = books.get(row);
        String askedColumnName = columnNames[col];

        if (askedColumnName.equals(Labels.author)) {
            return book.getAuthor();
        }else if (askedColumnName.equals(Labels.title)) {
            return book.getTitle();
        }else if (askedColumnName.equals(Labels.year)) {
            return book.getYear();
        }else if (askedColumnName.equals(Labels.category)) {
            return book.getCategory();
        }else if (askedColumnName.equals(Labels.price)) {
            return book.getPrice();
        }else if (askedColumnName.equals(Labels.piece)) {
            return book.getPiece();
        }else if (askedColumnName.equals(Labels.ancient)) {
            return book.isAncient();
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
