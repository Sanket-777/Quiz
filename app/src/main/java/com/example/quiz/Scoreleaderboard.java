package com.example.quiz;

public class Scoreleaderboard {
    String Name;
    long score_C;
    long rank;

    public Scoreleaderboard() {
    }

    public long getRank() {
        return rank;
    }

    public void setRank(long rank) {
        this.rank = rank;
    }

    public Scoreleaderboard(String name, long score_C, long rank) {
        Name = name;
        this.score_C = score_C;
        this.rank = rank;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public long getScore_C() {
        return score_C;
    }

    public void setScore_C(long score_C) {
        this.score_C = score_C;
    }
}
