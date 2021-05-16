package net.hashsploit.clank.config.objects;

public class DatabaseInfo {

	private String mode;
	private String host;
	private String database;
	private String username;
	private String password;

	public DatabaseInfo(final String mode, final String host, final String database, final String username, final String password) {
		this.mode = mode;
		this.host = host;
		this.database = database;
		this.username = username;
		if (password.equals("")) {
			this.password = null;
		}
		else {
			this.password = password;
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMode() {
		return mode;
	}

}
