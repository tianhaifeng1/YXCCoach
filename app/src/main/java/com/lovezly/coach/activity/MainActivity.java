package com.lovezly.coach.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.lovezly.coach.R;
import com.lovezly.coach.activity.fragment.MainOneFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        FragmentManager fragmentManager = getFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        // 使用LiftCycleFragment替换指定的 id 的 View
//        transaction.replace(R.id.main_fragment, new MainOneFragment());
//        transaction.commit();
    }
}