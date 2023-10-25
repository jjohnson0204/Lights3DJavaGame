package com.lightsengine.core.quest;

public class QuestStatus {
    private Quest PlayerQuest;
    private boolean IsCompleted;
    public QuestStatus(Quest quest) {
        setPlayerQuest(quest);
        setCompleted(false);
    }
    public final Quest getPlayerQuest() {
        return PlayerQuest;
    }
    public final void setPlayerQuest(Quest value) {
        PlayerQuest = value;
    }
    public final boolean isCompleted() {
        return IsCompleted;
    }
    public final void setCompleted(boolean value) {
        IsCompleted = value;
    }
}
