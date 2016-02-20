package ttu.ttu_appathon.database;

/**
 * Created by wojtyla on 2/20/16.
 */
public class Question {

    int id_question;
    int id_course;
    String text;

    public Question(int id_question, int id_course, String text) {
        this.id_question = id_question;
        this.id_course = id_course;
        this.text = text;
    }

    public int getId_question() {
        return id_question;
    }

    public void setId_question(int id_question) {
        this.id_question = id_question;
    }

    public int getId_course() {
        return id_course;
    }

    public void setId_course(int id_course) {
        this.id_course = id_course;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id_question=" + id_question +
                ", id_course=" + id_course +
                ", text=" + text +
                '}';
    }
}
