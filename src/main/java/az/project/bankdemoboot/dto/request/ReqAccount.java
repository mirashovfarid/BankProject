package az.project.bankdemoboot.dto.request;

import lombok.Data;

@Data
public class ReqAccount {
    private String name;
    private String accountNo;
    private String currency;
    private String iban;
    private Integer branchCode;
    private Long customerId;

}
