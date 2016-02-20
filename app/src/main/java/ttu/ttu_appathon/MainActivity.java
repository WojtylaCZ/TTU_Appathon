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
import java.sql.Statement;

import ttu.ttu_appathon.student.JoinCourse;
import ttu.ttu_appathon.teacher.AsyncTaskTestActivity;
import ttu.ttu_appathon.teacher.CreateCourse;
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
                myClickHandler(v);
            }
        });
    }

    public void studentButton(View view) {
        Intent intent = new Intent(this, JoinCourse.class);
        MainActivity.this.startActivity(intent);
    }

    public void myClickHandler(View view) {

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Toast.makeText(MainActivity.this, "ok!", Toast.LENGTH_LONG).show();
            new DownloadWebpageTask().execute("http://www.google.com");

        } else {
            Toast.makeText(MainActivity.this, "not ok!", Toast.LENGTH_LONG).show();
        }

    }

    class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
//        textView.setText(result);
            Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
        }

        private String downloadUrl(String myurl) throws IOException {
            String urld = "jdbc:mysql://WH25.farma.gigaserver.cz:3306/";
            String dbName = "hbbtvcesko_cz_testing";
            String driver = "com.mysql.jdbc.Driver";
            String userName = "34259_test";
            String password = "test";
            try {
                Class.forName(driver).newInstance();
                Connection conn = DriverManager.getConnection(urld + dbName, userName, password);
                Statement st = conn.createStatement();
//            ResultSet res = st.executeQuery("SELECT * FROM  courses");
//            while (res.next()) {
//                int id = res.getInt("id");
//                String msg = res.getString("msg");
//                System.out.println(id + "\t" + msg);
//            }
                int val = st.executeUpdate("INSERT into courses VALUES(" + Math.random() * 100 + "," + 5555 + ")");
                if (val == 1)
                    System.out.print("Successfully inserted value");
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }


            InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.
            int len = 500;

            try {
                URL url = new URL(myurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                int response = conn.getResponseCode();
//            Log.d(DEBUG_TAG, "The response is: " + response);
                is = conn.getInputStream();

                // Convert the InputStream into a string
                String contentAsString = readIt(is, len);
                return contentAsString;

                // Makes sure that the InputStream is closed after the app is
                // finished using it.
            } finally {
                if (is != null) {
                    is.close();
                }
            }


        }

        public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[len];
            reader.read(buffer);
            return new String(buffer);
        }
    }
}


