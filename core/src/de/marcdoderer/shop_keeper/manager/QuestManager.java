package de.marcdoderer.shop_keeper.manager;

import de.marcdoderer.shop_keeper.quest.*;

import java.util.ArrayList;
import java.util.Random;

public class QuestManager {
    public final static QuestManager QUEST_MANAGER = new QuestManager();

    private final ArrayList<Quest> availableQuests;
    private final ArrayList<Quest> activeQuests;
    private final ArrayList<Quest> doneQuests;
    private final ArrayList<QuestLocation> questLocations;

    private QuestManager() {
        availableQuests = new ArrayList<>();
        activeQuests = new ArrayList<>();
        doneQuests = new ArrayList<>();
        questLocations = new ArrayList<>();
    }

    public void createAcquireQuest(QuestLocation questLocation, int reward) {
        availableQuests.add(new AcquireQuest(questLocation.getLevel(), reward, questLocation.getItemID(), questLocation));
    }

    public QuestLocation generateQuestLocation(String itemID, String name) {
        final Random rand = new Random();
        final DifficultyLevel level = DifficultyLevel.values()[rand.nextInt(DifficultyLevel.values().length)];
        return new QuestLocation(level, itemID, rand.nextInt(99) + 1, true, name);
    }

    public void createSearchQuest(String itemID, int reward) {
        final QuestLocation location = generateQuestLocation(itemID, "genericQuestLocation");
        availableQuests.add(new SearchQuest(location.getLevel(), reward, itemID, location));
    }

    public void evaluateActiveQuests() {
        for (int questNumber = activeQuests.size(); questNumber > 0; questNumber--) {
            Quest quest = activeQuests.get(questNumber);
            switch (quest.calculateResult()) {
                case UNASSIGNED:
                    unassignQuest(questNumber);
                    break;
                case RETURNES:
                    if (quest instanceof AcquireQuest) {
                        //TODO let NPC return with item
                    } else if (quest instanceof SearchQuest) {
                        //TODO let NPC return and tell the player about the new Location
                        //TODO stop selection of location before player talks with NPC
                        questLocations.add(((SearchQuest) quest).getLocation());
                    }
                    finishQuest(questNumber);
                    break;
                case DIES:
                    //TODO kill NPC
                    unassignQuest(questNumber);
                    break;
                case WOUNDED_SUCCESS:
                    if (quest instanceof AcquireQuest) {
                        //TODO let NPC return wounded and with item
                    } else if (quest instanceof SearchQuest) {
                        //TODO let NPC return wounded and tell the player about the new Location
                        //TODO stop selection of location before player talks with NPC
                        questLocations.add(((SearchQuest) quest).getLocation());
                    }
                    finishQuest(questNumber);
                    break;
                case WOUNDED_FAILURE:
                    //TODO let NPC return wounded and with item
                    unassignQuest(questNumber);
                    break;
                case CONTINUES:
                    continue;
            }
        }
    }

    private boolean finishQuest(int questNumber) {
        final Quest quest = activeQuests.remove(questNumber);
        if (quest.isFinished()) {
            doneQuests.add(quest);
            return true;
        } else {
            activeQuests.add(questNumber, quest);
            return false;
        }
    }

    private boolean unassignQuest(int questNumber) {
        final Quest quest = activeQuests.remove(questNumber);
        if (!quest.isFinished()) {
            availableQuests.add(quest);
            return true;
        } else {
            return finishQuest(questNumber);
        }
    }

    public boolean assignQuest(int questNumber, int npcID) {
        final Quest quest = availableQuests.remove(questNumber);
        final boolean assigned = quest.assignNPC(npcID);
        if (assigned) {
            activeQuests.add(quest);
            return true;
        } else {
            availableQuests.add(questNumber, quest);
            return false;
        }
    }
}
