package Scene.Enums;

public enum voicesCharacteristic {
    scared("испуганные"),
    angry("сердитые");

    private String title;

    voicesCharacteristic(String title) {
        this.title = title;
    }

    public String getTitle(){
        return title;
    }
}