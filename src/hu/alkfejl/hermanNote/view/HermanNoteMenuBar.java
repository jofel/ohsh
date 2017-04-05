package hu.alkfejl.hermanNote.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import hu.alkfejl.hermanNote.model.bean.Book;
import hu.alkfejl.hermanNote.model.bean.Customer;
import hu.alkfejl.hermanNote.model.bean.Purchase;
import hu.alkfejl.hermanNote.model.bean.Student;
import hu.alkfejl.hermanNote.view.dialogs.AddCustomerDialog;
import hu.alkfejl.hermanNote.view.dialogs.AddStudentDialog;
import hu.alkfejl.hermanNote.view.dialogs.BuyBookDialog;
import hu.alkfejl.hermanNote.view.dialogs.SellBookDialog;
import hu.alkfejl.hermanNote.view.tablemodels.BookTableModel;
import hu.alkfejl.hermanNote.view.tablemodels.CustomerTableModel;
import hu.alkfejl.hermanNote.view.tablemodels.PurchaseTableModel;
import hu.alkfejl.hermanNote.view.tablemodels.StudentTableModel;

/**
 * A {@link BookShopMenuBar} oszt�ly reprezent�lja a men�t.
 * Az�rt teheti ezt meg, mert a {@link JMenuBar} oszt�lyb�l sz�rmazik.
 * �gy a men�re a this-szel hivatkozhatunk.
 */
public class HermanNoteMenuBar extends JMenuBar implements ActionListener {

    // A JMenuBar implement�lja a Serializable interf�szt, emiatt kell a serialVersionUID
    // Ez m�shol is el�fordulhat, felold�sa: az oszt�ly mellett bal oldalt s�rga
    // felki�lt�jel Eclipse-ben, r�klikkelve 'Add generated serial version id'.
    // Kipr�b�lhatjuk, ha ezt a sort t�r�lj�k.
    private static final long serialVersionUID = 2973555574160940115L;
    private HermanNoteGUI gui;

    public HermanNoteMenuBar(HermanNoteGUI gui) {
        super();
        this.gui = gui;

        // H�rom men�pontot gy�rtunk �ltal�nosan, a createMenuPoint met�dussal
        createMenuPoint(Labels.students, Labels.add_student, Labels.list_students);
        //createMenuPoint(Labels.customer, Labels.add_customer, Labels.list_customers);
        //createMenuPoint(Labels.book, Labels.buy_book, Labels.list_books);
        //createMenuPoint(Labels.sell, Labels.sell_book, Labels.list_sold_books);
    }

    private void createMenuPoint(String name, String... subnames) {
        // L�trehozunk egy menupontot az els� param�ter alapj�n
        JMenu menu = new JMenu(name);

        // A menupontot hozz�adjuk a BookShopMenuBar-hoz
        this.add(menu);

        // Az egyes menu itemeket a marad�k param�ter �rt�keivel hozzuk l�tre
        for (String subname : subnames) {
            JMenuItem menuItem = new JMenuItem(subname);

            menu.add(menuItem);

            // Minden egyes menu itemet figyel�nk
            // A menu itemek eset�n a megfigyel�st az ActionListener interf�sz
            // biztos�tja, ez�rt a menubar implement�lja ezt az interf�szt �s
            // fel�l�rja az actionPerformed met�dust
            menuItem.addActionListener(this);
        }
    }

    /*
     * Az interf�szekhez tartoz� met�dusokat c�lszer� gener�lni, azut�n, hogy
     * megadtuk az implements kulcs� ut�n az interf�szt,
     * Jobb klikk + Source + Override/Implement 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();

        System.out.println("A k�vetkez� men�t megh�vta a felhaszn�l�:" + actionCommand);
        if (actionCommand.equals(Labels.add_student)){
        	new AddStudentDialog(gui, true);
        } else if (actionCommand.equals(Labels.list_students)){
        	List<Student> students = gui.getController().getStudents();

            // Csin�lunk egy t�bl�zatot, a CustomerTableModel alapjan, ami
            // megkapja a controltol a customereket
            JTable table = new JTable(new StudentTableModel(students));

            // A t�blazatot r�rakjuk egy ScrollPane-re, �gy ha az t�l nagy lenne
            // az ablak m�ret�hez k�pest, akkor is g�rgetheto lesz
            JScrollPane container = new JScrollPane(table);

            // Ezt a ScrollPane-t �ll�tjuk be a f�ablak tartalm�nak
            gui.setActualContent(container);
            
        } else if (actionCommand.equals(Labels.add_customer)) {
            // Ha az �j �gyf�l felv�tel�t v�lasztott�k, akkor egy
            // AddCustomerDialog-ot ind�tunk
            new AddCustomerDialog(gui, true);
        } else if (actionCommand.equals(Labels.list_customers)) {
            List<Customer> customers = gui.getController().getCustomers();

            // Csin�lunk egy t�bl�zatot, a CustomerTableModel alapjan, ami
            // megkapja a controltol a customereket
            JTable table = new JTable(new CustomerTableModel(customers));

            // A t�blazatot r�rakjuk egy ScrollPane-re, �gy ha az t�l nagy lenne
            // az ablak m�ret�hez k�pest, akkor is g�rgetheto lesz
            JScrollPane container = new JScrollPane(table);

            // Ezt a ScrollPane-t �ll�tjuk be a f�ablak tartalm�nak
            gui.setActualContent(container);
        } else if(actionCommand.equals(Labels.buy_book)) {
            new BuyBookDialog(gui, true);
        } else if(actionCommand.equals(Labels.list_books)) {
            List<Book> books = gui.getController().getBooks();
            JTable table = new JTable(new BookTableModel(books));
            JScrollPane container = new JScrollPane(table);
            gui.setActualContent(container);
        } else if (actionCommand.equals(Labels.sell_book)) {
            new SellBookDialog(gui, true);
        } else if (actionCommand.equals(Labels.list_sold_books)) {
            List<Purchase> purchases = gui.getController().getPurchases();
            JTable table = new JTable(new PurchaseTableModel(purchases));
            JScrollPane container = new JScrollPane(table);
            gui.setActualContent(container);
        }
    }

}
