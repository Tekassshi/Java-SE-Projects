package Scene.Heroes;
import Scene.Scene;

public abstract class Hero {
    private final String name;
    private final Scene scene;

    public Hero(String name, Scene scene){
        this.name = name;
        this.scene = scene;
    }

    public String getName() {
        return name;
    }

    public Scene getScene(){
        return scene;
    }

    public abstract void startAction();
    public abstract void voicesReaction();
}