package Scene.Exceptions;

import java.io.IOException;

public class noGunException extends IOException {
    public noGunException(String message){
        super(message);
    }
}