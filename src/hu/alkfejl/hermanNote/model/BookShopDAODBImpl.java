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
 * Ez az oszt�ly az adatel�r�st szolg�lja. T�nyleges perzisztens
 * t�rol�t, adatb�zist haszn�l.
 */
public class BookShopDAODBImpl implements HermanNoteDAO {

    List<Customer> customers = new ArrayList<Customer>();

    // Adatb�zis f�jlt reprezent�l� string, melyet a
    // BookShopDB k�rnyezeti v�ltoz�b�l olvasunk ki (env.bat �ll�tja be)
    private static final String DATABASE_FILE = System.getenv("BookShopDB");

    // SQL param�terezhet� INSERT utas�t�s Customer felv�tel�re
    // Az egyes param�tereket ut�lagosan �ll�thatjuk be (PreparedStatement)
    private static final String SQL_ADD_CUSTOMER =
        "INSERT INTO Customer " +
        "(name, age, female, rented, student, grantee, qualification) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?)";

    // SQL lek�rdez�s, a Customerek lek�rdez�s�hez
    private static final String SQL_LIST_CUSTOMERS = "SELECT * FROM Customer";

    // A konstruktorban inicializ�ljuk az adatb�zist
    public BookShopDAODBImpl() {
        try {
            // Betoltjuk az SQLite JDBC drivert, ennek seg�ts�g�vel �rj�k majd
            // el az SQLite adatb�zist
            // kulso/java/sqlitejdbc-v054.jar - a classpath-ba is beker�lt
            // build.xml - javac taskn�l classpath attrib�tum (n�zz�k meg)
            // Valamint ezt megadtuk a disztrib�ci� futatt�s�n�l is a
            // run.bat-ban! (n�zz�k meg)
        	
            Class.forName("org.sqlite.JDBC");
            //Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to load SQLite JDBC driver.");
            e.printStackTrace();
        }
    }

    /**
     * Hozz�ad egy {@link Customer}-t az adatt�rhoz.
     *
     * @param customer A t�roland� {@link Customer}.
     * @return Igaz, ha sikeresen t�rolva, hamis, egy�bk�nt.
     */
    @Override
    public boolean addCustomer(Customer customer) {
        boolean rvSucceeded = false;

        // Adatb�zis kapcsolatot reprezent�l� objektum
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            // Az adatb�zis kapcsolatunkat a DriverManager seg�ts�g�vel hozzuk l�tre
            // Megadjuk hogy a JDBC milyen driveren kereszt�l milyen f�jlt keressen
            conn = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_FILE);
            //conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM", "SYS");
           

            // �j Customer felv�tele eset�n egy PreparedStatement objektumot
            // k�r�nk a kapcsolat objektumt�l
            // Ez egy param�terezhet� SQL utasit�st v�r, a param�terek ?-k�nt
            // jelennek meg
            pst = conn.prepareStatement(SQL_ADD_CUSTOMER);

            // Az egyes paramet�reket sorban kell megadni, poz�ci� alapj�n, ami
            // 1-t�l indul
            // C�lszer� egy indexet inkrement�lni, mivel ha az egyik param�ter
            // kiesik, akkor nem kell az ut�na k�vetkez�eket �jra sz�mozni...
            int index = 1;
            pst.setString(index++, customer.getName());
            pst.setInt(index++, customer.getAge());
            pst.setInt(index++, customer.isFemale() ? 1 : 0);
            pst.setInt(index++, customer.isRented() ? 1 : 0);
            pst.setInt(index++, customer.isStudent() ? 1 : 0);
            pst.setInt(index++, customer.isGrantee() ? 1 : 0);
            pst.setString(index++, customer.getQualification());

            // Az ExecuteUpdate paranccsal v�grehajtjuk az utas�t�st
            // Az executeUpdate visszaadja, hogy h�ny sort �rintett az SQL ha 
            // DML-t hajtunk v�gre (DDL eset�n 0-t ad vissza)
            int rowsAffected = pst.executeUpdate();

            // csak akkor sikeres, ha val�ban volt �rintett sor
            if (rowsAffected == 1) {
                rvSucceeded = true;
            }
        } catch (SQLException e) {
            System.out.println("Failed to execute adding customer.");
            e.printStackTrace();
        } finally {
            // NAGYON FONTOS!
            // Minden adatb�zis objektumot le kell z�rni, mivel ha ezt nem
            // tessz�k meg, akkor el�fordulhat, hogy nyitott kapcsolatok
            // maradnak az adatb�zis fel�. Az adatb�zis pedig korl�tozott
            // sz�mban tart fenn kapcsolatokat, ez�rt egy id� ut�n akar ez be is
            // telhet!
            // Minden egyes objektumot k�l�n try-catch �gban kell megpr�b�lni
            // bez�rni!
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
     * Visszaadja a t�rolt {@link Customer} p�ld�nyokat.
     *
     * @return A t�rolt {@link Customer}-ek list�ja.
     */
    public List<Customer> getCustomers(){
        Connection conn = null;
        Statement st = null;

        // T�r�lj�k a mem�ri�b�l a customereket (az�rt tartjuk bennt, mert
        // lehetnek k�s�bb olyan m�veletek, melyekhez nem kell friss�teni)
        customers.clear();

        try {
            // Az adatb�zis kapcsolatunkat a DriverManager seg�ts�g�vel hozzuk l�tre
            // Megadjuk, hogy a JDBC milyen driveren keresztul milyen f�jlt keressen
            conn = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_FILE);

            // A kapcsolat (conn) objektumt�l k�r�nk egy egyszer� (nem
            // param�terezhet�) utas�t�st
            st = conn.createStatement();

            // Az utas�t�s objektumon kereszt�l ind�tunk egy query-t
            // Az eredm�nyeket egy ResultSet objektumban kapjuk vissza
            ResultSet rs = st.executeQuery(SQL_LIST_CUSTOMERS);

            // Bej�rjuk a visszakapott ResultSet-et (ami a customereket tartalmazza)
            while (rs.next()) {
                // �j Customert hozunk l�tre
                Customer customer = new Customer();

                // A customer nev�t a ResultSet aktu�lis sor�b�l olvassuk (name column)
                customer.setName(rs.getString("name"));

                // A customer kor�t a ResultSet aktu�lis sor�b�l olvassuk (age column)
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
     * Hozz�ad egy {@link Book}-ot az adatt�rhoz.
     *
     * @param book A t�roland� {@link Book}.
     * @return Igaz, ha sikeresen t�rolva, hamis, egy�bk�nt.
     */
    public boolean addBook(Book book) {
        // TODO implement
        return false;
    }

    /**
     * Visszaadja a t�rolt {@link Book} p�ld�nyokat.
     *
     * @return A t�rolt {@link Book}-ek list�ja.
     */
    public List<Book> getBooks() {
        // TODO implement
        return new ArrayList<Book>();
    }

    /**
     * Hozz�ad egy {@link Purchase}-et az adatt�rhoz.
     *
     * @param purchase A t�roland� {@link Purchase}.
     * @return Igaz, ha sikeresen t�rolva, hamis, egy�bk�nt.
     */
    @Override
    public boolean addPurchase(Purchase purchase) {
        // TODO implement
        return false;
    }

    /**
     * Visszaadja a t�rolt {@link Purchase} p�ld�nyokat.
     *
     * @return A t�rolt {@link Purchase}-ek list�ja.
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
