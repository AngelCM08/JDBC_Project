import actions.DB_Actions;
import actions.DB_Deletes;
import actions.DB_Selects;
import ui.ACBMenu;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Scanner;

public class ACBMain {
	public static Connection c;

	public static void main(String[] args) throws IOException, SQLException, ParseException {
		ACBMenu menu = new ACBMenu();
		ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
		c = connectionFactory.connect();
		String table;
		int opt;
		boolean out;

		int option = menu.mainMenu();
		while (option != 9) {
			switch (option) {
			case 1: //Poblar o restaurar taules.
				DB_Actions.restart(c);
				DB_Actions.FillTable(c, "objeto");
				DB_Actions.FillTable(c, "personaje");
				DB_Actions.FillTable(c, "monstruo");
				break;
			case 2: // Consultas.
				if((table = menu.TablesMenu()).equals("")) break;
				DB_Selects.selectAllTable(c, table);
				break;
			case 3: // Consultas específicas.
				opt = menu.ConsultasEspecificasMenu();
				if(opt == 4 || (table = menu.TablesMenu()).equals("")) break;
				DB_Selects.selectAllTable(c, table);
				String[] column_data = menu.ColumnsMenu(c, table);
				switch (opt) {
					case 1 -> DB_Selects.selectSpecificText(c, table, column_data);
					case 2 -> DB_Selects.selectByCondition(c, table, column_data);
					case 3 -> DB_Selects.selectColumn(c, table, column_data);
				}
				break;

			case 4: // Insertar registro.

				break;

			case 5: // Actualizar registro.

				break;

			case 6: // Eliminar registro.
				Scanner sc = new Scanner(System.in);
				opt = menu.DeleteMenu();
				if(opt == 3 || (table = menu.TablesMenu()).equals("")) break;
				switch (opt) {
					case 1 -> DB_Deletes.deleteTable(c, table);
					case 2 -> {
						DB_Selects.selectAllTable(c, table);
						System.out.print("Indica el valor del registro a eliminar: ");
						DB_Deletes.deleteTableRegister(c, table, sc.nextInt());
					}
				}
				break;

			case 7: // Eliminar registros segun condición.

				break;

			case 8: // Vaciar tablas.
				DB_Actions.restart(c);
				break;

			case 9: // Finalizar la ejecución del programa.
				DB_Actions.sortir(c);
				break;

			default:
				System.out.println("Introdueixi una de les opcions anteriors");
				break;
			}
			option = menu.mainMenu();
		}

	}

}
