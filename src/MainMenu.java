import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class MainMenu {

    ArrayList<Animal> animalList = new ArrayList<>();
    File animalFile = new File("Animals.txt");

    ArrayList<Crop> cropList = new ArrayList<>();

    File cropFile = new File("Crops.txt");

    private void createFilesIfNotExist() {
        try {
            if (!animalFile.exists()) {
                animalList.add(new Animal(1, "Sheep", 3));
                animalList.add(new Animal(2, "Cow", 5));
                saveAnimal();  // Save initial data when creating the file
            } else {
                // If the file exists, read the data into the animalList
                try (BufferedReader reader = new BufferedReader(new FileReader(animalFile))) {
                    String animals;

                    while ((animals = reader.readLine()) != null) {
                        Animal animal = Animal.parseCSV(animals);
                        animalList.add(animal);
                    }
                }
            }

            if (!cropFile.exists()) {
                cropList.add(new Crop(1, "Wheat", 40));
                cropList.add(new Crop(2, "Carrot", 30));
                saveCrop();  // Save initial data when creating the file
            } else {
                // If the file exists, read the data into the cropList
                try (BufferedReader reader = new BufferedReader(new FileReader(cropFile))) {
                    String crops;

                    while ((crops = reader.readLine()) != null) {
                        Crop crop = Crop.parseCSV(crops);
                        cropList.add(crop);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void viewAnimal() {
        try (BufferedReader reader = new BufferedReader(new FileReader(animalFile))) {
            System.out.println("Current list of Animals:");
            String animal;
            while ((animal = reader.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(animal, ",");
                System.out.println("ID: " + tokenizer.nextToken() + " Type: " + tokenizer.nextToken() + " Quantity: " + tokenizer.nextToken());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean animalIdExists(int id) {
        for (Animal animal : animalList) {
            if (animal.getId() == id) {
                return true; // ID already exists
            }
        }
        return false; // ID does not exist
    }

    public void addAnimal() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Add Animal:");

        boolean animalAdded = false;

        while (!animalAdded) {
            try {
                System.out.print("Create id: ");
                int id = scanner.nextInt();
                scanner.nextLine();

                // Check if the ID already exists in the loaded data and newly created data
                if (animalIdExists(id) || animalIdExistsInFile(id)) {
                    System.out.println("Animal with ID " + id + " already exists. Please enter another ID.");
                } else {
                    System.out.print("Enter species: ");
                    String type = scanner.nextLine();

                    int quantity;

                    try {
                        System.out.print("Enter quantity: ");
                        quantity = scanner.nextInt();
                        scanner.nextLine();
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid integer for the quantity. Defaulting to 0.");
                        quantity = 0; // Set quantity to a default value in case of invalid input
                        scanner.nextLine();
                    }

                    animalList.add(new Animal(id, type, quantity));
                    System.out.println("Animal added successfully!");

                    animalAdded = true; // Set to true to exit the loop
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer for the ID.");
                scanner.nextLine();
            }
        }
    }
    private boolean animalIdExistsInFile(int id) {
        for (Animal animal : loadAnimalsFromFile()) {
            if (animal.getId() == id) {
                return true; // ID already exists in the file
            }
        }
        return false; // ID does not exist in the file
    }

    private List<Animal> loadAnimalsFromFile() {
        List<Animal> animalsFromFile = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(animalFile))) {
            String animals;
            while ((animals = reader.readLine()) != null) {
                Animal animal = Animal.parseCSV(animals);
                animalsFromFile.add(animal);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return animalsFromFile;
    }

    private void saveAnimal() {

        File animalFile = this.animalFile;

        try {
            FileWriter fileWriter = new FileWriter(animalFile);
            try (BufferedWriter bf = new BufferedWriter(fileWriter)) {

                for (Animal animal : animalList) {
                    bf.append(animal.get_csv());
                    bf.newLine();
                }

                bf.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeAnimal() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Current list of Animals:");
        viewAnimal();

        System.out.print("Enter the ID of the Animal to remove: ");
        int idToRemove = scanner.nextInt();
        scanner.nextLine();

        // Create a temporary list to store animals to keep
        ArrayList<Animal> animalsToKeep = new ArrayList<>();

        // Read the existing animals from the file
        try (BufferedReader reader = new BufferedReader(new FileReader(animalFile))) {
            String animals;

            while ((animals = reader.readLine()) != null) {
                Animal animal = Animal.parseCSV(animals);
                if (animal.getId() != idToRemove) {
                    animalsToKeep.add(animal);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write the updated list of animals back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(animalFile))) {
            for (Animal animal : animalsToKeep) {
                writer.write(animal.get_csv());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Animal with ID " + idToRemove + " removed successfully!");
        viewAnimal();
    }


    private boolean cropIdExists(int id) {
        for (Crop crop : cropList) {
            if (crop.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public void addCrop() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Add Crop:");

        boolean cropAdded = false;

        while (!cropAdded) {
            try {
                System.out.print("Create id: ");
                int id = scanner.nextInt();
                scanner.nextLine();

                // Check if the ID already exists in the loaded data and newly created data
                if (cropIdExists(id) || cropIdExistsInFile(id)) {
                    System.out.println("Crop with ID " + id + " already exists. Please enter another ID.");
                } else {
                    // ID is unique, so proceed to input species and quantity
                    System.out.print("Enter species: ");
                    String type = scanner.nextLine();

                    int quantity;

                    try {
                        System.out.print("Enter quantity: ");
                        quantity = scanner.nextInt();
                        scanner.nextLine();
                    } catch (java.util.InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid integer for the quantity. Defaulting to 0.");
                        quantity = 0; // Set quantity to a default value in case of invalid input
                        scanner.nextLine();
                    }

                    cropList.add(new Crop(id, type, quantity));
                    System.out.println("Crop added successfully!");

                    cropAdded = true; // Exit the loop
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer for the ID.");
                scanner.nextLine();
            }
        }
    }

    private boolean cropIdExistsInFile(int id) {
        for (Crop crop : loadCropsFromFile()) {
            if (crop.getId() == id) {
                return true; // ID already exists in the file
            }
        }
        return false; // ID does not exist in the file
    }

    private List<Crop> loadCropsFromFile() {
        List<Crop> cropsFromFile = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(cropFile))) {
            String crops;
            while ((crops = reader.readLine()) != null) {
                Crop crop = Crop.parseCSV(crops);
                cropsFromFile.add(crop);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cropsFromFile;
    }


    private void viewCrop() {
        try (BufferedReader reader = new BufferedReader(new FileReader(cropFile))) {
            System.out.println("Current list of Crops:");
            String crop;
            while ((crop = reader.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(crop, ",");
                System.out.println("ID: " + tokenizer.nextToken() + " Type: " + tokenizer.nextToken() + " Quantity: " + tokenizer.nextToken());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void saveCrop() {
        File cropFile = this.cropFile;

        try {
            FileWriter fileWriter = new FileWriter(cropFile);
            try (BufferedWriter bf = new BufferedWriter(fileWriter)) {

                for (Crop crop : cropList) {
                    bf.write(crop.get_csv());
                    bf.newLine();
                }

                bf.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeCrop() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Current list of Crop:");
        viewCrop();


        System.out.print("Enter the ID of the Crop to remove: ");
        int idToRemove = scanner.nextInt();
        scanner.nextLine();

        // Create a temporary list to store crop to keep
        ArrayList<Crop> cropsToKeep = new ArrayList<>();

        // Read the existing animals from the file
        try (BufferedReader reader = new BufferedReader(new FileReader(cropFile))) {
            String crops;

            while ((crops = reader.readLine()) != null) {
                Crop crop = Crop.parseCSV(crops);
                if (crop.getId() != idToRemove) {
                    cropsToKeep.add(crop);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write the updated list of animals back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(cropFile))) {
            for (Crop crop : cropsToKeep) {
                writer.write(crop.get_csv());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Crop with ID " + idToRemove + " removed successfully!");
    }

    public void feedAnimal() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Current list of Crops:");
        viewCrop();

        System.out.print("Enter the ID of the Crop to feed: ");
        int cropId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        // Find the selected crop in the cropList
        Crop selectedCrop = null;
        for (Crop crop : cropList) {
            if (crop.getId() == cropId) {
                selectedCrop = crop;
                System.out.println("Selected Crop: " + selectedCrop.getType() + ", Quantity: " + selectedCrop.getQuantity());
                break;
            }
        }

        System.out.println("Current list of Animals:");
        viewAnimal();

        System.out.print("Enter the ID of the Animal to feed: ");
        int animalId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Entered Animal ID: " + animalId);

        // Find the selected animal in the animalList
        Animal selectedAnimal = null;
        for (Animal animal : animalList) {
            if (animal.getId() == animalId) {
                selectedAnimal = animal;
                break;
            }
        }

        // If the selected animal is not found, print a message and return
        if (selectedAnimal == null) {
            System.out.println("Invalid Animal ID. No matching Animal found.");
            return;
        }

        // Ask for the quantity to feed
        System.out.print("Enter the quantity to feed: ");
        selectedAnimal.feedQuantity = scanner.nextInt();
        scanner.nextLine();

        // Feed the animal with the selected crop
        int quantityFed = selectedAnimal.feed(selectedCrop);

        // Display the result of the feeding operation
        if (quantityFed > 0) {
            System.out.println("Animal with ID " + animalId + " fed successfully with " +
                    quantityFed + " units of Crop with ID " + cropId);
        } else {
            System.out.println("No more units of Crop with ID " + cropId +
                    " can be fed to Animal with ID " + animalId + ". Crop quantity is insufficient.");
        }
    }






    Scanner scanner = new Scanner(System.in);

    public void Menu() {
        createFilesIfNotExist();
        boolean end = false;
        while (!end) {
            System.out.println("\nFarm Main Menu");
            System.out.println("1. View Animals");
            System.out.println("2. View Crops");
            System.out.println("3. Add Animal");
            System.out.println("4. Add Crop");
            System.out.println("5. Remove Animal");
            System.out.println("6. Remove Crop");
            System.out.println("7. Feed Animal");
            System.out.println("8. Save Changes to Files");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        viewAnimal();
                        break;
                    case 2:
                        viewCrop();
                        break;
                    case 3:
                        addAnimal();
                        break;
                    case 4:
                        addCrop();
                        break;
                    case 5:
                        removeAnimal();
                        break;
                    case 6:
                        removeCrop();
                        break;
                    case 7:
                        feedAnimal();
                        break;
                    case 8:
                        saveCrop();
                        saveAnimal();
                        System.out.println("Saved.\n");
                        break;
                    case 9:
                        System.out.println("Exiting program. Goodbye!\n");
                        end = true;
                        break;
                    default:
                        System.out.println("\nInvalid choice. Please enter a valid option.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine();
            }
        }
    }
}


