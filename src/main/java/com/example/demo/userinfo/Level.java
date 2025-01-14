package com.example.demo.userinfo;

public enum Level {
    F1_I(0),
    F1_II(13500),
    F2_I(27000),
    F2_II(39000),
    F2_III(51000),
    F3_I(63000),
    F3_II(78000),
    F3_III(93000),
    F4_I(108000),
    F4_II(126000),
    F4_III(144000),
    F5(162000),
    B1(0),
    B2(24000),
    B3(52000),
    B4(78000),
    B5(117000),
    B6(169000),
    G1(0),
    G2(24000),
    G3(52000),
    G4(78000),
    G5(117000),
    G6(169000),
    T1(0),
    T2(0),
    T3(0),
    T4(0),
    T5(0),
    T6(0);

    private final int requiredExp;

    Level(int requiredExp) {
        this.requiredExp = requiredExp;
    }

    public int getRequiredExp() {
        return requiredExp;
    }
}