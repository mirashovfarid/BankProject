package az.project.bankdemoboot.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RespAccount {
    private Long accountId;
    private String name;
    private String accountNo;
    private String currency;
    private String iban;
    private Integer branchCode;
    private RespCustomer respCustomer;
}
