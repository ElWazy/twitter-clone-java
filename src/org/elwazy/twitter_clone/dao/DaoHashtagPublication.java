package org.elwazy.twitter_clone.dao;

import org.elwazy.twitter_clone.models.HashtagPublication;

import java.util.List;

public interface DaoHashtagPublication extends GenericCrud<HashtagPublication> {
    List<HashtagPublication> getHastagsByPublication(int publicationId);
    int insertNullHashtag(HashtagPublication hashtagPublication);
}
