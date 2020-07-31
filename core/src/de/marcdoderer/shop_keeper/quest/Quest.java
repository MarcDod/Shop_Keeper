package de.marcdoderer.shop_keeper.quest;

import static de.marcdoderer.shop_keeper.quest.QuestResult.*;

public abstract class Quest {
    protected QuestLocation location;
    protected int reward;
    //TODO add npc type
    protected int npcID;
    protected String itemID;
    private DifficultyLevel level;
    private boolean finished;

    @Deprecated
    private Quest() {
    }

    public Quest(DifficultyLevel level, int reward, String itemID, QuestLocation location) {
        this.level = level;
        this.reward = reward;
        this.itemID = itemID;
        this.finished = false;
        this.location = location;
    }

    public boolean assignNPC(int npcID) {
        if (this.isUnassigned()) {
            this.npcID = npcID;
            return true;
        }
        return false;
    }

    public DifficultyLevel getLevel() {
        return level;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public int getNpcID() {
        return npcID;
    }

    public QuestResult calculateResult() {
        if (isUnassigned()) return UNASSIGNED;
        final float resultModifier = ((float) Math.random());//TODO add modifiers from NPC
        if (resultModifier >= 0.80) {
            this.finished = true;
            return RETURNES;
        } else if (resultModifier >= 0.75) {
            this.finished = true;
            return WOUNDED_SUCCESS;
        } else if (resultModifier <= 0.05) {
            this.npcID = 0;
            return DIES;
        } else if (resultModifier <= 0.10) {
            this.npcID = 0;
            return WOUNDED_FAILURE;
        } else return CONTINUES;

    }

    public boolean isUnassigned() {
        return (npcID == 0);
    }

    public boolean isFinished() {
        return finished;
    }
}
