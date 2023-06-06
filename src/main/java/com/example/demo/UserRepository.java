package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	public User findBylogin(String account);

	/**

	* 相当于select *from user where name like ?

	* 但是有一点需要注意的是，%需要我们自己来写

	* @param name

	* @return

	*/
}
