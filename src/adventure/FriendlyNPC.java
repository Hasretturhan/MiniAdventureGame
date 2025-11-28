package adventure;

public class FriendlyNPC extends NPC {

    private ConversationNode root;

    public FriendlyNPC(String name, ConversationNode root) {
        super(name);
        this.root = root;
    }

    @Override
    public void talk(Player player, GameEngine ctx) {
        ConversationNode current = root;
        while (current != null) {
            System.out.println(current.getText());
            if (current.getChoices().isEmpty()) {
                break;
            }

            for (int i = 0; i < current.getChoices().size(); i++) {
                ConversationChoice choice = current.getChoices().get(i);
                System.out.println((i + 1) + ") " + choice.getText());
            }

            String line = ctx.readLine();
            int choiceIndex;
            try {
                choiceIndex = Integer.parseInt(line.trim()) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Geçersiz seçim.");
                continue;
            }

            if (choiceIndex < 0 || choiceIndex >= current.getChoices().size()) {
                System.out.println("Geçersiz seçim.");
                continue;
            }

            ConversationChoice choice = current.getChoices().get(choiceIndex);
            current = choice.getNextNode();
        }
    }
}
