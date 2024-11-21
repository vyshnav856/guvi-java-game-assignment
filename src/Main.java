// Submitted by: Vyshnav R
// Email: vyshnavr856@gmail.com

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static int playerHealth = 100;
    static String currentRoom = "Entrance";
    static boolean hasTreasure = false;
    static ArrayList<String> inventory = new ArrayList<>();
    static boolean potionCollected = false;
    static boolean keyCollected = false;
    static boolean coinsCollected = false;
    static int[] monsterHealth = {100, 100};
    static int[] monsterDamage = {10, 50};
    static int[] playerDamage = {50, 10};
    static String[] monsterName = {"Dungeon Monster", "Forest Monster"};

    public static void printCurrentStatus() {
        System.out.println("\nYour current health is: " + playerHealth);
        System.out.println("You are currently in: " + currentRoom);

        switch (currentRoom) {
            case "Entrance" -> System.out.println("North: Forest, East: Treasure Room, West: Dungeon");
            case "Forest" -> System.out.println("South: Entrance");
            case "Treasure Room" -> System.out.println("West: Entrance");
            default -> System.out.println("East: Entrance");
        }
    }

    public static void talk() {
        switch (currentRoom) {
            case "Forest" -> {
                System.out.println("There is a wizard in the forest.");
                System.out.println("The wizard gives you the hint that the key is in the Dungeon");
            }

            case "Dungeon" -> {
                System.out.println("There is a man in the dungeon. The man gives you a treasure key.");
                if (!keyCollected) {
                    inventory.add("Treasure Key");
                }

                keyCollected = true;
            }

            default -> System.out.println("There is no one to talk to here");
        }
    }

    public static void collect() {
        if (currentRoom.equals("Treasure Room") && !coinsCollected) {
            System.out.println("You have collected gold coins");
            inventory.add("Gold Coins");
            coinsCollected = true;
        }

        else if (currentRoom.equals("Entrance") && !potionCollected) {
            System.out.println("You have collected a potion.");
            inventory.add("Potion");
            potionCollected = true;
        }

        else {
            System.out.println("There is nothing to collect here");
        }
    }

    public static void usePotion() {
        for (String item: inventory) {
            if (item.equals("Potion")) {
                System.out.println("You have used a potion. Your health has been restored");
                playerHealth = 100;
                inventory.remove("Potion");
                return;
            }
        }

        System.out.println("You don't have a potion to use");
    }

    public static void useKey() {
        for (String item: inventory) {
            if (item.equals("Treasure Key")) {
                if (currentRoom.equals("Treasure Room")) {
                    System.out.println("You have opened the treasure and won the game!");
                    hasTreasure = true;
                    return;
                }

                else {
                    System.out.println("You need to be in the treasure room to use the key");
                }
            }
        }

        System.out.println("You do not have a key to use");
    }

    public static void attack() {
        if (!currentRoom.equals("Dungeon") && !currentRoom.equals("Forest")) {
            System.out.println("There is nothing to fight here");
            return;
        }

        int index = currentRoom.equals("Dungeon") ? 0 : 1;

        if (monsterHealth[index] <= 0) {
            System.out.println("There is nothing to fight here");
            return;
        }

        System.out.println("You attacked the " + monsterName[index]);
        System.out.println("You deal " + playerDamage[index] + " damage");
        System.out.println("You take " + monsterDamage[index] + " damage");

        monsterHealth[index] -= playerDamage[index];
        playerHealth -= monsterDamage[index];

        if (playerHealth <= 0) {
            System.out.println("You have died, game over");
        }

        else if (monsterHealth[index] <= 0) {
            System.out.println("You have killed the monster!");
        }

        else {
            System.out.println("Monster health is now " + monsterHealth[index]);
        }
    }

    public static void run() {
        switch (currentRoom) {
            case "Dungeon" -> {
                if (monsterHealth[0] <= 0) {
                    System.out.println("There is nothing to run away from");
                }

                else {
                    System.out.println("You ran away from the dungeon monster");
                }
            }

            case "Forest" -> {
                if (monsterHealth[1] <= 0) {
                    System.out.println("There is nothing to run away from");
                }

                else {
                    System.out.println("You ran away from the forest monster");
                }
            }

            default -> System.out.println("There is nothing to run away from");
        }
    }

    public static void move(String destination) {
        switch (currentRoom) {
            case "Entrance" -> {
                switch (destination) {
                    case "north" -> currentRoom = "Forest";
                    case "east" -> currentRoom = "Treasure Room";
                    case "west" -> currentRoom = "Dungeon";
                    default -> System.out.println("You cannot go in that direction");
                }
            }

            case "Forest" -> {
                if (destination.equals("south")) {
                    currentRoom = "Entrance";
                }

                else {
                    System.out.println("You cannot go in that direction");
                }
            }

            case "Dungeon" -> {
                if (destination.equals("east")) {
                    currentRoom = "Entrance";
                }

                else {
                    System.out.println("You cannot go in that direction");
                }
            }

            case "Treasure Room" -> {
                if (destination.equals("west")) {
                    currentRoom = "Entrance";
                }

                else {
                    System.out.println("You cannot go in that direction");
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Welcome to Adventure Game!");
        System.out.println("Enter commands to play");

        while (!hasTreasure && playerHealth > 0) {
            printCurrentStatus();
            System.out.print("Enter command: ");
            String command = s.nextLine();

            switch (command) {
                case "talk" -> talk();
                case "check inventory" -> {
                    if (inventory.isEmpty()) {
                        System.out.println("Your inventory is empty");
                    }

                    else {
                        System.out.println("Your inventory: " + inventory);
                    }
                }

                case "collect" -> collect();
                case "use potion" -> usePotion();
                case "use key" -> useKey();
                case "attack" -> attack();
                case "run" -> run();
                case "go north" -> move("north");
                case "go south" -> move("south");
                case "go east" -> move("east");
                case "go west" -> move("west");
                default -> System.out.println("Invalid command");
            }
        }
    }
}