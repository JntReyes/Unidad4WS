package mx.edu.utng.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ArqueoCajaWS {

	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private PreparedStatement ps;
	
	
	public ArqueoCajaWS() {
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
	
	public int addArqueoCaja(ArqueoCaja arqueoCaja){
		int result =0;
		if (connection != null) {
			try {
				ps = connection.prepareStatement(
						"INSERT INTO arqueo_caja (cve_unidad_academica,fecha_arqueo,observaciones,total,personal) "
						+ "VALUES (?,?,?,?,?);");
				ps.setInt(1, arqueoCaja.getCveUnidadAcademica());
				ps.setString(2, arqueoCaja.getFechaArqueo());
				ps.setString(3,arqueoCaja.getObservaciones());
				ps.setFloat(4, arqueoCaja.getTotal());
				ps.setString(5,arqueoCaja.getPersonal());
				result = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public int editArqueoCaja(ArqueoCaja arqueoCaja){
		int result =0;
		if (connection != null) {
			try {
				ps = connection.prepareStatement(
						"UPDATE arqueo_caja SET cve_unidad_academica =? , "
						+ "fecha_arqueo = ? ,"
						+ "observaciones = ? , "
						+ "total = ? ,"
						+ "personal = ? "
						+ "WHERE cve_arqueo = ?;");
				ps.setInt(1, arqueoCaja.getCveUnidadAcademica());
				ps.setString(2, arqueoCaja.getFechaArqueo());
				ps.setString(3,arqueoCaja.getObservaciones());
				ps.setFloat(4, arqueoCaja.getTotal());
				ps.setString(5,arqueoCaja.getPersonal());
				ps.setInt(6, arqueoCaja.getCveArqueo());
				result = ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	public int removeArqueoCaja(int cveArqueo){
		int result =0;
		if (connection != null) {
			try {
				ps = connection.prepareStatement(
						"DELETE FROM arqueo_caja WHERE cve_arqueo = ?;");
				ps.setInt(1, cveArqueo);
				result =ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public ArqueoCaja[] getArqueoCajas(){
		ArqueoCaja [] result = null;
		List<ArqueoCaja> arqueoCajas = new ArrayList<ArqueoCaja>();
		
		if (connection != null) {
			try {
				statement = connection.createStatement();
				resultSet = statement.executeQuery(
						"SELECT * FROM arqueo_caja;");
				while (resultSet.next()) {
					ArqueoCaja arqueoCaja = new ArqueoCaja(
							resultSet.getInt("cve_arqueo"),
							resultSet.getInt("cve_unidad_academica"),
							resultSet.getString("fecha_arqueo"),
							resultSet.getString("observaciones"),
							resultSet.getFloat("total"),
							resultSet.getString("personal"));
					arqueoCajas.add(arqueoCaja);
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return arqueoCajas.toArray(new ArqueoCaja[arqueoCajas.size()]);
	}
	public ArqueoCaja getOneArqueoCaja(int cveArqueo){
		ArqueoCaja arqueoCaja = null;
		
		if (connection != null) {
			try {
				ps = connection.prepareStatement("SELECT * FROM arqueo_caja WHERE cve_arqueo = ?;");
				ps.setInt(1, cveArqueo);
			    resultSet = ps.executeQuery();
				if(resultSet.next()) {
					arqueoCaja = new ArqueoCaja(
							resultSet.getInt("cve_arqueo"),
							resultSet.getInt("cve_unidad_academica"),
							resultSet.getString("fecha_arqueo"),
							resultSet.getString("observaciones"),
							resultSet.getFloat("total"),
							resultSet.getString("personal"));
					
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return arqueoCaja;
	}
}


