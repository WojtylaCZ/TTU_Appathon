package ttu.ttu_appathon.teacher;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import ttu.ttu_appathon.R;

/**
 * Created by wojtyla on 2/20/16.
 */
public class AsyncTaskTestActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(AsyncTaskTestActivity.this, "Create course functionality!", Toast.LENGTH_LONG).show();

        //Starting the task. Pass an url as the parameter.
        //new PostTask().execute("http://feeds.pcworld.com/pcworld/latestnews");

        String url = "jdbc:mysql://WH25.farma.gigaserver.cz:3306/";
        String dbName = "hbbtvcesko_cz_testing";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "34259_test";
        String password = "test";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, password);
            Statement st = conn.createStatement();
//            ResultSet res = st.executeQuery("SELECT * FROM  courses");
//            while (res.next()) {
//                int id = res.getInt("id");
//                String msg = res.getString("msg");
//                System.out.println(id + "\t" + msg);
//            }
            int val = st.executeUpdate("INSERT into courses VALUES(" + 2 + "," + 5555 + ")");
            if (val == 1)
                System.out.print("Successfully inserted value");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // The definition of our task class
    private class PostTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //displayProgressBar("Downloading...");
        }

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];

            // Dummy code
            for (int i = 0; i <= 100; i += 5) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i);
            }
            return "All Done!";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //updateProgressBar(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //dismissProgressBar();
        }
    }
}