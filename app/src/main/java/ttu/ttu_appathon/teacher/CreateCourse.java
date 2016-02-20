package ttu.ttu_appathon.teacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import ttu.ttu_appathon.R;

public class CreateCourse extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.create_course);

        Intent createIntent = getIntent();
    }

    public void createCourse(View view) {
        Intent askIntent = new Intent(this, AskQuestion.class);
        CreateCourse.this.startActivity(askIntent);
    }
}
