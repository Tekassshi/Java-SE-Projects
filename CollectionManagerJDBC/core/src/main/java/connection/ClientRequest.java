package connection;

import java.io.Serializable;

public class ClientRequest implements Serializable {
    private String username;
    private String password;
    private Object obj;

    public ClientRequest(Object obj) {
        this.obj = obj;
    }

    public Object getObj() {
        return obj;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
