package ttu.ttu_appathon.database;

/**
 * Created by wojtyla on 2/20/16.
 */
public class Course {
    int id_course;
    int pin;


    public Course(int id_course, int pin) {
        this.id_course = id_course;
        this.pin = pin;
    }




    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getId_course() {
        return id_course;
    }

    public void setId_course(int id_course) {
        this.id_course = id_course;
    }

    @Override
    public String toString() {
        return "Response{" +
                "id_course=" + id_course +
                ", pin=" + pin +
                '}';
    }
}
