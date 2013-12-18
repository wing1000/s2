package fengfei.ucm.dao.transducer;

import fengfei.forest.database.dbutils.Transducer;
import fengfei.ucm.entity.profile.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserTransducer implements Transducer<User> {

    @Override
    public User transform(ResultSet rs) throws SQLException {
        Integer idUser = rs.getInt("id_user");
        String realName = rs.getString("real_name");
        String birthday = rs.getString("birthday");
        String sgender = rs.getString("gender");
        int gender = Integer.parseInt(sgender == null || "".equals(sgender) ? "0" : sgender);
        String phoneNum = rs.getString("phone_num");
        String country = rs.getString("country");
        String state = rs.getString("state");
        String city = rs.getString("city");
        String about = rs.getString("about");
        String niceName = rs.getString("nice_name");
        int headPhoto = rs.getInt("head_photo");
        int createAt = rs.getInt("create_at");
        long updateAt = rs.getLong("update_at");
        //
        int viewCount = rs.getInt("count_view");
        int voteCount = rs.getInt("count_vote");
        int favoriteCount = rs.getInt("count_favorite");
        int commentCount = rs.getInt("count_comment");
        int affectionCount = rs.getInt("count_affection");
        User user = new User(
            idUser,
            null,
            null,
            realName,
            birthday,
            gender,
            phoneNum,
            about,
            city,
            state,
            country);
        user.setNiceName(niceName);
        user.createAt = createAt;
        user.updateAt = updateAt;
        user.isHeadPhoto = headPhoto > 0;
        user.view = viewCount;
        user.vote = voteCount;
        user.comment = commentCount;
        user.favorite = favoriteCount;
        user.affection = affectionCount;
        return user;
    }

}
