package fengfei.web.app.init;

import fengfei.ucm.dao.Sequence;
import fengfei.ucm.repository.impl.SqlCommentRepository;
import fengfei.ucm.repository.impl.SqlPhotoRepository;
import fengfei.ucm.repository.impl.SqlUserRepository;
import play.Logger;
import play.PlayPlugin;

public class DbInstallPlugin extends PlayPlugin {

    final static String[] Tables = {
            SqlUserRepository.UserTableName,
            SqlPhotoRepository.PhotosTableName,
            SqlCommentRepository.CommentsTableName,
            SqlCommentRepository.CommentsPhotoTableName
    };

    @Override
    public void onApplicationStart() {
        Logger.info("Sequence init.");
        try {
            Sequence.install(1, Tables);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Sequence.install(2, Tables);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
