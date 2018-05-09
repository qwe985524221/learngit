package com.zyj.test.redistest;

import java.io.FileInputStream;

import com.zyj.util.GsonUtil;


public class CacheConfig {
	
	
	public static class ConfInfo{
		
		private String url;
		
		private int port;
		
		private int database;
		
		private String passwd;
	

		public String getUrl() {
			return url;
		}

		public int getPort() {
			return port;
		}

		public int getDatabase() {
			return database;
		}

		public String getPasswd() {
			return passwd;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public void setPort(int port) {
			this.port = port;
		}

		public void setDatabase(int database) {
			this.database = database;
		}

		public void setPasswd(String passwd) {
			this.passwd = passwd;
		}
		
		
	}
	

	private static CacheConfig instant;
	
	
	private ConfInfo[]  configs;


	public static void init() {

		String conf = readFile();

		instant = load(conf);

	}

	public static String readFile() {
		String configJson = null;
		FileInputStream in = null;
		try {
			in = new FileInputStream("./config/CacheConfig.json");
			byte[] data = new byte[in.available()];
			in.read(data);
			configJson = new String(data, "utf-8");
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return configJson;
	}

	public static CacheConfig load(String configJson) {
		Object obj = GsonUtil.fromJson(configJson, CacheConfig.class);
		return (CacheConfig) obj;
	}

	public static CacheConfig getInstant() {
		return instant;
	}
	
	public static void setConfs(ConfInfo[] configs) {
		instant.configs = configs;
	}

	public static ConfInfo get(int index){
		if(instant.configs!=null && instant.configs.length >=index){
			return instant.configs[index];
		}
		return null;
	}

	public static ConfInfo[] getConfs() {
		return instant.configs;
	}

	public static void setInstant(CacheConfig instant) {
		CacheConfig.instant = instant;
	}

	
	
}
