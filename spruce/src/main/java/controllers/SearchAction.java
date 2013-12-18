package controllers;

import cn.bran.play.JapidResult;
import fengfei.ucm.repository.impl.SqlSearchRespository;
import japidviews.Application.Search;
import org.apache.commons.collections.MapUtils;
import play.modules.router.Any;
import play.modules.router.Get;
import play.modules.router.Gets;

import java.util.Map;

public class SearchAction extends Admin {

    static SqlSearchRespository searchRespository = new SqlSearchRespository();

    @Gets({ @Get("/search/{<[0-9]+>pageNum}/?"), @Get("/search/?") })
    @Any("/search/{<[0-9]+>pageNum}/?")
    public static void search(int pageNum) {
        String pagePath = "/search";
        try {
            ResultsBean rb = actions(pageNum);
            throw new JapidResult(new Search().render(rb.type, pagePath, rb.result, pageNum));
        } catch (Exception e) {
            throw new JapidResult(new Search().render(0,pagePath,null,0));
        }

    }

    static ResultsBean actions(int pageNum) throws Exception {
        Map<String, String> map = params.allSimple();
        Byte category = MapUtils.getByte(map, "c", null);
        String actionType = MapUtils.getString(map, "t", "photos");
        String order = MapUtils.getString(map, "o", null);
        String queryStr = MapUtils.getString(map, "q", null);
        pageNum = pageNum <= 0 ? 1 : pageNum;
        int offset = (pageNum - 1) * TotalRowShow;

        ResultsBean rb = new ResultsBean();
        switch (actionType.toUpperCase()) {
        case "PHOTOS":
            rb.type = 0;
            rb.result = searchRespository.selectPhotos(queryStr, category, offset, TotalRowShow);
            break;
        case "PHOTO_TAGS":
            rb.type = 0;
            rb.result = searchRespository.selectPhotosByTag(
                queryStr,
                category,
                offset,
                TotalRowShow);
            break;
        case "USERS":
            rb.type = 1;
            rb.result = searchRespository.selectUsers(queryStr, offset, TotalRowShow);
            break;
        case "STORIES":
            rb.type = 0;
            break;
        case "STORY_TAGS":
            rb.type = 0;
            break;

        default:
            break;
        }
        return rb;

    }

    public static class ResultsBean {

        public int type;
        public Object result;

    }
}
