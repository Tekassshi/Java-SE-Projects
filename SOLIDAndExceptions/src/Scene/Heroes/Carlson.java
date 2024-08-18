package Scene.Heroes;

import Scene.Interfaces.Bun;
import Scene.Interfaces.CarlsonActions;
import Scene.Scene;
import Scene.Exceptions.misfireException;
import Scene.Exceptions.noGunException;
import java.util.Objects;

public class Carlson extends Hero implements CarlsonActions {
    private int gun;

    public Carlson(String carlson_name, Scene scene, int is_gun) {
        super(carlson_name, scene);
        gun = is_gun;
    }

    @Override
    public void startAction(){
        System.out.print(getName());
        System.out.print(", ликуя, ");

        try {
            fired();
        }
        catch (misfireException | noGunException e){
            System.out.println(e.getMessage());
            super.getScene().getSound().misfireAction();
            super.getScene().getVoices().misfireAction();
            super.getScene().getSomebody().misfireAction();
        }
    }

    @Override
    public void fired() throws misfireException, noGunException {
        if (gun == 0){
            throw new noGunException("хотел выстрелить, но вспомнил, что не взял пистолет.");
        }

        System.out.print("поднял руку с пистолетом над головой и выстрелил. ");

        if (Math.random() <= 0.25) {
            throw new misfireException("Пистолет дал осечку. ");
        }
        else {
            super.getScene().getSound().generalAction();
            super.getScene().getVoices().generalAction();
            super.getScene().getSomebody().generalAction();
        }
    }

    @Override
    public void voicesReaction() {
        System.out.print("Но " + super.getName() + " ");
        sat();
    }

    @Override
    public void sat() {
        System.out.print("сидел с невозмутимым видом и ");
        eat();
    }

    // static nested class
    public static class BunCond{
        private static String cond1;

        public static void setCond(String cond){
            cond1 = cond;
        }
        public static String getCond(){
            return cond1;
        }
    }

    @Override
    public void eat() {

        // anonymous local nested class
        Bun bun = new Bun(){
            @Override
            public String getCondition() {
                return BunCond.getCond();
            }

            @Override
            public void setCondition(String cond) {
                BunCond.setCond(cond);
            }
        };

        bun.setCondition("уже последнюю");
        System.out.println("жевал булочку, " + bun.getCondition() + ". ");
    }

    @Override
    public boolean equals(Object o){
        if (o == this)
            return true;

        if (!(o instanceof Carlson))
            return false;

        Carlson tmp = (Carlson) o;

        return tmp.getName().equals(this.getName()) && tmp.getScene() == this.getScene();
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.getName(), this.getScene());
    }

    @Override
    public String toString() {
        return "Carlson{Scene: " + super.getScene().getName() + ", " +
                "Name: " + super.getName() + "}";
    }
}
