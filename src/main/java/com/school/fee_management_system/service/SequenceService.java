package com.school.fee_management_system.service;

import org.springframework.stereotype.Service;

@Service
public interface SequenceService {
    long getNextSequence(String seqName);
}
