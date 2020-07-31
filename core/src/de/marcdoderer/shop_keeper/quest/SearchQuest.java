package de.marcdoderer.shop_keeper.quest;

public class SearchQuest extends Quest {

    public SearchQuest(DifficultyLevel level, int reward, String itemID, QuestLocation location) {
        super(level, reward, itemID, location);
    }

    public QuestLocation getLocation() {
        return location;
    }
}
