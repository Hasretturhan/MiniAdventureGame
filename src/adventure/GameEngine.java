  package adventure;

import java.util.Scanner;

public class GameEngine {

    private Scanner scanner;
    private Player player;
    private boolean running = true;

    // Bazı özel odaları referans tutalım (örneğin Salon ve Silah Odası)
    private Room salonRoom;
    private Room weaponRoom;

    // Kırmızı kapı kilitli mi?
    private boolean redDoorLocked = true;

    public GameEngine() {
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        initWorld();
        System.out.println("=== Mini Macera ===");
        System.out.println("Yardım için 'help' yazabilirsiniz.\n");
        player.getCurrentRoom().describe();

        while (running) {
            try {
                System.out.print("> ");
                String line = scanner.nextLine();
                handleCommand(line);
            } catch (Exception e) {
                System.out.println("Beklenmeyen bir hata oluştu: " + e.getMessage());
                // Program sonlanmasın
            }
        }

        System.out.println("\nOyun sona erdi. Teşekkürler!");
    }

    private void initWorld() {
        // Odalar
        salonRoom = new Room("salon", "Salon",
                "Büyük bir salon. Doğu ve güneyde çıkışlar var.");
        weaponRoom = new Room("weapon", "Silah Odası",
                "Duvarlarda kılıçlar ve eski zırhlar asılı.");
        Room corridor = new Room("corridor", "Koridor",
                "Uzun ve dar bir koridor.");
        Room storage = new Room("storage", "Depo",
                "Eski eşyalarla dolu karanlık bir depo.");
        Room healingRoom = new Room("healing", "Şifa Odası",
                "Huzur veren bir oda. Ortada parlak bir iksir masası var.");

        // Çıkışlar
        salonRoom.connect("east", weaponRoom);    // Kırmızı kapı ile kilitli kabul edeceğiz
        salonRoom.connect("south", corridor);
        corridor.connect("north", salonRoom);
        corridor.connect("east", storage);
        corridor.connect("south", healingRoom);
        storage.connect("west", corridor);
        healingRoom.connect("north", corridor);

        // Eşyalar
        KeyItem redKey = new KeyItem("key_red", "Kırmızı anahtar",
                "Salonun doğusundaki kırmızı kapıyı açar.", "red");
        salonRoom.addItem(redKey);

        PotionItem smallPotion = new PotionItem("potion_small", "Küçük İksir",
                "5 HP kazandırır.", 5);
        storage.addItem(smallPotion);

        WeaponItem sword = new WeaponItem("sword", "Paslı Kılıç",
                "Saldırı gücünü 5 artırır.", 5);
        weaponRoom.addItem(sword);

        // NPC'ler
        FriendlyNPC guard = createGuardNPC();
        salonRoom.addNPC(guard);

        EnemyNPC slime = new EnemyNPC("Sümüksü Yaratık", 15, 4);
        healingRoom.addNPC(slime);

        // Oyuncu
        player = new Player(salonRoom);
    }

    private FriendlyNPC createGuardNPC() {
        ConversationNode root = new ConversationNode(
                "Muhafız: \"Selam yolcu. Bu salonun doğusunda kilitli bir kapı var.\"");

        ConversationNode doorInfo = new ConversationNode(
                "Muhafız: \"Kırmızı kapı, özel bir anahtarla açılır. Etrafta iyi bak.\"");

        ConversationNode goodbye = new ConversationNode(
                "Muhafız: \"Yolun açık olsun.\"");

        root.addChoice(new ConversationChoice("Kapı hakkında bilgi sor", doorInfo));
        root.addChoice(new ConversationChoice("Boş ver, yoluma devam edeyim", goodbye));

        // doorInfo'dan sonra geri dönüş yok, sadece veda
        doorInfo.addChoice(new ConversationChoice("Teşekkür et ve ayrıl", goodbye));

        return new FriendlyNPC("Muhafız", root);
    }

    private void handleCommand(String line) {
        if (line == null) return;
        line = line.trim();
        if (line.isEmpty()) return;

        String[] parts = line.split("\\s+", 2);
        String command = parts[0].toLowerCase();
        String args = parts.length > 1 ? parts[1] : "";

        switch (command) {
            case "look":
                handleLook();
                break;
            case "go":
                handleGo(args);
                break;
            case "take":
                handleTake(args);
                break;
            case "use":
                handleUse(args);
                break;
            case "talk":
                handleTalk(args);
                break;
            case "say":
                handleSay(args);
                break;
            case "inv":
                handleInventory();
                break;
            case "help":
                handleHelp();
                break;
            case "quit":
                running = false;
                break;
            default:
                System.out.println("Bilinmeyen komut.");
        }
    }

    private void handleLook() {
        player.getCurrentRoom().describe();
    }

    private void handleGo(String direction) {
        if (direction.isEmpty()) {
            System.out.println("Kullanım: go <yön>");
            return;
        }

        direction = direction.toLowerCase();
        Room current = player.getCurrentRoom();

        // Özel: Salon -> doğu (kırmızı kapı kilitli)
        if (current == salonRoom && (direction.equals("east") || direction.equals("doğu"))) {
            if (redDoorLocked) {
                System.out.println("Kırmızı kapı kilitli.");
                return;
            }
        }

        boolean success = player.move(direction);
        if (!success) {
            System.out.println("Bu yönde çıkış yok.");
        } else {
            player.getCurrentRoom().describe();
        }
    }

    private void handleTake(String itemId) {
        if (itemId.isEmpty()) {
            System.out.println("Kullanım: take <eşya_id>");
            return;
        }

        Room room = player.getCurrentRoom();
        Item item = room.findItemById(itemId);
        if (item == null) {
            System.out.println("Böyle bir eşya/karakter bulunmuyor.");
            return;
        }

        room.removeItem(item);
        player.addItem(item);
        System.out.println(item.getName() + " envanterine eklendi.");
    }

    private void handleUse(String itemId) {
        if (itemId.isEmpty()) {
            System.out.println("Kullanım: use <eşya_id>");
            return;
        }

        Item item = player.findItemById(itemId);
        if (item == null) {
            System.out.println("Bu eşya sende yok.");
            return;
        }

        item.onUse(player, this);
    }

    private void handleTalk(String npcName) {
        if (npcName.isEmpty()) {
            System.out.println("Kullanım: talk <npc_adı>");
            return;
        }

        Room room = player.getCurrentRoom();
        NPC npc = room.findNPCByName(npcName);
        if (npc == null) {
            System.out.println("Böyle bir eşya/karakter bulunmuyor.");
            return;
        }

        npc.talk(player, this);
    }

    private void handleSay(String text) {
        if (text.isEmpty()) {
            System.out.println("Söylemek için bir şey yazmalısın.");
            return;
        }
        System.out.println("Sen: \"" + text + "\"");
    }

    private void handleInventory() {
        System.out.println(player.inventoryText());
    }

    private void handleHelp() {
        System.out.println("Kullanılabilir komutlar:");
        System.out.println("look          - Bulunduğun odayı incele");
        System.out.println("go <yön>      - Belirtilen yöne git (north, south, east, west)");
        System.out.println("take <id>     - Eşyayı al");
        System.out.println("use <id>      - Eşyayı kullan");
        System.out.println("talk <ad>     - NPC ile konuş");
        System.out.println("say <metin>   - Bir şey söyle");
        System.out.println("inv           - Envanteri göster");
        System.out.println("help          - Bu yardım mesajını göster");
        System.out.println("quit          - Oyundan çık");
    }

    // ---- Yardımcı metodlar (Item ve NPC'ler için) ----

    public String readLine() {
        System.out.print("> ");
        return scanner.nextLine();
    }

    public void damagePlayer(int amount, String sourceName) {
        player.setHp(player.getHp() - amount);
        System.out.println(sourceName + " sana " + amount + " hasar verdi. (HP: " + player.getHp() + ")");
        if (player.getHp() <= 0) {
            System.out.println("Öldün! Oyun bitti.");
            running = false;
        }
    }

    public boolean useKeyOnCurrentRoom(KeyItem key) {
        // Bu örnekte sadece kırmızı anahtarı, salonun doğusundaki kapıda kullanıyoruz
        if ("red".equals(key.getKeyId())
                && player.getCurrentRoom() == salonRoom
                && redDoorLocked) {

            redDoorLocked = false;
            System.out.println("Kapının kilidini açtın ve doğuya, Silah Odası'na geçtin.");
            player.move("east");
            player.getCurrentRoom().describe();
            // Anahtar tek kullanımlık olsun:
            player.removeItem(key);
            return true;
        }

        System.out.println("Bu anahtarı burada kullanamazsın.");
        return false;
    }

    public void healPlayer(int amount) {
        player.heal(amount);
        System.out.println("HP yenilendi. Güncel HP: " + player.getHp());
    }

    public void increasePlayerAttack(int amount) {
        player.increaseAttack(amount);
        System.out.println("Saldırı gücün arttı. Yeni saldırı gücü: " + player.getAttackPower());
    }

    public Player getPlayer() {
        return player;
    }
}
