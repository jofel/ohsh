package hu.alkfejl.hermanNote.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import hu.alkfejl.hermanNote.model.bean.Book;
import hu.alkfejl.hermanNote.model.bean.Customer;
import hu.alkfejl.hermanNote.model.bean.Purchase;
import hu.alkfejl.hermanNote.model.bean.User;
import oracle.jdbc.pool.OracleDataSource;

public class BookShopDAOOracle implements BookShopDAO {
	
	List<User> users = new ArrayList<User>();
	  
	public BookShopDAOOracle(){
		 try {
			  
			  /* Connect to the Oracle Database and using the "HR" user's schema */
			  //OracleDataSource ods = new OracleDataSource();
	  		  Class.forName ("oracle.jdbc.OracleDriver");

	  		  //ods.setURL("jdbc:oracle:thin:@localhost:1521:xe");
			  //Connection conn = ods.getConnection("SYSTEM","SYS");
			  //stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		  } catch ( Exception ex ) {
			  System.out.println("Failed to load ODBC driver.");
			  ex.printStackTrace();
		  }
	}
	private static final String SQL_ADD_USER =
	        "INSERT INTO users " +
	        "(name, eha, room, kb, admin)" +
	        "VALUES (?, ?, ?, ?, ?)";
	
	private static final String SQL_LIST_USERS = "SELECT * FROM users";
	
	@Override
	public boolean addUser(User user) {
		boolean rvSucceeded = false;
		
		Connection conn = null;
        PreparedStatement pst = null;
        
		if (!checkUserNameUnique(user)) {
            return false;
        }
		
		try {
			//////////////////////////////////
			// Az adatbázis kapcsolatunkat a OracleDataSource segítségével hozzuk létre
            OracleDataSource ods = new OracleDataSource();
	  		ods.setURL("jdbc:oracle:thin:@localhost:1521:xe");
	  		
	  		// Megadjuk, hogy a ODBC milyen driveren keresztul milyen fájlt keressen
			conn = ods.getConnection("SYSTEM","SYS");
			
			// A kapcsolat (conn) objektumtól kérünk egy egyszerû (nem
            // paraméterezhetõ) utasítást
			pst = conn.prepareStatement(SQL_ADD_USER);
			
			// Az egyes parametéreket sorban kell megadni, pozíció alapján, ami
            // 1-tõl indul
            // Célszerû egy indexet inkrementálni, mivel ha az egyik paraméter
            // kiesik, akkor nem kell az utána következõeket újra számozni...
            int index = 1;
            pst.setString(index++, user.getName());
            pst.setString(index++, user.getEha());
            pst.setInt(index++, user.getRoom());
            pst.setInt(index++, user.isKb() ? 1 : 0);
            pst.setInt(index++, user.isAdmin() ? 1 : 0);
            
            // Az ExecuteUpdate paranccsal végrehajtjuk az utasítást
            // Az executeUpdate visszaadja, hogy hány sort érintett az SQL ha 
            // DML-t hajtunk végre (DDL esetén 0-t ad vissza)
            int rowsAffected = pst.executeUpdate();
			
         // csak akkor sikeres, ha valóban volt érintett sor
            if (rowsAffected == 1) {
                rvSucceeded = true;
            }
        } catch (SQLException e) {
            System.out.println("Failed to execute adding user.");
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
                System.out.println("Failed to close statement when adding user.");
                e.printStackTrace();
            }

            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Failed to close connection when adding user.");
                e.printStackTrace();
            }
        }

		// eltároljuk memóriába
		//boolean isStored = users.add(user);
		//System.out.println("Memóriába mentve az új felhasználó.");
		
		return rvSucceeded;
	}

	/**
     * Visszaadja a tárolt {@link Customer} példányokat.
     *
     * @return A tárolt {@link Customer}-ek listája.
     */
	public List<User> getUsers() {
		Connection conn = null;
	    Statement st = null;
	    
	    // Töröljük a memóriából a usereket (azért tartjuk bennt, mert
        // lehetnek késõbb olyan mûveletek, melyekhez nem kell frissíteni)
	    users.clear();
	    
		try {
			// Az adatbázis kapcsolatunkat a OracleDataSource segítségével hozzuk létre
            OracleDataSource ods = new OracleDataSource();
	  		ods.setURL("jdbc:oracle:thin:@localhost:1521:xe");
	  		
	  		// Megadjuk, hogy a ODBC milyen driveren keresztul milyen fájlt keressen
			conn = ods.getConnection("SYSTEM","SYS");
			
			// A kapcsolat (conn) objektumtól kérünk egy egyszerû (nem
            // paraméterezhetõ) utasítást
			st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			
			// Az utasítás objektumon keresztül indítunk egy query-t
            // Az eredményeket egy ResultSet objektumban kapjuk vissza
			ResultSet rs = st.executeQuery( SQL_LIST_USERS );
			System.out.println( SQL_LIST_USERS );
			
			// Bejárjuk a visszakapott ResultSet-et (ami a usereket tartalmazza)
			while (rs.next()) {
				// új Usert hozunk létre
				User user = new User();
				
				// A user nevét a ResultSet aktuális sorából olvassuk (name column)
                user.setName(rs.getString("name"));
                user.setEha(rs.getString("eha"));
                user.setRoom(Integer.parseInt(rs.getString("room")));
                user.setKb(rs.getInt("kb")==1?true:false);
                user.setAdmin(rs.getInt("admin")==1?true:false);
                
                users.add(user);
                
			}
		}catch ( SQLException ex ) {
			System.out.println("Failed to execute listing users.");
			ex.printStackTrace();
		} finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                System.out.println("Failed to close statement when listing users.");
                e.printStackTrace();
            }

            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Failed to close connection when listing users.");
                e.printStackTrace();
            }
        }
		
		return users;
	}
	
	/**
     * Ellenõrzi a {@link #customers} integritását, a {@link Customer#getName()}
     * egyedi kell legyen.
     *
     * @param newCustomer Az újonnan felveendõ {@link Customer}.
     * @return True, ha a név egyedi, false egyébként.
     */
    private boolean checkUserNameUnique(User newUser) {
        boolean rvIsValid = true;

        for (User user : users) {
            if (user.getName().equals(newUser.getName())) {
                rvIsValid = false;
                break;
            }
        }

        return rvIsValid;
    }
    
	@Override
	public boolean addCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Customer> getCustomers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addBook(Book book) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Book> getBooks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addPurchase(Purchase purchase) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Purchase> getPurchases() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
