/*
 * SPDX-FileCopyrightText: 2006-2009 Dirk Riehle <dirk@riehle.org> https://dirkriehle.com
 * SPDX-License-Identifier: AGPL-3.0-or-later
 */

package org.wahlzeit.services;

import java.sql.*;
import java.util.*;

/**
 * A database connection wraps an RDMBS connection object.
 * It pools and reuses existing connections; it caches common SQL statements.
 *
 * @author dirkriehle
 *
 */
public class DatabaseConnection {
	
	/**
	 * 
	 */
	protected static Set<DatabaseConnection> pool = new HashSet<DatabaseConnection>();
	
	/**
	 * 
	 */
	protected static int dbcId = 0;
	
	/**
	 * 
	 */
	public static synchronized DatabaseConnection ensureDatabaseConnection() throws SQLException {
		DatabaseConnection result = null;
		if (pool.isEmpty()) {
			result = new DatabaseConnection("dbc" + dbcId++);
			SysLog.logCreatedObject("DatabaseConnection", result.getName());
		} else {
			result = pool.iterator().next();
			pool.remove(result);
		}
		
		return result;
	}

	/**
	 * Wait until a database connection can be established or the max retry counter is reached.
	 * 
	 * @param retries amount of retries until fail.
	 * @param sleepTimeBetweenRetries time to wait until next retry in milliseconds.
	 * @return true if connection could be established, otherwise return false.
	 */
	public static synchronized boolean waitForDatabaseIsReady(final int retries, final int sleepTimeBetweenRetries) {
		int retryCounter = retries;
		String dbUrl = SysConfig.getDbConnectionAsString();
		do {
			try {
				DatabaseConnection.ensureDatabaseConnection();
				SysLog.logSysInfo("[success] Service check for URL " + dbUrl);
				return true;
			} catch (final SQLException e) {
				final String msg = String.format("[%d/%d] Retry service check for URL %s", retryCounter, retries, dbUrl);
				SysLog.logSysInfo(msg);
				doSleep(sleepTimeBetweenRetries);
			}   

			retryCounter--;
		} while (retryCounter > 0);

		SysLog.logSysError("[failure] Service check for URL  " + dbUrl);
		return false;
	}

	private static void doSleep(final int sleepTime) {
		try {
			Thread.sleep(sleepTime);
		} catch (final InterruptedException e) {
		}
	}
	
	/**
	 * 
	 */
	public static synchronized void returnDatabaseConnection(DatabaseConnection dbc) {
		if (dbc != null) {
			if (dbc.isOpen()) {
				pool.add(dbc);				
			} else {
				SysLog.logSysError("tried to return closed database connection to pool; ignoring it");
			}
		} else {
			SysLog.logSysError("tried to return null to database connection pool; ignoring it");
		}
	}
	
	/**
	 * 
	 */
	protected String name = null;
	
	/**
	 * 
	 */
	protected Connection rdbmsConnection = null;
	
	/**
	 * Map contains prepared statements retrieved by query string
	 */
	protected Map<String, PreparedStatement> readingStatements = new HashMap<String, PreparedStatement>();
	protected Map<String, PreparedStatement> updatingStatements = new HashMap<String, PreparedStatement>();

	/**
	 * 
	 */
	protected DatabaseConnection(String dbcName) throws SQLException {
		name = dbcName;
		rdbmsConnection = openRdbmsConnection();
	}
	
	/**
	 * 
	 */
	protected void finalize() {
		try {
			pool.remove(this); // just to be sure...
			closeConnection(rdbmsConnection);
		} catch (Throwable t) {
			SysLog.logThrowable(t);
		}
	}
	
	/**
	 * 
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 */
	public boolean isOpen() {
		boolean result = false;
		
		try {
			result = (rdbmsConnection != null) && !rdbmsConnection.isClosed();
		} catch (SQLException ex) {
			SysLog.logThrowable(ex);
		}
		
		return result;
	}
	
    /**
     * 
     */
    public Connection getRdbmsConnection() throws SQLException {
    	return rdbmsConnection;
    }
    
	/**
	 * 
	 */
	public PreparedStatement getReadingStatement(String stmt) throws SQLException {
		PreparedStatement result = readingStatements.get(stmt);
		if (result == null) {
			result = getRdbmsConnection().prepareStatement(stmt);
	   		SysLog.logCreatedObject("PreparedStatement", result.toString());
	   		readingStatements.put(stmt, result);
		}
		
		return result;
	}
	
	/**
	 * 
	 */
	public PreparedStatement getUpdatingStatement(String stmt) throws SQLException {
		PreparedStatement result = updatingStatements.get(stmt);
		if (result == null) {
			result = getRdbmsConnection().prepareStatement(stmt, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
	   		SysLog.logCreatedObject("UpdatingStatement", result.toString());
	   		updatingStatements.put(stmt, result);
		}
		
		return result;
	}
		
	/**
	 *
	 */
	static {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException ex) {
			SysLog.logThrowable(ex);
		}
	}
	
	/**
	 * 
	 */
	public static Connection openRdbmsConnection() throws SQLException {
		String dbConnection = SysConfig.getDbConnectionAsString();
		String dbUser = SysConfig.getDbUserAsString();
		String dbPassword = SysConfig.getDbPasswordAsString();
   		Connection result = DriverManager.getConnection(dbConnection, dbUser, dbPassword);
   		SysLog.logSysInfo("opening database connection: " + result.toString());
   		return result;
	}
	
	/**
	 * 
	 */
	public static void closeConnection(Connection cn) throws SQLException {
  		SysLog.logSysInfo("closing database connection: " + cn.toString());
		cn.close();
	}

}
