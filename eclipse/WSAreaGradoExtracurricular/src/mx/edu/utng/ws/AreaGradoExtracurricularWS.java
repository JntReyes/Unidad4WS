package mx.edu.utng.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AreaGradoExtracurricularWS {

	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private PreparedStatement ps;
	
	
	public AreaGradoExtracurricularWS() {
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
	
	public int addAreaGradoExtracurricular(AreaGradoExtracurricular areaGradoExtracurricular){
		int result =0;
		if (connection != null) {
			try {
				ps = connection.prepareStatement(
						"INSERT INTO area_grado_extracurricular (descripcion,activo)"
						+ "VALUES (?,?);");
				ps.setString(1, areaGradoExtracurricular.getDescripcion());
				ps.setString(2, areaGradoExtracurricular.getActivo());
				
				result = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public int editAreaGradoExtracurricular(AreaGradoExtracurricular areaGradoExtracurricular){
		int result =0;
		if (connection != null) {
			try {
				ps = connection.prepareStatement(
						"UPDATE area_grado_extracurricular SET descripcion =? , "
						+ "activo = ?"
						+ "WHERE cve_area_grado_extracurricular = ?;");
				ps.setString(1, areaGradoExtracurricular.getDescripcion());
				ps.setString(2, areaGradoExtracurricular.getActivo());
				ps.setInt(3, areaGradoExtracurricular.getCveAreaGradoExtracurricular());
				result = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	public int removeAreaGradoExtracurricular(int cveAreaGradoExtracurricular){
		int result =0;
		if (connection != null) {
			try {
				ps = connection.prepareStatement(
						"DELETE FROM area_grado_extracurricular WHERE cve_area_grado_extracurricular = ?;");
				ps.setInt(1, cveAreaGradoExtracurricular);
				result =ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public AreaGradoExtracurricular[] getAreasGradoExtracurricular(){
		AreaGradoExtracurricular [] result = null;
		List<AreaGradoExtracurricular> areasGradoExtracurricular = new ArrayList<AreaGradoExtracurricular>();
		
		if (connection != null) {
			try {
				statement = connection.createStatement();
				resultSet = statement.executeQuery(
						"SELECT * FROM area_grado_extracurricular;");
				while (resultSet.next()) {
					AreaGradoExtracurricular areaGradoExtracurricular = new AreaGradoExtracurricular(
							resultSet.getInt("cve_area_grado_extracurricular"),
							resultSet.getString("descripcion"),
							resultSet.getString("activo"));
					areasGradoExtracurricular.add(areaGradoExtracurricular);
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		 result = areasGradoExtracurricular.toArray(new AreaGradoExtracurricular[areasGradoExtracurricular.size()]);
		 
		return result;
	}
	public AreaGradoExtracurricular getOneAreaGradoExtracurricular(int cveAreaGradoExtracurricular){
		AreaGradoExtracurricular areaGradoExtracurricular = null;
		
		if (connection != null) {
			try {
				ps = connection.prepareStatement("SELECT * FROM area_grado_extracurricular WHERE cve_area_grado_extracurricular = ?;");
				ps.setInt(1, cveAreaGradoExtracurricular);
			    resultSet = ps.executeQuery();
				if(resultSet.next()) {
					areaGradoExtracurricular = new AreaGradoExtracurricular(
							resultSet.getInt("cve_area_grado_extracurricular"),
							resultSet.getString("descripcion"),
							resultSet.getString("activo"));
					
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return areaGradoExtracurricular;
	}
}

