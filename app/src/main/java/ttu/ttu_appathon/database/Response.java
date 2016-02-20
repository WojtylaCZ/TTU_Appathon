package ttu.ttu_appathon.database;

/**
 * Created by wojtyla on 2/20/16.
 */
public class Response {

    int id_reponse;
    int id_question;

    public Response(int id_reponse, int id_question) {
        this.id_reponse = id_reponse;
        this.id_question = id_question;
    }

    public int getId_reponse() {
        return id_reponse;
    }

    public void setId_reponse(int id_reponse) {
        this.id_reponse = id_reponse;
    }

    public int getId_question() {
        return id_question;
    }

    public void setId_question(int id_question) {
        this.id_question = id_question;
    }

    @Override
    public String toString() {
        return "Response{" +
                "id_reponse=" + id_reponse +
                ", id_question=" + id_question +
                '}';
    }
}
