package com.vivebest.redis.dao;

import java.util.List;

import com.vivebest.redis.entity.User;
/**
 * 用户数据操作接口
 * @author clear
 *
 */
public interface UserDao {
	/**
	 * 新增 <br>
	 * ------------------------------<br>
	 * 
	 * @param user
	 * @return
	 */
	boolean add(User user);

	/**
	 * 批量新增 使用pipeline方式 <br>
	 * ------------------------------<br>
	 * 
	 * @param list
	 * @return
	 */
	boolean add(List<User> list);

	/**
	 * 删除 <br>
	 * ------------------------------<br>
	 * 
	 * @param key
	 */
	void delete(String key);

	/**
	 * 删除多个 <br>
	 * ------------------------------<br>
	 * 
	 * @param keys
	 */
	void delete(List<String> keys);

	/**
	 * 修改 <br>
	 * ------------------------------<br>
	 * 
	 * @param user
	 * @return
	 */
	boolean update(User user);

	/**
	 * 通过key获取 <br>
	 * ------------------------------<br>
	 * 
	 * @param keyId
	 * @return
	 */
	User get(String keyId);
}
