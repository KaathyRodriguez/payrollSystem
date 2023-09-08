<template>
    <el-menu :default-openeds="opens" style="min-height: 100%; overflow-x: hidden"
             background-color="rgb(48, 65, 86)"
             text-color="#fff"
             active-text-color="#ffd04b"
             :collapse-transition="false"
             :collapse="isCollapse"
             router
    >
        <div style="height: 60px; line-height: 60px; text-align: center">
            <img src="../assets/logo.png" alt="" style="width: 20px; position: relative; top: 5px;">
            <b style="color: white; margin-left: 5px" v-show="logoTextShow">工资管理系统</b>
        </div>
        <el-menu-item index="/home">
            <template>
                <i class="el-icon-house"></i>
                <span slot="title">主页</span>
            </template>
        </el-menu-item>

        <el-submenu index="/system">
            <template>
                <i class="el-icon-setting"></i>
                <span slot="title">系统管理</span>
            </template>
            <el-menu-item index="/user">
                <i class="el-icon-s-custom"></i>
                <span slot="title">用户管理</span>
            </el-menu-item>
        </el-submenu>

        <el-submenu index="/base">
            <template>
                <i class="el-icon-user-solid"></i>
                <span slot="title">基本信息</span>
            </template>
            <el-menu-item index="/staff">
                <i class="el-icon-s-custom"></i>
                <span slot="title">教职工信息</span>
            </el-menu-item>
        </el-submenu>


        <el-submenu index="/finance">
            <template>
                <i class="el-icon-s-finance"></i>
                <span slot="title">财务管理</span>
            </template>
            <el-menu-item index="/personnelData">
                <i class="el-icon-document"></i>
                <span slot="title">人事数据</span>
            </el-menu-item>
            <el-menu-item index="/salary">
                <i class="el-icon-coin"></i>
                <span slot="title">工资信息</span>
            </el-menu-item>
            <el-menu-item index="/financialStatement">
                <i class="el-icon-document"></i>
                <span slot="title">财务报表</span>
            </el-menu-item>
        </el-submenu>

    </el-menu>
</template>

<script>
export default {
    name: "Aside",
    props: {
        isCollapse: Boolean,
        logoTextShow: Boolean
    }
}
</script>

<style>
.el-menu-item.is-active {
    background-color: rgb(38, 52, 69) !important;
}
.el-menu-item:hover {
    background-color: rgb(38, 52, 69) !important;
}

.el-submenu__title:hover {
    background-color: rgb(38, 52, 69) !important;
}
/*解决收缩菜单文字不消失问题*/
.el-menu--collapse span {
    visibility: hidden;
}
</style>
