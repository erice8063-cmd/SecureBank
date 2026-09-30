package org.example.dbproject.entity;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "branch")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branchId;

    private String branchName;
    private String address;
    private String ifscCode;

}
