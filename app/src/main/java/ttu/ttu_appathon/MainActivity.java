package ttu.ttu_appathon;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import ttu.ttu_appathon.student.JoinCourse;
import ttu.ttu_appathon.student.Student;
import ttu.ttu_appathon.teacher.AsyncTaskTestActivity;
import ttu.ttu_appathon.teacher.CreateCourse;
import ttu.ttu_appathon.teacher.Teacher;

public class MainActivity extends AppCompatActivity {
    AlphaAnimation buttonClickxx = new AlphaAnimation(1F, 0.8F);
    Button selectTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//        selectTeacher = (Button) findViewById(R.id.select_teacher);

//        selectTeacher.setOnClickListener(new View.OnClickListener() {

//            @Override
//            public void onClick(View v) {
                //##############################################
//                Teacher teacher = new Teacher(MainActivity.this);
//                teacher.createCourseSurvey();
//                teacher.createQuestion(1);
//                int id_question = 1;
//                teacher.getResponses(id_question);


                //##############################################
//                Student student = new Student(MainActivity.this);

//                int pin = 9206;
//                student.logInToCourseSurvey(pin);

//                int question = 1;
//                student.getCourseQuestions(question);

//                int enumr = 0;
//                int id_question = 2;
//                student.addResponse(id_question, enumr);
                //##############################################

//            }
//        });
    }
    public void studentButton(View view) {
        view.startAnimation(buttonClickxx);
//        Student student = new Student(MainActivity.this);
//
//        int pin = 9206;
//        student.logInToCourseSurvey(pin);
//
//        int question = 1;
//        student.getCourseQuestions(question);
//
//        int enumr = 0;
//        int id_question = 2;
//        student.addResponse(id_question, enumr);

        Intent joinIntent = new Intent(this, JoinCourse.class);
        MainActivity.this.startActivity(joinIntent);
    }

    public void teacherButton(View view) {
        view.startAnimation(buttonClickxx);

        Intent createIntent = new Intent(this, CreateCourse.class);
//        teacher.createCourseSurvey();
//        teacher.createQuestion(1);
//        int id_question = 1;
//        teacher.getResponses(id_question);

        MainActivity.this.startActivity(createIntent);
    }
}


