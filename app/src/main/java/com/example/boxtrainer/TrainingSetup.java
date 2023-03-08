package com.example.boxtrainer;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.boxtrainer.databinding.TrainingsetupBinding;

public class TrainingSetup extends Fragment {

    private TrainingsetupBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = TrainingsetupBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonLaunch.setOnClickListener(v -> {
            FirstFragmentDirections.ActionFirstFragmentToSecondFragment action = FirstFragmentDirections.actionFirstFragmentToSecondFragment();
            String roundNumber = binding.roundNumber.getText().toString();
            String roundTime = binding.roundTime.getText().toString();
            String restTime = binding.restTime.getText().toString();
            action.setRoundNumber(roundNumber);
            action.setRoundTime(roundTime);
            action.setRestTime(restTime);
            NavHostFragment.findNavController(TrainingSetup.this).navigate(action);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
