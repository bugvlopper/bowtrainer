package com.example.boxtrainer.database.trainingdatabase;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TrainingViewModel extends AndroidViewModel {

    private TrainingReposisotry myRepository;

    private final LiveData<List<TrainingDB>> myAllTraining;

    public TrainingViewModel (Application application) {
        super(application);
        myRepository = new TrainingReposisotry(application);
        myAllTraining = myRepository.getAllTraining();
    }

    public LiveData<List<TrainingDB>> getAllTraining() { return myAllTraining;}

    public void insert(TrainingDB trainingDB) { myRepository.insert(trainingDB);}

    public void deleteAll() { myRepository.deleteAll();}

    public void deleteById(int id) {myRepository.deleteById(id);}
}
