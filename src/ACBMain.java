import action.Actions;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;

public class ACBMain {
	public static Connection c;

	public static void main(String[] args) throws IOException, SQLException, ParseException {
		ACBMenu menu = new ACBMenu();
		
		ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
		c = connectionFactory.connect();

		String table = "";
		int option = menu.mainMenu();
		while (option != 9) {
			switch (option) {
			case 1: //Poblar o restaurar taules.
				Actions.restart(c);
				Actions.FillTable(c, "objeto");
				Actions.FillTable(c, "personaje");
				Actions.FillTable(c, "monstruo");
				break;
			case 2: // Consultas.
				switch (menu.TablesMenu()) {
					case 1 -> table = "personaje";
					case 2 -> table = "monstruo";
					case 3 -> table = "objeto";
				}
				Actions.selectAllTable(c, table);
				break;
			case 3: // Consultas específicas.
				switch (menu.TablesMenu()) {
					case 1 -> table = "personaje";
					case 2 -> table = "monstruo";
					case 3 -> table = "objeto";
				}
				Actions.selectAllTable(c, table);
				Actions.selectSpecificText(c, table, menu.ColumnsMenu());
				break;

			case 4: // Insertar registro.

				break;

			case 5: // Actualizar registro.

				break;

			case 6: // Eliminar registro.

				break;

			case 7: // Eliminar registros segun condición.

				break;

			case 8: // Vaciar tablas.
				Actions.restart(c);
				break;

			case 9: // Finalizar la ejecución del programa.
				Actions.sortir(c);
				break;

			default:
				System.out.println("Introdueixi una de les opcions anteriors");
				break;
			}
			option = menu.mainMenu();
		}

	}

}
