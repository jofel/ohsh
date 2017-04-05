package hu.alkfejl.hermanNote.controller;

import java.util.List;

import hu.alkfejl.hermanNote.model.HermanNoteDAO;
import hu.alkfejl.hermanNote.model.HermanNoteDAOOracle;
import hu.alkfejl.hermanNote.model.bean.Book;
import hu.alkfejl.hermanNote.model.bean.Customer;
import hu.alkfejl.hermanNote.model.bean.Purchase;
import hu.alkfejl.hermanNote.model.bean.Student;
import hu.alkfejl.hermanNote.view.HermanNoteGUI;

/**
 * Ez az oszt�ly vez�rli az eg�sz programot, valamint a view �s model csomagokat
 * k�ti �ssze. Itt tal�lhat� az �zleti logika (business logic) is.
 */
public class HermanNoteController {

    // Data Access Object - az adat el�r�s�t szolg�l� objektum
    // FONTOS!!! A BookShopDAO az adatel�r�si r�teg interf�sze (absztraktci�ja)
    // a r�teget mindig az interf�szen kereszt�l �rj�k el.
    // A r�teg implement�ci�j�t egyszer haszn�ljuk, p�ld�nyos�t�skor,
    // visszacastolni TILOS!!!
    private HermanNoteDAO dao = new HermanNoteDAOOracle();

    /**
     * Elind�tja az alkalmaz�s desktopra specializ�lt user interface-�t.
     */
    public void startDesktop() {
        HermanNoteGUI vc = new HermanNoteGUI(this);

        // GUI fel�let elind�t�sa
        vc.startGUI();
    }
    public boolean addStudent(Student s) {
		// Controller, business logic-ra (�zleti logika, szab�lyok) p�lda
        // Szab�ly: valaki akkor hallgat� ha 14-n�l fiatalabb, valaki akkor
        // nyugd�jas ha 62-nel id�sebb
        
        return dao.addStudent(s);
	}
    
    public List<Student> getStudents() {
        // A customer list�z�sn�l nincs �zleti szab�ly, ez�rt csak visszaadjuk a
        // model-t�l kapott list�t.
        return dao.getStudents();
    }
    
    public boolean addCustomer(Customer c) {
        // Controller, business logic-ra (�zleti logika, szab�lyok) p�lda
        // Szab�ly: valaki akkor hallgat� ha 14-n�l fiatalabb, valaki akkor
        // nyugd�jas ha 62-nel id�sebb
        if (c.getAge() < 14) {
            c.setStudent(true);
        } else if(c.getAge() > 62) {
            c.setRented(true);
        }

        return dao.addCustomer(c);
    }

    public List<Customer> getCustomers() {
        // A customer list�z�sn�l nincs �zleti szab�ly, ez�rt csak visszaadjuk a
        // model-t�l kapott list�t.
        return dao.getCustomers();
    }

    public boolean addBook(Book book) {
        if (book.getYear() < 1900 ) {
            book.setAncient(true);
        }

        return dao.addBook(book);
    }

    public List<Book> getBooks(){
        return dao.getBooks();
    }

    public boolean addPurchase(Purchase p){
        return dao.addPurchase(p);
    }

    public List<Purchase> getPurchases(){
        return dao.getPurchases();
    }

}
