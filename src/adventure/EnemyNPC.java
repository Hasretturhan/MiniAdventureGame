package adventure;

public class EnemyNPC extends NPC {
    private int hp;
    private int attackDamage;

    public EnemyNPC(String name, int hp, int attackDamage) {
        super(name);
        this.hp = hp;
        this.attackDamage = attackDamage;
    }

    @Override
    public void talk(Player player, GameEngine ctx) {
        System.out.println(name + ": \"Buraya gelmemen gerekirdi!\"");

        // Basit dövüş sistemi: oyuncu ve düşman sırayla saldırıyor
        while (hp > 0 && player.getHp() > 0) {
            System.out.println("\nNe yapmak istiyorsun?");
            System.out.println("1) Saldır");
            System.out.println("2) Kaç");

            String line = ctx.readLine();
            if ("1".equals(line.trim())) {
                // Oyuncu saldırır
                hp -= player.getAttackPower();
                System.out.println("Düşmana " + player.getAttackPower() + " hasar verdin. (Düşman HP: " + hp + ")");
                if (hp <= 0) {
                    System.out.println(name + " yenildi!");
                    break;
                }

                // Düşman saldırır
                ctx.damagePlayer(attackDamage, name);
            } else if ("2".equals(line.trim())) {
                System.out.println("Kaçtın!");
                break;
            } else {
                System.out.println("Geçersiz seçim.");
            }

            if (player.getHp() <= 0) {
                break;
            }
        }
    }
}
