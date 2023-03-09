package com.example.boxtrainer;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.boxtrainer.database.Word;
import com.example.boxtrainer.database.WordViewModel;
import com.example.boxtrainer.databinding.FragmentThirdBinding;

public class ThirdFragment extends Fragment{

    private FragmentThirdBinding binding;

    private WordViewModel mWordViewModel;
    private EditText mEditWordView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentThirdBinding.inflate(inflater, container, false);
        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        return binding.getRoot();

    }



    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEditWordView = binding.editWord.findViewById(R.id.edit_word);

        final Button button = binding.buttonSave.findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = mEditWordView.getText().toString().trim();

                if(!TextUtils.isEmpty(word)){
                    Word newWord = new Word(word);
                    mWordViewModel.insert(newWord);
                    mEditWordView.setText("");
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
