package com.school.fee_management_system.service;

import com.school.fee_management_system.model.Sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class SequenceServiceImpl implements SequenceService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public long getNextSequence(String seqName) {
        Query query = new Query(Criteria.where("_id").is(seqName));
        Update update = new Update().inc("seq", 1);
        FindAndModifyOptions options = new FindAndModifyOptions().returnNew(true).upsert(true);
        Sequence seqId = mongoTemplate.findAndModify(query, update, options, Sequence.class);
        assert seqId != null;
        return seqId.getSeq();
    }
}
