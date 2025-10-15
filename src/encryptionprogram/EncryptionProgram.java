package encryptionprogram;

import java.util.*;
import java.util.stream.*;

public class EncryptionProgram {
	
	private final Scanner scanner;
	private final List<Character> originalList;
	private List<Character> encryptedList;
	
	EncryptionProgram() {
		scanner = new Scanner(System.in);
		originalList = new ArrayList<>();
		encryptedList = new ArrayList<>();
		
		generateNewKey();
		mainLoop();
	}
	
	private void mainLoop() {
		
		while(true) {
			System.out.println("\n**********************************************************");
			System.out.println("What do you want to do?");
			System.out.println("(N)ewkey, (G)etKey, (S)etKey, (E)ncrypt, (D)ecrypt, (Q)uit");
			char response = scanner.nextLine().toUpperCase().charAt(0);
			
			switch(response) {
				case 'N':
					generateNewKey();
					break;
				case 'G':
					displayKeys();
					break;
				case 'S':
					setCustomKey();
					break;
				case 'E':
					encryptMessage();
					break;
				case 'D':
					decryptMessage();
					break;
				case 'Q':
					quit();
					break;
				default:
					System.out.println("Not a valid answer!");
			}
		}
	}
	
	private void generateNewKey() {
		
		originalList.clear();
		encryptedList.clear();
		
		for(char c = 32; c < 127; c++) {
			originalList.add(c);
		}
		
		encryptedList = new ArrayList<>(originalList);
		Collections.shuffle(encryptedList);
		System.out.println("* A new key has been generated *");
	}
	
	private void displayKeys() {
		System.out.println("\nOriginal Key:");
		originalList.forEach(System.out::print);
		
		System.out.println("\n\nEncrypted Key:");
		encryptedList.forEach(System.out::print);
		System.out.println();
	}
	
	private void setCustomKey() {
		
		System.out.println("\nEnter your new key:");
		String inputKey = scanner.nextLine();
        
		Set<Character> customKeySet = new LinkedHashSet<>();
        for (char c : inputKey.toCharArray()) {
        	customKeySet.add(c); 
        }
		
        List<Character> notFoundChars = originalList.stream()
        		.filter(c -> !customKeySet.contains(c))
        		.collect(Collectors.toList());

        if (notFoundChars.isEmpty()) {
            encryptedList = new ArrayList<>(customKeySet);
            System.out.println("* A new key has been set *");
        } else {
        	System.out.println("Invalid key! " + notFoundChars + " not found.");
        }
	}
	
	private void encryptMessage() {
		
		System.out.println("\nEnter a message to be encrypted: ");
		String message = scanner.nextLine();
		char[] messageChars = message.toCharArray();
		
		for(int i=0; i<messageChars.length; i++) {
			messageChars[i] = encryptedList.get(originalList.indexOf(messageChars[i]));
		}
		
		System.out.println("\nEncrypted:\n" + new String(messageChars));
	}
	
	private void decryptMessage() {
		
		System.out.println("\nEnter a message to be decrypted: ");
		String message = scanner.nextLine();
		char[] messageChars = message.toCharArray();
		
		for(int i=0; i<messageChars.length; i++) {
			messageChars[i] = originalList.get(encryptedList.indexOf(messageChars[i]));
		}
		
		System.out.println("\nDecrypted:\n" + new String(messageChars));
	}
	
	private void quit() {
	    System.out.println("\nThank you for using the Encryption Program. Goodbye!");
		System.exit(0);
	}
}
