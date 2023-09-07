package az.project.bankdemoboot.service.impl;

import az.project.bankdemoboot.dto.request.ReqCustomer;
import az.project.bankdemoboot.dto.request.ReqToken;
import az.project.bankdemoboot.dto.response.RespCustomer;
import az.project.bankdemoboot.dto.response.RespStatus;
import az.project.bankdemoboot.dto.response.Response;
import az.project.bankdemoboot.entity.Customer;
import az.project.bankdemoboot.entity.User;
import az.project.bankdemoboot.entity.UserToken;
import az.project.bankdemoboot.enums.EnumAvailableStatus;
import az.project.bankdemoboot.exception.BankException;
import az.project.bankdemoboot.exception.ExceptionConstants;
import az.project.bankdemoboot.repository.CustomerRepository;
import az.project.bankdemoboot.repository.UserRepository;
import az.project.bankdemoboot.repository.UserTokenRepository;
import az.project.bankdemoboot.service.CustomerService;
import az.project.bankdemoboot.util.Utility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final Utility utility;

    @Override
    public Response<List<RespCustomer>> getCustomerList(ReqToken reqToken) {
        Response<List<RespCustomer>> response = new Response<>();
        try{
            utility.checkToken(reqToken);
            List<Customer> customerList = customerRepository.findAllByActive(EnumAvailableStatus.ACTIVE.value);
            if (customerList.isEmpty()){
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND,"Customer not found!");
            }
            List<RespCustomer> respCustomerList = customerList.stream().map(this::mapping).collect(Collectors.toList());

            response.setT(respCustomerList);
            response.setStatus(RespStatus.getSuccessMessage());
        }catch (BankException ex){
            response.setStatus(new RespStatus(ex.getCode(),ex.getMessage()));
            ex.printStackTrace();
        }catch (Exception ex){
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION,"Internal exception"));
            ex.printStackTrace();
        }
        return response;
    }

    @Override
    public Response<RespCustomer> getCustomerById(ReqCustomer reqCustomer) {
        Response<RespCustomer> response = new Response<>();
        Long customerId = reqCustomer.getCustomerId();
        utility.checkToken(reqCustomer.getReqToken());
        try{
            if (customerId == null){
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA,"Invalid request data!");
            }
            Customer customer = customerRepository.getCustomerByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.value);
            if (customer == null){
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found!");
            }
            RespCustomer respCustomer = mapping(customer);
            response.setT(respCustomer);
            response.setStatus(RespStatus.getSuccessMessage());

        }catch (BankException ex){
            response.setStatus(new RespStatus(ex.getCode(),ex.getMessage()));
            ex.printStackTrace();
        }catch (Exception ex){
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION,"Internal exception"));
            ex.printStackTrace();
        }
        return response;
    }

    @Override
    public Response addCustomer(ReqCustomer reqCustomer) {
        Response response = new Response();
        utility.checkToken(reqCustomer.getReqToken());
        try {
            String name = reqCustomer.getName();
            String surname = reqCustomer.getSurname();
            if (name == null || surname == null){
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data !");
            }
            Customer customer = Customer.builder().
                    name(name).
                    surname(surname).
                    address(reqCustomer.getAddress()).
                    phone(reqCustomer.getPhone()).
                    cif(reqCustomer.getCif()).
                    seria(reqCustomer.getSeria()).
                    dob(reqCustomer.getDob()).
                    pin(reqCustomer.getPin()).
                    build();
            customerRepository.save(customer);
            response.setStatus(RespStatus.getSuccessMessage());
        }catch (BankException ex){
            response.setStatus(new RespStatus(ex.getCode(),ex.getMessage()));
            ex.printStackTrace();
        }catch (Exception ex){
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION,"Internal exception"));
            ex.printStackTrace();
        }
        return response;
    }

    @Override
    public Response updateCustomer(ReqCustomer reqCustomer) {
        Response response = new Response();
        utility.checkToken(reqCustomer.getReqToken());
        try {
            String name = reqCustomer.getName();
            String surname = reqCustomer.getSurname();
            Long customerId = reqCustomer.getCustomerId();
            if (customerId == null || name == null || surname == null){
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data !");
            }
            Customer customer = customerRepository.getCustomerByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.value);
            if (customer == null){
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found !");
            }
            customer.setName(name);
            customer.setSurname(surname);
            customer.setAddress(reqCustomer.getAddress());
            customer.setDob(reqCustomer.getDob());
            customer.setCif(reqCustomer.getCif());
            customer.setSeria(reqCustomer.getSeria());
            customer.setPhone(reqCustomer.getPhone());
            customer.setPin(reqCustomer.getPin());
            customerRepository.save(customer);
            response.setStatus(RespStatus.getSuccessMessage());
        }catch (BankException ex){
            response.setStatus(new RespStatus(ex.getCode(),ex.getMessage()));
            ex.printStackTrace();
        }catch (Exception ex){
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION,"Internal exception"));
            ex.printStackTrace();
        }
        return response;
    }

    @Override
    public Response deleteCustomer(ReqCustomer reqCustomer) {
        Response response = new Response();
        Long customerId = reqCustomer.getCustomerId();
        utility.checkToken(reqCustomer.getReqToken());
        try {
            if (customerId == null){
                throw new BankException(ExceptionConstants.INVALID_REQUEST_DATA, "Invalid request data !");
            }
            Customer customer = customerRepository.getCustomerByIdAndActive(customerId, EnumAvailableStatus.ACTIVE.value);
            if (customer == null){
                throw new BankException(ExceptionConstants.CUSTOMER_NOT_FOUND, "Customer not found !");
            }
            customer.setActive(EnumAvailableStatus.DEACTIVE.value);
            customerRepository.save(customer);
            response.setStatus(RespStatus.getSuccessMessage());
        }catch(BankException ex){
            response.setStatus(new RespStatus(ex.getCode(), ex.getMessage()));
            ex.printStackTrace();
        }catch (Exception ex){
            response.setStatus(new RespStatus(ExceptionConstants.INTERNAL_EXCEPTION, "Internal exception"));
        }
        return response;
    }

    public RespCustomer mapping(Customer customer){
        return RespCustomer.builder().
                customerId(customer.getId()).
                name(customer.getName()).
                surname(customer.getSurname()).
                phone(customer.getPhone()).
                dob(customer.getDob()).
                seria(customer.getSeria()).
                address(customer.getAddress()).
                build();
    }
}
