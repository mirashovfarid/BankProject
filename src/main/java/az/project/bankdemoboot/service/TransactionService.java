package az.project.bankdemoboot.service;

import az.project.bankdemoboot.dto.request.ReqTransaction;
import az.project.bankdemoboot.dto.response.RespTransaction;
import az.project.bankdemoboot.dto.response.Response;

import java.util.List;

public interface TransactionService {

    Response<List<RespTransaction>> getTransactionList(Long accountId);

    Response createOperation(ReqTransaction reqTransaction);
}
