package adventure;

public class KeyItem extends Item {

    private String keyId; // ör: "red", "blue" vb.

    public KeyItem(String id, String name, String description, String keyId) {
        super(id, name, description);
        this.keyId = keyId;
    }

    public String getKeyId() {
        return keyId;
    }

    @Override
    public void onUse(Player p, GameEngine ctx) {
        ctx.useKeyOnCurrentRoom(this);
    }
}
