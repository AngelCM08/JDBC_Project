import java.sql.Connection;

public class PersonajeController {
	private Connection connection;
	
	public PersonajeController(Connection c) {
		this.connection = c;
	}
}
