package fengfei.fir.search.lucene;

import fengfei.sprucy.AppConstants;
import fengfei.ucm.entity.profile.User;
import fengfei.ucm.entity.profile.UserPwd;
import org.apache.lucene.document.*;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import java.io.IOException;

/**
 */
public class UserIndexCreator extends IndexCreator<UserPwd> {


    public UserIndexCreator(LuceneFactory factory) {
        super(factory);
    }

    @Override
    protected Document toDocument(UserPwd user) throws IOException {
        Document doc = new Document();

        String content = toContent(user);
        doc.add(new StringField(UserFields.ID, String.valueOf(user.idUser), Field.Store.YES));
//        doc.add(new StringField(UserFields.Name, String.valueOf(user.idUser), Field.Store.YES));
        doc.add(new TextField(UserFields.Content, content, Field.Store.NO));//存储
        doc.add(new NumericDocValuesField(UserFields.At, user.createAt));//存储
        return doc;
    }

    private String toContent(UserPwd user) {
        StringBuilder sb = new StringBuilder();
        split(sb, user.userName);
        split(sb, user.email);
        if (user instanceof User) {
            User u = (User) user;
            if (u.getNiceName() != null) split(sb,u.niceName);
            if (u.getRealName() != null) split(sb, u.realName);
//            sb.append(((User) user).getGender())


        }
        return sb.toString();
    }

    private void split(StringBuilder sb, String email) {
        if (email == null || "".equals(email)) return;
        String[] s = email.split("@|.|_| |-");
        for (String s1 : s) {
            if (!"".equals(s1)) {
                sb.append(s1).append(AppConstants.CommaSeparator);
            }
        }

    }

    @Override
    protected String getEntityId(UserPwd obj) {
        return String.valueOf(obj.idUser);
    }

    public static void main(String[] args) throws Exception {
        String dir = "/opt/lucene/index/user";
        LuceneFactory luceneFactory = LuceneFactory.get(dir);
        UserIndexCreator creator = new UserIndexCreator(luceneFactory);
        creator.begin();
        for (int i = 0; i < 5; i++) {
            User user = new User();
            long current = System.currentTimeMillis();
            user.idUser = i;

            user.userName = "user_name";
            user.niceName = "nice name";
            user.realName = "real name last";
            user.createAt = (int) (current / 1000);
            user.updateAt = current;
            user.gender = i % 2 == 0 ? 1 : 2;
            creator.add(user);
        }
        creator.end();

        Searcher searcher = new Searcher(luceneFactory);
        TopDocs tds = searcher.search(null, 100, 1, "user", "nice");
        out(tds, luceneFactory.getSearcher());

    }

    private static void out(TopDocs tds, IndexSearcher searcher) throws IOException {
        System.out.println("总共有【" + tds.totalHits + "】条匹配结果");
        //6.根据TopDocs获取ScoreDoc对象
        ScoreDoc[] sds = tds.scoreDocs;
        for (ScoreDoc sd : sds) {
            //7.根据searcher和TopDocs对象获取Document对象
            Document d = searcher.doc(sd.doc);//sd.doc:文档内部编号
            //8.根据Document对象获取需要的值
            System.out.println(String.format("%f  %d  %s    %s", sd.score, sd.shardIndex, d.get("id"), d.get(PhotoFields.Content)));
        }
    }
}
