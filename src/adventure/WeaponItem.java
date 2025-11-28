package adventure;

public class WeaponItem extends Item {

    private int attackBoost;

    public WeaponItem(String id, String name, String description, int attackBoost) {
        super(id, name, description);
        this.attackBoost = attackBoost;
    }

    @Override
    public void onUse(Player p, GameEngine ctx) {
        System.out.println(getName() + " kuşandın.");
        ctx.increasePlayerAttack(attackBoost);
        // Silah kalıcı olabilir, envanterde kalsın
    }
}
