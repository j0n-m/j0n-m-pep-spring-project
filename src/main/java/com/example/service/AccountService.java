package com.example.service;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.exception.AuthenticationException;
import com.example.exception.DuplicateDataException;
import com.example.exception.InvalidRequestException;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Account registerAccount(Account account) throws DuplicateDataException,InvalidRequestException{
        //check validation on account username and passsword otherwise throws exception (Note: null values are handled in @RestControllerAdvice class)
        if(account.getUsername().isBlank() || account.getPassword().length() < 4){
            throw new InvalidRequestException();
        }

        //check if there is an already existing account
        boolean isDuplicateAccount = this.accountRepository.existsByUsername(account.getUsername());

        //throw exception because the account exists
        if(isDuplicateAccount){
            throw new DuplicateDataException("Account with username: '" + account.getUsername() + "' already exists.");
        }

        //Persist the account and return the newly created account
        Account newAccount = this.accountRepository.save(account);
        return newAccount;
    }

    public Account authenticateAccount(Account account) throws AuthenticationException{
        //checks if username and password credentials are correct and return a user from the db, otherwise it throws exception
        Account authAccount = this.accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword())
                                                    .orElseThrow(()->new AuthenticationException());
        return authAccount;
    }

    public Account getAccountByIdOrThrow(int userId) throws InvalidRequestException{
        //returns the user found by id otherwise throws exception
        return this.accountRepository.findById(userId).orElseThrow(()->new InvalidRequestException());
    }

}
