package ttu.ttu_appathon.teacher;

import android.app.Activity;
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

import ttu.ttu_appathon.util.Util;

/**
 * Created by wojtyla on 2/20/16.
 */
public class StaticTeacher {

    public static int coursePIN = 0;
    public static int courseId = -1;
    public static int questionId = -1;

    static boolean parseResponses = false;
    static boolean parseCreateSurvey = false;
    static boolean parseCreateQuestion = false;
    static boolean parsePINresponse = false;
    static boolean parseQuestionIdresponse = false;
    static Activity activity;


    private static StaticTeacher staticTeacher = new StaticTeacher();

    /* A private Constructor prevents any other
     * class from instantiating.
     */
    private StaticTeacher() {
    }

    /* Static 'instance' method */
    public static StaticTeacher getInstance() {
        return staticTeacher;
    }

    public static int getCoursePIN() {
        return coursePIN;
    }


    public static void createCourseSurvey(Activity a) {
        activity = a;

        int randomPIN = (int) (Math.random() * 9000) + 1000;

        coursePIN = randomPIN;

        if (netCheck()) {
            PrepareUpdate task = new PrepareUpdate();
            task.execute("INSERT into courses (`pin`) VALUES(" + randomPIN + ")");
            parseCreateSurvey = true;
        }
    }

    public void createQuestion() {
        if (netCheck()) {
            PrepareUpdate task = new PrepareUpdate();
            task.execute("INSERT into questions (`id_course`,`text`) VALUES(" + courseId + ", " + Util.text + ")");
            parseCreateQuestion = true;
        }

    }

    public void getCourseId(int pin) {
        if (netCheck()) {
            PrepareQuery task = new PrepareQuery();
            task.execute("SELECT * FROM courses WHERE PIN = " + String.valueOf(pin));
            parsePINresponse = true;
        }
    }

    public void getQuestionId() {
        if (netCheck()) {
            PrepareQuery task = new PrepareQuery();
            task.execute("SELECT * FROM questions WHERE id_course = " + String.valueOf(courseId));
            parseQuestionIdresponse = true;
        }
    }


    private static boolean netCheck() {
        ConnectivityManager connMgr = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Toast.makeText(activity, "Internet ok!", Toast.LENGTH_LONG).show();
            return true;
        } else {
//            Toast.makeText(mainActivity, "connectivy issue", Toast.LENGTH_LONG).show();
            return false;
        }

    }

    static class PrepareUpdate extends AsyncTask<String, Void, Integer> {
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

            if (parseCreateSurvey) {
                if (result == 1) {
                    Toast.makeText(activity, "Course created", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(activity, "Course NOT created", Toast.LENGTH_LONG).show();
                }
                parseCreateSurvey = false;
            } else if (parseCreateQuestion) {
                if (result == 1) {
                    Toast.makeText(activity, "Question created", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(activity, "Question not created", Toast.LENGTH_LONG).show();
                }
                parseCreateQuestion = false;
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

            if (parsePINresponse) {
                try {
                    boolean done = false;
                    while (result.next()) {
                        courseId = Integer.valueOf(result.getInt("id_course"));
                        done = true;
                        Toast.makeText(activity, "got course id" + String.valueOf(courseId) + ".", Toast.LENGTH_LONG).show();
                    }
                    if (done) {
                    } else {
                        Toast.makeText(activity, "No such course! " + courseId, Toast.LENGTH_LONG).show();
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                parsePINresponse = false;
            } else if (parseQuestionIdresponse) {
                try {
                    while (result.next()) {
                        questionId = Integer.valueOf(result.getInt("id_question"));
                        Toast.makeText(activity, "question id" + String.valueOf(questionId) + " done.", Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(activity, "No such question! " + questionId, Toast.LENGTH_LONG).show();

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                parseQuestionIdresponse = false;
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
}
