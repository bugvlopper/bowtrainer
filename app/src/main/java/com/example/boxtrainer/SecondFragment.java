package com.example.boxtrainer;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.boxtrainer.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private String chronoCount;
    private int chrono;
    private boolean isRunnning = false;
    private Runnable runnable;
    private Handler handler = new Handler();
    private boolean inRestTime = false;
    private int roundLeft;
    private MediaPlayer mediaPlayer;
    private final int circleOffset =  0;// previously 830
    ValueAnimator valueAnimator = new ValueAnimator().ofInt(circleOffset,10000 - circleOffset);
    ValueAnimator valueAnimatorDown = new ValueAnimator().ofInt(10000-circleOffset,circleOffset);
    private boolean animatorIsRunning = false;
    private  boolean animatorDownIsRunning = false;
    private boolean volumeOff = false;
    ClipDrawable clipDrawable;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState

    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @SuppressLint("ClickableViewAccessibility")
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
                }
                return false;
            }
            @Override
            public boolean onSingleTapConfirmed(@NonNull MotionEvent e) {
                handler.removeCallbacks(runnable);
                isRunnning = false;
                binding.secondFragmentLayout.setKeepScreenOn(false);
                valueAnimator.cancel();
                valueAnimatorDown.cancel();
                animatorIsRunning = false;
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
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {

                int level = (int) animator.getAnimatedValue();
                clipDrawable.setLevel(level);
            }
        });
        valueAnimatorDown.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {

                int level = (int) animator.getAnimatedValue();
                clipDrawable.setLevel(level);
            }
        });


        String roundNumber = SecondFragmentArgs.fromBundle(getArguments()).getRoundNumber();
        String roundTime = SecondFragmentArgs.fromBundle(getArguments()).getRoundTime();
        String restTime = SecondFragmentArgs.fromBundle(getArguments()).getRestTime();
        binding.numberOfRoundInput.setText(roundNumber);
        binding.roundTimeInput.setText(roundTime);
        binding.restTimeInput.setText(restTime);

        binding.imageViewSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!volumeOff){
                    volumeOff = true;
                    binding.imageViewSound.setImageDrawable((Drawable) getResources().getDrawable(R.drawable.ic_baseline_volume_off_24));
                }else{
                    volumeOff = false;
                    binding.imageViewSound.setImageDrawable((Drawable) getResources().getDrawable(R.drawable.ic_baseline_volume_up_24));
                }
            }
        });

        binding.buttonStop.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                doubletap.onTouchEvent(event);
                return false;
            }
        });

        binding.buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*imageView.setImageDrawable(clipDrawable);
                clipDrawable.setLevel(9000);*/


                mediaPlayer = MediaPlayer.create(getContext(), R.raw.beepsound);

                if(!isRunnning) {
                    binding.secondFragmentLayout.setKeepScreenOn(true);
                    isRunnning = true;
                    if(roundLeft == 0) {
                        chronoCount = binding.textCount.getText().toString();
                        chrono = Integer.parseInt(chronoCount);
                        int numberOfRound = Integer.parseInt(binding.numberOfRoundInput.getText().toString());
                        roundLeft = numberOfRound;
                        int roundTime = Integer.parseInt(binding.roundTimeInput.getText().toString());
                        int restTime = Integer.parseInt(binding.restTimeInput.getText().toString());
                        runnable = new Runnable() {
                            @Override
                            public void run() {
                                handler.postDelayed(runnable, 1000);
                                myChrono(roundTime, restTime);
                            }
                        };
                    }
                    handler.postDelayed(runnable, 1000);
                }
                
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacks(runnable);
        binding = null;
    }

    public void myChrono(int roundTime, int restTime){
        binding.roundLeft.setText(String.valueOf(roundLeft));
        if (roundLeft >0){

            if(!inRestTime) {
                animatorDownIsRunning = false;
                if(!animatorIsRunning){
                    animatorIsRunning = true;
                    valueAnimator.setDuration(roundTime*1000);
                    valueAnimator.start();
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
                        valueAnimatorDown.setDuration(restTime * 1000);
                        valueAnimatorDown.start();
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
        }
    }





}