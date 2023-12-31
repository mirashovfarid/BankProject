package az.project.bankdemoboot.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class RespCustomer {

    private Long customerId;
    private String name;
    private String surname;
    private String address;
    private String seria;
    private Date dob;
    private String phone;

}
