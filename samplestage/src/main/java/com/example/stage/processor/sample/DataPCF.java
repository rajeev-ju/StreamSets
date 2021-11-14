package com.example.stage.processor.sample;

import java.math.BigDecimal;

public class DataPCF {
    private BigDecimal salary;
    private String year;
    private String empid;

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    @Override
    public String toString() {
        return "DataPCF{" +
                "salary='" + salary + '\'' +
                ", year='" + year + '\'' +
                ", empid='" + empid + '\'' +
                '}';
    }
}
