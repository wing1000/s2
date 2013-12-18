package fengfei.ucm.repository;

import fengfei.ucm.dao.UnitNames;
import fengfei.ucm.entity.photo.Photo;
import fengfei.ucm.entity.photo.PhotoTag;

import java.util.List;

/**
 */
public interface PhotoTagRepository extends UnitNames {
    int[] saveTags(Photo photo,
                   String[] oldTags,
                   String[] newTags) throws Exception;

    List<PhotoTag> fuzzilyFind(
            String name,
            Byte category,
            int offset,
            int limit) throws Exception;

    List<PhotoTag> preciselyFind(
            String name,
            Byte category,
            int offset,
            int limit) throws Exception;

    List<PhotoTag> find(
            String name,
            Byte category,
            boolean isFuzzed,
            int offset,
            int limit) throws Exception;

    List<PhotoTag> find(
            String name,
            boolean isFuzzed,
            int offset,
            int limit) throws Exception;

    List<Long> findPhotoIds(
            String name,
            Byte category,
            boolean isFuzzed,
            int offset,
            int limit) throws Exception;

    List<Long> findPhotoIds(
            String name,
            boolean isFuzzed,
            int offset,
            int limit) throws Exception;
}
