package MainPackage;

import java.io.Serializable;
import java.util.Objects;

public class PairOfUserAndPass implements Serializable {
    private String password;
    private String userName;

    public PairOfUserAndPass(String pass, String name) {
        password = pass;
        userName = name;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        PairOfUserAndPass that = (PairOfUserAndPass) other;
        return that.getPassword().equals(password) && that.getUserName().equals(userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password, userName);
    }
    @Override
    public String toString(){
        return "User: " + this.getUserName() + " ,Password: " + this.getPassword();
    }
}
