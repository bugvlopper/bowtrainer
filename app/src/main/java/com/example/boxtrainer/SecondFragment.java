package com.example.boxtrainer;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.graphics.drawable.ClipDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.boxtrainer.databinding.FragmentSecondBinding;


public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private String chronoCount;
    private int chrono;
    private boolean isRunnning = false;
    private Runnable runnable;
    private final Handler handler = new Handler();
    private boolean inRestTime = false;
    private int roundLeft;
    private MediaPlayer mediaPlayer;
    private final int circleOffset =  0;// previously 830
    ValueAnimator valueAnimator = ValueAnimator.ofInt(circleOffset,10000 - circleOffset);
    ValueAnimator valueAnimatorDown = ValueAnimator.ofInt(10000-circleOffset,circleOffset);
    private boolean animatorIsRunning = false;
    private  boolean animatorDownIsRunning = false;
    private boolean volumeOff = false;
    ClipDrawable clipDrawable;
    int colorAttrId2;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState

    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @SuppressLint({"ClickableViewAccessibility", "UseCompatLoadingForDrawables"})
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TypedArray a2 = requireContext().getTheme().obtainStyledAttributes(R.style.Theme_BoxTrainer,new int[] {com.google.android.material.R.attr.colorOnSecondary});
        colorAttrId2 = a2.getResourceId(0,0);
        a2.recycle();



        GestureDetector doubletap = new GestureDetector( new GestureDetector.SimpleOnGestureListener());
        doubletap.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onDoubleTap(@NonNull MotionEvent e) {
                chronoCount = binding.textCount.getText().toString();
                chrono = Integer.parseInt(chronoCount);
                if(chrono > 0){
                    chrono = 0;
                    roundLeft = 0;
                    binding.textCount.setText("0");
                    binding.roundLeft.setText("0");
                    clipDrawable.setLevel(circleOffset);
                    binding.buttonStop.setText(R.string.stop);
                    binding.buttonStart.setText(R.string.start);
                    valueAnimatorDown.cancel();
                    valueAnimator.cancel();
                    binding.textCount.setTextColor(ContextCompat.getColor(requireContext(), colorAttrId2));
                }
                return false;
            }
            @Override
            public boolean onSingleTapConfirmed(@NonNull MotionEvent e) {
                if(isRunnning) {
                    handler.removeCallbacks(runnable);
                    isRunnning = false;
                    binding.secondFragmentLayout.setKeepScreenOn(false);
                    valueAnimator.pause();
                    valueAnimatorDown.pause();
                    animatorIsRunning = false;
                    if (roundLeft != 0) {
                        binding.buttonStop.setText(R.string.reset);
                        binding.buttonStart.setText(R.string.resume);
                    }
                }else if(roundLeft != 0){
                    Toast toast = Toast.makeText(getContext(),"Double click to reset", Toast.LENGTH_SHORT);
                    toast.show();
                }
                return false;
            }

            @Override
            public boolean onDoubleTapEvent(@NonNull MotionEvent e) {
                return false;
            }
        });


        ImageView imageView = binding.imageViewCircleColor.findViewById(R.id.imageViewCircleColor);
        clipDrawable = new ClipDrawable(imageView.getDrawable(),Gravity.BOTTOM,ClipDrawable.VERTICAL);
        imageView.setImageDrawable(clipDrawable);
        clipDrawable.setLevel(circleOffset);
        valueAnimator.addUpdateListener(animator -> {

            int level = (int) animator.getAnimatedValue();
            clipDrawable.setLevel(level);

            TypedArray a = requireContext().getTheme().obtainStyledAttributes(R.style.Theme_BoxTrainer,new int[] {com.google.android.material.R.attr.colorOnPrimary});
            int colorAttrId = a.getResourceId(0,0);
            a.recycle();

            if(level >5000 && level <5500) {
                binding.textCount.setTextColor(ContextCompat.getColor(requireContext(), colorAttrId));
            }
        });
        valueAnimatorDown.addUpdateListener(animator -> {

            int level = (int) animator.getAnimatedValue();
            clipDrawable.setLevel(level);

            if(level <5500 && level >5000) {
                binding.textCount.setTextColor(ContextCompat.getColor(requireContext(), colorAttrId2));
            }
        });


        String roundNumber = SecondFragmentArgs.fromBundle(getArguments()).getRoundNumber();
        String roundTime = SecondFragmentArgs.fromBundle(getArguments()).getRoundTime();
        String restTime = SecondFragmentArgs.fromBundle(getArguments()).getRestTime();
        binding.numberOfRoundInput.setText(roundNumber);
        binding.roundTimeInput.setText(roundTime);
        binding.restTimeInput.setText(restTime);

        binding.imageViewSound.setOnClickListener(v -> {

            if(!volumeOff){
                volumeOff = true;
                binding.imageViewSound.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_volume_off_24));
            }else{
                volumeOff = false;
                binding.imageViewSound.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_volume_up_24));
            }
        });

        binding.buttonStop.setOnTouchListener((v, event) -> {
            doubletap.onTouchEvent(event);
            return false;
        });

        binding.buttonStart.setOnClickListener(v -> {

            mediaPlayer = MediaPlayer.create(getContext(), R.raw.beepsound);

            if(!isRunnning) {
                binding.secondFragmentLayout.setKeepScreenOn(true);
                isRunnning = true;
                if(roundLeft == 0) {
                    chronoCount = binding.textCount.getText().toString();
                    chrono = Integer.parseInt(chronoCount);
                    roundLeft = Integer.parseInt(binding.numberOfRoundInput.getText().toString());
                    int roundTime1 = Integer.parseInt(binding.roundTimeInput.getText().toString());
                    int restTime1 = Integer.parseInt(binding.restTimeInput.getText().toString());
                    runnable = () -> {
                        handler.postDelayed(runnable, 1000);
                        myChrono(roundTime1, restTime1);
                    };
                }else if(roundLeft > 0){
                    if (valueAnimator.isRunning()) {
                        valueAnimator.resume();
                    }
                    if(valueAnimatorDown.isRunning()){
                        valueAnimatorDown.resume();
                    }
                    binding.buttonStart.setText(R.string.start);
                    binding.buttonStop.setText(R.string.stop);
                }
                handler.postDelayed(runnable, 1000);
            }


        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(runnable);
        binding = null;
    }

    @SuppressLint("ResourceAsColor")
    public void myChrono(int roundTime, int restTime){
        binding.roundLeft.setText(String.valueOf(roundLeft));
        if (roundLeft >0){

            if(!inRestTime) {
                animatorDownIsRunning = false;
                if(!animatorIsRunning){
                    animatorIsRunning = true;
                    if(!valueAnimator.isRunning()) {
                        valueAnimator.setDuration(roundTime * 1000L);
                        valueAnimator.start();
                    }
                }

                if (chrono < roundTime) {
                    chrono += 1;
                    chronoCount = String.valueOf(chrono);
                    binding.textCount.setText(chronoCount);
                    if(chrono > roundTime-3 && !volumeOff){
                        mediaPlayer.start();
                    }
                }else{
                    inRestTime = true;
                    roundLeft -= 1;
                    if(roundLeft > 0) {
                        binding.textCount.setText(String.valueOf(restTime));
                        chrono = Integer.parseInt(binding.textCount.getText().toString());
                    }else if(roundLeft == 0){
                        binding.textCount.setText("0");
                    }
                }
            }else{
                if (chrono > 0) {
                    animatorIsRunning = false;
                    if (!animatorDownIsRunning) {
                        animatorDownIsRunning = true;
                        if(!valueAnimatorDown.isRunning()) {
                            valueAnimatorDown.setDuration(restTime * 1000L);
                            valueAnimatorDown.start();
                        }
                    }
                    chrono -= 1;
                    chronoCount = String.valueOf(chrono);
                    binding.textCount.setText(chronoCount);
                    if (chrono < 3 && !volumeOff){
                        mediaPlayer.start();
                    }
                }else{
                    inRestTime = false;
                }
            }
        }else {
            handler.removeCallbacks(runnable);
            clipDrawable.setLevel(circleOffset);
            animatorIsRunning = false;
            isRunnning = false;
            binding.secondFragmentLayout.setKeepScreenOn(false);
            binding.textCount.setTextColor(ContextCompat.getColor(requireContext(),colorAttrId2));
            binding.buttonStart.setText(R.string.start);
            binding.buttonStop.setText(R.string.stop);
        }
    }





}