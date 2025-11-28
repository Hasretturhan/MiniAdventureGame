package adventure;

public class ConversationChoice {

    private String text;
    private ConversationNode nextNode;

    public ConversationChoice(String text, ConversationNode nextNode) {
        this.text = text;
        this.nextNode = nextNode;
    }

    public String getText() {
        return text;
    }

    public ConversationNode getNextNode() {
        return nextNode;
    }
}
