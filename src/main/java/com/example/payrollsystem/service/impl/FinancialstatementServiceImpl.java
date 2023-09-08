package com.example.payrollsystem.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.payrollsystem.entity.Financialstatement;
import com.example.payrollsystem.mapper.FinancialstatementMapper;
import com.example.payrollsystem.service.IFinancialstatementService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Kevin
 * @since 2023-06-02
 */
@Service
public class FinancialstatementServiceImpl extends ServiceImpl<FinancialstatementMapper, Financialstatement> implements IFinancialstatementService {

    @Resource
    private FinancialstatementMapper financialstatementMapper;

    @Override
    public Page<Financialstatement> findPage(Page<Financialstatement> page, String statementNo, String statementDate, String auditor) {
        return financialstatementMapper.findPage(page, statementNo, statementDate, auditor);
    };
    @Override
    public void removeByStatementNo(String statementNo)
    {
        financialstatementMapper.removeByStatementNo(statementNo);
    };

    @Override
    public void removeByStatementNos(List<String> statementNos)
    {
        financialstatementMapper.removeByStatementNos(statementNos);
    };
}
