package de.marcdoderer.shop_keeper.quest;

public class AcquireQuest extends Quest {

    public AcquireQuest(DifficultyLevel level, int reward, String itemID, QuestLocation location) {
        super(level, reward, itemID, location);
    }

    public int returnedAmount() {
        return location.acquireAmount(1);
    }
}
