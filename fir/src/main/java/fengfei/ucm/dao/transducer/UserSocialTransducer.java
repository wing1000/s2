package fengfei.ucm.dao.transducer;

import fengfei.forest.database.dbutils.Transducer;
import fengfei.ucm.entity.profile.UserSocial;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSocialTransducer implements Transducer<UserSocial> {

    @Override
    public UserSocial transform(ResultSet rs) throws SQLException {
        Integer idUser = rs.getInt("id_user");
        String web_site = rs.getString("web_site");
        String weibo = rs.getString("weibo");
        String qq = rs.getString("qq");
        String qq_weibo = rs.getString("qq_weibo");
        String douban = rs.getString("douban");
        String twitter = rs.getString("twitter");
        String facebook = rs.getString("facebook");
        String flickr = rs.getString("flickr");
        String blog = rs.getString("blog");
        String skype = rs.getString("skype");
        String fengniao = rs.getString("fengniao");
        String renren = rs.getString("renren");
        UserSocial userSocial = new UserSocial();
        userSocial.idUser = idUser;
        userSocial.web_site = web_site;
        userSocial.weibo = weibo;
        userSocial.qq = qq;
        userSocial.qq_weibo = qq_weibo;
        userSocial.douban = douban;
        userSocial.twitter = twitter;
        userSocial.facebook = facebook;
        userSocial.flickr = flickr;
        userSocial.blog = blog;
        userSocial.skype = skype;
        userSocial.fengniao = fengniao;
        userSocial.renren = renren;

        return userSocial;
    }

}
