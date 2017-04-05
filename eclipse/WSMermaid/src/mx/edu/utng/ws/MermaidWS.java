package mx.edu.utng.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MermaidWS {
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private PreparedStatement ps;
	
	
	public MermaidWS() {
		conectar();
	}
	private void conectar(){
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/WSTest",
					"postgres","admin");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	
	public int addMermaid(Mermaid mermaid){
		int result =0;
		if (connection != null) {
			try {
				ps = connection.prepareStatement(
						"INSERT INTO mermaid (name,age,description,type,sing,color) "
						+ "VALUES (?,?,?,?,?,?);");
				ps.setString(1, mermaid.getName());
				ps.setString(2, mermaid.getAge());
				ps.setString(3,mermaid.getDescription());
				ps.setString(4, mermaid.getType());
				ps.setString(5, mermaid.getSing());
				ps.setString(6, mermaid.getColor());
				result = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public int editMermaid(Mermaid mermaid){
		int result =0;
		if (connection != null) {
			try {
				ps = connection.prepareStatement(
						"UPDATE mermaid SET name =? , "
						+ "age = ? ,"
						+ "description = ? , "
						+ "type = ? ,"
						+ "sing = ? ,"
						+ "color = ? "
						+ "WHERE id = ?;");
				ps.setString(1, mermaid.getName());
				ps.setString(2, mermaid.getAge());
				ps.setString(3, mermaid.getDescription());
				ps.setString(4, mermaid.getType());
				ps.setString(5, mermaid.getSing());
				ps.setString(6, mermaid.getColor());
				ps.setInt(7, mermaid.getId());
				result = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	public int removeMermaid(int id){
		int result =0;
		if (connection != null) {
			try {
				ps = connection.prepareStatement(
						"DELETE FROM mermaid WHERE id = ?;");
				ps.setInt(1, id);
				result =ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public Mermaid[] getMermaids(){
		Mermaid [] result = null;
		List<Mermaid> mermaids = new ArrayList<Mermaid>();
		
		if (connection != null) {
			try {
				statement = connection.createStatement();
				resultSet = statement.executeQuery(
						"SELECT * FROM mermaid;");
				while (resultSet.next()) {
					Mermaid mermaid = new Mermaid(
							resultSet.getInt("id"),
							resultSet.getString("name"),
							resultSet.getString("age"),
							resultSet.getString("description"),
							resultSet.getString("type"),
							resultSet.getString("sing"),
							resultSet.getString("color"));
					mermaids.add(mermaid);
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return mermaids.toArray(new Mermaid[mermaids.size()]);
	}
	public Mermaid getOneMermaid(int id){
		Mermaid mermaid = null;
		
		if (connection != null) {
			try {
				ps = connection.prepareStatement("SELECT * FROM mermaid WHERE id = ?;");
				ps.setInt(1, id);
			    resultSet = ps.executeQuery();
				if(resultSet.next()) {
					mermaid = new Mermaid(
							resultSet.getInt("id"),
							resultSet.getString("name"),
							resultSet.getString("age"),
							resultSet.getString("description"),
							resultSet.getString("type"),
							resultSet.getString("sing"),
							resultSet.getString("color"));
					
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return mermaid;
	}
}



