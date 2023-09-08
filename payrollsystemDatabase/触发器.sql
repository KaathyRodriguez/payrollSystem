DELIMITER //

CREATE TRIGGER insert_personnelData_trigger
AFTER INSERT ON personnelData
FOR EACH ROW
BEGIN
    -- 随机生成salaryNo
    SET @salaryNo = LPAD(FLOOR(RAND() * 100000000), 8, '0');
    
    -- 获取staffNo，staffName，staffPost和staffTitle
    SELECT staffNo, staffName, staffPost, staffTitle INTO @staffNo, @staffName, @staffPost, @staffTitle
    FROM staff
    WHERE staffNo = NEW.staffNo;
    
    -- 计算income和expend
    SET @income = NEW.livingAllowances + NEW.bookFee + NEW.commutingFee + NEW.cleanFee + (NEW.workHours * NEW.hourlyEarnings) + NEW.postAllowance;
    SET @expend = NEW.individualIncomeTax + NEW.housingFund + NEW.premium;
    
    -- 计算netPayroll
    SET @netPayroll = NEW.baseSalary + @income - @expend;
    
    -- 插入新数据到Salary表
    INSERT INTO Salary (salaryNo, staffNo, staffName, staffPost, staffTitle, baseSalary, income, expend, netPayroll)
    VALUES (@salaryNo, @staffNo, @staffName, @staffPost, @staffTitle, NEW.baseSalary, @income, @expend, @netPayroll);
END//

DELIMITER ;

DROP TRIGGER insert_personnelData_trigger;


DELIMITER //

CREATE TRIGGER update_personnelData_trigger
AFTER UPDATE ON personnelData
FOR EACH ROW
BEGIN
    -- 检查相关字段是否有更新
    IF NEW.baseSalary <> OLD.baseSalary OR
       NEW.livingAllowances <> OLD.livingAllowances OR
       NEW.bookFee <> OLD.bookFee OR
       NEW.commutingFee <> OLD.commutingFee OR
       NEW.cleanFee <> OLD.cleanFee OR
       NEW.workHours <> OLD.workHours OR
       NEW.hourlyEarnings <> OLD.hourlyEarnings OR
       NEW.postAllowance <> OLD.postAllowance OR
       NEW.individualIncomeTax <> OLD.individualIncomeTax OR
       NEW.housingFund <> OLD.housingFund OR
       NEW.premium <> OLD.premium THEN
        
        -- 计算income和expend
        SET @income = NEW.livingAllowances + NEW.bookFee + NEW.commutingFee + NEW.cleanFee + (NEW.workHours * NEW.hourlyEarnings) + NEW.postAllowance;
        SET @expend = NEW.individualIncomeTax + NEW.housingFund + NEW.premium;
        
        -- 检查是否存在相同staffNo的数据
        IF EXISTS (SELECT 1 FROM Salary WHERE staffNo = NEW.staffNo) THEN
            -- 更新现有数据
            UPDATE Salary
            SET baseSalary = NEW.baseSalary,
                income = @income,
                expend = @expend,
                netPayroll = NEW.baseSalary + @income - @expend
            WHERE staffNo = NEW.staffNo;
        ELSE
            -- 插入新数据
            -- 随机生成salaryNo
            SET @salaryNo = LPAD(FLOOR(RAND() * 100000000), 8, '0');
            
            -- 获取staffName，staffPost和staffTitle
            SELECT staffName, staffPost, staffTitle INTO @staffName, @staffPost, @staffTitle
            FROM staff
            WHERE staffNo = NEW.staffNo;
            
            -- 插入新数据到Salary表
            INSERT INTO Salary (salaryNo, staffNo, staffName, staffPost, staffTitle, baseSalary, income, expend, netPayroll)
            VALUES (@salaryNo, NEW.staffNo, @staffName, @staffPost, @staffTitle, NEW.baseSalary, @income, @expend, NEW.baseSalary + @income - @expend);
        END IF;
    END IF;
END//

DELIMITER ;


DROP TRIGGER update_personnelData_trigger;

DELIMITER //

CREATE TRIGGER update_salary_trigger
AFTER UPDATE ON salary
FOR EACH ROW
BEGIN
    
		-- 检查相关字段是否有更新
    IF NEW.netPayroll <> OLD.netPayroll THEN -- 检查 billStatus 列是否被更新
        SET @netPayroll = NEW.netPayroll;
        SET @YM = DATE_FORMAT(NOW(), '%Y%m');
        SET @STATEMENT_NO = CONCAT(@YM, '00');
        
            -- 检查当前年份和月份的财务报表是否存在
            IF NOT EXISTS (
                SELECT 1
                FROM FinancialStatement
                WHERE statementNo COLLATE utf8mb4_unicode_ci= @STATEMENT_NO
            ) THEN
                -- 如果不存在，则插入新的财务报表记录
                INSERT INTO FinancialStatement (statementNo, statementDate, monthIncome, monthExpend, monthProfit, auditor, note)
                VALUES (@STATEMENT_NO, NOW(), 0, OLD.netPayroll, 0-OLD.netPayroll, 'Auditor', '无');
            END IF;
            
            -- 更新月收入和月利润
            UPDATE FinancialStatement
            SET monthExpend = monthExpend + @netPayroll - OLD.netPayroll,
                monthProfit = monthIncome - monthExpend
            WHERE statementNo COLLATE utf8mb4_unicode_ci = @STATEMENT_NO;
    END IF;
END //

DELIMITER ;


DROP TRIGGER update_salary_trigger;


DELIMITER //

CREATE TRIGGER insert_salary_trigger
AFTER INSERT ON salary
FOR EACH ROW
BEGIN
    

            SET @netPayroll = NEW.netPayroll;
            SET @YM = DATE_FORMAT(NOW(), '%Y%m');
            SET @STATEMENT_NO = CONCAT(@YM, '00');
        
            -- 检查当前年份和月份的财务报表是否存在
            IF NOT EXISTS (
                SELECT 1
                FROM FinancialStatement
                WHERE statementNo COLLATE utf8mb4_unicode_ci= @STATEMENT_NO
            ) THEN
                -- 如果不存在，则插入新的财务报表记录
                INSERT INTO FinancialStatement (statementNo, statementDate, monthIncome, monthExpend, monthProfit, auditor, note)
                VALUES (@STATEMENT_NO, NOW(), 0, NEW.netPayroll, 0-NEW.netPayroll, 'Auditor', '无');
            ELSE
            -- 更新月收入和月利润
            UPDATE FinancialStatement
            SET monthExpend = monthExpend + @netPayroll,
                monthProfit = monthIncome - monthExpend
            WHERE statementNo COLLATE utf8mb4_unicode_ci = @STATEMENT_NO;
            END IF;
END //

DELIMITER ;

DROP TRIGGER insert_salary_trigger;
