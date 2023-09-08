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
@TableName("staff")
@ApiModel(value = "Staff对象", description = "")
public class Staff implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("staffNo")
    @TableId(value = "staffNo", type = IdType.INPUT)
    private String staffNo;

    private String staffName;

    private String staffPost;

    private String staffTitle;


}
