package connection;

import java.io.Serializable;

public class ServerResponse implements Serializable {
    private Object obj;

    public ServerResponse(Object obj) {
        this.obj = obj;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}