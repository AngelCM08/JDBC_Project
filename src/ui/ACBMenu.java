package ui;

import actions.DB_Actions;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class ACBMenu {
	private int option;

	public ACBMenu() {
		super();
	}

	public int mainMenu() {
		Scanner sc = new Scanner(System.in);
		System.out.println(" \nMENU PRINCIPAL \n");

		System.out.println("1. Poblar o restaurar tablas.");
		System.out.println("2. Mostrar tabla completa.");
		System.out.println("3. Consultas específicas.");
		System.out.println("4. Insertar registro.");
		System.out.println("5. Actualizar registro.");
		System.out.println("6. Eliminar datos.");
		System.out.println("7. Eliminar registros según condición.");
		System.out.println("8. Vaciar tablas.");
		System.out.println("9. Salir.");
		System.out.print("Escoger opción: ");

		option = sc.nextInt();

		return option;
	}

	public String TablesMenu() {
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println(" \nSOBRE QUE TABLA QUIERES REALIZAR LA ACCIÓN\n");

			System.out.println("1. Personaje.");
			System.out.println("2. Monstruo.");
			System.out.println("3. Objeto.");
			System.out.println("4. Atrás.");
			System.out.print("Escoger opción: ");

			option = sc.nextInt();

		} while (option < 1 || option > 4);
		switch (option) {
			case 1 -> { return "personaje"; }
			case 2 -> { return "monstruo"; }
			case 3 -> { return "objeto"; }
		}
		return "";
	}

	public String[] ColumnsMenu(Connection c, String tabla) {
		Scanner sc = new Scanner(System.in);
		List<List<String>> header = DB_Actions.GetHeader(c, tabla);

		for (int i = 0; i < header.get(0).size(); i++) {
			System.out.println(i+". "+header.get(0).get(i));
		}
		System.out.println("Indica el valor de la columna que quieres seleccionar:");
		int index = sc.nextInt();
		return new String[]{header.get(0).get(index), header.get(1).get(index)};
	}

	public int ConsultasEspecificasMenu() {
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println(" \nQUE TIPO DE ACCIÓN QUIERES REALIZAR\n");

			System.out.println("1. Seleccionar todos los elementos que contengan un texto concreto.");
			System.out.println("2. Seleccionar todos los elementos que cumplan una condición.");
			System.out.println("3. Seleccionar una columna.");
			System.out.println("4. Atrás.");
			System.out.print("Escoger opción: ");

			option = sc.nextInt();

		} while (option < 1 || option > 4);
		return option;
	}

	public int ConsultasPorCondicionesMenu() {
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println(" \nQUE TIPO DE ACCIÓN QUIERES REALIZAR\n");

			System.out.println("1. Seleccionar todos los elementos con un valor superior al indicado. ");
			System.out.println("2. Seleccionar todos los elementos con un valor inferior al indicado. ");
			System.out.println("3. Atrás.");
			System.out.print("Escoger opción: ");

			option = sc.nextInt();
		} while (option < 1 || option > 4);
		return option;
	}

	public int DeleteMenu() {
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println(" \nQUE TIPO DE ACCIÓN QUIERES REALIZAR\n");

			System.out.println("1. Eliminar tabla.");
			System.out.println("2. Eliminar registro de una tabla.");
			System.out.println("3. Atrás.");
			System.out.print("Escoger opción: ");

			option = sc.nextInt();
		} while (option < 1 || option > 3);
		return option;
	}


	/*public Identity authenticate(int tries) throws IOException {
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

	}*/

}
