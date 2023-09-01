package az.project.bankdemoboot.controller;

import az.project.bankdemoboot.dto.request.ReqTransaction;
import az.project.bankdemoboot.dto.response.RespTransaction;
import az.project.bankdemoboot.dto.response.Response;
import az.project.bankdemoboot.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/getTransactionList/{accountId}")
    public Response<List<RespTransaction>> getTransactionList(@PathVariable Long accountId){
        return transactionService.getTransactionList(accountId);
    }

    @PostMapping("/createOperation")
    public Response createOperation(@RequestBody ReqTransaction reqTransaction){
        return transactionService.createOperation(reqTransaction);
    }
}
