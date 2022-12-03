package com.noron.commons.repository.impl.session;

import com.noron.commons.data.model.session.Metadata;
import com.noron.commons.repository.IMongoRepository;

import java.util.List;

public interface IMetadataRepository extends IMongoRepository<Metadata> {
    void delete(String id);

    List<Metadata> getBySessionId(String sessionId);

    List<Metadata> getBySessionIds(List<String> sessionIds);

    void deletedBySessionId(String sessionId);

    void deletedByIds(List<String> ids, String userId);
}
