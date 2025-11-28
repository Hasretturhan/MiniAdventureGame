package adventure;

public abstract class NPC {

    protected String name;

    public NPC(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void talk(Player player, GameEngine ctx);
}
