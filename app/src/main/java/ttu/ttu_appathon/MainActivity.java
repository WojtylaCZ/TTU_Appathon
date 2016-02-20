package ttu.ttu_appathon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import ttu.ttu_appathon.teacher.Teacher;

public class MainActivity extends AppCompatActivity {
    Button selectTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectTeacher = (Button) findViewById(R.id.select_teacher);

        selectTeacher.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Teacher teacher = new Teacher();
                teacher.connectToAndQueryDatabase("a","b");
                Toast.makeText(MainActivity.this, "Create course functionality!" + String.valueOf(teacher.createCourseSurvey()), Toast.LENGTH_LONG).show();

            }
        });
    }
}
