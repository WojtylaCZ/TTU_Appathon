package ttu.ttu_appathon.student;

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
import java.util.concurrent.ExecutionException;

import ttu.ttu_appathon.MainActivity;
import ttu.ttu_appathon.util.Util;

/**
 * Created by wojtyla on 2/20/16.
 */
public class Student {
    Activity activity;
    boolean findCoursePIN = false;
    boolean coursePINinDB = false;

    boolean parseCourseQuestions = false;
    boolean parseCreateResponse = false;


    private static Student student = new Student();

    /* A private Constructor prevents any other
     * class from instantiating.
     */
    private Student() {
    }

    /* Static 'instance' method */
    public static Student getInstance() {
        return student;
    }


    public boolean logInToCourse(int pin) {
        if (netCheck()) {
            PrepareQuery task = new PrepareQuery();
            this.findCoursePIN = true;
//            AsyncTask<String, Void, ResultSet> atask = null;
            try {
                ResultSet rs = task.execute("SELECT * FROM courses WHERE pin = " + String.valueOf(pin)).get();
                while (rs.next()) {
                    int ridc = rs.getInt("id_course");
                    return true;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }


//            while(atask.getStatus() != AsyncTask.Status.FINISHED){
//                try {
//                    Thread.sleep(300);
//                    Toast.makeText(activity, "sleep", Toast.LENGTH_LONG).show();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            Toast.makeText(activity, "done", Toast.LENGTH_LONG).show();
//            Toast.makeText(activity, String.valueOf(ridc), Toast.LENGTH_LONG).show();


        }
        return false;
//        if (coursePINinDB){
//            return true;
//        }else{
//            return false;
//        }
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
        ConnectivityManager connMgr = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            //Toast.makeText(activity, "", Toast.LENGTH_LONG).show();
            return true;
        } else {
            Toast.makeText(activity, "connectivy issue", Toast.LENGTH_LONG).show();
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

            if (findCoursePIN) {

//                int id_course = -1;
//                try {
//                    while (result.next()) {
//                        int ridc = result.getInt("id_course");
//                        coursePINinDB = true;
//                    }
////                    if (id_course == -1) {
////                        Toast.makeText(mainActivity, "No such course! " + result.getFetchSize(), Toast.LENGTH_LONG).show();
////                        return;
////                    }
//                    findCoursePIN = false;
//                    return;
//
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
            } else if (parseCourseQuestions) {
                try {
                    String response = "";

                    while (result.next()) {
                        String rtext = result.getString("text");
                        response = response + rtext + "\n";
                        Toast.makeText(activity, "Query done; " + response, Toast.LENGTH_LONG).show();

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
                    Toast.makeText(activity, "Query INSERTED; ", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(activity, "Record NOT inserted", Toast.LENGTH_LONG).show();
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

    public class getInstance extends Student {
    }
}
