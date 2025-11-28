package adventure;

public class PotionItem extends Item {

    private int healAmount;

    public PotionItem(String id, String name, String description, int healAmount) {
        super(id, name, description);
        this.healAmount = healAmount;
    }

    @Override
    public void onUse(Player p, GameEngine ctx) {
        System.out.println(getName() + " içtin.");
        ctx.healPlayer(healAmount);
        // İksir kullanıldıktan sonra envanterden sil
        p.removeItem(this);
    }
}
