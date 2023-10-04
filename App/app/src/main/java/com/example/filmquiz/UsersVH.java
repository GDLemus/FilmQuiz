package com.example.filmquiz;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.Shapeable;

public class UsersVH extends RecyclerView.ViewHolder {

    public TextView Pprofilename, Pptstext;
    public ShapeableImageView Pprofileimg;


    public UsersVH(@NonNull View itemView) {
        super(itemView);
        Pprofilename = itemView.findViewById(R.id.profilename);
        Pprofileimg = itemView.findViewById(R.id.profileimg);
        Pptstext = itemView.findViewById(R.id.ptstext);
    }
}
