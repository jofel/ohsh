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
	        "(nev, szoba, pont, kb, felhasznalo, admin)" +
	        "VALUES (UPPER(?),?, ?, ?, ?, ?)";
	
	private static final String SQL_LIST_STUDENTS = "SELECT nev, szoba, pont, kb, felhasznalo, admin FROM hallgato";

	private static final String SQL_EDIT_STUDENT = "update hallgato set nev = '?' where id = ?";

	private static final String SQL_REMOVE_STUDENT = "DELETE FROM hallgato WHERE id=?";
	//UPDATE hallgato INNER JOIN szoba ON hallgato.id=szoba.id SET nev = ?, pont = ?, kb = ?, admin = ?, felhasznalo = ?, szobaszam = ? WHERE hallgato.id = ?";
	
	private static String SQL_SEARCH_STUDENTS = "SELECT * FROM hallgato";
	
	public boolean addStudent(Student student) {
		boolean rvSucceeded = false;
		
		Connection conn = null;
        PreparedStatement pst = null;
        
		if (!checkUserIdUnique(student)) {
            return false;
        }
		
		try {
			//////////////////////////////////
			// Az adatb�zis kapcsolatunkat a OracleDataSource seg�ts�g�vel hozzuk l�tre
            OracleDataSource ods = new OracleDataSource();
	  		ods.setURL("jdbc:oracle:thin:@localhost:1521:xe");
	  		
	  		// Megadjuk, hogy a ODBC milyen driveren keresztul milyen f�jlt keressen
			conn = ods.getConnection("SYSTEM","SYS");
			
			// A kapcsolat (conn) objektumt�l k�r�nk egy egyszer� (nem
            // param�terezhet�) utas�t�st
			pst = conn.prepareStatement(SQL_ADD_STUDENT);
			
			// Az egyes paramet�reket sorban kell megadni, poz�ci� alapj�n, ami
            // 1-t�l indul
            // C�lszer� egy indexet inkrement�lni, mivel ha az egyik param�ter
            // kiesik, akkor nem kell az ut�na k�vetkez�eket �jra sz�mozni...
            int index = 1;
            pst.setString(index++, student.getName());
            pst.setInt(index++, student.getRoom());
            pst.setInt(index++, student.getPoint());
            pst.setInt(index++, student.isKb() ? 1 : 0);
            pst.setInt(index++, student.isUser() ? 1 : 0);
            pst.setInt(index++, student.isAdmin() ? 1 : 0);
            
            int rowsAffected = pst.executeUpdate();
            System.out.println("rowsAffected1: "+ rowsAffected);
            
            			
         // csak akkor sikeres, ha val�ban volt �rintett sor
            if (rowsAffected == 1) {
                rvSucceeded = true;
            }
        } catch (SQLException e) {
            System.out.println("Failed to execute adding user.");
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

		// elt�roljuk mem�ri�ba
		//boolean isStored = users.add(user);
		//System.out.println("Mem�ri�ba mentve az �j felhaszn�l�.");
		
		return rvSucceeded;
	}
	
	public boolean removeStudent(Student student) {
		boolean rvSucceeded = false;
		
		Connection conn = null;
        PreparedStatement pst = null;
        
			
		try {
			//////////////////////////////////
			// Az adatb�zis kapcsolatunkat a OracleDataSource seg�ts�g�vel hozzuk l�tre
            OracleDataSource ods = new OracleDataSource();
	  		ods.setURL("jdbc:oracle:thin:@localhost:1521:xe");
	  		
	  		// Megadjuk, hogy a ODBC milyen driveren keresztul milyen f�jlt keressen
			conn = ods.getConnection("SYSTEM","SYS");
			
			// A kapcsolat (conn) objektumt�l k�r�nk egy egyszer� (nem
            // param�terezhet�) utas�t�st
			pst = conn.prepareStatement(SQL_REMOVE_STUDENT);
			
			// Az egyes paramet�reket sorban kell megadni, poz�ci� alapj�n, ami
            // 1-t�l indul
            // C�lszer� egy indexet inkrement�lni, mivel ha az egyik param�ter
            // kiesik, akkor nem kell az ut�na k�vetkez�eket �jra sz�mozni...
            int index = 1;
            pst.setInt(index++, student.getId());
            
            int rowsAffected = pst.executeUpdate();
            System.out.println("rowsAffected1: "+ rowsAffected);
            
            			
         // csak akkor sikeres, ha val�ban volt �rintett sor
            if (rowsAffected == 1) {
                rvSucceeded = true;
            }
        } catch (SQLException e) {
            System.out.println("Failed to execute adding user.");
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

		// elt�roljuk mem�ri�ba
		//boolean isStored = users.add(user);
		//System.out.println("Mem�ri�ba mentve az �j felhaszn�l�.");
		
		return rvSucceeded;
	}

	/**
     * Visszaadja a t�rolt {@link Customer} p�ld�nyokat.
     *
     * @return A t�rolt {@link Customer}-ek list�ja.
     */
	public List<Student> getStudents() {
		Connection conn = null;
	    Statement st = null;
	    
	    // T�r�lj�k a mem�ri�b�l a usereket (az�rt tartjuk bennt, mert
        // lehetnek k�s�bb olyan m�veletek, melyekhez nem kell friss�teni)
	    students.clear();
	    
		try {
			// Az adatb�zis kapcsolatunkat a OracleDataSource seg�ts�g�vel hozzuk l�tre
            OracleDataSource ods = new OracleDataSource();
	  		ods.setURL("jdbc:oracle:thin:@localhost:1521:xe");
	  		
	  		// Megadjuk, hogy a ODBC milyen driveren keresztul milyen f�jlt keressen
			conn = ods.getConnection("SYSTEM","SYS");
			
			// A kapcsolat (conn) objektumt�l k�r�nk egy egyszer� (nem
            // param�terezhet�) utas�t�st
			st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			
			// Az utas�t�s objektumon kereszt�l ind�tunk egy query-t
            // Az eredm�nyeket egy ResultSet objektumban kapjuk vissza
			ResultSet rs = st.executeQuery( SQL_LIST_STUDENTS );
			System.out.println( SQL_LIST_STUDENTS );
			
			// Bej�rjuk a visszakapott ResultSet-et (ami a usereket tartalmazza)
			while (rs.next()) {
				// �j Usert hozunk l�tre
				Student student = new Student();
				
				// A user nev�t a ResultSet aktu�lis sor�b�l olvassuk (name column)
				
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
	    
	    // T�r�lj�k a mem�ri�b�l a usereket (az�rt tartjuk bennt, mert
        // lehetnek k�s�bb olyan m�veletek, melyekhez nem kell friss�teni)
	    students.clear();
	    
		try {
			// Az adatb�zis kapcsolatunkat a OracleDataSource seg�ts�g�vel hozzuk l�tre
            OracleDataSource ods = new OracleDataSource();
	  		ods.setURL("jdbc:oracle:thin:@localhost:1521:xe");
	  		
	  		// Megadjuk, hogy a ODBC milyen driveren keresztul milyen f�jlt keressen
			conn = ods.getConnection("SYSTEM","SYS");
			
			// A kapcsolat (conn) objektumt�l k�r�nk egy egyszer� (nem
            // param�terezhet�) utas�t�st
			st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String SQL_SEARCH_STUDENTS = "SELECT * FROM hallgato ";
			//System.out.println(s.getEha());
			System.out.println(s.getName());
			boolean sqlBegin = true;
//			if (!s.getEha().equals("")){
//				SQL_SEARCH_STUDENTS += "WHERE eha LIKE '" + s.getEha() + "' ";
//				sqlBegin = false;
//			} 
			if (!s.getName().equals("")){
				
				SQL_SEARCH_STUDENTS += "WHERE REGEXP_LIKE(nev, '^" + s.getName() + "(*)')";
				sqlBegin = false;
			} 
			if (s.getRoom()!=0){
				if (sqlBegin == false){
					SQL_SEARCH_STUDENTS += "AND ";
				} else {
					SQL_SEARCH_STUDENTS += "WHERE ";
					sqlBegin = false;
				}
				//regex pl: ^9(*) -> az �sszes 9-el kezd�d� szob�t adja.
				SQL_SEARCH_STUDENTS += "REGEXP_LIKE (szoba, '^" + s.getRoom() + "(*)')";
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
			// Az utas�t�s objektumon kereszt�l ind�tunk egy query-t
            // Az eredm�nyeket egy ResultSet objektumban kapjuk vissza
			System.out.println( SQL_SEARCH_STUDENTS );
			ResultSet rs = st.executeQuery( SQL_SEARCH_STUDENTS );
			
			// Bej�rjuk a visszakapott ResultSet-et (ami a usereket tartalmazza)
			while (rs.next()) {
				// �j Usert hozunk l�tre
				Student student = new Student();
				
				// A user nev�t a ResultSet aktu�lis sor�b�l olvassuk (name column)
				
                student.setId(rs.getInt("id"));
                student.setName(rs.getString("nev"));
                student.setRoom(Integer.parseInt(rs.getString("szoba")));
                student.setPoint(Integer.parseInt(rs.getString("pont")));
                student.setKb(rs.getInt("kb")==1?true:false);
                student.setUser(rs.getInt("felhasznalo")==1?true:false);
                student.setAdmin(rs.getInt("admin")==1?true:false);
                student.setPassword(rs.getString("jelszo"));
                
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
	public boolean editStudent(Student student) {
		boolean rvSucceeded = false;
		System.out.println("editStudent");
		Connection conn = null;
        PreparedStatement pst = null;
        
			
		try {
			//////////////////////////////////
			// Az adatb�zis kapcsolatunkat a OracleDataSource seg�ts�g�vel hozzuk l�tre
            OracleDataSource ods = new OracleDataSource();
	  		ods.setURL("jdbc:oracle:thin:@localhost:1521:xe");
	  		
	  		// Megadjuk, hogy a ODBC milyen driveren keresztul milyen f�jlt keressen
			conn = ods.getConnection("SYSTEM","SYS");
			
			// A kapcsolat (conn) objektumt�l k�r�nk egy egyszer� (nem
            // param�terezhet�) utas�t�st
			pst = conn.prepareStatement(SQL_EDIT_STUDENT);
			
			// Az egyes paramet�reket sorban kell megadni, poz�ci� alapj�n, ami
            // 1-t�l indul
            // C�lszer� egy indexet inkrement�lni, mivel ha az egyik param�ter
            // kiesik, akkor nem kell az ut�na k�vetkez�eket �jra sz�mozni...
            int index = 1;
            pst.setString(index++, student.getName());
            String idString = student.getId()+"";
            pst.setString(index++,idString );
            
            //pst.setInt(index++, student.getPoint());
            //pst.setInt(index++, student.isKb() ? 1 : 0);
            //pst.setInt(index++, student.isAdmin() ? 1 : 0);
            //pst.setInt(index++, student.isUser() ? 1 : 0);
            
            int rowsAffected = pst.executeUpdate();
            System.out.println("rowsAffected: "+ rowsAffected);
            
            
         // csak akkor sikeres, ha val�ban volt �rintett sor
            if (rowsAffected == 1) {
                rvSucceeded = true;
            }
        } catch (SQLException e) {
            System.out.println("Failed to execute adding user.");
            e.printStackTrace();
        } finally {
            try {
                if (pst!= null) {
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
		// elt�roljuk mem�ri�ba
		return rvSucceeded;
	}
	
		
	/**
     * Ellen�rzi a {@link #customers} integrit�s�t, a {@link Customer#getName()}
     * egyedi kell legyen.
     *
     * @param newCustomer Az �jonnan felveend� {@link Customer}.
     * @return True, ha a n�v egyedi, false egy�bk�nt.
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
