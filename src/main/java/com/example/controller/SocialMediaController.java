package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.service.AccountService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    private final AccountService accountService;

    //Inject services
    @Autowired
    public SocialMediaController(AccountService accountService){
        this.accountService = accountService;
    }

    @GetMapping("/test")
    public String handleTestEndpoint(){
        return "Hello world!";
    }

    @PostMapping("/register")
    public ResponseEntity<Account> handleAccountRegistration(@RequestBody Account account){
        //call accountService to register account
        Account newAccount = this.accountService.registerAccount(account);

        return ResponseEntity.status(HttpStatus.OK).body(newAccount);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> handleAccountLogin(@RequestBody Account account){
        //call accountService to login with the provided account details
        Account authAccount = this.accountService.authenticateAccount(account);

        return ResponseEntity.status(HttpStatus.OK).body(authAccount);
    }
}
