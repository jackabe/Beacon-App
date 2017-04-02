package nsa.com.museum;

/**
 * Created by Jack on 02/04/2017.
 */

public class Messages {

    String messageTitle;
    String messageQuestion;
    String messageAnswered;

    public Messages(String messageTitle, String messageQuestion, String messageAnswered) {
        this.messageTitle = messageTitle;
        this.messageQuestion = messageQuestion;
        this.messageAnswered = messageAnswered;
    }

    public Messages() {

    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageQuestion() {
        return messageQuestion;
    }

    public void setMessageQuestion(String messageQuestion) {
        this.messageQuestion = messageQuestion;
    }

    public String getmessageAnswered() {
        return messageAnswered;
    }

    public void setmessageAnswered(String messageAnswered) {
        this.messageAnswered = messageAnswered;
    }

}
