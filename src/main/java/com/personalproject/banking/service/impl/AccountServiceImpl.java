package com.personalproject.banking.service.impl;

import com.personalproject.banking.dto.AccountDto;
import com.personalproject.banking.entity.Account;
import com.personalproject.banking.mapper.AccountMapper;
import com.personalproject.banking.repositories.AccountRepository;
import com.personalproject.banking.service.AccountService;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

  private AccountRepository accountRepository;

  @Override
  public AccountDto createAccount(AccountDto accountDto) {

    Account account = AccountMapper.mapToAccount(accountDto);
    Account saveAccount = accountRepository.save(account);

    return AccountMapper.mapToAccountDto(saveAccount);
  }

  @Override
  public AccountDto getAccountById(Long id) {

   Account account =
           accountRepository.findById(id)
                   .orElseThrow(() -> new RuntimeException("Account does not exists"));

    return AccountMapper.mapToAccountDto(account);
  }

  @Override
  public AccountDto deposit(Long id, double amount) {

    Account account =
            accountRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Account does not exists"));

    double total = account.getBalance() + amount;
    account.setBalance(total);
   Account savedAccount = accountRepository.save(account);

    return AccountMapper.mapToAccountDto(savedAccount);
  }

  @Override
  public AccountDto withDraw(Long id, double amount) {
    Account account =
            accountRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Account does not exists"));
    if(account.getBalance() < amount) {
      throw new RuntimeException("Insufficient amount");
    }

    double total = account.getBalance() - amount;
    account.setBalance(total);
    Account savedAccount = accountRepository.save(account);

    return AccountMapper.mapToAccountDto(savedAccount);
  }

  @Override
  public List<AccountDto> getAllAccounts() {
    List<Account> accounts = accountRepository.findAll();
    return accounts.stream().map(AccountMapper::mapToAccountDto)
            .collect(Collectors.toList());
  }

  @Override
  public void deleteAccount(Long id) {

    Account account =
            accountRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Account does not exists"));
    accountRepository.deleteById(id);
  }

  public AccountServiceImpl(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }
}
