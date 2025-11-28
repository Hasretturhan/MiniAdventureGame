package adventure;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private Room currentRoom;
    private List<Item> inventory = new ArrayList<>();
    private int hp;
    private int maxHp = 20;
    private int attackPower = 5;

    public Player(Room startRoom) {
        this.currentRoom = startRoom;
        this.hp = maxHp;
    }

    public boolean move(String direction) {
        Room next = currentRoom.getExit(direction);
        if (next == null) {
            return false;
        }
        currentRoom = next;
        return true;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void removeItem(Item item) {
        inventory.remove(item);
    }

    public Item findItemById(String id) {
        for (Item item : inventory) {
            if (item.getId().equalsIgnoreCase(id)) {
                return item;
            }
        }
        return null;
    }

    public String inventoryText() {
        if (inventory.isEmpty()) {
            return "Envanterin boş.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Envanter:\n");
        for (Item item : inventory) {
            sb.append("- ")
              .append(item.getId())
              .append(" (")
              .append(item.getName())
              .append(")\n");
        }
        return sb.toString();
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = Math.max(0, hp);
    }

    public void heal(int amount) {
        this.hp = Math.min(maxHp, hp + amount);
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void increaseAttack(int amount) {
        this.attackPower += amount;
    }
}
