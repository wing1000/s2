package fengfei.ucm.repository;

import fengfei.ucm.dao.UnitNames;
import fengfei.ucm.entity.photo.Tag;
import fengfei.ucm.entity.photo.Tag.TagType;

import java.util.List;

public interface TagRepository extends UnitNames {

    boolean addTags(long idContent, TagType type, String... tags) throws Exception;

    List<Tag> select(String name, String type, int offset, int limit) throws Exception;

    List<Tag> select(String name, int offset, int limit) throws Exception;

}
