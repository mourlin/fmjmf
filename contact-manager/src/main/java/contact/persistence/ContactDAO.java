package contact.persistence;

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

public class ContactDAO implements IFacade<Contact> {
	final static Logger logger = Logger.getLogger(ContactDAO.class);
	// load the resource bundle
	private static Properties props;
	
	static {
		props	= new Properties();
		InputStream inputStream	= ContactDAO.class.getResourceAsStream("orders.sql");
		try {
			props.load(inputStream);
			String urlDriver	= props.getProperty("driverName");
			Class.forName(urlDriver);
		} catch (Exception e) {
			logger.error("Where are the SQL orders ?");
		}
	}
	
	private String urlConnection;
	private String login;
	private String password;
	
	public ContactDAO() {
		urlConnection	= props.getProperty("urlConnection");
		login			= props.getProperty("login");
		password		= props.getProperty("password");
	}
	@Override
	public boolean create(Contact p) {
		try {
			Connection connection	= DriverManager.getConnection(urlConnection, login, password);
			String sqlStmt			= props.getProperty("createPersonne");
			PreparedStatement pstmt	= connection.prepareStatement(sqlStmt);
			pstmt.setString(1, p.getNom());
			pstmt.setString(2, p.getPrenom());
			pstmt.setString(3, p.getEmail());
			boolean resultat	= pstmt.execute();
			connection.close();
			return resultat;
		} catch (SQLException e) {
			logger.error("Pas de connection", e);
		}
		return false;
	}

	@Override
	public Contact read(long id) {
		try {
			Connection connection	= DriverManager.getConnection(urlConnection, login, password);
			String sqlStmt			= props.getProperty("readPersonne");
			PreparedStatement pstmt	= connection.prepareStatement(sqlStmt);
			pstmt.setLong(1, id);
			ResultSet resultSet		= pstmt.executeQuery();
			Contact personne		= null;
			if (resultSet.next()) {
				personne			= new Contact();
				personne.setId(resultSet.getLong("id"));
				personne.setNom(resultSet.getString("nom"));
				personne.setPrenom(resultSet.getString("prenom"));
				personne.setEmail(resultSet.getString("email"));
			}
			connection.close();
			return personne;
		} catch (SQLException e) {
			logger.error("Pas de connection", e);
		}
		return null;
	}

	@Override
	public int update(Contact p) {
		try {
			Connection connection	= DriverManager.getConnection(urlConnection, login, password);
			String sqlStmt			= props.getProperty("updatePersonne");
			PreparedStatement pstmt	= connection.prepareStatement(sqlStmt);
			pstmt.setString(1, p.getNom());
			pstmt.setString(2, p.getPrenom());
			pstmt.setString(3, p.getEmail());
			pstmt.setLong(4, p.getId().longValue());
			int resultat			= pstmt.executeUpdate();
			connection.close();
			return resultat;
		} catch (SQLException e) {
			logger.error("Pas de connection", e);
		}
		return 0;
	}

	@Override
	public boolean delete(long id) {
		try {
			Connection connection	= DriverManager.getConnection(urlConnection, login, password);
			String sqlStmt			= props.getProperty("deletePersonne");
			PreparedStatement pstmt	= connection.prepareStatement(sqlStmt);
			pstmt.setLong(1, id);
			boolean resultat		= pstmt.execute();
			connection.close();
			return resultat;
		} catch (SQLException e) {
			logger.error("Pas de connection", e);
		}
		return false;
	}

	@Override
	public List<Contact> searchAll() {
		List<Contact> personnes	= new ArrayList<Contact>();
		try {
			Connection connection	= DriverManager.getConnection(urlConnection, login, password);
			String sqlStmt				= props.getProperty("selectAllPersonne");
			Statement stmt				= connection.createStatement();
			ResultSet resultSet			= stmt.executeQuery(sqlStmt);
			while (resultSet.next()) {
				Contact personne		= new Contact();
				personne.setId(resultSet.getLong("id"));
				personne.setNom(resultSet.getString("nom"));
				personne.setPrenom(resultSet.getString("prenom"));
				personne.setEmail(resultSet.getString("email"));
				personnes.add(personne);
			}
			connection.close();
			return personnes;
		} catch (SQLException e) {
			logger.error("Pas de connection", e);
		}
		return personnes;
	}
}
