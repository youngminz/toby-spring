package springbook.user.dao;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import springbook.user.domain.Level;
import springbook.user.domain.User;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoJdbc implements UserDao {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<User> userMapper = new RowMapper<User>() {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();

            user.setId(resultSet.getString("id"));
            user.setName(resultSet.getString("name"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            user.setLevel(Level.valueOf(resultSet.getInt("level")));
            user.setLogin(resultSet.getInt("login"));
            user.setRecommend(resultSet.getInt("recommend"));

            return user;
        }
    };

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void add(final User user) throws DuplicateKeyException {
        jdbcTemplate.update(
                "insert into users (id, name, email, password, level, login, recommend) values (?, ?, ?, ?, ?, ?, ?)",
                user.getId(), user.getName(), user.getEmail(), user.getPassword(),
                user.getLevel().intValue(), user.getLogin(), user.getRecommend());
    }

    public User get(String id) {
        return jdbcTemplate.queryForObject(
                "select * from users where id = ?",
                new Object[]{id},
                userMapper);
    }

    public List<User> getAll() {
        return jdbcTemplate.query(
                "select * from users order by id",
                userMapper);
    }

    @Override
    public void update(User user) {
        this.jdbcTemplate.update(
                "update users set name = ?, email = ?, password = ?, level = ?, login = ?, " +
                        "recommend = ? where id = ?", user.getName(), user.getEmail(), user.getPassword(),
                user.getLevel().intValue(), user.getLogin(), user.getRecommend(),
                user.getId()
        );
    }

    public void deleteAll() {
        jdbcTemplate.update("delete from users");
    }

    public int getCount() {
        return jdbcTemplate.queryForInt("select count(*) from users");
    }
}
