package com.zyj.test.redistest;

import com.zyj.test.redistest.CacheConfig.ConfInfo;
import com.zyj.util.GsonUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisTest {
	 private static JedisPool pool = null;
	 
		private static boolean running = false;

		private static JedisPool cachedPool;

		private static JedisPool persistedPool;

	    public static JedisPool getPool() {
	        if (pool == null) {
	            JedisPoolConfig config = new JedisPoolConfig();
	            config.setMaxTotal(500);
	            config.setMaxIdle(5);
	            config.setMaxWaitMillis(1000*10);
	            //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	            config.setTestOnBorrow(true);
	            //new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
	            pool = new JedisPool(config, "127.0.0.1", 6379, 10000, "");

	        }
	        return pool;
	    }

	    public synchronized static Jedis getResource() {
	        if (pool == null) {
	            pool = getPool();
	        }
	        return pool.getResource();
	    }

	    // 返还到连接池
	    // Deprecated
	    // 换成用完之后, redis.close()
	    /*
	    public static void returnResource(Jedis redis) {
	        if (redis != null) {
	            pool.returnResource(redis);
	        }
	    }
	    */

	    public static void main(String[] args) {
	    	CacheConfig.init();
	    	
	    	
	    	JedisPoolConfig config = new JedisPoolConfig();// Jedis池配置

			config.setMaxTotal(500);;// 最大活动的对象个数

			config.setMaxIdle(1000 * 60);// 对象最大空闲时间

			config.setMaxWaitMillis(1000 * 3);// 获取对象时最大等待时间

			ConfInfo  conf = CacheConfig.get(0);
			
			if(conf !=null){			
				String url = conf.getUrl();
				int port = conf.getPort();
				int database = conf.getDatabase();
				String passwd = conf.getPasswd();
				if (passwd.equals("")) {
					cachedPool = new JedisPool(config, url, port);
				} else {
					cachedPool = new JedisPool(config, url, port, 30, passwd);
				}
//				StoredObjManager.initDB(database);
//				log.info(cachedPool.getResource().ping());
//				log.info(" * connect StoredObjManager cache " + url + " db " + database);
			}
			
			
			conf = CacheConfig.get(1);
			
			if(conf !=null){			
				String url = conf.getUrl();
				int port = conf.getPort();
				int database = conf.getDatabase();
				System.out.println("database:"+database);
				String passwd = conf.getPasswd();
				if (passwd.equals("")) {
					persistedPool = new JedisPool(config, url, port);
				} else {
					persistedPool = new JedisPool(config, url, port, 30, passwd);
				}
//				StoredDataManager.initDB(database);
//				log.info(persistedPool.getResource().ping());
//				log.info(" * connect StoredDataManager cache " + url + " db " + database);
			}
			Jedis redis = persistedPool.getResource();
			
			
			System.out.println("[pyj-3dcd93094b72441e9d378b16f6bebdf1,pyj-77069362f4324cd4962267e79244abed,pyj-7ac17199a42f402b8f03433be57bb1d6,pyj-d59375e2803540da9198159bbc5f5603]".length());
			int loop = 1;
//	        while (loop < 20) {
//	            try {
//	                long start = System.currentTimeMillis();
////	                redis = getResource();
//	                redis.set("k1", "v1");
//	                
//	                String stu =GsonUtil.toJson(new Student(1,"zhu"+loop));
//	                redis.zadd("redis_test_key", 10000, stu);
//	                Long ret = redis.zcard("redis_test_key");
//	                System.out.println(ret);
////	                System.out.println(GsonUtil.fromJson(ret, Student.class));
//	        
//	                long end = System.currentTimeMillis();
//	                System.out.printf("Get ret from redis: %s with %d millis\n", ret, end-start);
//	            } finally {
//	            	  
//	            }
//	            loop++;
//	        }
	        if (redis != null) {
                redis.close();
            }
	    }
}

class Student{
	private int id;
	
	private String name ;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Student(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + "]";
	}
	
}
