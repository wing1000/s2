package fengfei.ucm.dao;

import fengfei.forest.database.dbutils.ForestGrower;
import fengfei.ucm.dao.transducer.UserSocialTransducer;
import fengfei.ucm.entity.profile.UserSocial;

import java.sql.SQLException;

/**
 * @User: tietang
 */
public class UserSocialDao {
    static final String Insert = "insert into user_social(id_user,web_site,weibo,qq,qq_weibo,douban,twitter,facebook,flickr,blog,skype,fengniao,renren) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
    static final String Update = "update user_social set web_site=?,weibo=?,qq=?,qq_weibo=?,douban=?,twitter=?,facebook=?,flickr=?,blog=?,skype=?,fengniao=?,renren=? where id_user=?";
    static final String Delete = "delete user_social where id_user=?";
    static final String SelectOne = "select id_user,web_site,weibo,qq,qq_weibo,douban,twitter,facebook,flickr,blog,skype,fengniao,renren from user_social where id_user=?";
    static final String SelectOneForLock = "select id_user,web_site,weibo,qq,qq_weibo,douban,twitter,facebook,flickr,blog,skype,fengniao,renren from user_social where id_user=? for update";

    public static int save(ForestGrower grower, String suffix, UserSocial userSocial) throws SQLException {
        UserSocial oldUserSocial1 = grower.selectOne(
                String.format(SelectOneForLock, suffix), new UserSocialTransducer(),
                userSocial.idUser);

        if (oldUserSocial1 == null) {
            int updated = grower.update(String.format(Insert, suffix),
                    userSocial.idUser,
                    userSocial.web_site,
                    userSocial.weibo,
                    userSocial.qq,
                    userSocial.qq_weibo,
                    userSocial.douban,
                    userSocial.twitter,
                    userSocial.facebook,
                    userSocial.flickr,
                    userSocial.blog,
                    userSocial.skype,
                    userSocial.fengniao,
                    userSocial.renren);
            return updated;
        } else {
            int updated = grower.update(
                    String.format(Update, suffix),
                    userSocial.web_site,
                    userSocial.weibo,
                    userSocial.qq,
                    userSocial.qq_weibo,
                    userSocial.douban,
                    userSocial.twitter,
                    userSocial.facebook,
                    userSocial.flickr,
                    userSocial.blog,
                    userSocial.skype,
                    userSocial.fengniao,
                    userSocial.renren,
                    userSocial.idUser);
            return updated;
        }

    }

    public static UserSocial get(ForestGrower grower, String suffix, Integer idUser) throws SQLException {
        UserSocial userSocial = grower.selectOne(
                String.format(SelectOne, suffix), new UserSocialTransducer(),
                idUser);
        return userSocial;
    }
}
