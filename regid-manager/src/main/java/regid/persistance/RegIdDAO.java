package regid.persistance;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class RegIdDAO implements IFacade<RegId> {
	final static Logger logger = Logger.getLogger(RegIdDAO.class);
	// load the resource bundle
	private static Properties props;

	static {
		props	= new Properties();
		InputStream inputStream	= RegIdDAO.class.getResourceAsStream("orders.sql");
		try {
			props.load(inputStream);
			String urlDriver	= props.getProperty("driverName");
			logger.info(urlDriver);
			Class.forName(urlDriver);
		} catch (Exception e) {
			logger.error("Where are the SQL orders ?");
		}
	}

	@Override
	public boolean create(RegId ri) {
		String urlConnection	= props.getProperty("urlConnection");
		try {
			Connection connection	= DriverManager.getConnection(urlConnection, "root", "admin");
			String sqlStmt			= props.getProperty("createRegId");
			PreparedStatement pstmt	= connection.prepareStatement(sqlStmt);
			pstmt.setString(1, ri.getRegId());
			boolean resultat	= pstmt.execute();
			connection.close();
			return resultat;
		} catch (SQLException e) {
			logger.error("No db connection");
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public RegId read(long id) {
		String urlConnection	= props.getProperty("urlConnection");
		try {
			Connection connection	= DriverManager.getConnection(urlConnection, "root", "admin");
			String sqlStmt			= props.getProperty("readRegId");
			PreparedStatement pstmt	= connection.prepareStatement(sqlStmt);
			pstmt.setLong(1, id);
			ResultSet resultSet		= pstmt.executeQuery();
			RegId regId		= null;
			if (resultSet.next()) {
				regId			= new RegId();
				regId.setId(resultSet.getLong("id"));
				regId.setRegId(resultSet.getString("regId"));
			}
			connection.close();
			return regId;
		} catch (SQLException e) {
			logger.error("No db connection");
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public int update(RegId p) {
		String urlConnection	= props.getProperty("urlConnection");
		try {
			Connection connection	= DriverManager.getConnection(urlConnection, "root", "admin");
			String sqlStmt			= props.getProperty("updateRegId");
			PreparedStatement pstmt	= connection.prepareStatement(sqlStmt);
			pstmt.setString(1, p.getRegId());
			if (p.getId() != null)
				pstmt.setLong(2, p.getId());
			else {
				Statement stmt		= connection.createStatement();
				ResultSet resultSet	= stmt.executeQuery("select count(*) from annuaireregiddb.t_regId");
				if (resultSet.next()) {
					int size	= resultSet.getInt(1);
					pstmt.setLong(2, size);
				}
			}
			int resultat			= pstmt.executeUpdate();
			connection.close();
			return resultat;
		} catch (SQLException e) {
			logger.error("No db connection");
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public boolean delete(long id) {
		String urlConnection	= props.getProperty("urlConnection");
		try {
			Connection connection	= DriverManager.getConnection(urlConnection, "root", "admin");
			String sqlStmt			= props.getProperty("deleteRegId");
			PreparedStatement pstmt	= connection.prepareStatement(sqlStmt);
			pstmt.setLong(1, id);
			boolean resultat		= pstmt.execute();
			connection.close();
			return resultat;
		} catch (SQLException e) {
			logger.error("No db connection");
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public List<RegId> searchAll() {
		String urlConnection	= props.getProperty("urlConnection");
		List<RegId> regIds	= new ArrayList<RegId>();
		try {
			Connection connection		= DriverManager.getConnection(urlConnection, "root", "admin");
			String sqlStmt				= props.getProperty("selectAllRegId");
			Statement stmt				= connection.createStatement();
			ResultSet resultSet			= stmt.executeQuery(sqlStmt);
			while (resultSet.next()) {
				RegId regId		= new RegId();
				regId.setId(resultSet.getLong("id"));
				regId.setRegId(resultSet.getString("regId"));
				regIds.add(regId);
			}
			connection.close();
			return regIds;
		} catch (SQLException e) {
			logger.error("No db connection");
			e.printStackTrace();
		}
		return regIds;
	}
}
