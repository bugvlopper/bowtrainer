package com.example.boxtrainer;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.boxtrainer.database.WordViewModel;
import com.example.boxtrainer.database.trainingdatabase.TrainingDB;
import com.example.boxtrainer.database.trainingdatabase.TrainingViewModel;
import com.example.boxtrainer.databinding.FragmentThirdBinding;

public class ThirdFragment extends Fragment{

    private FragmentThirdBinding binding;

    private WordViewModel mWordViewModel;

    private EditText nameOfTraining;
    private EditText roundNumber;
    private EditText roundTime;
    private EditText restTime;

    private TrainingViewModel trainingViewModel;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentThirdBinding.inflate(inflater, container, false);
        /*mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);*/
        trainingViewModel = new ViewModelProvider(this).get(TrainingViewModel.class);
        return binding.getRoot();

    }



    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameOfTraining = binding.nameOfTrainingDBInput.findViewById(R.id.nameOfTrainingDBInput);
        roundNumber = binding.roundNumberDBInput.findViewById(R.id.roundNumberDBInput);
        roundTime = binding.roundTimeDBInput.findViewById(R.id.roundTimeDBInput);
        restTime = binding.restTimeDBInput.findViewById(R.id.restTimeDBInput);

        final Button button = binding.buttonSave.findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameOfTraining.getText().toString().trim();
                String numberOfRound = roundNumber.getText().toString().trim();
                String timeOfRound = roundTime.getText().toString().trim();
                String timeOfRest = restTime.getText().toString().trim();

                if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(numberOfRound) && !TextUtils.isEmpty(timeOfRound) && !TextUtils.isEmpty(timeOfRest)){
                    /*Word newWord = new Word(word);
                    mWordViewModel.insert(newWord);
                    mEditWordView.setText("");*/

                    TrainingDB trainingDB = new TrainingDB();
                    trainingDB.setName(name);
                    trainingDB.setRoundNumber(numberOfRound);
                    trainingDB.setRoundTime(timeOfRound);
                    trainingDB.setRestTime(timeOfRest);
                    trainingViewModel.insert(trainingDB);

                    getActivity().onBackPressed();

                }
            }

        });

         
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
