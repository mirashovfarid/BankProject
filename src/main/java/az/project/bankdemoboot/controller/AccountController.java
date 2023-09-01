package az.project.bankdemoboot.controller;

import az.project.bankdemoboot.dto.request.ReqAccount;
import az.project.bankdemoboot.dto.response.RespAccount;
import az.project.bankdemoboot.dto.response.Response;
import az.project.bankdemoboot.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountservice;

    @GetMapping("/GetAccountListByCustomerId/{customerId}")
    public Response<List<RespAccount>> getAccountListByCustomerId(@PathVariable Long customerId){
        return accountservice.getAccountListByCustomerId(customerId);
    }

    @PostMapping("/CreateAccount")
    public Response createAccount(@RequestBody ReqAccount reqAccount){
        return accountservice.createAccount(reqAccount);
    }

    @PutMapping("/DeleteAccount/{accountId}")
    public Response deleteAccount(@PathVariable Long accountId){
        return accountservice.deleteAccount(accountId);
    }
}
