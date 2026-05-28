package model.user;

public class Admin extends User{
    public Admin(String name, String password) {
        super(name, password);
    }

    @Override
    protected void showMenu() {

    }
}
