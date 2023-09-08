package com.example.payrollsystem.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.payrollsystem.entity.Financialstatement;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Kevin
 * @since 2023-06-02
 */
public interface IFinancialstatementService extends IService<Financialstatement> {

    Page<Financialstatement> findPage(Page<Financialstatement> page, String statementNo, String statementDate, String auditor);

    void removeByStatementNo(String statementNo);

    void removeByStatementNos(List<String> statementNos);

}
