package hu.alkfejl.hermanNote.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import hu.alkfejl.hermanNote.model.bean.Book;
import hu.alkfejl.hermanNote.model.bean.Customer;
import hu.alkfejl.hermanNote.model.bean.Purchase;
import hu.alkfejl.hermanNote.model.bean.Student;

/**
 * Ez az osztály az adatelérést szolgálja. Tényleges perzisztens
 * tárolót, adatbázist használ.
 */
public class BookShopDAODBImpl implements HermanNoteDAO {

    List<Customer> customers = new ArrayList<Customer>();

    // Adatbázis fájlt reprezentáló string, melyet a
    // BookShopDB környezeti változóból olvasunk ki (env.bat állítja be)
    private static final String DATABASE_FILE = System.getenv("BookShopDB");

    // SQL paraméterezhetõ INSERT utasítás Customer felvételére
    // Az egyes paramétereket utólagosan állíthatjuk be (PreparedStatement)
    private static final String SQL_ADD_CUSTOMER =
        "INSERT INTO Customer " +
        "(name, age, female, rented, student, grantee, qualification) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?)";

    // SQL lekérdezés, a Customerek lekérdezéséhez
    private static final String SQL_LIST_CUSTOMERS = "SELECT * FROM Customer";

    // A konstruktorban inicializáljuk az adatbázist
    public BookShopDAODBImpl() {
        try {
            // Betoltjuk az SQLite JDBC drivert, ennek segítségével érjük majd
            // el az SQLite adatbázist
            // kulso/java/sqlitejdbc-v054.jar - a classpath-ba is bekerült
            // build.xml - javac tasknál classpath attribútum (nézzük meg)
            // Valamint ezt megadtuk a disztribúció futattásánál is a
            // run.bat-ban! (nézzük meg)
        	
            Class.forName("org.sqlite.JDBC");
            //Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load SQLite JDBC driver.");
            e.printStackTrace();
        }
    }

    /**
     * Hozzáad egy {@link Customer}-t az adattárhoz.
     *
     * @param customer A tárolandó {@link Customer}.
     * @return Igaz, ha sikeresen tárolva, hamis, egyébként.
     */
    @Override
    public boolean addCustomer(Customer customer) {
        boolean rvSucceeded = false;

        // Adatbázis kapcsolatot reprezentáló objektum
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            // Az adatbázis kapcsolatunkat a DriverManager segítségével hozzuk létre
            // Megadjuk hogy a JDBC milyen driveren keresztül milyen fájlt keressen
            conn = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_FILE);
            //conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM", "SYS");
           

            // új Customer felvétele esetén egy PreparedStatement objektumot
            // kérünk a kapcsolat objektumtól
            // Ez egy paraméterezhetõ SQL utasitást vár, a paraméterek ?-ként
            // jelennek meg
            pst = conn.prepareStatement(SQL_ADD_CUSTOMER);

            // Az egyes parametéreket sorban kell megadni, pozíció alapján, ami
            // 1-tõl indul
            // Célszerû egy indexet inkrementálni, mivel ha az egyik paraméter
            // kiesik, akkor nem kell az utána következõeket újra számozni...
            int index = 1;
            pst.setString(index++, customer.getName());
            pst.setInt(index++, customer.getAge());
            pst.setInt(index++, customer.isFemale() ? 1 : 0);
            pst.setInt(index++, customer.isRented() ? 1 : 0);
            pst.setInt(index++, customer.isStudent() ? 1 : 0);
            pst.setInt(index++, customer.isGrantee() ? 1 : 0);
            pst.setString(index++, customer.getQualification());

            // Az ExecuteUpdate paranccsal végrehajtjuk az utasítást
            // Az executeUpdate visszaadja, hogy hány sort érintett az SQL ha 
            // DML-t hajtunk végre (DDL esetén 0-t ad vissza)
            int rowsAffected = pst.executeUpdate();

            // csak akkor sikeres, ha valóban volt érintett sor
            if (rowsAffected == 1) {
                rvSucceeded = true;
            }
        } catch (SQLException e) {
            System.out.println("Failed to execute adding customer.");
            e.printStackTrace();
        } finally {
            // NAGYON FONTOS!
            // Minden adatbázis objektumot le kell zárni, mivel ha ezt nem
            // tesszük meg, akkor elõfordulhat, hogy nyitott kapcsolatok
            // maradnak az adatbázis felé. Az adatbázis pedig korlátozott
            // számban tart fenn kapcsolatokat, ezért egy idõ után akar ez be is
            // telhet!
            // Minden egyes objektumot külön try-catch ágban kell megpróbálni
            // bezárni!
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException e) {
                System.out.println("Failed to close statement when adding customer.");
                e.printStackTrace();
            }

            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Failed to close connection when adding customer.");
                e.printStackTrace();
            }
        }

        return rvSucceeded;
    }

    /**
     * Visszaadja a tárolt {@link Customer} példányokat.
     *
     * @return A tárolt {@link Customer}-ek listája.
     */
    public List<Customer> getCustomers(){
        Connection conn = null;
        Statement st = null;

        // Töröljük a memóriából a customereket (azért tartjuk bennt, mert
        // lehetnek késõbb olyan mûveletek, melyekhez nem kell frissíteni)
        customers.clear();

        try {
            // Az adatbázis kapcsolatunkat a DriverManager segítségével hozzuk létre
            // Megadjuk, hogy a JDBC milyen driveren keresztul milyen fájlt keressen
            conn = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_FILE);

            // A kapcsolat (conn) objektumtól kérünk egy egyszerû (nem
            // paraméterezhetõ) utasítást
            st = conn.createStatement();

            // Az utasítás objektumon keresztül indítunk egy query-t
            // Az eredményeket egy ResultSet objektumban kapjuk vissza
            ResultSet rs = st.executeQuery(SQL_LIST_CUSTOMERS);

            // Bejárjuk a visszakapott ResultSet-et (ami a customereket tartalmazza)
            while (rs.next()) {
                // új Customert hozunk létre
                Customer customer = new Customer();

                // A customer nevét a ResultSet aktuális sorából olvassuk (name column)
                customer.setName(rs.getString("name"));

                // A customer korát a ResultSet aktuális sorából olvassuk (age column)
                customer.setAge(rs.getInt("age"));
                customer.setFemale(rs.getInt("female") == 1);
                customer.setRented(rs.getInt("rented") == 1);
                customer.setStudent(rs.getInt("student") == 1);
                customer.setGrantee(rs.getInt("grantee") == 1);
                customer.setQualification(rs.getString("qualification"));

                customers.add(customer);
            }
        } catch (SQLException e) {
            System.out.println("Failed to execute listing customers.");
            e.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                System.out.println("Failed to close statement when listing customers.");
                e.printStackTrace();
            }

            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Failed to close connection when listing customers.");
                e.printStackTrace();
            }
        }

        return customers;
    }

    /**
     * Hozzáad egy {@link Book}-ot az adattárhoz.
     *
     * @param book A tárolandó {@link Book}.
     * @return Igaz, ha sikeresen tárolva, hamis, egyébként.
     */
    public boolean addBook(Book book) {
        // TODO implement
        return false;
    }

    /**
     * Visszaadja a tárolt {@link Book} példányokat.
     *
     * @return A tárolt {@link Book}-ek listája.
     */
    public List<Book> getBooks() {
        // TODO implement
        return new ArrayList<Book>();
    }

    /**
     * Hozzáad egy {@link Purchase}-et az adattárhoz.
     *
     * @param purchase A tárolandó {@link Purchase}.
     * @return Igaz, ha sikeresen tárolva, hamis, egyébként.
     */
    @Override
    public boolean addPurchase(Purchase purchase) {
        // TODO implement
        return false;
    }

    /**
     * Visszaadja a tárolt {@link Purchase} példányokat.
     *
     * @return A tárolt {@link Purchase}-ek listája.
     */
    @Override
    public List<Purchase> getPurchases() {
        // TODO implement
        return new ArrayList<Purchase>();
    }

	
	@Override
	public boolean addStudent(Student s) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Student> getStudents() {
		// TODO Auto-generated method stub
		return null;
	}

}
