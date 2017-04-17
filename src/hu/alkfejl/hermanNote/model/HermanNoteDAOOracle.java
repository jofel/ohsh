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
import hu.alkfejl.hermanNote.model.bean.Student;
import oracle.jdbc.pool.OracleDataSource;

public class HermanNoteDAOOracle implements HermanNoteDAO {
	
	List<Student> students = new ArrayList<Student>();
	  
	public HermanNoteDAOOracle(){
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
	private static final String SQL_ADD_STUDENT =
	        "INSERT INTO hallgato " +
	        "(nev, pont, kb, admin, felhasznalo)" +
	        "VALUES (UPPER(?), ?, ?, ?, ?)";
	
	private static final String SQL_LIST_STUDENTS = "SELECT * FROM hallgato";

	private static final String SQL_ADD_STUDENT_ROOM = "INSERT INTO szoba (id, szobaszam) VALUES ((SELECT MAX(id) FROM hallgato), ?)";
	
	private static String SQL_SEARCH_STUDENTS = "SELECT * FROM hallgato";
	
	public boolean addStudent(Student student) {
		boolean rvSucceeded = false;
		
		Connection conn = null;
        PreparedStatement pst1 = null;
        PreparedStatement pst2 = null;
        
		if (!checkUserIdUnique(student)) {
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
			pst1 = conn.prepareStatement(SQL_ADD_STUDENT);
			
			// Az egyes parametéreket sorban kell megadni, pozíció alapján, ami
            // 1-tõl indul
            // Célszerû egy indexet inkrementálni, mivel ha az egyik paraméter
            // kiesik, akkor nem kell az utána következõeket újra számozni...
            int index = 1;
            //pst.setString(index++, student.getEha());
            pst1.setString(index++, student.getName());
            pst1.setInt(index++, student.getPoint());
            pst1.setInt(index++, student.isKb() ? 1 : 0);
            //System.out.println("Student kollbizes?"+ student.isKb());
            pst1.setInt(index++, student.isAdmin() ? 1 : 0);
            pst1.setInt(index++, student.isUser() ? 1 : 0);
            
            int rowsAffected1 = pst1.executeUpdate();
            System.out.println("rowsAffected1: "+ rowsAffected1);
            
            index = 1;
            pst2 = conn.prepareStatement(SQL_ADD_STUDENT_ROOM);
            pst2.setInt(index++, student.getRoom());
            
            
            
            
            
            
            // Az ExecuteUpdate paranccsal végrehajtjuk az utasítást
            // Az executeUpdate visszaadja, hogy hány sort érintett az SQL ha 
            // DML-t hajtunk végre (DDL esetén 0-t ad vissza)
            int rowsAffected2 = pst2.executeUpdate();
            
			
         // csak akkor sikeres, ha valóban volt érintett sor
            if (rowsAffected1 == 1 && rowsAffected2==1) {
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
                if (pst1 != null) {
                    pst1.close();
                } else if (pst2 != null){
                	pst2.close();
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
	public List<Student> getStudents() {
		Connection conn = null;
	    Statement st = null;
	    
	    // Töröljük a memóriából a usereket (azért tartjuk bennt, mert
        // lehetnek késõbb olyan mûveletek, melyekhez nem kell frissíteni)
	    students.clear();
	    
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
			ResultSet rs = st.executeQuery( SQL_LIST_STUDENTS );
			System.out.println( SQL_LIST_STUDENTS );
			
			// Bejárjuk a visszakapott ResultSet-et (ami a usereket tartalmazza)
			while (rs.next()) {
				// új Usert hozunk létre
				Student student = new Student();
				
				// A user nevét a ResultSet aktuális sorából olvassuk (name column)
				
                student.setId(0);
                student.setName(rs.getString("nev"));
                student.setPoint(Integer.parseInt(rs.getString("pont")));
                student.setKb(rs.getInt("kb")==1?true:false);
                student.setAdmin(rs.getInt("admin")==1?true:false);
                student.setUser(rs.getInt("felhasznalo")==1?true:false);
                
                students.add(student);
                
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
		
		return students;
	}
	
	/**
     *
     */
	@Override
	public List<Student> searchStudent(Student s) {
		Connection conn = null;
	    Statement st = null;
	    
	    // Töröljük a memóriából a usereket (azért tartjuk bennt, mert
        // lehetnek késõbb olyan mûveletek, melyekhez nem kell frissíteni)
	    students.clear();
	    
		try {
			// Az adatbázis kapcsolatunkat a OracleDataSource segítségével hozzuk létre
            OracleDataSource ods = new OracleDataSource();
	  		ods.setURL("jdbc:oracle:thin:@localhost:1521:xe");
	  		
	  		// Megadjuk, hogy a ODBC milyen driveren keresztul milyen fájlt keressen
			conn = ods.getConnection("SYSTEM","SYS");
			
			// A kapcsolat (conn) objektumtól kérünk egy egyszerû (nem
            // paraméterezhetõ) utasítást
			st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String SQL_SEARCH_STUDENTS = "SELECT * FROM hallgato, szoba ";
			//System.out.println(s.getEha());
			System.out.println(s.getName());
			boolean sqlBegin = true;
//			if (!s.getEha().equals("")){
//				SQL_SEARCH_STUDENTS += "WHERE eha LIKE '" + s.getEha() + "' ";
//				sqlBegin = false;
//			} 
			if (!s.getName().equals("")){
				
				SQL_SEARCH_STUDENTS += "WHERE hallgato.id = szoba.id AND REGEXP_LIKE(nev, '^" + s.getName() + "(*)')";
				sqlBegin = false;
			} 
			if (s.getRoom()!=0){
				if (sqlBegin == false){
					SQL_SEARCH_STUDENTS += "AND ";
				} else {
					SQL_SEARCH_STUDENTS += "WHERE ";
					sqlBegin = false;
				}
				//regex pl: ^9(*) -> az összes 9-el kezdõdõ szobát adja.
				SQL_SEARCH_STUDENTS += "hallgato.id = szoba.id AND REGEXP_LIKE (szobaszam, '^" + s.getRoom() + "(*)')";
			} 
			if (s.isKb() == true){
				if (sqlBegin == false){
					SQL_SEARCH_STUDENTS += "AND ";
				} else {
					SQL_SEARCH_STUDENTS += "WHERE ";
					sqlBegin = false;
				}
				SQL_SEARCH_STUDENTS += "kb = 1 ";
			} 
			if (s.isAdmin() == true){
				if (sqlBegin == false){
					SQL_SEARCH_STUDENTS += "AND ";
				} else {
					SQL_SEARCH_STUDENTS += "WHERE ";
					sqlBegin = false;
				}
				SQL_SEARCH_STUDENTS += "admin = 1 ";
			} 
			if (s.isUser() == true){
				if (sqlBegin == false){
					SQL_SEARCH_STUDENTS += "AND ";
				} else {
					SQL_SEARCH_STUDENTS += "WHERE ";
					sqlBegin = false;
				}
				SQL_SEARCH_STUDENTS += "felhasznalo = 1 ";
			} 
			// Az utasítás objektumon keresztül indítunk egy query-t
            // Az eredményeket egy ResultSet objektumban kapjuk vissza
			System.out.println( SQL_SEARCH_STUDENTS );
			ResultSet rs = st.executeQuery( SQL_SEARCH_STUDENTS );
			System.out.println( "RS? ");
			System.out.println( "RS " + rs );
			
			// Bejárjuk a visszakapott ResultSet-et (ami a usereket tartalmazza)
			while (rs.next()) {
				// új Usert hozunk létre
				Student student = new Student();
				
				// A user nevét a ResultSet aktuális sorából olvassuk (name column)
				
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("nev"));
                student.setPoint(Integer.parseInt(rs.getString("pont")));
                student.setKb(rs.getInt("kb")==1?true:false);
                student.setAdmin(rs.getInt("admin")==1?true:false);
                student.setUser(rs.getInt("felhasznalo")==1?true:false);
                
                students.add(student);
                System.out.println( rs + ".Student ");
                
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
		
		return students;
	}
	
	@Override
	public boolean removeStudent(Student s) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
     * Ellenõrzi a {@link #customers} integritását, a {@link Customer#getName()}
     * egyedi kell legyen.
     *
     * @param newCustomer Az újonnan felveendõ {@link Customer}.
     * @return True, ha a név egyedi, false egyébként.
     */
    private boolean checkUserIdUnique(Student newStudent) {
        boolean rvIsValid = true;

        for (Student student : students) {
            if (student.getId()==(newStudent.getId())) {
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
