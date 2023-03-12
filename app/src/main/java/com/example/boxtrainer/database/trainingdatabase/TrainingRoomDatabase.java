package com.example.boxtrainer.database.trainingdatabase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {TrainingDB.class},version = 1, exportSchema = false)
public abstract class TrainingRoomDatabase extends RoomDatabase {

    public abstract TrainingDao trainingDao();

    private static volatile TrainingRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static TrainingRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (TrainingRoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TrainingRoomDatabase.class, "training_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static  RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);

            databaseWriteExecutor.execute(()->{
                TrainingDao dao = INSTANCE.trainingDao();
                dao.deleteAll();

                TrainingDB trainingDB = new TrainingDB();
                trainingDB.setName("boxe");
                trainingDB.setRoundNumber("5");
                trainingDB.setRoundTime("25");
                trainingDB.setRestTime("30");
                dao.insert(trainingDB);
            });
        }

    };
}
