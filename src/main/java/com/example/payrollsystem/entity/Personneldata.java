package com.example.payrollsystem.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author Kevin
 * @since 2023-06-07
 */
@Getter
@Setter
@TableName("personnelData")
@ApiModel(value = "Personneldata对象", description = "")
public class Personneldata implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("personnelDataNo")
    @TableId(value = "personnelDataNo", type = IdType.INPUT)
    private String personnelDataNo;

    private String staffNo;

    private Float baseSalary;

    private Float livingAllowances;

    private Float bookFee;

    private Float commutingFee;

    private Float cleanFee;

    private Float workHours;

    private Float hourlyEarnings;

    private Float postAllowance;

    private Float individualIncomeTax;

    private Float housingFund;

    private Float premium;


}
