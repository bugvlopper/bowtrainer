package com.example.boxtrainer.database.trainingdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TrainingDao {
    @Insert
    void insert(TrainingDB trainingDB);

    @Query("DELETE FROM training_table")
    void deleteAll();

    @Query("DELETE FROM training_table WHERE id = :id")
    void deleteById(int id);

    @Query("SELECT * FROM training_table ")
    LiveData<List<TrainingDB>> getAllTrainingOrderById();
}
