package ttu.ttu_appathon.student;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import ttu.ttu_appathon.R;

public class JoinCourse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.join_course);

        Intent joinIntent = getIntent();
    }

    public void joinCourse(View view) {
        Intent respondIntent = new Intent(this, Respond.class);
        JoinCourse.this.startActivity(respondIntent);
    }
}
