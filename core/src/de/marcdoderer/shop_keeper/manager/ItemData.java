package de.marcdoderer.shop_keeper.manager;

public class ItemData {
    private String modID;
    private String name;
    private float width;
    private float heigth;

    @Deprecated
    private ItemData() {
    }

    public ItemData(String modID, String name, float width, float heigth) {
        this.modID = modID;
        this.name = name;
        this.width = width;
        this.heigth = heigth;
    }

    public final String getFullID() {
        return modID + ":" + name;
    }

    @Override
    public String toString() {
        return "ItemData{" +
                "modID='" + modID + '\'' +
                ", name='" + name + '\'' +
                ", width=" + width +
                ", heigth=" + heigth +
                '}';
    }

    public String getModID() {
        return modID;
    }

    public String getName() {
        return name;
    }

    public float getWidth() {
        return width;
    }

    public float getHeigth() {
        return heigth;
    }
}
