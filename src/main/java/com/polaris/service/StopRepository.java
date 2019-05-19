package com.polaris.service;

import com.microsoft.azure.spring.data.documentdb.repository.DocumentDbRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StopRepository extends DocumentDbRepository<Stop, String> { }