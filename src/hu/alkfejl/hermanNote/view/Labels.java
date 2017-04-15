package hu.alkfejl.hermanNote.view;

/**
 * Ez az oszt�ly tartalmazza a programban el�fordul� {@link String} konstansokat.
 * Csak itt fordulhat el� {@link String} konstans!
 * <p>
 * �ltal�noss�gban elfogadott �s szok�s, hogy a k�d nem tartalmaz olyan
 * {@link String} konstansokat, melyek a felhaszn�l�i fel�leten jelennek meg.
 * Ezeket mindig egy k�z�s helyre gy�jtj�k, �gy k�s�bb lokaliz�lhat� az alkalmaz�s
 * �s eleve �tl�that�bb �gy.
 * </p>
 */
public class Labels {

    // General labels
    public final static String name = "N�v";
    public final static String ok = "OK";
    public final static String cancel = "M�gse";
    public final static String error = "Hiba";
    public final static String empty = "";
    public final static String unknown = "Unknown";
    public final static String search = "Keres�s";
    public final static String edit = "Szerkeszt�s";
    public final static String delete = "T�rl�s";
    public final static String save = "Ment�s";
    public final static String print = "Nyomtat�s";

    // Main window
    public static final String main_window_title_format = "Herman Note (%1$s)";
    public static final String main_window_title_unknown_user = "ismeretlen";

    // Student labels
    public final static String students = "Hallgat�k";
    public final static String add_student = "Hallgat� hozz�ad�sa";
    public final static String list_students = "Hallgat�k list�ja";
    public final static String students_pane = "Hallgat�k...";
    public final static String student_eha = "EHA";
    public final static String student_name = "N�v";
    public final static String student_point = "Pontok";
    public final static String student_kb = "KB";
    public final static String student_admin = "Admin";
    public final static String student_user = "Felhaszn�l�";
    public static final String student_room = "Szoba";
    
    
    
    
    // Customer labels
    public final static String customer = "Customer";
    public final static String add_customer = "Add customer";
    public final static String list_customers = "List customers";
    public final static String gender = "Gender";
    public final static String female = "Female";
    public final static String male = "Male";
    public final static String age = "Age";
    public final static String grantee = "Grantee";
    public final static String university = "University";
    public final static String college = "College";
    public final static String high_school = "High school";
    public final static String elementary_school = "Elementary school";
    public final static String[] qualifications = {
        Labels.university,
        Labels.college,
        Labels.high_school,
        Labels.elementary_school
    };
    public final static String qualification = "Qualification";
    public final static String student = "Student";
    public final static String rented = "Rented";
    public final static String student_name_is_required = "Hallgat� nev�t ki kell t�lteni!";
    public final static String student_exists = "Hallgat� felv�tele sikertelen";

    // Book labels
    public final static String book = "Book";
    public final static String buy_book = "Buy book";
    public final static String list_books = "List books";
    public final static String author = "Author";
    public final static String title = "Title";
    public final static String year = "Year";
    public final static String category = "Category";
    public final static String price = "Price";
    public final static String piece = "Piece";
    public final static String sci_fi = "sci-fi";
    public final static String horror = "horror";
    public final static String drama = "Dr�ma";
    public final static String[] categories = new String[] {
        Labels.sci_fi,
        Labels.horror,
        Labels.drama
    };
    public final static String ancient = "Ancient";
    public final static String book_title_is_required = "Book title is required!";
    public final static String book_exists = "Such book is already bought!";

    // Sell labels
    public final static String sell = "Sell";
    public final static String sell_book = "Sell book";
    public final static String list_sold_books = "List sold books";
    public final static String purchase_choose_book_and_customer =
            "V�lassz ki k�nyvet �s v�s�rl�t!";
    public final static String purchase_failed =
            "Nem siker�lt r�gz�teni a v�s�rl�st!";
	
}
