package Scene.InanimatedObjects;

import Scene.Interfaces.InanimateActions;
import Scene.Scene;

import java.util.Objects;

public class Somebody extends StoryObject implements InanimateActions {
    public Somebody(Scene scene) {
        super(scene);
    }

    @Override
    public void generalAction() {
        System.out.print("а кто то крикнул, что нужно вызывать полицию. ");
        super.getScene().getBoy().voicesReaction();
        super.getScene().getCarlson().voicesReaction();
    }

    @Override
    public void misfireAction() {
        System.out.print("люди спокойно занимались своими делами. \n");
    }

    @Override
    public boolean equals(Object o){
        if (o == this)
            return true;

        if (!(o instanceof Somebody))
            return false;

        Somebody tmp = (Somebody) o;

        return tmp.getScene() == this.getScene();
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.getScene());
    }

    @Override
    public String toString() {
        return "Somebody{Scene: " + super.getScene().getName() + "}";
    }
}