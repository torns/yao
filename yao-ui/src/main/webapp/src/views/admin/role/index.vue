<template>
    <div class="app-container">
        <div class="head-container">
            <!-- 搜索 -->
            <el-input v-model="queryName"
                      clearable placeholder="输入名称或角色编码搜索"
                      style="width: 200px;"
                      class="filter-item"
                      @keyup.enter.native="search"/>
            <el-button
                    class="filter-item"
                    size="mini"
                    type="success"
                    icon="el-icon-search"
                    style="margin-left: 10px"
                    @click="search">搜索
            </el-button>
            <!-- 新增 -->
            <div style="display: inline-block;margin: 0px 2px;">
                <el-button
                        class="filter-item"
                        size="mini"
                        type="primary"
                        icon="el-icon-plus"
                        @click="handleAdd">新增
                </el-button>
            </div>
        </div>

        <el-row :gutter="15">
            <el-col :xs="24" :sm="24" :md="16" :lg="16" :xl="17">
                <el-card shadow="never">
                    <div slot="header" class="clearfix">
                        <span class="role-span">角色列表</span>
                        <div id="opt" style="float: right">
                            <el-radio-group v-model="opt" size="mini">
                                <el-radio-button label="菜单权限"/>
                                <el-radio-button label="数据权限"/>
                            </el-radio-group>
                        </div>
                    </div>

                    <el-table v-loading="loading" :data="pageInfo.records" highlight-current-row size="small"
                              style="width: 100%;"
                              @current-change="handleCurrentChange">
                        <el-table-column prop="name" label="角色名称"/>
                        <el-table-column prop="roleCode" label="角色编码"/>
                        <el-table-column :show-overflow-tooltip="true" prop="description" label="描述"/>
                        <el-table-column :show-overflow-tooltip="true" prop="createTime" label="创建日期">
                            <template slot-scope="scope">
                                <span>{{ parseTime(scope.row.createTime) }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column :show-overflow-tooltip="true" prop="updateTime" label="更新日期">
                            <template slot-scope="scope">
                                <span>{{ parseTime(scope.row.updateTime) }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作" width="130px" align="center">
                            <template slot-scope="scope">

                                <el-button @click="handleEdit(scope.row)" slot="reference" type="primary"
                                           icon="el-icon-edit"
                                           size="mini"/>

                                <el-popover
                                        :ref="scope.row.id"
                                        placement="top"
                                        width="180">
                                    <p>确定删除本条数据吗？</p>
                                    <div style="text-align: right; margin: 0">
                                        <el-button size="mini" type="text" @click="$refs[scope.row.id].doClose()">取消
                                        </el-button>
                                        <el-button :loading="delLoading" type="primary" size="mini"
                                                   @click="subDelete(scope.row.id)">确定
                                        </el-button>
                                    </div>
                                    <el-button slot="reference" type="danger" icon="el-icon-delete" size="mini"/>
                                </el-popover>
                            </template>
                        </el-table-column>
                    </el-table>
                    <!--分页组件-->
                    <el-pagination
                            :total="pageInfo.total"
                            style="margin-top: 8px;"
                            layout="total, prev, pager, next, sizes"
                            @size-change="sizeChange"
                            @current-change="pageChange"/>
                </el-card>
            </el-col>
            <!-- 授权 -->
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="7">
                <el-card v-show="opt === '菜单权限'" class="box-card" shadow="never">
                    <div slot="header" class="clearfix">
                        <el-tooltip class="item" effect="dark" content="选择指定角色分配菜单" placement="top">
                            <span class="role-span">菜单权限</span>
                        </el-tooltip>
                        <el-button
                                :disabled="!showButton"
                                :loading="resourceLoading"
                                icon="el-icon-check"
                                size="mini"
                                style="float: right; padding: 6px 9px"
                                type="primary"
                                @click="saveResource">保存
                        </el-button>
                    </div>
                    <el-tree
                            ref="resource"
                            :data="resources"
                            :default-checked-keys="resourceIds"
                            :props="defaultProps"
                            accordion
                            show-checkbox
                            node-key="id"/>
                </el-card>
                <el-card v-show="opt === '数据权限'" class="box-card" shadow="never">
                    <div slot="header" class="clearfix">
                        <el-tooltip class="item" effect="dark" content="选择指定角色分配数据权限" placement="top">
                            <span class="role-span">数据权限</span>
                        </el-tooltip>
                        <el-button
                                :disabled="!showButton"
                                :loading="departmentLoading"
                                icon="el-icon-check"
                                size="mini"
                                style="float: right; padding: 6px 9px"
                                type="primary"
                                @click="saveDepartment">保存
                        </el-button>
                    </div>
                    <el-tree
                            ref="department"
                            :data="departments"
                            :default-checked-keys="departmentIds"
                            :props="defaultProps"
                            show-checkbox
                            accordion
                            node-key="id"/>
                </el-card>
            </el-col>
        </el-row>

        <RoleForm
                ref="roleForm"
                :is-add="isAdd"/>
    </div>
</template>

<script>
    import {page, delRoleByIds, editRoleResource, editRoleDepartment} from '@/api/role'
    import {getMenuTree} from "@/api/menu";
    import {getDepartmentTree} from '@/api/department'
    import {parseTime} from '@/utils/index'

    import RoleForm from './form'

    export default {
        name: "role",
        components: {RoleForm},
        data() {
            return {
                defaultProps: {
                    label: 'name',
                    children: 'children',
                },
                isAdd: true,
                queryName: '',
                loading: false,
                delLoading: false,
                resourceLoading: false,
                departmentLoading: false,
                showButton: false,
                editForm: {},
                currentId: '',
                resourceIds: [],
                resources: [],
                departmentIds: [],
                departments: [],
                pageInfo: {
                    records: [],
                    total: 0,
                    current: 1,
                    size: 10,
                    sort: [],
                    params: {}
                },
                opt: "菜单权限"
            }
        },
        mounted() {
            this.init();
        },
        methods: {
            parseTime,
            //获取全部角色列表
            init() {
                this.getRoleList();
                this.getMenuTree();
                this.getDepartmentTree();
            },
            search() {
                this.pageInfo.current = 1;
                this.pageInfo.params = {name: this.queryName}
                this.getRoleList();
            },
            getRoleList() {
                const _this = this;
                _this.loading = true;
                page(this.pageInfo).then(res => {
                    _this.pageInfo = res.data;
                    _this.loading = false;
                }).catch(err => {
                    _this.loading = false;
                })
            },
            //获取菜单树
            getMenuTree() {
                const _this = this;
                getMenuTree().then(res => {
                    _this.resources = res.data;
                })
            },
            //获取部门树
            getDepartmentTree() {
                const _this = this;
                getDepartmentTree().then(res => {
                    _this.departments = res.data;
                })
            },
            //选择表格中的某行时调用
            handleCurrentChange(val) {
                if (val) {
                    const _this = this;
                    // 清空权限与菜单的选中
                    _this.$refs.department.setCheckedKeys([])
                    _this.$refs.resource.setCheckedKeys([])
                    // 保存当前的角色id
                    _this.currentId = val.id
                    // 点击后显示按钮
                    _this.showButton = true
                    // 初始化
                    _this.resourceIds = []
                    _this.departmentIds = []
                    // 菜单权限需要特殊处理
                    if (val.resources !== null && val.resources.length > 0) {
                        val.resources.forEach(function (data, index) {
                            let add = true
                            for (let i = 0; i < val.resources.length; i++) {
                                if (data.id === val.resources[i].parentId) {
                                    add = false
                                    break
                                }
                            }
                            if (add) {
                                _this.resourceIds.push(data.id)
                            }
                        })
                    }
                    // 数据权限数据
                    if (val.departments !== null && val.departments.length > 0) {
                        val.departments.forEach(function (data, index) {
                            _this.departmentIds.push(data.id)
                        })
                    }
                }
            },
            handleAdd() {
                this.$refs.roleForm.dialog = true;
                this.isAdd = true;
            },
            handleEdit(row) {
                this.$refs.roleForm.dialog = true;
                this.$refs.roleForm.form = this.copyObj(row);
                this.isAdd = false;
            },
            subDelete(id) {
                this.delLoading = true
                delRoleByIds(id).then(res => {
                    this.delLoading = false
                    this.$refs[id].doClose()
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    })
                    this.getRoleList();
                }).catch(err => {
                    this.delLoading = false
                    this.$refs[id].doClose()
                })
            },
            sizeChange(e) {
                this.pageInfo.size = e;
                this.pageInfo.current = 1;
                this.getRoleList();
            },
            pageChange(e) {
                this.pageInfo.current = e - 1;
                this.getRoleList();
            },
            saveResource() {
                const _this = this;
                this.resourceLoading = true;
                let resourceIds = []
                this.$refs.resource.getCheckedKeys().forEach(function (data, index) {
                    resourceIds.push(data);
                });
                editRoleResource(_this.currentId, resourceIds).then(res => {
                    _this.$message({
                        type: 'success',
                        message: '菜单权限保存成功！'
                    })
                    this.resourceLoading = false;
                    _this.resourceIds = []
                    _this.init();
                }).catch(err => {
                    this.resourceLoading = false;
                })
            },
            saveDepartment() {
                const _this = this;
                _this.departmentLoading = true;
                let departmentIds = []
                _this.$refs.department.getCheckedKeys().forEach(function (data, index) {
                    departmentIds.push(data)
                })
                let dataType = 0;//全部默认数据权限
                if (departmentIds.length < 1) {
                    dataType = 1;//自定义数据权限
                }
                editRoleDepartment(_this.currentId, dataType, departmentIds).then(res => {
                    _this.$message({
                        type: 'success',
                        message: '数据权限保存成功！'
                    })
                    _this.departmentLoading = false;
                    _this.departmentIds = []
                    _this.init();
                }).catch(err => {
                    _this.departmentLoading = false;
                });
            }
        }
    };
</script>

<style scoped>
    .role-span {
        font-weight: bold;
        color: #303133;
        font-size: 15px;
    }
</style>