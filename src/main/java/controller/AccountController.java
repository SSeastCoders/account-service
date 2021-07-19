package controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import service.AccountService;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PreAuthorize("principal == #id or hasAuthority('Admin')")
    @DeleteMapping(path = "/accounts/{id}")
    public ResponseEntity<String> deactivateAccount(@PathVariable Integer id) {
        accountService.deactivateAccount(id);
        return new ResponseEntity<>("Account deleted", HttpStatus.NO_CONTENT);
    }

}
