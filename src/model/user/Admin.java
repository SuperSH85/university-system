package model.user;

public class Admin extends User{
    static int idMaker = 1;
    public Admin(String name, String password) {
        super("A-",name, password , idMaker);
        ++idMaker;
    }
    @Override
    protected void showMenu() {

    }
}
