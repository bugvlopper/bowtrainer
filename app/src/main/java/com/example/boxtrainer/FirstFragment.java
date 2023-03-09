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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boxtrainer.database.WordListAdapter;
import com.example.boxtrainer.database.WordViewModel;
import com.example.boxtrainer.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private WordViewModel mWordViewModel;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        RecyclerView recyclerView = binding.recyclerview.findViewById(R.id.recyclerview);
        final WordListAdapter adapter = new WordListAdapter(new WordListAdapter.WordDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        mWordViewModel.getAllWords().observe(getViewLifecycleOwner(), words -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(words);
        });

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        binding.addFLoatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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