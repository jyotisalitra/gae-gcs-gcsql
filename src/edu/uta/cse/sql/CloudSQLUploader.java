/**
 * Jyoti Salitra
 * UTA ID: ********
 * Cloud Computing (CSE - 6331) - David Levine
 * Programming Assignment # 5
 * Date: 11/23/2014
 */

package edu.uta.cse.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Loads data into Google Cloud SQL MySQL instance from the csv files
 */
public class CloudSQLUploader {

	/**
	 * Loads data from csvFileName into the table tableName.
	 * Ignores first ignoreLines
	 * @param csvFileName
	 * @param tableName
	 * @param ignoreLines
	 * @param conn
	 */
	public static void importData(String csvFileName, String tableName, int ignoreLines, Connection conn) {
		//start the timer
		long start = System.currentTimeMillis();

		Statement stmt = null;
		try {
			//create a Statement instance
			stmt = conn.createStatement();

			//LOAD DATA query for MySQL
			String query = "LOAD DATA LOCAL INFILE '" + csvFileName + "' INTO TABLE "
					+ tableName + " FIELDS TERMINATED BY ','"
					+ "LINES TERMINATED BY '\r\n' " + "IGNORE "+ignoreLines+" LINES";
			
			//execute the query
			stmt.executeUpdate(query);

		} catch (Exception e) {
			e.printStackTrace();
			//close JDBC resources
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		//calculate the time taken for this LOAD query
		long totalTime = System.currentTimeMillis() - start;
		System.out.println("UPLOADING: " + csvFileName + " | TIME TAKEN [ms]: " + totalTime);
	}
	
	/**
	 * Queries a table on MySQL instance on Google Cloud SQL and determines the time taken 
	 * @param tableName
	 * @param conn
	 */
	public static void queryData(String tableName, Connection conn) {
		//start the timer
		long start = System.currentTimeMillis();

		Statement stmt = null;
		try {
			//create a Statement instance
			stmt = conn.createStatement();

			//SELECT query for this table
			String query = "SELECT * FROM "+ tableName;
			
			//execute the query
			stmt.executeQuery(query);

		} catch (Exception e) {
			e.printStackTrace();
			//close JDBC resources
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		//calculate the time taken for this SELECT query
		long totalTime = System.currentTimeMillis() - start;
		System.out.println("QUERYING | TIME TAKEN [ms]: " + totalTime);
	}
	
	/**
	 * Deletes all records from the table so that this table can be reused again.
	 * @param tableName
	 * @param conn
	 */
	public static void truncateTable (String tableName, Connection conn) {
		Statement stmt = null;
		try {
			//create a Statement instance
			stmt = conn.createStatement();

			//TRUNCATE query for MySQL
			String query = "TRUNCATE TABLE " + tableName;
			
			//execute the query
			stmt.executeUpdate(query);

		} catch (Exception e) {
			e.printStackTrace();
			//close JDBC resources
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		
		//get a Connection instance
		Connection conn = DBManager.getConnection();
		String tableName = "virginia";
		
		System.out.println("Starting loading csv files into MySQL");
		
		//create an array of all the files need to be uploaded.
		//This files are divided in to rows containing 10k, 25k, 50k, 100k, and 150k.
		String [] filePaths = {"./gcs/virginia_10k.csv", "./gcs/virginia_25k.csv", "./gcs/virginia_50k.csv", "./gcs/virginia_100k.csv", "./gcs/virginia_150k.csv"};
				
		//delete all rows from the table if there were any
		truncateTable("virginia", conn);
		
		//loop over all filePaths
		for(String file: filePaths)
		{
			//import data from the csv file into the table and measure the time
			importData(file,tableName, 0, conn);
			
			//query data from the table and measure the time
			queryData(tableName, conn);
			
			//delete the table for reuse.
			truncateTable(tableName, conn);
			System.out.println("--------");
		}
		
		//cleanup JDBC resources
		DBManager.cleanup(null, null, conn);
		System.out.println("MySQL load done.");
	}
}
