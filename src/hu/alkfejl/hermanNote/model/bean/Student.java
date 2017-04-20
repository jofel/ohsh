package hu.alkfejl.hermanNote.model.bean;

/**
 * Az osztály egy vásárlót/ügyfelet ír le a JavaBean konvenciók betartásával:
 * Jobb klikk+source+generate getters/setters.
 * Meghívasa az összes nem statikus, nem transient attribútumra.
 */
public class Student {

    
    private int id = 0;
    private String name;
    private int room = 0;
    private int point = 0;
    private boolean kb;
    private boolean user;
    private boolean admin;
    private String password = "";
    
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRoom() {
		return room;
	}
	public void setRoom(int room) {
		this.room = room;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public boolean isKb() {
		return kb;
	}
	public void setKb(boolean kb) {
		this.kb = kb;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public boolean isUser() {
		return user;
	}
	public void setUser(boolean user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "Student [id=" + id +  ", name=" + name + ", room=" + room + ", point=" + point + ", kb=" + kb 
				+ ", felhasznalo=" + user + ", admin=" + admin + "]";
	}
    


}
