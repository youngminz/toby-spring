package springbook.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import springbook.user.dao.UserDao;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import java.util.List;

public class UserService {
    UserDao userDao;
    // FIXME: 왜 위는 Autowired가 없어도 되고 아래는 붙어 있어야 하는 걸까??
    @Autowired
    UserLevelUpgradePolicy userLevelUpgradePolicy;

    public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
    public static final int MIN_RECOMMEND_FOR_GOLD = 30;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setUserLevelUpgradePolicy(UserLevelUpgradePolicy userLevelUpgradePolicy) {
        this.userLevelUpgradePolicy = userLevelUpgradePolicy;
    }

    public void add(User user) {
        if (user.getLevel() == null) {
            user.setLevel(Level.BASIC);
        }
        userDao.add(user);
    }

    public void upgradeLevels() {
        List<User> users = userDao.getAll();
        for (User user : users) {
            if (userLevelUpgradePolicy.canUpgradeLevel(user)) {
                userLevelUpgradePolicy.upgradeLevel(user);
            }
        }
    }
}
