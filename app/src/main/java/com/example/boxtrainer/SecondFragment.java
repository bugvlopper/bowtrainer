package com.example.boxtrainer;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.boxtrainer.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    private String chronoCount;
    private int chrono;
    private boolean isRunnning = false;
    private Runnable runnable;
    private Handler handler = new Handler();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isRunnning) {
                    isRunnning = true;
                    chronoCount = binding.textCount.getText().toString();
                    chrono = Integer.parseInt(chronoCount);
                    runnable = new Runnable() {
                        @Override
                        public void run() {
                            chrono += 1;
                            chronoCount = String.valueOf(chrono);
                            binding.textCount.setText(chronoCount);
                            handler.postDelayed(runnable, 1000);
                        }
                    };
                    handler.postDelayed(runnable, 1000);
                }

            }
        });
        binding.buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(runnable);
                isRunnning = false;
            }
        });
        binding.buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronoCount = binding.textCount.getText().toString();
                chrono = Integer.parseInt(chronoCount);
                if(chrono > 0){
                    chrono = 0;
                    binding.textCount.setText("0");
                }
            }
        });
        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(runnable);
        binding = null;
    }

}