package adventure;

import java.util.*;

public class Room {

    private String id;
    private String name;
    private String description;

    private Map<String, Room> exits = new HashMap<>();
    private List<Item> items = new ArrayList<>();
    private List<NPC> npcs = new ArrayList<>();

    public Room(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public void connect(String direction, Room target) {
        // direction küçük harfe indir
        exits.put(direction.toLowerCase(), target);
    }

    public Room getExit(String direction) {
        if (direction == null) return null;
        direction = direction.toLowerCase();
        return exits.get(direction);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public Item findItemById(String id) {
        for (Item item : items) {
            if (item.getId().equalsIgnoreCase(id)) {
                return item;
            }
        }
        return null;
    }

    public void addNPC(NPC npc) {
        npcs.add(npc);
    }

    public void removeNPC(NPC npc) {
        npcs.remove(npc);
    }

    public NPC findNPCByName(String name) {
        for (NPC npc : npcs) {
            if (npc.getName().equalsIgnoreCase(name)) {
                return npc;
            }
        }
        return null;
    }

    public void describe() {
        System.out.println("\nŞu anda bulunduğun yer: " + name);
        System.out.println(description);

        // Çıkışlar
        if (exits.isEmpty()) {
            System.out.println("Çıkışlar: yok");
        } else {
            System.out.print("Çıkışlar: ");
            System.out.println(String.join(", ", exits.keySet()));
        }

        // Eşyalar
        if (items.isEmpty()) {
            System.out.println("Eşyalar: yok");
        } else {
            System.out.print("Eşyalar: ");
            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                System.out.print(item.getId());
                if (i < items.size() - 1) System.out.print(", ");
            }
            System.out.println();
        }

        // Karakterler
        if (npcs.isEmpty()) {
            System.out.println("Karakterler: yok");
        } else {
            System.out.print("Karakterler: ");
            for (int i = 0; i < npcs.size(); i++) {
                NPC npc = npcs.get(i);
                System.out.print(npc.getName());
                if (i < npcs.size() - 1) System.out.print(", ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public String getId() {
        return id;
    }
}
