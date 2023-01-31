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

		TeamController teamController = new TeamController(c);
		PersonajeController playerController = new PersonajeController(c);

		int option = menu.mainMenu();
		while (option != 8) {
			switch (option) {
			case 1: //Poblar o restaurar taules.
				Actions.restart(c);
				Actions.FillTable(c, "objeto");
				Actions.FillTable(c, "personaje");
				Actions.FillTable(c, "monstruo");
				break;

			case 2: // Consultas.
				option = menu.TablesMenu();
				Actions.selectAllTable(c,"personaje");
				break;

			case 3: // Insertar registro.

				break;

			case 4: // Actualizar registro.

				break;

			case 5: // Eliminar registro.

				break;

			case 6: // Eliminar registros segun condición.

				break;

			case 7: // Vaciar tablas.
				Actions.restart(c);
				break;

			case 8: // Finalizar la ejecución del programa.
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
