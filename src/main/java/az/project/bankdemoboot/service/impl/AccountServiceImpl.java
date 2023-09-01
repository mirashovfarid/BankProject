package az.project.bankdemoboot.service.impl;

import az.project.bankdemoboot.dto.request.ReqAccount;
import az.project.bankdemoboot.dto.response.RespAccount;
import az.project.bankdemoboot.dto.response.RespStatus;
import az.project.bankdemoboot.dto.response.Response;
import az.project.bankdemoboot.entity.Account;
import az.project.bankdemoboot.entity.Customer;
import az.project.bankdemoboot.enums.EnumAvailableStatus;
import az.project.bankdemoboot.exception.BankException;
import az.project.bankdemoboot.exception.ExceptionConstants;
import az.project.bankdemoboot.repository.AccountRepository;
import az.project.bankdemoboot.repository.CustomerRepository;
import az.project.bankdemoboot.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;


    @Override
    public Response<List<RespAccount>> getAccountListByCustomerId(Long customerId) {
        Response<List<RespAccount>> response = new Response<>();
        try{
            if (customerId == null){
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data !");
            }
            Customer customer = customerRepository.getCustomerByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.value);
            if (customer == null){
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found !");
            }
            List<Account> accountList = accountRepository.findAllByCustomerAndActive(customer, EnumAvailableStatus.ACTIVE.value);
            if (accountList.isEmpty()){
                throw new BankException(ExceptionConstants.ACCOUNT_NOT_FOUND, "Account not found !");
            }
            List<RespAccount> respAccountList = accountList.stream().map(account -> mapping(account)).collect(Collectors.toList());

            response.setT(respAccountList);
            response.setStatus(RespStatus.getSuccessMessage());
        }catch (BankException ex){
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        }catch (Exception ex){
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception!"));
            ex.printStackTrace();
        }
        return response;
    }

    @Override
    public Response createAccount(ReqAccount reqAccount) {
        Response response = new Response();
        try {
            String name = reqAccount.getName();
            String accountNo = reqAccount.getAccountNo();
            String currency = reqAccount.getCurrency();
            String iban = reqAccount.getIban();
            Integer branchCode = reqAccount.getBranchCode();
            Long customerId = reqAccount.getCustomerId();
            if (name == null || accountNo == null || currency == null || iban == null || branchCode == null){
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data !");
            }
            Customer customer = customerRepository.getCustomerByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.value);
            if (customer == null){
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found !");
            }
            Account account = Account.builder()
                    .accountNo(accountNo)
                    .name(name)
                    .currency(currency)
                    .iban(iban)
                    .customer(customer)
                    .branchCode(branchCode)
                    .build();
            accountRepository.save(account);
            response.setStatus(RespStatus.getSuccessMessage());
        }catch (BankException ex){
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        }catch (Exception ex){
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception ! "));
            ex.printStackTrace();
        }
        return response;
    }

    @Override
    public Response deleteAccount(Long accountId) {
        Response response = new Response();
       try {
            if (accountId == null){
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data !");
            }
            Account account = accountRepository.getAccountByIdAndActive(accountId, EnumAvailableStatus.ACTIVE.value);
            if (account == null){
                throw new BankException(ExceptionConstants.ACCOUNT_NOT_FOUND, "Account not found !");
            }
            account.setActive(EnumAvailableStatus.DEACTIVE.value);
            accountRepository.save(account);
            response.setStatus(RespStatus.getSuccessMessage());
        }catch (BankException ex){
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        }catch (Exception ex){
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception !"));
            ex.printStackTrace();
        }
        return response;
    }

    public RespAccount mapping(Account account){
        RespAccount respAccount = RespAccount.builder().
                accountNo(account.getAccountNo()).
                name(account.getName()).
                accountId(account.getId()).
                iban(account.getIban()).
                currency(account.getCurrency()).
                branchCode(account.getBranchCode())
                .build();

        return respAccount;
    }
}
