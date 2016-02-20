package ttu.ttu_appathon.student;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ttu.ttu_appathon.MainActivity;
import ttu.ttu_appathon.util.Util;

/**
 * Created by wojtyla on 2/20/16.
 */
public class Student {
    MainActivity mainActivity;
    boolean parseCourses = false;
    boolean parseCourseQuestions = false;
    boolean parseCreateResponse = false;
//    private int id_course;

    public Student(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }


    public int logInToCourseSurvey(int pin) {
        if (netCheck()) {
            PrepareQuery task = new PrepareQuery();
            task.execute("SELECT * FROM courses WHERE pin = " + String.valueOf(pin));
            this.parseCourses = true;
        }
        return -1;
    }

    public int getCourseQuestions(int id_course) {
        if (netCheck()) {
            PrepareQuery task = new PrepareQuery();
            task.execute("SELECT * FROM questions WHERE id_course = " + String.valueOf(id_course));
            this.parseCourseQuestions = true;
        }
        return -1;
    }

    public void addResponse(int id_question, int enumr) {
        if (netCheck()) {
            PrepareUpdate task = new PrepareUpdate();
            task.execute("INSERT into responses (`id_question`,`enum`) VALUES(" + id_question + ", " + enumr + ")");
            this.parseCreateResponse = true;
        }


    }

    private boolean netCheck() {
        ConnectivityManager connMgr = (ConnectivityManager) mainActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Toast.makeText(mainActivity, "ok!", Toast.LENGTH_LONG).show();
            return true;
        } else {
            Toast.makeText(mainActivity, "connectivy issue", Toast.LENGTH_LONG).show();
            return false;
        }

    }


    class PrepareQuery extends AsyncTask<String, Void, ResultSet> {
        @Override
        protected ResultSet doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return query(urls[0]);
//                queryResult = query(urls[0]);
//                return queryResult;
            } catch (IOException e) {
                return null;
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(ResultSet result) {

            if (parseCourses) {
                int id_course = -1;
                try {
                    while (result.next()) {
                        int ridc = result.getInt("id_course");
                        Toast.makeText(mainActivity, "Allowed to go to course with id " + String.valueOf(ridc), Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (id_course == -1) {
                        Toast.makeText(mainActivity, "No such course! " + result.getFetchSize(), Toast.LENGTH_LONG).show();
                        return;
                    }


                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else if (parseCourseQuestions) {
                try {
                    String response = "";

                    while (result.next()) {
                        String rtext = result.getString("text");
                        response = response + rtext + "\n";
                        Toast.makeText(mainActivity, "Query done; " + response, Toast.LENGTH_LONG).show();

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        private ResultSet query(String query) throws IOException {
            String response = "";
            try {
                Class.forName(Util.driver).newInstance();
                Connection conn = DriverManager.getConnection(Util.urld + Util.dbName, Util.userName, Util.password);
                Statement st = conn.createStatement();
                ResultSet res = st.executeQuery(query);
                conn.close();
                return res;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    class PrepareUpdate extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                return query(urls[0]);
//                queryUpdate = query(urls[0]);
//                return queryUpdate;
            } catch (IOException e) {
                return null;
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(Integer result) {

            if (parseCreateResponse) {
                if (result == 1) {
                    Toast.makeText(mainActivity, "Query INSERTED; ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(mainActivity, "Record NOT inserted", Toast.LENGTH_LONG).show();
                }
            }
        }


        private Integer query(String query) throws IOException {

            String response = "";
            try {
                Class.forName(Util.driver).newInstance();
                Connection conn = DriverManager.getConnection(Util.urld + Util.dbName, Util.userName, Util.password);
                Statement st = conn.createStatement();
                int res = st.executeUpdate(query);
                conn.close();
                return res;


            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }
    }
}
