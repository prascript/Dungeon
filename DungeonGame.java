import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Main class that contains the game logic
public class DungeonGame {

    // Player class to manage health and inventory
    static class Player {
        private String name;
        private int health;
        private List<String> inventory;

        public Player(String name, int health) {
            this.name = name;
            this.health = health;
            this.inventory = new ArrayList<>();
        }

        public String getName() {
            return name;
        }

        public int getHealth() {
            return health;
        }

        public void takeDamage(int damage) {
            this.health -= damage;
        }

        public void heal(int amount) {
            this.health += amount;
        }

        public void addItem(String item) {
            inventory.add(item);
        }

        public void removeItem(String item) {
            inventory.remove(item);
        }

        public boolean hasItem(String item) {
            return inventory.contains(item);
        }

        public void printStatus() {
            System.out.println("--- Player Status ---");
            System.out.println("Name: " + name);
            System.out.println("Health: " + health);
            System.out.println("Inventory: " + (inventory.isEmpty() ? "Empty" : String.join(", ", inventory)));
            System.out.println("---------------------");
        }
    }

    // A simple class to represent an item in the game
    static class Item {
        private String name;
        private String description;

        public Item(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }
    }

    // A simple class to represent a room in the game
    static class Room {
        private String description;
        private String enemy;
        private String item;
        private boolean hasVisited;

        public Room(String description, String enemy, String item) {
            this.description = description;
            this.enemy = enemy;
            this.item = item;
            this.hasVisited = false;
        }

        public String getDescription() {
            return description;
        }

        public String getEnemy() {
            return enemy;
        }

        public String getItem() {
            return item;
        }

        public boolean hasVisited() {
            return hasVisited;
        }

        public void setVisited(boolean visited) {
            this.hasVisited = visited;
        }
    }

    // Main method to run the game
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Dungeon Crawler mini-project!");
        System.out.print("What is your name, adventurer? ");
        String playerName = scanner.nextLine();

        // Game setup
        Player player = new Player(playerName, 100);
        int currentRoom = 0;
        Room[] rooms = new Room[3];
        rooms[0] = new Room("a dark and damp cellar. There is a rusty sword on the floor.", null, "Rusty Sword");
        rooms[1] = new Room("a long, narrow corridor. A goblin stands guard.", "Goblin", "Health Potion");
        rooms[2] = new Room("a large, treasure-filled vault!", "Dragon", "Golden Key");

        boolean gameOver = false;

        // Main game loop
        while (!gameOver) {
            System.out.println("\nYou are in " + rooms[currentRoom].getDescription());

            // Check for unhandled items or enemies
            if (!rooms[currentRoom].hasVisited()) {
                if (rooms[currentRoom].getEnemy() != null) {
                    System.out.println("A " + rooms[currentRoom].getEnemy() + " attacks!");
                    player.takeDamage(20);
                    System.out.println("You take 20 damage.");
                }
                if (rooms[currentRoom].getItem() != null) {
                    System.out.println("You found a " + rooms[currentRoom].getItem() + "!");
                    player.addItem(rooms[currentRoom].getItem());
                }
                rooms[currentRoom].setVisited(true);
            }

            player.printStatus();

            if (player.getHealth() <= 0) {
                System.out.println("\nYour health dropped to zero. You have been defeated!");
                gameOver = true;
                continue;
            }

            // Player choices
            System.out.println("\nWhat would you like to do? (type 'move', 'use', or 'quit')");
            String command = scanner.nextLine().toLowerCase();

            switch (command) {
                case "move":
                    System.out.println("Which direction do you want to move? (type 'forward' or 'back')");
                    String direction = scanner.nextLine().toLowerCase();
                    if (direction.equals("forward") && currentRoom < rooms.length - 1) {
                        currentRoom++;
                        System.out.println("You move into the next room.");
                    } else if (direction.equals("back") && currentRoom > 0) {
                        currentRoom--;
                        System.out.println("You go back to the previous room.");
                    } else {
                        System.out.println("You can't go that way.");
                    }
                    break;
                case "use":
                    System.out.println("What item do you want to use? (type 'health potion')");
                    String itemToUse = scanner.nextLine().toLowerCase();
                    if (itemToUse.equals("health potion") && player.hasItem("Health Potion")) {
                        player.heal(30);
                        player.removeItem("Health Potion");
                        System.out.println("You used a Health Potion and healed for 30 health.");
                    } else {
                        System.out.println("You don't have that item or it can't be used now.");
                    }
                    break;
                case "quit":
                    System.out.println("You quit the game. Goodbye!");
                    gameOver = true;
                    break;
                default:
                    System.out.println("Invalid command. Please try again.");
                    break;
            }
        }
        scanner.close();
    }
}
