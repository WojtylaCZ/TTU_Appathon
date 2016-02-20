package ttu.ttu_appathon.teacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import ttu.ttu_appathon.R;

public class AskQuestion extends AppCompatActivity {
    TextView displayID;

    TextView coursePIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.ask_question);

        Intent intent = getIntent();

        StaticTeacher tmp = StaticTeacher.getInstance();

        tmp.createCourseSurvey(AskQuestion.this);

        coursePIN = (TextView) findViewById(R.id.display_ID);

        coursePIN.setText(String.valueOf(tmp.getCoursePIN()));
    }
}
