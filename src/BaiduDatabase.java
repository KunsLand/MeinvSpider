import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaiduDatabase {

	private String dbname = "fSAGSUSsjRfdVfBbpcvk";

	private String api_key = "o5F8GpYXxiBNhCROsLc7i6dj";

	private String secret_key = "ptFslujWdP5e1NNQtBTLcyFrij4Iytym";

	private String host = "sqld.duapp.com";

	private String port = "4050";

	private Connection connection = null;

	public BaiduDatabase() throws SQLException {
		String dburl = "jdbc:mysql://" + host + ":" + port + "/" + dbname;
		connection = DriverManager.getConnection(dburl, api_key, secret_key);
	}

	public void testCreateTable() {
		String sql = "create table if not exists test_remote(id int primary key auto_increment, words char(20));";
		try {
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testInsertIntoDatabase(String words) {
		String sql = "insert into test_remote(words) values(" + words + ");";
		try {
			Statement stmt = connection.createStatement();
			stmt.execute(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testQueryDatabse() {
		String sql = "select * from test_remote;";
		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			while (result.next()) {
				System.out.println(result.getInt(1) + ":\t"
						+ result.getString(2));
			}
			result.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		try {
			BaiduDatabase bdb = new BaiduDatabase();
			bdb.testCreateTable();
			bdb.testInsertIntoDatabase("hello");
			bdb.testInsertIntoDatabase("world");
			bdb.testInsertIntoDatabase("fuck");
			bdb.testInsertIntoDatabase("baidu");
			bdb.testQueryDatabse();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
