
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

class Passenger {
    String id;
    String name;
    String destination;

    Passenger(String id, String name, String destination) {
        this.id = id;
        this.name = name;
        this.destination = destination;
    }

    void display() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Destination: " + destination);
    }
}

class CircularQueue {
    Passenger queue[];
    int front, rear, count, capacity;

    CircularQueue(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }

        this.capacity = capacity;
        queue = new Passenger[capacity];
        front = rear = -1;
        count = 0;
    }

    // Add passenger
    void enqueue(Passenger p) {
        if (p == null) {
            System.out.println("Invalid passenger!");
            return;
        }

        if (count == capacity) {
            System.out.println("Queue is FULL!");
            return;
        }

        if (front == -1) {
            front = rear = 0;
        } else {
            rear = (rear + 1) % capacity;
        }

        queue[rear] = p;
        count++;

        System.out.println("Passenger added successfully!");
    }

    // Issue ticket
    void dequeue() {
        if (front == -1 || count == 0) {
            System.out.println("Queue is EMPTY!");
            return;
        }

        try {
            if (queue[front] == null) {
                System.out.println("Invalid passenger data!");
                return;
            }

            System.out.println("Ticket issued to:");
            queue[front].display();

            queue[front] = null;

            if (front == rear) {
                front = rear = -1;
            } else {
                front = (front + 1) % capacity;
            }

            count--;

        } catch (NullPointerException e) {
            System.out.println("Error: Passenger details are unavailable!");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: Invalid queue position!");
        }
    }

    // Display queue
    void display() {
        if (count == 0) {
            System.out.println("Queue is EMPTY!");
            return;
        }

        try {
            System.out.println("\nPassenger Details:");

            for (int i = 0; i < count; i++) {
                int index = (front + i) % capacity;

                if (queue[index] != null) {
                    queue[index].display();
                    System.out.println("----------------");
                }
            }

        } catch (NullPointerException e) {
            System.out.println("Error: Passenger data is missing!");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: Invalid array index!");
        }
    }

    // Search passenger
    void search(String id) {
        for (int i = 0; i < count; i++) {
            int index = (front + i) % capacity;

            if (queue[index] != null &&
                queue[index].id.equalsIgnoreCase(id)) {

                System.out.println("Passenger Found!");
                queue[index].display();
                return;
            }
        }

        System.out.println("Passenger not found!");
    }

    // Check duplicate ID
    boolean searchExists(String id) {
        for (int i = 0; i < count; i++) {
            int index = (front + i) % capacity;

            if (queue[index] != null &&
                queue[index].id.equalsIgnoreCase(id)) {
                return true;
            }
        }

        return false;
    }

    // Cancel booking
    void cancel(String id) {
        int position = -1;

        for (int i = 0; i < count; i++) {
            int index = (front + i) % capacity;

            if (queue[index] != null &&
                queue[index].id.equalsIgnoreCase(id)) {
                position = i;
                break;
            }
        }

        if (position == -1) {
            System.out.println("Passenger not found!");
            return;
        }

        for (int i = position; i < count - 1; i++) {
            int current = (front + i) % capacity;
            int next = (front + i + 1) % capacity;

            queue[current] = queue[next];
        }

        queue[rear] = null;
        count--;

        if (count == 0) {
            front = rear = -1;
        } else {
            rear = (rear - 1 + capacity) % capacity;
        }

        System.out.println("Booking cancelled successfully!");
    }
}

public class Main {

    // Safe integer input
    static Integer readInt(Scanner sc) {
        while (true) {
            try {
                int value = sc.nextInt();
                sc.nextLine();
                return value;

            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Enter a number.");
                sc.nextLine();

            } catch (NoSuchElementException e) {
                System.out.println("Input ended unexpectedly.");
                return null;

            } catch (IllegalStateException e) {
                System.out.println("Scanner is already closed!");
                return null;
            }
        }
    }

    // Safe text input
    static String readText(Scanner sc, String message) {
        while (true) {
            try {
                System.out.print(message);
                String value = sc.nextLine().trim();

                if (!value.isEmpty()) {
                    return value;
                }

                System.out.println("Input cannot be empty!");

            } catch (NoSuchElementException e) {
                System.out.println("Input ended unexpectedly.");
                return null;

            } catch (IllegalStateException e) {
                System.out.println("Scanner is already closed!");
                return null;
            }
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        CircularQueue busQueue = new CircularQueue(5);
        CircularQueue trainQueue = new CircularQueue(5);

        int choice = 0;

        try {
            do {
                System.out.println("\n===== TRANSPORT MENU =====");
                System.out.println("1. Bus");
                System.out.println("2. Train");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");

                Integer input = readInt(sc);

                if (input == null) {
                    break;
                }

                choice = input;

                CircularQueue selectedQueue = null;

                if (choice == 1) {
                    selectedQueue = busQueue;
                    System.out.println("BUS QUEUE SELECTED");

                } else if (choice == 2) {
                    selectedQueue = trainQueue;
                    System.out.println("TRAIN QUEUE SELECTED");

                } else if (choice == 3) {
                    System.out.println("Exiting program...");
                    break;

                } else {
                    System.out.println("Invalid choice!");
                    continue;
                }

                int operation = 0;

                do {
                    System.out.println("\n===== OPERATIONS MENU =====");
                    System.out.println("1. Add Passenger");
                    System.out.println("2. Issue Ticket");
                    System.out.println("3. Display Queue");
                    System.out.println("4. Search Passenger");
                    System.out.println("5. Cancel Booking");
                    System.out.println("6. Back to Main Menu");
                    System.out.print("Enter your choice: ");

                    Integer op = readInt(sc);

                    if (op == null) {
                        return;
                    }

                    operation = op;

                    switch (operation) {

                        case 1: {
                            if (selectedQueue.count == selectedQueue.capacity) {
                                System.out.println("Queue is FULL!");
                                break;
                            }

                            String id = readText(sc, "Enter Passenger ID: ");
                            if (id == null) return;

                            if (selectedQueue.searchExists(id)) {
                                System.out.println("Duplicate ID!");
                                break;
                            }

                            String name = readText(sc, "Enter Name: ");
                            if (name == null) return;

                            String destination = readText(sc, "Enter Destination: ");
                            if (destination == null) return;

                            Passenger p = new Passenger(id, name, destination);
                            selectedQueue.enqueue(p);
                            break;
                        }

                        case 2:
                            selectedQueue.dequeue();
                            break;

                        case 3:
                            selectedQueue.display();
                            break;

                        case 4: {
                            String id = readText(sc, "Enter Passenger ID: ");
                            if (id == null) return;
                            selectedQueue.search(id);
                            break;
                        }

                        case 5: {
                            String id = readText(sc, "Enter Passenger ID: ");
                            if (id == null) return;
                            selectedQueue.cancel(id);
                            break;
                        }

                        case 6:
                            System.out.println("Returning to Main Menu...");
                            break;

                        default:
                            System.out.println("Invalid operation!");
                    }

                } while (operation != 6);

            } while (choice != 3);

        } catch (NullPointerException e) {
            System.out.println("Unexpected null value encountered!");

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Unexpected invalid array position!");

        } finally {
            sc.close();
            System.out.println("Program terminated.");
        }
    }
}
