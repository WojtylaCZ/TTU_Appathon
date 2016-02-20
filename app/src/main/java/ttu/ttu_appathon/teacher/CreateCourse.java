package ttu.ttu_appathon.teacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CreateCourse extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
    }

    public void createCourse(View view) {
        Intent intent = new Intent(this, AskQuestion.class);
    }
}
