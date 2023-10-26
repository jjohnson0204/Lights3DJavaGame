package com.lightsengine.core.entity.enemy;

public class EnemyEncounter {
    // Variables
    private int EnemyID;
    private int ChanceOfEncountering;

    // Main Constructor
    public EnemyEncounter(int enemyID, int chanceOfEncountering) {
        EnemyID = enemyID;
        setChanceOfEncountering(chanceOfEncountering);
    }

    // Getters and setters
    public final int getEnemyID() {
        return EnemyID;
    }
    public final void setEnemyID(int value) {
        EnemyID = value;
    }
    public final int getChanceOfEncountering() {
        return ChanceOfEncountering;
    }
    public final void setChanceOfEncountering(int value) {
        ChanceOfEncountering = value;
    }
}
