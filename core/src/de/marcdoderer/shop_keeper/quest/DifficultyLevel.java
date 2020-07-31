package de.marcdoderer.shop_keeper.quest;

public enum DifficultyLevel {
    S(100, 2f),
    A(50, 1f),
    B(20, .75f),
    C(10, .5f),
    D(5, 0);

    private final int excpectetMoney;
    private final float difficultyModifier;

    DifficultyLevel(int excpectetMoney, float difficultyModifier) {
        this.excpectetMoney = excpectetMoney;
        this.difficultyModifier = difficultyModifier;
    }

    public float successChance(DifficultyLevel questLevel, float baseChance) {
        return baseChance * (1 + this.difficultyModifier - questLevel.difficultyModifier);
    }
}
