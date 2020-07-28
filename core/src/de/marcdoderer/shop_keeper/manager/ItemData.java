package de.marcdoderer.shop_keeper.manager;

public class ItemData {
    public final String modID;
    public final String name;
    public final float width;
    public final float heigth;

    @Deprecated
    private ItemData() {
        System.out.println("def");
        modID = "shopKeeper";
        name = "apple";
        width = 2f;
        heigth = 2f;
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
}
