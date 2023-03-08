package com.example.boxtrainer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;

import com.example.boxtrainer.databinding.FragmentFirstBinding;

import java.util.Timer;
import java.util.TimerTask;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
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


                fragmentManager.beginTransaction()
                        .add(R.id.fisrtFragmentLayout, TrainingSetup.class, null, "tag")
                        .addToBackStack(null)
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

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}