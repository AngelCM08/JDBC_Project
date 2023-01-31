import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ACBMenu {
	private int option;

	public ACBMenu() {
		super();
	}

	public int mainMenu() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		do {
			System.out.println(" \nMENU PRINCIPAL \n");

			System.out.println("1. Poblar o restaurar tablas.");
			System.out.println("2. Consultas."); //Mostrar tablas completas o hacer selects específicos.
			System.out.println("3. Insertar registro.");
			System.out.println("4. Actualitzar registro.");
			System.out.println("5. Eliminar registro.");
			System.out.println("6. Eliminar registros según condición.");
			System.out.println("7. Vaciar tablas.");
			System.out.println("8. Salir.");
			System.out.println("Escoger opción: ");
			try {
				option = Integer.parseInt(br.readLine());
			} catch (NumberFormatException | IOException e) {
				System.out.println("valor no válido");
				e.printStackTrace();

			}

		} while (option != 1 && option != 2 && option != 3 && option != 4 && option != 5 && option != 6 && option != 7
				&& option != 8 && option != 9 && option != 10);

		return option;
	}

	public Identity authenticate(int tries) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("============================ACB=============================");
		System.out.println("============================================================");
		System.out.println("Avís: tens " + (3 - tries) + " intents per loginarte");
		System.out.println("============================================================");
		System.out.println("Inserta nom del usuari: ");
		String user = br.readLine();
		System.out.println("Inserta contrasenya: ");
		String password = br.readLine();

		Identity identity = new Identity(user, password);
		return identity;

	}

}
