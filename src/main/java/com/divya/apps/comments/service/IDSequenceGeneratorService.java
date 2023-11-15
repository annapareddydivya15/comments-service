package com.divya.apps.comments.service;

import com.divya.apps.comments.model.SequenceId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class IDSequenceGeneratorService {

    private MongoOperations mongoOperations;

    @Autowired
    public IDSequenceGeneratorService(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public int generateSequence(String id) {

        SequenceId seq = mongoOperations.findAndModify(query(where("_id").is(id)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                SequenceId.class);
        return !Objects.isNull(seq) && seq.getSeq() != null ? seq.getSeq() : 1;
    }
}
