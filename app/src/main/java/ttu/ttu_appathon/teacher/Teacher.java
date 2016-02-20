package ttu.ttu_appathon.teacher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by wojtyla on 2/20/16.
 */
public class Teacher {

    //static final String

    public Teacher() {

    }

    public int createCourseSurvey() {

        int randomPIN = (int) (Math.random() * 9000) + 1000;

        return randomPIN;

    }

    public void connectToAndQueryDatabase(String usernamex, String passwordx) {

        String url = "jdbc:mysql://WH25.farma.gigaserver.cz:3306/";
        String dbName = "hbbtvcesko_cz_testing";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "34259_test";
        String password = "test";
        try {
            Class.forName(driver).newInstance();
            Connection conn = DriverManager.getConnection(url + dbName, userName, password);
            Statement st = conn.createStatement();
            ResultSet res = st.executeQuery("SELECT * FROM  event");
            while (res.next()) {
                int id = res.getInt("id");
                String msg = res.getString("msg");
                System.out.println(id + "\t" + msg);
            }
            int val = st.executeUpdate("INSERT into courses VALUES(" + 2 + "," + 5555 + ")");
            if (val == 1)
                System.out.print("Successfully inserted value");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

