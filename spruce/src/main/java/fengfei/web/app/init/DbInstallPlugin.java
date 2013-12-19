package fengfei.web.app.init;

import fengfei.ucm.dao.Sequence;
import fengfei.ucm.repository.impl.SqlCommentRepository;
import fengfei.ucm.repository.impl.SqlPhotoRepository;
import fengfei.ucm.repository.impl.SqlUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DbInstallPlugin  {
    static Logger logger= LoggerFactory.getLogger(AppInitPlugin.class);

    final static String[] Tables = {
            SqlUserRepository.UserTableName,
            SqlPhotoRepository.PhotosTableName,
            SqlCommentRepository.CommentsTableName,
            SqlCommentRepository.CommentsPhotoTableName
    };

    public void onApplicationStart() {
        logger.info("Sequence init.");
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
