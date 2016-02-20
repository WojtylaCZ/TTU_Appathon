package ttu.ttu_appathon.teacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import ttu.ttu_appathon.R;
import ttu.ttu_appathon.database.Course;
import ttu.ttu_appathon.student.Respond;

public class CreateCourse extends AppCompatActivity {

    Teacher teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.create_course);

        Intent createIntent = getIntent();

        teacher = Teacher.getInstance();
        teacher.activity = CreateCourse.this;
    }

    public void createCourse(View view) {
        Course course = teacher.createCourse();
        if (course != null){
            teacher.setCourse(course);
            Toast.makeText(CreateCourse.this, "Course INSERTED: "+teacher.getCourse().getId_course(), Toast.LENGTH_LONG).show();
            Intent askIntent = new Intent(this, AskQuestion.class);
            CreateCourse.this.startActivity(askIntent);
        }else{
            Toast.makeText(CreateCourse.this, "Course NOT inserted", Toast.LENGTH_LONG).show();
        }


    }
}
