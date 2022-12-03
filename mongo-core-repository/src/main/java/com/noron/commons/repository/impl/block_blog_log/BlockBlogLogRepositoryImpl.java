package com.noron.commons.repository.impl.block_blog_log;

import com.mongodb.client.MongoCollection;
import com.noron.commons.data.model.BlockBlogLog;
import com.noron.commons.repository.AbsMongoRepository;
import org.bson.Document;
import org.springframework.stereotype.Repository;

@Repository
public class BlockBlogLogRepositoryImpl extends AbsMongoRepository<BlockBlogLog>
        implements IBlockBlogLogRepository {
    public BlockBlogLogRepositoryImpl(MongoCollection<Document> blockBlogLogCollection) {
        super(blockBlogLogCollection);
    }
}
