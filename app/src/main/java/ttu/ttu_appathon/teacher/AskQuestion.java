package ttu.ttu_appathon.teacher;

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
import ttu.ttu_appathon.database.Course;
import ttu.ttu_appathon.database.Question;

public class AskQuestion extends AppCompatActivity {
    TextView displayID;

    TextView coursePIN;
    Teacher teacher;

    TextView labelYes;
    TextView labelNo;
    TextView numberYes;
    TextView numberNo;
    TextView label_question;

    Button pollTheClass;
    Button endThePoll;

    Timer timer;
    int value =0;
    int PIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.ask_question);

        Intent intent = getIntent();

        teacher = Teacher.getInstance();
        teacher.activity = AskQuestion.this;

        labelYes = (TextView) findViewById(R.id.label_yes);
        labelNo = (TextView) findViewById(R.id.label_no);
        numberYes = (TextView) findViewById(R.id.number_yes);
        numberNo = (TextView) findViewById(R.id.number_no);
        label_question = (TextView) findViewById(R.id.label_question);
        pollTheClass = (Button) findViewById(R.id.button_start);
        endThePoll = (Button) findViewById(R.id.button_end);


        coursePIN = (TextView) findViewById(R.id.display_ID);
        System.out.println(coursePIN);
        coursePIN.setText(Integer.toString(teacher.getCourse().getPin()));

    }

    public void pollTheClass(View view) {
        timer  = new Timer();
        Question question = teacher.createQuestion();

        if (question != null){
            teacher.setQuestion(question);
            //Toast.makeText(AskQuestion.this, "Question INSERTED: " + teacher.getQuestion().getId_question(), Toast.LENGTH_LONG).show();


            labelYes.setVisibility(View.VISIBLE);
            labelNo.setVisibility(View.VISIBLE);
            numberYes.setVisibility(View.VISIBLE);
            numberNo.setVisibility(View.VISIBLE);


            pollTheClass.setVisibility(View.INVISIBLE);
            endThePoll.setVisibility(View.VISIBLE);

            label_question.setText(question.getText());
            label_question.setVisibility(View.VISIBLE);

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    //inkrement values
                    value++;

                    //spusteni v UI threadu
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateVotes();
                        }
                    });
                }
            };
            timer.schedule(task, 100, 2000);



        }else{
            Toast.makeText(AskQuestion.this, "Question NOT inserted", Toast.LENGTH_LONG).show();
        }
    }

    public void updateVotes(){
        if(teacher.collectResponces()){
            numberYes.setText(String.valueOf(teacher.getCount_yes()));
            numberNo.setText(String.valueOf(teacher.getCount_no()));
        }
    }


    public void endThePoll(View view) {
        onStop();
        labelYes.setVisibility(View.INVISIBLE);
        labelNo.setVisibility(View.INVISIBLE);
        numberYes.setVisibility(View.INVISIBLE);
        numberNo.setVisibility(View.INVISIBLE);
        label_question.setVisibility(View.INVISIBLE);

        pollTheClass.setVisibility(View.VISIBLE);
        endThePoll.setVisibility(View.INVISIBLE);

        teacher.deleteQuestion(teacher.getQuestion().getId_question());
    }

    @Override
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }
}
