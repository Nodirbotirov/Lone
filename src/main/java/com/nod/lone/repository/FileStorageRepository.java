package com.nod.lone.repository;

import com.nod.lone.model.FileStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileStorageRepository extends JpaRepository<FileStorage, Long> {
    Optional<FileStorage> findByName(String name);
}
