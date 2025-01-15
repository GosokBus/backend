package com.example.demo.userinfo;

public enum Level {
    F1_I(0, "F1-I"),
    F1_II(13500, "F1-II"),
    F2_I(27000, "F2-I"),
    F2_II(39000, "F2-II"),
    F2_III(51000, "F2-III"),
    F3_I(63000, "F3-I"),
    F3_II(78000, "F3-II"),
    F3_III(93000, "F3-III"),
    F4_I(108000, "F4-I"),
    F4_II(126000, "F4-II"),
    F4_III(144000, "F4-III"),
    F5(162000, "F5"),
    B1(0, "B1"),
    B2(24000, "B2"),
    B3(52000, "B3"),
    B4(78000, "B4"),
    B5(117000, "B5"),
    B6(169000, "B6"),
    G1(0, "G1"),
    G2(24000, "G2"),
    G3(52000, "G3"),
    G4(78000, "G4"),
    G5(117000, "G5"),
    G6(169000, "G6"),
    T1(0, "T1"),
    T2(0, "T2"),
    T3(0, "T3"),
    T4(0, "T4"),
    T5(0, "T5"),
    T6(0, "T6");

    private final int requiredExp;
    private final String firestoreValue;

    Level(int requiredExp, String firestoreValue) {
        this.requiredExp = requiredExp;
        this.firestoreValue = firestoreValue;
    }

    public int getRequiredExp() {
        return requiredExp;
    }

    public String getFirestoreValue() {
        return firestoreValue;
    }

    public static Level fromFirestoreValue(String firestoreValue) {
        for (Level level : values()) {
            if (level.getFirestoreValue().equals(firestoreValue)) {
                return level;
            }
        }
        return null;
    }
}