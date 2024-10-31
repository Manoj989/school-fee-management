package com.school.fee_management_system.service;

import com.school.fee_management_system.model.Sequence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class SequenceServiceImplTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private SequenceServiceImpl sequenceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetNextSequence() {
        String seqName = "testSequence";
        Sequence sequence = new Sequence();
        sequence.setSeq(1L);

        when(mongoTemplate.findAndModify(
                any(Query.class),
                any(Update.class),
                any(FindAndModifyOptions.class),
                eq(Sequence.class)
        )).thenReturn(sequence);

        long nextSeq = sequenceService.getNextSequence(seqName);

        assertEquals(1L, nextSeq);
    }

    @Test
    public void testGetNextSequenceWithUpsert() {
        String seqName = "testSequence";
        Sequence sequence = new Sequence();
        sequence.setSeq(1L);

        when(mongoTemplate.findAndModify(
                any(Query.class),
                any(Update.class),
                any(FindAndModifyOptions.class),
                eq(Sequence.class)
        )).thenReturn(sequence);

        long nextSeq = sequenceService.getNextSequence(seqName);

        assertEquals(1L, nextSeq);
    }
}
