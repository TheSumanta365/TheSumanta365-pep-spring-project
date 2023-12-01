package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Message;
import com.example.repository.MessageRepository;


import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class MessageService {
    @Autowired
    MessageRepository messageRepository;
  
//creating  new msg
    public Message createNewMessage(Message msg){
      return messageRepository.save(msg);
    }
    //getting all message
    public List<Message> getAllMessages()
    {
        return messageRepository.findAll();
    }

    //get message by msg id
    public Message getMessageByMessageId(int id)
    {
          Optional<Message> messageOptional=messageRepository.findById(id);
          if(messageOptional.isPresent())
          {
           Message message=messageOptional.get();
           return message;
          }
          
        return null;
    }

    //delete by msg id
    public void deleteBymsgId(int id)
    {
        messageRepository.deleteById(id);
    }

    //update msg by msg id
    public Message updateMessageById(int id,Message message)
    {
      Optional<Message> messageOptional=messageRepository.findById(id);
      if(messageOptional.isPresent()){
        Message updated_message=messageOptional.get();
        updated_message.setMessage_text(message.getMessage_text());
        messageRepository.save(updated_message);
        return updated_message;
      }
      else
      {
       return null;  
       }
    }
    //get all msg by a user
    public List<Message> getAllMessagesByUserId(int id){
    return messageRepository.getAllMessagesByUserId(id);
    }
   //msg exist or not
   public boolean messageExistOrNot(int message_id){
      return messageRepository.existsById(message_id);
  }
  
}
