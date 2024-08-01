package com.personalproject.banking.controller;

import com.personalproject.banking.dto.AccountDto;
import com.personalproject.banking.service.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/accounts")
public class AccountWebController {
  private final AccountService accountService;

  public AccountWebController(AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping
  public String getAllAccounts(Model model) {
    List<AccountDto> accounts = accountService.getAllAccounts();
    model.addAttribute("accounts", accounts);
    return "accounts"; // Nome do template Thymeleaf
  }

  @GetMapping("/{id}")
  public String getAccountById(@PathVariable Long id, Model model) {
    AccountDto accountDto = accountService.getAccountById(id);
    model.addAttribute("account", accountDto);
    return "account"; // Nome do template Thymeleaf
  }

  @GetMapping("/new")
  public String showCreateAccountForm(Model model) {
    model.addAttribute("account", new AccountDto());
    return "create-account"; // Nome do template Thymeleaf
  }

  @PostMapping("/new")
  public String createAccount(@ModelAttribute AccountDto accountDto) {
    accountService.createAccount(accountDto);
    return "redirect:/accounts";
  }

  @PostMapping("/{id}/deposit")
  public String deposit(@PathVariable Long id, @RequestParam Double amount) {
    accountService.deposit(id, amount);
    return "redirect:/accounts/" + id;
  }

  @PostMapping("/{id}/withdraw")
  public String withdraw(@PathVariable Long id, @RequestParam Double amount) {
    accountService.withDraw(id, amount);
    return "redirect:/accounts/" + id;
  }
}
