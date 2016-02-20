package ttu.ttu_appathon.student;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;

import ttu.ttu_appathon.R;

public class JoinCourse extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        this.setContentView(R.layout.join_course);

        Intent joinIntent = getIntent();
    }

    public void joinCourse(View view) {
        Intent respondIntent = new Intent(this, Respond.class);
        JoinCourse.this.startActivity(respondIntent);
    }
}
