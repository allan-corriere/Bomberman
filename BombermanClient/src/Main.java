
public class Main {
	public static void main(String[] args) {
		Connection conn = new Connection(65432, "localhost", "Osloh");
		
		try {
			conn.connect();
			conn.sendPositionData(100, 100);
			conn.sendPositionData(100, 102);
			conn.sendPositionData(100, 104);
			conn.closeConnection();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}
