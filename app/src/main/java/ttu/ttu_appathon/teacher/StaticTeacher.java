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
import java.sql.Statement;

import ttu.ttu_appathon.util.Util;

/**
 * Created by wojtyla on 2/20/16.
 */
public class StaticTeacher {

    public static int coursePIN = 0;

    static boolean parseResponses = false;
    static boolean parseCreateSurvey = false;
    static Activity activity;


    private static StaticTeacher staticTeacher = new StaticTeacher( );

    /* A private Constructor prevents any other
     * class from instantiating.
     */
    private StaticTeacher(){ }

    /* Static 'instance' method */
    public static StaticTeacher getInstance( ) {
        return staticTeacher;
    }

    public static int getCoursePIN(){
        return coursePIN;
    }



    public static void createCourseSurvey(Activity a) {
        activity = a;

        int randomPIN = (int) (Math.random() * 9000) + 1000;

        coursePIN = randomPIN;

        if (netCheck(a)) {
            PrepareUpdate task = new PrepareUpdate();
            task.execute("INSERT into courses (`pin`) VALUES(" + randomPIN + ")");
            parseCreateSurvey = true;
        }
    }
    private static boolean netCheck(Activity a) {
        ConnectivityManager connMgr = (ConnectivityManager) a.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Toast.makeText(a, "Internet ok!", Toast.LENGTH_LONG).show();
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

                    return;
                } else {
//                    Toast.makeText(mainActivity, "Record NOT inserted", Toast.LENGTH_LONG).show();
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
