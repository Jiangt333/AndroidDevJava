package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	public User findByphone(String phone);

	/**

	* 相当于select *from user where name like ?

	* 但是有一点需要注意的是，%需要我们自己来写

	* @param name

	* @return

	*/
}
