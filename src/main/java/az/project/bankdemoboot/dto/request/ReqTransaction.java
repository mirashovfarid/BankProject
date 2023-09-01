package az.project.bankdemoboot.dto.request;

import az.project.bankdemoboot.entity.Account;
import lombok.Data;

@Data
public class ReqTransaction {

    private Long dtAccountId;
    private String crAccount;
    private Double amount;
    private Double commission;
    private String currency;

}
