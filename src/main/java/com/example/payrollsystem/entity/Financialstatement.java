package com.example.payrollsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 
 * </p>
 *
 * @author Kevin
 * @since 2023-06-02
 */
@Getter
@Setter
@TableName("financialstatement")
@ApiModel(value = "Financialstatement对象", description = "")
public class Financialstatement implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("statementNo")
    @TableId(value = "statementNo", type = IdType.INPUT)
    private String statementNo;

    private LocalDate statementDate;

    private Float monthIncome;

    private Float monthExpend;

    private Float monthProfit;

    private String auditor;

    private String note;


}
