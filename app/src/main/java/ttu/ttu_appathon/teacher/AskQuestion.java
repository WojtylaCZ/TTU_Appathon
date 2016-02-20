package ttu.ttu_appathon.teacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ttu.ttu_appathon.R;

public class AskQuestion extends AppCompatActivity {
    TextView displayID;

    TextView coursePIN;
    StaticTeacher tmp;
    int PIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.ask_question);

        Intent intent = getIntent();

        tmp = StaticTeacher.getInstance();

        tmp.createCourseSurvey(AskQuestion.this);

        coursePIN = (TextView) findViewById(R.id.display_ID);

        PIN = tmp.getCoursePIN();
        coursePIN.setText(String.valueOf(PIN));
    }

    public void pollTheClass(View view) {
        tmp.getCourseId(PIN);
        System.err.println("got course ID" + String.valueOf(tmp.coursePIN));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tmp.createQuestion();
        System.err.println("question created");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        tmp.getQuestionId();
//        System.err.println("got question ID" + String.valueOf(tmp.questionId));
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        TextView tw =(TextView) findViewById(R.id.display_question);
//        tw.setText("Quest. with id "+tmp.questionId);



    }
}
