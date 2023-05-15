package Scene.InanimatedObjects;

import Scene.Interfaces.InanimateActions;
import Scene.Scene;

import java.util.Objects;

public class Sound extends StoryObject implements InanimateActions {
    public Sound(Scene scene){
        super(scene);
    }

    @Override
    public void generalAction() {
        System.out.println("Резкий звук прокатился по крышам и замер.");
    }

    @Override
    public void misfireAction() {
        System.out.print("Резкий звук выстрела не раздался. ");
    }

    @Override
    public boolean equals(Object o){
        if (o == this)
            return true;

        if (!(o instanceof Sound))
            return false;

        Sound tmp = (Sound) o;

        return tmp.getScene() == this.getScene();
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.getScene());
    }

    public String toString() {
        return "Sound{Scene: " + super.getScene().getName() + "}";
    }
}
