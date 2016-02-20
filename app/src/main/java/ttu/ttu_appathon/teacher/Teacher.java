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
import java.util.concurrent.ExecutionException;

import ttu.ttu_appathon.MainActivity;
import ttu.ttu_appathon.database.Course;
import ttu.ttu_appathon.database.Question;
import ttu.ttu_appathon.util.Util;

/**
 * Created by wojtyla on 2/20/16.
 */
public class Teacher {


    Activity activity;

    ResultSet queryResult;
    Integer queryUpdate;
    boolean parseResponses = false;
    boolean parseCreateSurvey = false;

    private static Teacher teacher = new Teacher();

    Course course;

    /* A private Constructor prevents any other
     * class from instantiating.
     */
    private Teacher() {
    }

    /* Static 'instance' method */
    public static Teacher getInstance() {
        return teacher;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    public Course getCourse() {
        return this.course;
    }

    public Course createCourse() {

        int randomPIN = (int) (Math.random() * 9000) + 1000;
        int randomID = (int) (Math.random() * 90000) + 100;

        if (netCheck()) {
            PrepareUpdate task = new PrepareUpdate();

            try {
                Integer rs = task.execute("INSERT into courses (`id_course`,`pin`) VALUES(" + randomID + "," + randomPIN + ")").get();
                if (rs == 1) {
                    Course course = new Course(randomID,randomPIN);
                    return course;
                    //return randomID;
                } else {
                    return null;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public Question createQuestion() {

        return null;
//        int randomPIN = (int) (Math.random() * 9000) + 1000;
//        int randomID = (int) (Math.random() * 90000) + 100;
//
//        if (netCheck()) {
//            PrepareUpdate task = new PrepareUpdate();
//
//            try {
//                Integer rs = task.execute("INSERT into courses (`id_course`,`pin`) VALUES(" + randomID + "," + randomPIN + ")").get();
//                if (rs == 1) {
//                    Course course = new Course(randomID,randomPIN);
//                    return course;
//                    //return randomID;
//                } else {
//                    return null;
//                }
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
    }

    public void createQuestion(int id_course) {
        if (netCheck()) {
            PrepareUpdate task = new PrepareUpdate();
            task.execute("INSERT into questions (`id_course`,`text`) VALUES(" + id_course + ", " + Util.text + ")");
            this.parseCreateSurvey = true;
        }

    }

    public void getResponses(int id_question) {
        if (netCheck()) {
            PrepareQuery task = new PrepareQuery();
            task.execute("SELECT * FROM responses WHERE ID_QUESTION = " + String.valueOf(id_question));
            this.parseResponses = true;
        }
    }

    private boolean netCheck() {
        ConnectivityManager connMgr = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Toast.makeText(activity, "ok!", Toast.LENGTH_LONG).show();
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

            if (parseResponses) {
                String response = "";
                try {
                    while (result.next()) {
                        int ridr = result.getInt("id_response");
                        int ridq = result.getInt("id_question");
                        int renum = result.getInt("enum");
                        response = response + String.valueOf(ridr) + String.valueOf(ridq) + String.valueOf(renum) + "\n";
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

//                while (res.next()) {
//                    int id = res.getInt("id_course");
//                    String msg = res.getString("pin");
//                    response = response + String.valueOf(id) + msg + "\n";
////                System.out.println(id + "\t" + msg);
//                }

//                int val = st.executeUpdate("INSERT into courses VALUES(" + Math.random() * 100 + "," + 5555 + ")");
//                if (val == 1)
//                    System.out.print("Successfully inserted value");


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

//            InputStream is = null;
//            // Only display the first 500 characters of the retrieved
//            // web page content.
//            int len = 500;
//
//            try {
//                URL url = new URL(myurl);
//                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//                conn.setReadTimeout(10000 /* milliseconds */);
//                conn.setConnectTimeout(15000 /* milliseconds */);
//                conn.setRequestMethod("GET");
//                conn.setDoInput(true);
//                // Starts the query
//                conn.connect();
//                int response = conn.getResponseCode();
////            Log.d(DEBUG_TAG, "The response is: " + response);
//                is = conn.getInputStream();
//
//                // Convert the InputStream into a string
//                String contentAsString = readIt(is, len);
//                return contentAsString;
//
//                // Makes sure that the InputStream is closed after the app is
//                // finished using it.
//            } finally {
//                if (is != null) {
//                    is.close();
//                }
//            }
//
//

//
//        public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
//            Reader reader = null;
//            reader = new InputStreamReader(stream, "UTF-8");
//            char[] buffer = new char[len];
//            reader.read(buffer);
//            return new String(buffer);
//        }
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

