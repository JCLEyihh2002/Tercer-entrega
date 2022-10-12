package com.wizeline.maven.learningjava.service;

import java.util.List;

import com.wizeline.maven.learningjava.model.BankAccountDTO;
import com.wizeline.maven.learningjava.model.BankAccountModel;

public interface BankAccountService {
	List<BankAccountModel> getAccounts(); // <- Regresa una lista de tipo BankAccountDTO
    BankAccountModel getAccountDetails(String user, String lastUsage);
    
    //BankAccountDTO getAccountDetails(String user, String lastUsage);

   // void deleteAccounts();

   // List<BankAccountDTO> getAccountByUser(String user);
}