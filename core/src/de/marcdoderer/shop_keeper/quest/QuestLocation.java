package de.marcdoderer.shop_keeper.quest;

import java.util.Random;

public class QuestLocation {
    private final DifficultyLevel level;
    private final String itemID;
    private final int maxAmount;
    private final boolean regenerable;
    private final String name;
    private int amount;

    public QuestLocation(DifficultyLevel level, String itemID, int maxAmount, boolean regenerable, String name) {
        this.level = level;
        this.itemID = itemID;
        this.maxAmount = maxAmount;
        this.amount = new Random().nextInt(maxAmount) + 1;
        this.regenerable = regenerable;
        this.name = name;
    }

    public int acquireAmount(int amount) {
        int acquiredAmount = Math.min(amount, this.amount);
        this.amount -= acquiredAmount;
        return acquiredAmount;
    }

    public int availableAmount() {
        return amount;
    }

    public int regenerate() {
        if (regenerable
                && amount < maxAmount
                && Math.random() <= 0.2) {
            amount++;
        }
        return amount;
    }

    public DifficultyLevel getLevel() {
        return level;
    }

    public String getItemID() {
        return itemID;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public boolean isRegenerable() {
        return regenerable;
    }

    public String getName() {
        return name;
    }
}
