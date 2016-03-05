package com.epam.preprod.bohdanov.model.entity;

public enum Faculty {
    GRYPHINDOR(0), SLYTHETIN(1), RAVENCLAW(2), HUFFLEPUFF(3);
    private int value;

    private Faculty(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Faculty getFacultyByValue(int value) {
        for (Faculty faculty : Faculty.values()) {
            if (faculty.getValue() == value)
                return faculty;
        }
        throw new IllegalArgumentException();
    }

}
