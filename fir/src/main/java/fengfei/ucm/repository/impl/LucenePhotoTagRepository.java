package fengfei.ucm.repository.impl;

import fengfei.ucm.entity.photo.Photo;
import fengfei.ucm.entity.photo.PhotoTag;
import fengfei.ucm.repository.PhotoTagRepository;

import java.util.List;

/**
 */
public class LucenePhotoTagRepository implements PhotoTagRepository {

    @Override
    public int[] saveTags(Photo photo, String[] oldTags, String[] newTags) throws Exception {
        return new int[0];
    }

    @Override
    public List<PhotoTag> fuzzilyFind(String name, Byte category, int offset, int limit) throws Exception {
        return null;
    }

    @Override
    public List<PhotoTag> preciselyFind(String name, Byte category, int offset, int limit) throws Exception {
        return null;
    }

    @Override
    public List<PhotoTag> find(String name, Byte category, boolean isFuzzed, int offset, int limit) throws Exception {
        return null;
    }

    @Override
    public List<PhotoTag> find(String name, boolean isFuzzed, int offset, int limit) throws Exception {
        return null;
    }

    @Override
    public List<Long> findPhotoIds(String name, Byte category, boolean isFuzzed, int offset, int limit) throws Exception {
        return null;
    }

    @Override
    public List<Long> findPhotoIds(String name, boolean isFuzzed, int offset, int limit) throws Exception {
        return null;
    }
}
