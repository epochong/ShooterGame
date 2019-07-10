package cn.epochong.shoot;

import java.sql.*;

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
    public static PreparedStatement ps;

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

    public static boolean isHasUserName(String name) throws SQLException {
        Connection connection = JdbcUtils.getConnection();
        //根据查到的学号在成绩表中查中所有的成绩,只显示姓名和成绩
        PreparedStatement ps = connection.prepareStatement("select * from score");
        ResultSet resultSet = ps.executeQuery();
        boolean judge = false;
        while(resultSet.next()) {
            //封装成一个学生对象
            String username = resultSet.getString("username");
            if (username.equals(name)) {
                judge = true;
            }
        }
        resultSet.close();
        ps.close();
        connection.close();
        return judge;
    }
    public static void save(String name,int score) throws Exception {
        DBConnection();
        if (isHasUserName(name)) {
            ps = connection.prepareStatement("update score set score = ? where username = ?");
            ps.setInt(1,score);
            ps.setString(2,name);
            ps.executeUpdate();
            System.out.println(score);
            JdbcUtils.close(connection,ps);
        } else {
            String sql = "insert into score(username,score) values('"+name+"',"+score+")";
            statement.executeUpdate(sql);
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
