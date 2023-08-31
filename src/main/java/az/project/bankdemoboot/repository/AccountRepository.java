package az.project.bankdemoboot.repository;

import az.project.bankdemoboot.entity.Account;
import az.project.bankdemoboot.entity.Customer;
import az.project.bankdemoboot.enums.EnumAvailableStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllByCustomerAndActive(Customer customer, Integer active);
}
