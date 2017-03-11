package hu.alkfejl.hermanNote.model.bean;

/**
 * Az osztály egy vásárlót/ügyfelet ír le a JavaBean konvenciók betartásával:
 * Jobb klikk+source+generate getters/setters.
 * Meghívasa az összes nem statikus, nem transient attribútumra.
 */
public class User {

    private String name;
    private String eha;
    private int room;
    private boolean kb;
    private boolean admin;
    
    

    public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getEha() {
		return eha;
	}



	public void setEha(String eha) {
		this.eha = eha;
	}



	public int getRoom() {
		return room;
	}



	public void setRoom(int room) {
		this.room = room;
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


	@Override
	public String toString() {
		return "User [name=" + name + ", eha=" + eha + ", room=" + room + ", kb=" + kb + ", admin=" + admin + "]";
	}

}
