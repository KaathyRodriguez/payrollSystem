<template>
    <div>
        <div style="margin: 10px 0">
            <el-input style="width: 200px" placeholder="请输入人事数据编号" suffix-icon="el-icon-search" v-model="personnelDataNo"></el-input>
            <el-input style="width: 200px" placeholder="请输入教职工编号" suffix-icon="el-icon-message" class="ml-5" v-model="staffNo"></el-input>
            <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
            <el-button type="warning" @click="reset">重置</el-button>
        </div>
        <div style="margin: 10px 0">
            <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
            <el-button type="danger" @click="delBatch">批量删除 <i class="el-icon-remove-outline"></i></el-button>
            <el-upload :action="'http://' + serverIp + ':9090/personneldata/import'" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block">
                <el-button type="primary" class="ml-5">导入 <i class="el-icon-bottom"></i></el-button>
            </el-upload>
            <el-button type="primary" @click="exp" class="ml-5">导出 <i class="el-icon-top"></i></el-button>
        </div>


        <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="personnelDataNo" label="人事数据编号" width="80"></el-table-column>
            <el-table-column prop="staffNo" label="教职工编号" width="80"></el-table-column>
            <el-table-column prop="baseSalary" label="基本工资" width="100"></el-table-column>
            <el-table-column prop="livingAllowances" label="生活补贴" width="100"></el-table-column>
            <el-table-column prop="bookFee" label="书报费" width="100"></el-table-column>
            <el-table-column prop="commutingFee" label="交通费" width="100"></el-table-column>
            <el-table-column prop="cleanFee" label="洗理费" width="100"></el-table-column>
            <el-table-column prop="workHours" label="工作小时数" width="100"></el-table-column>
            <el-table-column prop="hourlyEarnings" label="计时工资" width="100"></el-table-column>
            <el-table-column prop="postAllowance" label="岗位津贴" width="100"></el-table-column>
            <el-table-column prop="individualIncomeTax" label="个人所得税" width="100"></el-table-column>
            <el-table-column prop="housingFund" label="住房公积金" width="100"></el-table-column>
            <el-table-column prop="premium" label="保险费" width="100"></el-table-column>
            <el-table-column label="操作"  width="200" align="center">
                <template #default="scope">
                    <el-button size="small" type="success" @click="handleEdit(scope.$index,scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
                    <el-button size="small" type="danger" @click="del(scope.$index, scope.row.empNo)">删除<i class="el-icon-remove-outline"></i></el-button>
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

        <el-dialog title="员工信息" :visible.sync="dialogFormVisible" width="30%" >
            <el-form label-width="80px" size="small">
                <el-form-item label="人事数据编号">
                    <el-input v-model="form.personnelDataNo" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="教职工编号">
                    <el-input v-model="form.staffNo" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="基本工资">
                    <el-input v-model="form.baseSalary" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="生活补贴">
                    <el-input v-model="form.livingAllowances" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="书报费">
                    <el-input v-model="form.bookFee" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="交通费">
                    <el-input v-model="form.commutingFee" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="洗理费">
                    <el-input v-model="form.cleanFee" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="工作小时数">
                    <el-input v-model="form.workHours" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="计时工资">
                    <el-input v-model="form.hourlyEarnings" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="岗位津贴">
                    <el-input v-model="form.postAllowance" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="个人所得税">
                    <el-input v-model="form.individualIncomeTax" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="住房公积金">
                    <el-input v-model="form.housingFund" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="保险">
                    <el-input v-model="form.premium" autocomplete="off"></el-input>
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
    name: "PersonnelData",
    data() {
        return {
            serverIp: serverIp,
            tableData: [],
            total: 0,
            pageNum: 1,
            pageSize: 10,
            personnelDataNo: "",
            staffNo: "",
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
            this.request.get("/personneldata/page", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    personnelDataNo: this.personnelDataNo,
                    staffNo: this.staffNo
                }
            }).then(res => {
                this.tableData = res.data.records
                this.total = res.data.total

            })
        },
        save() {
            this.request.post("/personneldata", this.form).then(res => {
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
        del(index,personnelDataNo) {
            this.request.delete("/personneldata/" + personnelDataNo).then(res => {
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
            let personnelDataNos = [];
            for(let j = 0;j < this.multipleSelection.length;j++)
            {
                var i = this.multipleSelection[j];
                personnelDataNos.push(i.personnelDataNo);
            }
            this.request.post("/personneldata/del/batch", personnelDataNos).then(res => {
                if (res.code === '200') {
                    this.$message.success("批量删除成功")
                    this.load()
                } else {
                    this.$message.error("批量删除失败")
                }
            })
        },
        reset() {
            this.personnelDataNo = ""
            this.staffNo = ""
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
            window.open(`http://${serverIp}:9090/personneldata/export`)
        },
        handleExcelImportSuccess() {
            this.$message.success("导入成功")
            this.load()
        }
    }
}
</script>


<style>
.headerBg {
    background: #eee!important;
}
</style>
