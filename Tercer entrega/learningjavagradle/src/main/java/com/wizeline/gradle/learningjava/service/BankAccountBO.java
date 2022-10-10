package com.wizeline.gradle.learningjava.service;

import java.util.List;

import com.wizeline.gradle.learningjava.model.BankAccountDTO;


public interface BankAccountBO {
    List<BankAccountDTO> getAccounts();
    BankAccountDTO getAccountDetails(String user, String lastUsage);
}
