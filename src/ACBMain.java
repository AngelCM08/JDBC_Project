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
		boolean out;
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
				if((table = menu.TablesMenu()).equals("")) break;
				Actions.selectAllTable(c, table);
				break;
			case 3: // Consultas específicas.
				int opt = menu.ConsultasEspecificasMenu();
				if(opt == 4 || (table = menu.TablesMenu()).equals("")) break;
				Actions.selectAllTable(c, table);
				switch (opt) {
					case 1 -> Actions.selectSpecificText(c, table, menu.ColumnsMenu(c, table));
					case 2 -> System.out.println("Seleccionar todos los elementos que cumplan una condición.");
					case 3 -> Actions.selectColumn(c, table, menu.ColumnsMenu(c,table));
				}
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
