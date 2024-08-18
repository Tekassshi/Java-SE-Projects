package Scene;

import Scene.Enums.voicesCharacteristic;
import Scene.Heroes.*;
import Scene.InanimatedObjects.*;
import Scene.Interfaces.InanimateActions;

import java.util.Objects;

public class Scene {
    final Carlson carlson;
    final Boy boy;
    final Sound sound;
    final Voices voices;
    final Somebody somebody;
    final String name;

    public Scene(String name, String carlson_name, String boy_name) {
        carlson = new Carlson(carlson_name, this, Math.random() <= 0.2 ? 0 : 1);
        boy = new Boy(boy_name, this);
        sound = new Sound(this);
        voices = new Voices(this);
        somebody = new Somebody(this);
        this.name = name;
    }

    public Carlson getCarlson() {
        return carlson;
    }

    public Boy getBoy() {
        return boy;
    }

    public Voices getVoices(){
        return voices;
    }

    public Sound getSound() {
        return sound;
    }

    public Somebody getSomebody() {
        return somebody;
    }

    public String getName(){
        return name;
    }

    public void run(){
        System.out.println("\n");
        boy.startAction();
    }

    @Override
    public boolean equals(Object o){
        if (o == this)
            return true;

        if (!(o instanceof Scene))
            return false;

        Scene tmp = (Scene) o;

        return tmp.getCarlson().equals(this.getCarlson()) &&
                tmp.getBoy().equals(this.getBoy()) &&
                tmp.getSound().equals(this.getSound()) &&
                tmp.getSomebody().equals(this.getSomebody()) &&
                tmp.getVoices().equals(this.getVoices());
    }

    @Override
    public int hashCode() {
        return Objects.hash(carlson, boy, sound, voices, somebody);
    }

    @Override
    public String toString() {
        return "Scene{Name: " + getName() + ", " +
                "Carlson: " + getCarlson().toString() + ", " +
                "Boy: " + getBoy().toString() + ", " +
                "Sound: " + getSound().toString() + ", " +
                "Voices: " + getVoices().toString() + ", " +
                "Somebody: " + getSomebody().toString() + "}";
    }

    // Nested class
    public class Voices extends StoryObject implements InanimateActions {
        public Voices(Scene scene){
            super(scene);
        }

        @Override
        public void generalAction() {
            System.out.print("В соседних домах загудели голоса: ");
            System.out.print("то " + voicesCharacteristic.scared.getTitle() + ", то "
                    + voicesCharacteristic.angry.getTitle() + ", ");
        }

        @Override
        public void misfireAction() {
            System.out.print("В соседних домах царила тишина, ");
        }

        @Override
        public boolean equals(Object o){
            if (o == this)
                return true;

            if (!(o instanceof Voices))
                return false;

            Voices tmp = (Voices) o;

            return tmp.getScene() == this.getScene();
        }

        @Override
        public int hashCode(){
            return Objects.hash(this.getScene());
        }

        public String toString() {
            return "Voices{Scene: " + super.getScene().getName() + "}";
        }
    }
}