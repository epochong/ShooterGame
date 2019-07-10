package cn.epochong.shoot;

/**
 * @author epochong
 * @date 2019/7/3 9:55
 * @email epochong@163.com
 * @blog epochong.github.io
 * @describe 奖励接口,小蜜蜂实现该接口,可以扩展
 */
public interface Award {

    /**
     * 增加火力
     */
    int DOUBLE_FIRE = 0;
    /**
     * 加命
     */
    int LIFE = 1;
    /**
     * 获取奖励类型
     * @return
     */
    int getAwardType();
}