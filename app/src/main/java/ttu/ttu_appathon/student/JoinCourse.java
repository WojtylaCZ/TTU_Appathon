package ttu.ttu_appathon.student;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ttu.ttu_appathon.R;

public class JoinCourse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_course);

        Intent intent = getIntent();
    }
}
