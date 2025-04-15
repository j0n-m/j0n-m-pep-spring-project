package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message,Integer> {

    public List<Message> findAllByPostedBy(int accountId);

    //Directly updates messageText to message by id and returns rows affected
    @Modifying
    @Query("UPDATE Message m SET m.messageText = :mText WHERE m.messageId = :mId")
    public int updateMessageTextByMessageId(@Param("mText") String mText, @Param("mId")int mId);

    //Directly deletes message row by messageId and returns rows affected
    @Modifying
    @Query("DELETE FROM Message m WHERE m.messageId = :messageId")
    public int deleteById(@Param("messageId") int messageId);
}
