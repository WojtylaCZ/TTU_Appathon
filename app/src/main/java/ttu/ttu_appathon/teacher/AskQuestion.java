package ttu.ttu_appathon.teacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import ttu.ttu_appathon.R;

public class AskQuestion extends AppCompatActivity {
    TextView displayID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.ask_question);

        Intent intent = getIntent();

        displayID = (TextView) findViewById(R.id.display_ID);

        // This is where we would change the TextView such that it displays the ID
        // displayID.setText();
    }
}
