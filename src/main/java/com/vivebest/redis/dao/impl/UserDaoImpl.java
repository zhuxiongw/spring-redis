package com.vivebest.redis.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import com.vivebest.redis.dao.BasicRedisDao;
import com.vivebest.redis.dao.UserDao;
import com.vivebest.redis.entity.User;

@Component("userDaoImpl")
public class UserDaoImpl extends BasicRedisDao<String, User> implements UserDao {

	public boolean add(final User user) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] key = serializer.serialize(user.getId());
				byte[] name = serializer.serialize(user.getName());
				return connection.setNX(key, name);
			}
		});
		return result;
	}

	public boolean add(final List<User> list) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				for (User user : list) {
					byte[] key = serializer.serialize(user.getId());
					byte[] name = serializer.serialize(user.getName());
					connection.setNX(key, name);
				}
				return true;
			}
		}, true, false);
		return result;
	}

	public void delete(String key) {
		List<String> list = new ArrayList<String>();
		list.add(key);
		delete(list);
	}

	public void delete(List<String> keys) {
		redisTemplate.delete(keys);
	}

	public boolean update(final User user) {
		String id = user.getId();
		if(id == null){
			throw new NullPointerException("数据不存在");
		}
		
		boolean result= redisTemplate.execute(new RedisCallback<Boolean>() {

			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
			RedisSerializer<String> serializer = getRedisSerializer();
				byte[] key = serializer.serialize(user.getId());
				byte[] name = serializer.serialize(user.getName());
				
				connection.set(key, name);
				return true;
			}
		});
		return result;
	}

	public User get(final String keyId) {
		User user = redisTemplate.execute(new RedisCallback<User>() {

			public User doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] key = serializer.serialize(keyId);
				byte[] value = connection.get(key);
				if (value == null) {
					return null;
				}
				String name = serializer.deserialize(value);
				return new User(keyId, name, null);

			}
		});
		return user;
	}

}
