package viewstar.doubleauth.demo.jpa.core;

import viewstar.doubleauth.demo.config.DataSourceConfig;
import viewstar.doubleauth.demo.config.TargetDateSource;
import viewstar.doubleauth.demo.jpa.api.ActionLog;
import viewstar.doubleauth.demo.jpa.api.User;

public interface UserService {

    @TargetDateSource(dataSource = DataSourceConfig.WRITE_DATASOURCE_KEY)
    String save(User entity) throws Exception;

    @TargetDateSource(dataSource = DataSourceConfig.WRITE_DATASOURCE_KEY)
    Long logsave(ActionLog entity) throws Exception;

    @TargetDateSource(dataSource = DataSourceConfig.READ_DATASOURCE_KEY)
    User getUserByUserId(String userid);
}
