package ttu.ttu_appathon.student;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import ttu.ttu_appathon.R;
import ttu.ttu_appathon.database.Question;

public class Respond extends AppCompatActivity {
    Student student;
    Timer timer;
    int value = 0;

    Button button_yes;
    Button button_no;
    TextView question_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.respond);
        Intent respondIntent = getIntent();
        timer = new Timer();
        student = Student.getInstance();
        student.activity = Respond.this;

        question_text = (TextView) findViewById(R.id.label_questionx);
        button_yes = (Button) findViewById(R.id.respond_yes);
        button_no = (Button) findViewById(R.id.respond_no);

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //inkrement values
                value++;

                //spusteni v UI threadu
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        checkForQuestion();
                    }
                });
            }
        };
        timer.schedule(task, 100, 2000);
    }

    private void checkForQuestion() {
        Question question = student.checkForQuestion();
        if (question != null) {
            if (question.getId_question() != student.getQuestionIdSeen()) {
                question_text.setVisibility(View.VISIBLE);
                question_text.setText(question.getText());
                button_yes.setVisibility(View.VISIBLE);
                button_no.setVisibility(View.VISIBLE);
                student.setQuestionIdSeen(question.getId_question());
            } else {
                Toast.makeText(Respond.this, "Question seeen. ", Toast.LENGTH_SHORT).show();

            }

        } else {
            Toast.makeText(Respond.this, "No question to course ", Toast.LENGTH_SHORT).show();


        }
    }

    public void butt_response_yes(View v) {
        butt_response(1);

    }

    public void butt_response_no(View v) {
        butt_response(0);
    }

    public void butt_response(int enumx) {
        if (student.addResponse(enumx)) {
            question_text.setVisibility(View.INVISIBLE);
            button_yes.setVisibility(View.INVISIBLE);
            button_no.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }
}
