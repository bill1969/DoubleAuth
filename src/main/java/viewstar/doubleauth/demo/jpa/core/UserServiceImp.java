package viewstar.doubleauth.demo.jpa.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import viewstar.doubleauth.demo.config.DataSourceConfig;
import viewstar.doubleauth.demo.config.TargetDateSource;
import viewstar.doubleauth.demo.jpa.api.ActionLog;
import viewstar.doubleauth.demo.jpa.api.User;
import viewstar.doubleauth.demo.jpa.dao.ActionLogRepository;
import viewstar.doubleauth.demo.jpa.dao.UserRepository;

@Service
public class UserServiceImp implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ActionLogRepository actionLogRepository;


    @Override
    @TargetDateSource(dataSource = DataSourceConfig.READ_DATASOURCE_KEY)
    public User getUserByUserId(String userid) {
        User user = userRepository.getUserByUserID(userid);
        return user;
    }

    @Override
    @Transactional
    @TargetDateSource(dataSource = DataSourceConfig.WRITE_DATASOURCE_KEY)
    public String save(User entity) throws Exception {
        if (entity.getUserID() != null) {
            User perz = userRepository.save(entity);
            return perz.getUserID();
        }
        User perz = userRepository.save(entity);
        return perz.getUserID();
    }

    @Override
    @Transactional
    @TargetDateSource(dataSource = DataSourceConfig.WRITE_DATASOURCE_KEY)
    public Long logsave(ActionLog entity) throws Exception {
        if (entity.getId() != null) {
            ActionLog perz = actionLogRepository.save(entity);
            return perz.getId();
        }
        ActionLog perz = actionLogRepository.save(entity);
        return perz.getId();
    }


}
