package service;

import lombok.extern.slf4j.Slf4j;
import model.Account;
import org.springframework.stereotype.Service;
import repository.AccountRepository;
import service.CustomExceptions.AccountNotFoundException;

import java.util.Optional;

//import javax.validation.ConstraintViolationException;

@Service
@Slf4j
public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Integer deactivateAccount(Integer id) {

        Optional<Account> deactivatedAccount = accountRepository.findById(id);

        Account account = deactivatedAccount.orElseThrow(AccountNotFoundException::new);
        if (!account.getActiveStatus()) throw new AccountNotFoundException();
        account.setActiveStatus(false);
        accountRepository.save(account);
        return account.getId();
    }


}

