package cn.epochong.shoot;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author epochong
 * @date 2019/7/5 9:43
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe
 */
public class UserDao {
    public static Connection connection ;
    public static Statement statement;

    public static void main(String[] args) throws Exception{
        String[] result = getScoreAndUserName();
        for (int i = 0; i < result.length; i++) {
            if (result[i] != null) {
                System.out.println(result[i]);
            }
        }
    }
    public static void DBConnection() throws Exception {
        connection = JdbcUtils.getConnection();
        statement = connection.createStatement();
    }

    public static void save(String name,int score) {
        try {
            DBConnection();
            String sql = "insert into score(username,score) values('"+name+"',"+score+")";
            statement.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(connection,statement);
        }
    }

    public static String[] getScoreAndUserName() throws Exception {
        ResultSet resultSet = null;
        String result[] = new String[100];
        try {
            DBConnection();
            String sql = "select username,score from score order by score desc";
            resultSet = statement.executeQuery(sql);
            for (int i = 0; i < result.length; i++) {
                if (resultSet.next()) {
                    result[i] = "第" + (i + 1) + "名:" + resultSet.getString("username") + ":" + resultSet.getInt("score");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.close(connection,statement,resultSet);
        }
        return result;
    }
}
