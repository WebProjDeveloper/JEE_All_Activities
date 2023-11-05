package org.emsi.bankaccountservice.service;

import org.emsi.bankaccountservice.dto.BankAccountRequestDTO;
import org.emsi.bankaccountservice.dto.BankAccountResponseDTO;

public interface AccountService  {
    BankAccountResponseDTO addAccount(BankAccountRequestDTO bankAccountDTO);

    BankAccountResponseDTO updateAccount(String id, BankAccountRequestDTO bankAccountDTO);
}
