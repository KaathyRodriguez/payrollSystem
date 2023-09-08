<template>
    <div>
        <div style="margin: 10px 0">
            <el-input style="width: 200px" placeholder="请输入教职工编号" suffix-icon="el-icon-search" v-model="staffNo"></el-input>
            <el-input style="width: 200px" placeholder="请输入教职工姓名" suffix-icon="el-icon-message" class="ml-5" v-model="staffName"></el-input>
            <el-input style="width: 200px" placeholder="请输入教职工岗位" suffix-icon="el-icon-position" class="ml-5" v-model="staffPost"></el-input>
            <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
            <el-button type="warning" @click="reset">重置</el-button>
        </div>
        <div style="margin: 10px 0">
            <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
            <el-button type="danger" @click="delBatch">批量删除 <i class="el-icon-remove-outline"></i></el-button>
        </div>


        <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="staffNo" label="教职工编号" width="120"></el-table-column>
            <el-table-column prop="staffName" label="教职工姓名" width="150"></el-table-column>
            <el-table-column prop="staffPost" label="岗位" width="150"></el-table-column>
            <el-table-column prop="staffTitle" label="职称" ></el-table-column>
            <el-table-column label="操作"  width="200" align="center">
                <template #default="scope">
                    <el-button size="small" type="success" @click="handleEdit(scope.$index,scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
                    <el-button size="small" type="danger" @click="del(scope.$index, scope.row.staffNo)">删除<i class="el-icon-remove-outline"></i></el-button>
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
                <el-form-item label="教职工编号">
                    <el-input v-model="form.staffNo" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="教职工姓名">
                    <el-input v-model="form.staffName" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="岗位">
                    <el-input v-model="form.staffPost" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="职称">
                    <el-input v-model="form.staffTitle" autocomplete="off"></el-input>
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
    name: "Staff",
    data() {
        return {
            serverIp: serverIp,
            tableData: [],
            total: 0,
            pageNum: 1,
            pageSize: 10,
            staffNo: "",
            staffName: "",
            staffPost: "",
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
            this.request.get("/staff/page", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    staffNo: this.staffNo,
                    staffName: this.staffName,
                    staffPost: this.staffPost,
                }
            }).then(res => {
                this.tableData = res.data.records
                this.total = res.data.total

            })
        },
        save() {
            this.request.post("/staff", this.form).then(res => {
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
        del(index,staffNo) {
            this.request.delete("/staff/" + staffNo).then(res => {
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
            let staffNos = [];
            for(let j = 0;j < this.multipleSelection.length;j++)
            {
                var i = this.multipleSelection[j];
                staffNos.push(i.staffNo);
            }
            this.request.post("/staff/del/batch", staffNos).then(res => {
                if (res.code === '200') {
                    this.$message.success("批量删除成功")
                    this.load()
                } else {
                    this.$message.error("批量删除失败")
                }
            })
        },
        reset() {
            this.staffNo = ""
            this.staffName = ""
            this.staffPost = ""
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
        }
    }
}
</script>


<style>
.headerBg {
    background: #eee!important;
}
</style>
