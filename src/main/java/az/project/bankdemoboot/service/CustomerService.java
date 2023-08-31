package az.project.bankdemoboot.service;

import az.project.bankdemoboot.dto.request.ReqCustomer;
import az.project.bankdemoboot.dto.response.RespCustomer;
import az.project.bankdemoboot.dto.response.Response;

import java.util.List;

public interface CustomerService {

    Response<List<RespCustomer>> getCustomerList();

    Response<RespCustomer> getCustomerById(Long customerId);

    Response addCustomer(ReqCustomer reqCustomer);

    Response updateCustomer(ReqCustomer reqCustomer);

    Response deleteCustomer(Long customerId);
}
