package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

@Modifying
@Transactional
@Query("DELETE FROM Message m WHERE m.messageId = :messageId")
int deleteById(int messageId);  // Use 'int' instead of 'Integer' here
}
