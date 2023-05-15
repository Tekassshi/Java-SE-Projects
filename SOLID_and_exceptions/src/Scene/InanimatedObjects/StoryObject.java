package Scene.InanimatedObjects;

import Scene.Scene;

public abstract class StoryObject {
    private final Scene scene;

    public StoryObject(Scene scene){
        this.scene = scene;
    }

    public Scene getScene(){
        return scene;
    }
}