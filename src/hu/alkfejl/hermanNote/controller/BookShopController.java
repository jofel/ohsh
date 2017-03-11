package hu.alkfejl.hermanNote.controller;

import java.util.List;

import hu.alkfejl.hermanNote.model.BookShopDAO;
import hu.alkfejl.hermanNote.model.BookShopDAOOracle;
import hu.alkfejl.hermanNote.model.bean.Book;
import hu.alkfejl.hermanNote.model.bean.Customer;
import hu.alkfejl.hermanNote.model.bean.Purchase;
import hu.alkfejl.hermanNote.model.bean.User;
import hu.alkfejl.hermanNote.view.BookShopGUI;

/**
 * Ez az oszt�ly vez�rli az eg�sz programot, valamint a view �s model csomagokat
 * k�ti �ssze. Itt tal�lhat� az �zleti logika (business logic) is.
 */
public class BookShopController {

    // Data Access Object - az adat el�r�s�t szolg�l� objektum
    // FONTOS!!! A BookShopDAO az adatel�r�si r�teg interf�sze (absztraktci�ja)
    // a r�teget mindig az interf�szen kereszt�l �rj�k el.
    // A r�teg implement�ci�j�t egyszer haszn�ljuk, p�ld�nyos�t�skor,
    // visszacastolni TILOS!!!
    private BookShopDAO dao = new BookShopDAOOracle();

    /**
     * Elind�tja az alkalmaz�s desktopra specializ�lt user interface-�t.
     */
    public void startDesktop() {
        BookShopGUI vc = new BookShopGUI(this);

        // GUI fel�let elind�t�sa
        vc.startGUI();
    }
    public boolean addUser(User u) {
		// Controller, business logic-ra (�zleti logika, szab�lyok) p�lda
        // Szab�ly: valaki akkor hallgat� ha 14-n�l fiatalabb, valaki akkor
        // nyugd�jas ha 62-nel id�sebb
        
        return dao.addUser(u);
	}
    
    public List<User> getUsers() {
        // A customer list�z�sn�l nincs �zleti szab�ly, ez�rt csak visszaadjuk a
        // model-t�l kapott list�t.
        return dao.getUsers();
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
