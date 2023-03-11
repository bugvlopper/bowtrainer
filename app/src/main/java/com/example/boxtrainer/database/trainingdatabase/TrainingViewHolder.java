package com.example.boxtrainer.database.trainingdatabase;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.boxtrainer.FirstFragmentDirections;
import com.example.boxtrainer.R;

class TrainingViewHolder extends RecyclerView.ViewHolder {
    private final TextView nameText;
    private final TextView roundNumber;
    private final TextView roundTime;
    private final TextView restTime;

    private TrainingViewHolder(View view) {
        super(view);

        nameText = view.findViewById(R.id.trainingTitle);
        roundNumber = view.findViewById(R.id.roundNumber);
        roundTime = view.findViewById(R.id.roundTime);
        restTime = view.findViewById(R.id.restTime);

        view.findViewById(R.id.buttonLaunch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle action = new Bundle();
                action.putString("roundNumber", roundNumber.getText().toString());
                action.putString("roundTime", roundTime.getText().toString());
                action.putString("restTime", restTime.getText().toString());
                NavController nav = Navigation.findNavController(v);
                nav.navigate(R.id.action_FirstFragment_to_SecondFragment,action);
            }
        });
    }

    public void bind(String name,String roundNumberVal,String roundTimeVal,String restTimeVal) {
        nameText.setText(name);
        roundNumber.setText(roundNumberVal);
        roundTime.setText(roundTimeVal);
        restTime.setText(restTimeVal);
    }

    static TrainingViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.training_recyclerview_item, parent, false);

        return new TrainingViewHolder(view);
    }


}
