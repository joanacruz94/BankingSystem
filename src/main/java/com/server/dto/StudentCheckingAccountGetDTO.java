package com.server.dto;

import com.server.enums.AccountType;
import com.server.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentCheckingAccountGetDTO extends AccountGetDTO {
    private final AccountType type = AccountType.STUDENT_CHECKING;
    private String secretKey;

    public StudentCheckingAccountGetDTO(Long id, BigDecimal balance, String currency, BigDecimal penaltyFee, Status status, List<String> ownersNames, String secretKey) {
        super(id, balance, currency, penaltyFee, status, ownersNames);
        this.secretKey = secretKey;
    }
}

