package client;

import java.util.Scanner;

public class MainClient {
	public static Scanner scanner = new Scanner(System.in);
	public static String command = "";
	
	public static void main(String[] args) {
		
		System.out.println("Informe seu apelido: ");
		String nickname = scanner.nextLine();
		
		System.out.println("Informe o endereco do servidor: ");
		String privateAdress = scanner.nextLine();
		
		ChatManager chatManager = new ChatManager(nickname, 6799, 6789, privateAdress);
		chatManager.initialize();
		
		do {
			System.out.println("O que devo fazer?");
			scanner = new Scanner(System.in);
			command = scanner.nextLine();
			chatManager.executeCommand(command);
			
		} while(!command.matches("sair"));
	}

}
