package com.example.payrollsystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.payrollsystem.entity.Financialstatement;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Kevin
 * @since 2023-06-02
 */
public interface FinancialstatementMapper extends BaseMapper<Financialstatement> {

    Page<Financialstatement> findPage(Page<Financialstatement> page, @Param("statementNo") String statementNo, @Param("statementDate") String statementDate, @Param("auditor") String auditor);
    void removeByStatementNo(@Param("statementNo") String statementNo);

    void removeByStatementNos(@Param("statementNos") List<String> statementNos);

}
