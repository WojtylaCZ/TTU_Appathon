package ttu.ttu_appathon.student;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import ttu.ttu_appathon.R;

public class JoinCourse extends AppCompatActivity {
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        this.setContentView(R.layout.join_course);
        Intent joinIntent = getIntent();

        student = Student.getInstance();
        student.activity = JoinCourse.this;
    }

    public void joinCourse(View view) {

        TextView textView = ((TextView) findViewById(R.id.join_id));
        String inputId = textView.getText().toString();
        int i = Integer.parseInt(inputId);
        if (student.logInToCourse(i)) {
            Toast.makeText(JoinCourse.this, "PIN CORRECT", Toast.LENGTH_LONG).show();
            Intent respondIntent = new Intent(this, Respond.class);
            JoinCourse.this.startActivity(respondIntent);
        } else {
            Toast.makeText(JoinCourse.this, "ERROR CODE", Toast.LENGTH_LONG).show();
        }

    }
}
