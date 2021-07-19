package model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

@MappedSuperclass
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Account {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Id
    protected Integer id;

    @Column(nullable = false, length = 10)
    protected String accountType;

    // CANNOT REFERENCE OBJECT SELF, NEED TO COMMUNICATE OTHER WAY TO KEEP SEPARATE
    @Column(nullable = false)
    protected Integer userID;

    @Column(nullable = false)
    protected Double interestRate;

    @Column(nullable = false)
    protected Date openDate;

    @Column(nullable = false)
    protected Float currentBalance;

    @Column(nullable = false)
    protected Boolean activeStatus;
}
