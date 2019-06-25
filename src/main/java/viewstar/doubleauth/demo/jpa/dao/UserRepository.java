package viewstar.doubleauth.demo.jpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import viewstar.doubleauth.demo.jpa.api.User;

public interface UserRepository extends JpaRepository<User, String> {

    /**
     * 我们这里只需要写接口，不需要写实现，spring boot会帮忙自动实现
     *
     * */

    @Query("from User where userid =:userid ")
    public User getUserByUserID(String userid);
}