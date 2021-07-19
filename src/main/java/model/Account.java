package model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

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
    private Integer accountID;

    @Column(nullable = false, length = 10)
    private String accountType;

    // CANNOT REFERENCE OBJECT SELF, NEED TO COMMUNICATE OTHER WAY TO KEEP SEPARATE
    @Column(nullable = false)
    private Integer userID;
}
