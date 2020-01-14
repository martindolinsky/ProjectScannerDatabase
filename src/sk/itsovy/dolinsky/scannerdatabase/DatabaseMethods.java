package sk.itsovy.dolinsky.scannerdatabase;

import java.sql.Connection;

/**
 * @author Martin Dolinsky
 */
public interface DatabaseMethods {
	Connection getConnection() throws Exception;
	void updateSlovakCity() throws Exception;
	void deleteSlovakCity() throws Exception;
	void insertSlovakCity() throws Exception;
	void selectSlovakCities() throws Exception;

}
