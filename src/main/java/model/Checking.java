package model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Date;

@Entity
@Getter
@RequiredArgsConstructor
@Setter
@ToString
public class Checking extends Account{

    @Column(nullable = false, unique = true)
    private Long checkingAccountID;

    @Column(nullable = false)
    private Float interestRate;

    @Column(nullable = false)
    private Date accountOpenedDate;

    @Column(nullable = false)
    private Integer accountTransactionLimit;

    @Column(nullable = false)
    private Float accountCurrentBalance;

}
