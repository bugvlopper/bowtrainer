package com.example.boxtrainer.database.trainingdatabase;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "training_table")
public class TrainingDB {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "round_number")
    private String roundNumber;

    @NonNull
    @ColumnInfo(name = "round_time")
    private String roundTime;

    @NonNull
    @ColumnInfo(name = "rest_time")
    private String restTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(@NonNull String roundNumber) {
        this.roundNumber = roundNumber;
    }

    @NonNull
    public String getRoundTime() {
        return roundTime;
    }

    public void setRoundTime(@NonNull String roundTime) {
        this.roundTime = roundTime;
    }

    @NonNull
    public String getRestTime() {
        return restTime;
    }

    public void setRestTime(@NonNull String restTime) {
        this.restTime = restTime;
    }
}
