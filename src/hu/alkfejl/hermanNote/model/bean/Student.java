package hu.alkfejl.hermanNote.model.bean;

/**
 * Az osztály egy vásárlót/ügyfelet ír le a JavaBean konvenciók betartásával:
 * Jobb klikk+source+generate getters/setters.
 * Meghívasa az összes nem statikus, nem transient attribútumra.
 */
public class Student {

    
    private String eha;
    private String name;
    private int point = 0;
    private boolean kb;
    private boolean admin;
    private boolean user;
    private int room = 0;
    
    
	public int getRoom() {
		return room;
	}
	public void setRoom(int room) {
		this.room = room;
	}
	public String getEha() {
		return eha;
	}
	public void setEha(String eha) {
		this.eha = eha;
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
	@Override
	public String toString() {
		return "Student [eha=" + eha + ", name=" + name + ", point=" + point + ", kb=" + kb + ", admin=" + admin
				+ ", felhasznalo=" + user + "]";
	}
    


}
