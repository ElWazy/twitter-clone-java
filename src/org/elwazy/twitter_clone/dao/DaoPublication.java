package org.elwazy.twitter_clone.dao;

import org.elwazy.twitter_clone.models.Publication;

public interface DaoPublication extends GenericCrud<Publication> {
    int getIdLastPublication();
}
