package model;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@ToString
@Builder
public class Employee {

  private Integer id;
  private String name;
  private BigDecimal salary;
  private Date hireDate;
  private String department;

}
