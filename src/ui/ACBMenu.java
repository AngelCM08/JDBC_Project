package ui;

import actions.DB_Actions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		System.out.println("7. Eliminar registro que contengan un texto específico.");
		System.out.println("8. Vaciar tablas.");
		System.out.println("9. Salir.");
		System.out.print("Escoger opción: ");

		return sc.nextInt();
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

	public int UpdateMenu() {
		Scanner sc = new Scanner(System.in);
		do {
			System.out.println(" \nQUE TIPO DE ACCIÓN QUIERES REALIZAR\n");

			System.out.println("1. Seleccionar registro y modificar sus elementos.");
			System.out.println("2. Modificar varios registros según condición.");
			System.out.println("3. Atrás.");
			System.out.print("Escoger opción: ");

			option = sc.nextInt();
		} while (option < 1 || option > 3);
		return option;
	}

	public int SelectFieldMenu(ResultSet result){
		Scanner sc = new Scanner(System.in);
		try {
			int columnCount = result.getMetaData().getColumnCount();
			do {
				while(result.next()) {
					for (int i = 1; i <= columnCount; i++) {
						System.out.println(i + ". " + result.getMetaData().getColumnName(i) + ": " + result.getString(i));
					}
				}
				System.out.println("0. Cancelar.");
				System.out.print("Selecciona el atributo que quieres actualizar: ");
				option = sc.nextInt();
			} while (option < 0 || option > columnCount);
			return option;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}