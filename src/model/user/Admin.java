package model.user;

public class Admin extends User{
    private static int idMaker = 1;
    public Admin(String name, String password) {
        super("A-",name, password , idMaker++);
    }
    @Override
    protected void showMenu() {

    }
}
