package Scene.Heroes;

import Scene.Interfaces.BoyActions;
import Scene.Scene;

import java.util.Objects;

public class Boy extends Hero implements BoyActions {
    public Boy(String boy_name, Scene scene){
        super(boy_name, scene);
    }

    @Override
    public void startAction() {
        System.out.print("Но прежде чем ");
        System.out.print(super.getName());
        System.out.print(" успел ответить, ");
        super.getScene().getCarlson().startAction();
    }

    @Override
    public void getAngry() {
        System.out.println("Тут " + super.getName() + " совсем вышел из себя. ");
    }

    @Override
    public void voicesReaction() {
        getAngry();
    }

    @Override
    public boolean equals(Object o){
        if (o == this)
            return true;

        if (!(o instanceof Boy))
            return false;

        Boy tmp = (Boy) o;

        return tmp.getName().equals(this.getName()) && tmp.getScene() == this.getScene();
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.getName(), this.getScene());
    }

    @Override
    public String toString() {
        return "Boy{Scene: " + super.getScene().getName() + ", " +
                "Name: " + super.getName() + "}";
    }
}
