package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@Component
@RestController
public class SocialMediaController {
@Autowired
AccountService accountService;
@Autowired
MessageService messageService;

    @PostMapping("/register")
    public ResponseEntity<?> newUserAccount(@RequestBody Account account) {
        Account existingAccount = accountService.getAccountByUsername(account.getUsername());
        if(existingAccount!=null)
        {
            return ResponseEntity.status(409).body("Username already exists");

        }
        else if (!account.getUsername().isBlank() && account.getPassword().length() >4)
        {
            accountService.addNewAccount(account);
            return ResponseEntity.ok(account);
        }
        else
        {
            return ResponseEntity.status(400).body("Username is invalid");
        }
        
           
 }


    @PostMapping("/login")
    public ResponseEntity<Account> verifyLogin(@RequestBody Account account){
        Account requested_acc=accountService.getAccountByUsername(account.getUsername());
        if(requested_acc != null && (requested_acc.getPassword().contains(account.getPassword())))
        {
            return ResponseEntity.ok(requested_acc);
        }
        else
        {
            return ResponseEntity.status(401).build();
        }
}


    @PostMapping("/messages")
    public ResponseEntity<Message> createNewMessage(@RequestBody Message message){
       
        if(messageService.messageExistOrNot(message.getPosted_by()) && !message.getMessage_text().isBlank() && message.getMessage_text().length()< 255)
        {
           Message addedMsg=messageService.createNewMessage(message);
           return ResponseEntity.ok(addedMsg);
        }
        else
        {
           return ResponseEntity.status(400).build();
        }
}

    
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessagesHandler(){
        List<Message> msg=messageService.getAllMessages();
        return ResponseEntity.ok(msg);
}


    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageByMessageId(@PathVariable int message_id){
        Message msg=messageService.getMessageByMessageId(message_id);
        if(msg != null)
        {
            return ResponseEntity.ok(msg);
        }
        else
            return ResponseEntity.ok().build();
}

    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<String> deleteMessageByMessageId(@PathVariable int message_id){
        if(messageService.getMessageByMessageId(message_id) != null)
        {
            messageService.deleteBymsgId(message_id);
            return ResponseEntity.ok("1 row affected");
        }
        else
            return ResponseEntity.ok().build();
}


    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<?> UpdateMessage(@PathVariable int message_id,@RequestBody Message messageUpdate){
    try {
        if(messageUpdate.getMessage_text().trim().isEmpty() || messageUpdate.getMessage_text().length() >255){
            return ResponseEntity.badRequest().body("String is empty or character range exceeds");
        }
        Message updateMessage=messageService.updateMessageById(message_id,messageUpdate);
        if(updateMessage==null){
            return ResponseEntity.badRequest().body("update unsuccessful");
        }
        return ResponseEntity.ok(1);
       }catch (Exception e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
}




    @GetMapping("/accounts/{account_id}/messages")
    public ResponseEntity<List<Message>> getAllMessagesByUserId(@PathVariable int account_id){
        List<Message> messages=messageService.getAllMessagesByUserId(account_id);
            return ResponseEntity.ok(messages);
}

}



