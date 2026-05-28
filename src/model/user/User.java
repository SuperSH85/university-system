package model.user;

import model.Searchable;

public abstract class User implements Searchable {
    private String userID;
    private String name;
    private String password;
    static int idMaker = 0;

    public User(String idType ,String name , String password){
        this.userID = idType + (++idMaker);
        this.name = name;
        this.password = password;
    }
    protected abstract void showMenu();

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return this.userID;
    }

    @Override
    public boolean matches(String keyword) {
        return false;
    }
}
