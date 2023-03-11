package com.example.boxtrainer.database.trainingdatabase;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TrainingReposisotry {

    private TrainingDao trainingDao;
    private LiveData<List<TrainingDB>> allTraining;

    TrainingReposisotry(Application application) {
        TrainingRoomDatabase db = TrainingRoomDatabase.getDatabase(application);
        trainingDao = db.trainingDao();
        allTraining = trainingDao.getAllTrainingOrderById();
    }

    LiveData<List<TrainingDB>> getAllTraining() { return allTraining; }

    void insert(TrainingDB trainingDB) {
        TrainingRoomDatabase.databaseWriteExecutor.execute(()->{
            trainingDao.insert(trainingDB);
        });
    }

    void deleteAll(){
        TrainingRoomDatabase.databaseWriteExecutor.execute(()->{
            trainingDao.deleteAll();
        });
    }

}
