package com.example.boxtrainer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boxtrainer.database.Word;
import com.example.boxtrainer.database.WordDao;
import com.example.boxtrainer.database.WordDao_Impl;
import com.example.boxtrainer.database.WordListAdapter;
import com.example.boxtrainer.database.WordRoomDatabase;
import com.example.boxtrainer.database.WordViewModel;
import com.example.boxtrainer.database.trainingdatabase.DeleteTrainingDialog;
import com.example.boxtrainer.database.trainingdatabase.TrainingDB;
import com.example.boxtrainer.database.trainingdatabase.TrainingListAdapter;
import com.example.boxtrainer.database.trainingdatabase.TrainingViewModel;
import com.example.boxtrainer.databinding.FragmentFirstBinding;

import java.util.List;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private WordViewModel mWordViewModel;
    private TrainingViewModel trainingViewModel;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        /*RecyclerView recyclerView = binding.recyclerview.findViewById(R.id.recyclerview);
        final WordListAdapter adapter = new WordListAdapter(new WordListAdapter.WordDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        mWordViewModel.getAllWords().observe(getViewLifecycleOwner(), words -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(words);
        });*/


        RecyclerView recyclerView = binding.recyclerview.findViewById(R.id.recyclerview);
        final TrainingListAdapter adapter = new TrainingListAdapter(new TrainingListAdapter.TrainingDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        trainingViewModel = new ViewModelProvider(this).get(TrainingViewModel.class);
        trainingViewModel.getAllTraining().observe(getViewLifecycleOwner(),trainings -> {
            adapter.submitList(trainings);
        });


        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();


        binding.addFLoatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*TrainingDB trainingDB = new TrainingDB();
                trainingDB.setName("eye");
                trainingDB.setRoundNumber("5");
                trainingDB.setRoundTime("25");
                trainingDB.setRestTime("30");
                trainingViewModel.insert(trainingDB);*/
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_thirdFragment);
                /*
                Bundle bundle = new Bundle();
                bundle.putString("roundNumber", "10");
                bundle.putString("roundTime", "10");
                bundle.putString("restTime","10");
                fragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.fisrtFragmentLayout, TrainingSetup.class, bundle, "tag")
                        .addToBackStack("name")
                        .commit();
                fragmentManager.executePendingTransactions();
                View firstView = binding.fragmentContainerView;
                fragmentManager.findFragmentByTag("tag").getView().setId(1234);
                System.out.println(fragmentManager.findFragmentByTag("tag").getId());

                ConstraintLayout constraintLayout = binding.fisrtFragmentLayout.findViewById(R.id.fisrtFragmentLayout);
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);
                constraintSet.connect(1234, ConstraintSet.TOP, firstView.getId(), ConstraintSet.BOTTOM);
                constraintSet.applyTo(constraintLayout);

                 */

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}