package Scene.Interfaces;

import Scene.Exceptions.noGunException;

public interface CarlsonActions {
    public void fired() throws noGunException;
    public void sat();
    public void eat();
}
