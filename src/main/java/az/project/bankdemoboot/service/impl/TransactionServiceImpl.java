package az.project.bankdemoboot.service.impl;

import az.project.bankdemoboot.dto.request.ReqTransaction;
import az.project.bankdemoboot.dto.response.*;
import az.project.bankdemoboot.entity.Account;
import az.project.bankdemoboot.entity.Transaction;
import az.project.bankdemoboot.enums.EnumAvailableStatus;
import az.project.bankdemoboot.exception.BankException;
import az.project.bankdemoboot.exception.ExceptionConstants;
import az.project.bankdemoboot.repository.AccountRepository;
import az.project.bankdemoboot.repository.TransactionRepository;
import az.project.bankdemoboot.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountLockedException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    @Override
    public Response<List<RespTransaction>> getTransactionList(Long accountId) {
        Response<List<RespTransaction>> response = new Response<>();
        try {
            if (accountId == null){
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data !");
            }
            Account account = accountRepository.getAccountByIdAndActive(accountId, EnumAvailableStatus.ACTIVE.value);
            if (account == null){
                throw new BankException(ExceptionConstants.ACCOUNT_NOT_FOUND, "Account not fount !");
            }
            List<Transaction> transactionList = transactionRepository.findAllByDtAccountAndActive(account, EnumAvailableStatus.ACTIVE.value);
            if (transactionList.isEmpty()){
                throw new BankException(ExceptionConstants.TRANSACTION_NOT_FOUND, "Transaction not found !");
            }
            List<RespTransaction> respTransactionsList = transactionList.stream().map(transaction -> mapping(transaction)).collect(Collectors.toList());

           /* for (Transaction transaction: transactionList){
                RespTransaction respTransaction = mapping(transaction);
                respTransaction.setTransactionId(transaction.getId());
                respTransaction.setDtAccount(transaction.getDtAccount());
                respTransaction.setCrAccount(transaction.getCrAccount());
                respTransaction.setAmount(transaction.getAmount());
                respTransaction.setCurrency(transaction.getCurrency());
                respTransaction.setCommission(transaction.getCommission());
                respTransactionsList.add(respTransaction);
            }*/
            response.setT(respTransactionsList);
            response.setStatus(RespStatus.getSuccessMessage());

        }catch (BankException ex){
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        }catch(Exception ex){
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal Exception !"));
            ex.printStackTrace();
        }
        return response;
    }

    @Override
    public Response createOperation(ReqTransaction reqTransaction) {
        return null;
    }

    public RespTransaction mapping(Transaction transaction){

        RespCustomer respCustomer = RespCustomer.builder().
                customerId(transaction.getDtAccount().getCustomer().getId()).
                name(transaction.getDtAccount().getCustomer().getName()).
                surname(transaction.getDtAccount().getCustomer().getSurname()).
                address(transaction.getDtAccount().getCustomer().getAddress()).
                phone(transaction.getDtAccount().getCustomer().getPhone()).
                dob(transaction.getDtAccount().getCustomer().getDob()).
                build();

        RespAccount dtAccount = RespAccount.builder().
                accountId(transaction.getDtAccount().getId()).
                name(transaction.getDtAccount().getName()).
                accountNo(transaction.getDtAccount().getAccountNo()).
                iban(transaction.getDtAccount().getIban()).
                currency(transaction.getDtAccount().getCurrency()).
                respCustomer(respCustomer).
                build();

        RespTransaction respTransaction = RespTransaction.builder().
                transactionId(transaction.getId()).
                dtAccount(dtAccount).
                crAccount(transaction.getCrAccount()).
                amount(transaction.getAmount()).
                currency(transaction.getCurrency()).
                commission(transaction.getCommission()).
                trDate(transaction.getTrDate()).
                build();
        return respTransaction;
    }
}
