package az.project.bankdemoboot.service;

import az.project.bankdemoboot.dto.request.ReqAccount;
import az.project.bankdemoboot.dto.response.RespAccount;
import az.project.bankdemoboot.dto.response.Response;

import java.util.List;

public interface AccountService {
    Response<List<RespAccount>> getAccountListByCustomerId(Long customerId);

    Response createAccount(ReqAccount reqAccount);

    Response deleteAccount(Long accountId);
}
