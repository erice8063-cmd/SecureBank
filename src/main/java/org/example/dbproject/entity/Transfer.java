package org.example.dbproject.entity;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class Transfer {

    private Long senderId;

    private Long receiverId;

    private BigDecimal amount;
}