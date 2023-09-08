<template>
    <div>
        <div style="margin: 10px 0">
            <el-input style="width: 200px" placeholder="请输入财务编号" suffix-icon="el-icon-search" v-model="statementNo"></el-input>
            <el-input style="width: 200px" placeholder="请输入财务日期" suffix-icon="el-icon-message" class="ml-5" v-model="statementDate"></el-input>
            <el-input style="width: 200px" placeholder="请输入审计人姓名" suffix-icon="el-icon-position" class="ml-5" v-model="auditor"></el-input>
            <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
            <el-button type="warning" @click="reset">重置</el-button>
        </div>

        <div style="margin: 10px 0">
            <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
            <el-button type="danger" @click="delBatch">批量删除 <i class="el-icon-remove-outline"></i></el-button>
            <el-button type="primary" @click="exp" class="ml-5">导出 <i class="el-icon-top"></i></el-button>
        </div>

        <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="statementNo" label="财务编号" width="80"></el-table-column>
            <el-table-column prop="statementDate" label="财务日期" width="120"></el-table-column>
            <el-table-column prop="monthIncome" label="月收入" width="80"></el-table-column>
            <el-table-column prop="monthExpend" label="月支出" width="80"></el-table-column>
            <el-table-column prop="monthProfit" label="月利润" width="80"></el-table-column>
            <el-table-column prop="auditor" label="审计人" width="100"></el-table-column>
            <el-table-column prop="note" label="备注" ></el-table-column>
            <el-table-column label="操作"  width="200" align="center">
                <template #default="scope">
                    <el-button size="small" type="success" @click="handleEdit(scope.$index,scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
                    <el-button size="small" type="danger" @click="del(scope.$index, scope.row.statementNo)">删除<i class="el-icon-remove-outline"></i></el-button>
                </template>
            </el-table-column>
        </el-table>
        <div style="padding: 10px 0">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="pageNum"
                    :page-sizes="[2, 5, 10, 20]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
        </div>

        <el-dialog title="财务信息" :visible.sync="dialogFormVisible" width="30%" >
            <el-form label-width="80px" size="small">
                <el-form-item label="财务编号">
                    <el-input v-model="form.statementNo" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="财务日期">
                    <el-input v-model="form.statementDate" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="月收入">
                    <el-input v-model="form.monthIncome" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="月支出">
                    <el-input v-model="form.monthExpend" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="月利润">
                    <el-input v-model="form.monthProfit" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="审计人">
                    <el-input v-model="form.auditor" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="备注">
                    <el-input v-model="form.note" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
import {serverIp} from "../../public/config";

export default {
    name: "FinancialStatement",
    data() {
        return {
            serverIp: serverIp,
            tableData: [],
            total: 0,
            pageNum: 1,
            pageSize: 10,
            statementNo: "",
            statementDate: "",
            auditor: "",
            form: {},
            dialogFormVisible: false,
            multipleSelection: []
        }
    },
    created() {
        this.load()
    },
    methods: {
        load() {
            this.request.get("/financialstatement/page", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    statementNo: this.statementNo,
                    statementDate: this.statementDate,
                    auditor: this.auditor,
                }
            }).then(res => {
                this.tableData = res.data.records
                this.total = res.data.total

            })
        },
        save() {
            this.request.post("/financialstatement", this.form).then(res => {
                if (res.code === '200') {
                    this.$message.success("保存成功")
                    this.dialogFormVisible = false
                    this.load()
                } else {
                    this.$message.error("保存失败")
                }
            })
        },
        handleAdd() {
            this.dialogFormVisible = true
            this.form = {}
        },
        handleEdit(index,row) {
            this.form = JSON.parse(JSON.stringify(row))
            this.dialogFormVisible = true
        },
        del(index,statementNo) {
            this.request.delete("/financialstatement/" + statementNo).then(res => {
                if (res.code === '200') {
                    this.$message.success("删除成功")
                    this.load()
                } else {
                    this.$message.error("删除失败")
                }
            })
        },
        handleSelectionChange(val) {
            console.log(val)
            this.multipleSelection = val
        },
        delBatch() {
            if(this.multipleSelection.length ==0 )
            {
                this.$message("你还没有勾选删除项")
                return;
            }
            let statementNos = [];
            for(let j = 0;j < this.multipleSelection.length;j++)
            {
                var i = this.multipleSelection[j];
                statementNos.push(i.statementNo);
            }
            this.request.post("/financialstatement/del/batch", statementNos).then(res => {
                if (res.code === '200') {
                    this.$message.success("批量删除成功")
                    this.load()
                } else {
                    this.$message.error("批量删除失败")
                }
            })
        },
        reset() {
            this.statementNo = ""
            this.statementDate = ""
            this.auditor = ""
            this.load()
        },
        handleSizeChange(pageSize) {
            console.log(pageSize)
            this.pageSize = pageSize
            this.load()
        },
        handleCurrentChange(pageNum) {
            console.log(pageNum)
            this.pageNum = pageNum
            this.load()
        },
        exp() {
            window.open(`http://${serverIp}:9090/financialstatement/export`)
        }
    }
}
</script>


<style>
.headerBg {
    background: #eee!important;
}
</style>
