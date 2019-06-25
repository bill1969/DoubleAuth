package viewstar.doubleauth.demo.jpa.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import viewstar.doubleauth.demo.jpa.api.ActionLog;
import viewstar.doubleauth.demo.jpa.api.User;

public interface  ActionLogRepository extends JpaRepository<ActionLog, Long> {
}
