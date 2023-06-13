package connection;

import java.io.Serializable;

public class ClientRequest implements Serializable {
    private String token;

    private Object obj;

    public ClientRequest(Object obj) {
        this.obj = obj;
    }

    public Object getObj() {
        return obj;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
