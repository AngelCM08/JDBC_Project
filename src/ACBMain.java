import actions.*;
import connection.ConnectionFactory;
import ui.ACBMenu;

import java.sql.Connection;
import java.util.Scanner;

public class ACBMain {
	public static Connection c;

	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		ACBMenu menu = new ACBMenu();
		ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
		c = connectionFactory.connect();
		String table;
		String[] column_data;
		int opt;

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
				column_data = menu.ColumnsMenu(c, table);
				switch (opt) {
					case 1 -> DB_Selects.selectSpecificText(c, table, column_data);
					case 2 -> DB_Selects.selectByCondition(c, table, column_data);
					case 3 -> DB_Selects.selectColumn(c, table, column_data);
				}
				break;

			case 4: // Insertar registro.
				if((table = menu.TablesMenu()).equals("")) break;
				DB_Inserts.InsertRegister(c, table);
				break;

			case 5: // Actualizar registro.
				opt = menu.UpdateMenu();
				if(opt == 3 || (table = menu.TablesMenu()).equals("")) break;
				DB_Selects.selectAllTable(c, table);
				switch (opt) {
					case 1 -> {
						System.out.print("Indica el registro a actualizar: ");
						DB_Updates.UpdateRegister(c, table, sc.nextInt());
					}
					case 2 -> {
						column_data = menu.ColumnsMenu(c, table);
						DB_Updates.updateRegistersByCondition(c, table, column_data);
					}
				}
				break;

			case 6: // Eliminar registro.
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
				if((table = menu.TablesMenu()).equals("")) break;
				DB_Selects.selectAllTable(c, table);
				DB_Deletes.deleteTableRegisterByCondition(c, table, menu.ColumnsMenu(c, table));
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
