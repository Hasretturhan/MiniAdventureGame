package adventure;

import java.util.ArrayList;
import java.util.List;

public class ConversationNode {

    private String text;
    private List<ConversationChoice> choices = new ArrayList<>();

    public ConversationNode(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public List<ConversationChoice> getChoices() {
        return choices;
    }

    public void addChoice(ConversationChoice choice) {
        choices.add(choice);
    }
}
