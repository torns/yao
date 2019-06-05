<template>
    <div class="app-container">
        <el-row>
            <div class="head-container">
                <el-form :inline="true" :model="pageInfo.params" label-width='100px'>
                    <el-form-item label="用户名">
                        <el-input v-model="pageInfo.params.username"
                                  clearable
                                  placeholder="请输入用户名"
                                  style="width: 200px"/>
                    </el-form-item>

                    <el-form-item label="部门" prop="departmentId">
                        <el-cascader
                                change-on-select
                                clearable
                                v-model="departmentIds"
                                :options='departmentTreeData'
                                :props="treeProps"
                                style="width: 200px">
                        </el-cascader>
                    </el-form-item>

                    <span v-if="drop">
                        <el-form-item label="手机号" prop="mobile">
                            <el-input
                                    type="text"
                                    v-model="pageInfo.params.mobile"
                                    clearable
                                    placeholder="请输入手机号"
                                    style="width: 200px"/>
                        </el-form-item>

                        <el-form-item label="邮箱" prop="email">
                            <el-input
                                    type="text"
                                    v-model="pageInfo.params.email"
                                    clearable
                                    placeholder="请输入邮箱"
                                    style="width: 200px"/>
                        </el-form-item>
                        <el-form-item label="用户状态" prop="status">
                            <el-select
                                    v-model="pageInfo.params.status"
                                    placeholder="请选择"
                                    clearable
                                    style="width: 200px">
                            <el-option value="0" label="正常"></el-option>
                            <el-option value="1" label="锁定"></el-option>
                          </el-select>
                        </el-form-item>

                        <el-form-item label="创建时间" prop="createTime">
                            <el-date-picker
                                    v-model="pageInfo.params.createTime"
                                    align="right"
                                    type="date"
                                    placeholder="选择日期"
                                    style="width: 200px"/>
                        </el-form-item>
                    </span>

                    <el-form-item style="margin-left: 20px">
                        <el-button
                                size="mini"
                                type="success"
                                icon="el-icon-search"
                                @click="query">
                            查询
                        </el-button>

                        <el-button
                                size="mini"
                                type="primary"
                                icon="el-icon-plus"
                                @click="add">新增
                        </el-button>

                        <el-button
                                :loading="downloadLoading"
                                size="mini"
                                type="warning"
                                icon="el-icon-download"
                                @click="download">导出
                        </el-button>

                        <el-button
                                :loading="downloadLoading"
                                size="mini"
                                @click="dropDown">{{dropDownContent}}
                            <icon :name="dropDownIcon"></icon>
                        </el-button>

                    </el-form-item>

                </el-form>
            </div>
        </el-row>
        <el-row>
            <!--表格渲染-->
            <el-table v-loading="loading" :data="pageInfo.records" size="small" style="width: 100%;">
                <el-table-column prop="username" label="用户名"/>
                <el-table-column prop="mobile" label="电话"/>
                <el-table-column :show-overflow-tooltip="true" prop="email" label="邮箱"/>
                <el-table-column label="部门">
                    <template slot-scope="scope">
                        <div>{{ scope.row.departmentName }}</div>
                    </template>
                </el-table-column>
                <el-table-column label="状态" align="center" prop="status">
                </el-table-column>
                <el-table-column :show-overflow-tooltip="true" prop="createTime" label="创建日期">
                    <template slot-scope="scope">
                        <span>{{ parseTime(scope.row.createTime) }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="操作"
                                 width="125"
                                 align="center">
                    <template slot-scope="scope">

                        <el-button type="primary" icon="el-icon-edit" size="mini" @click="edit(scope.row)"/>

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
        </el-row>

        <user-form ref="form" :is-add="isAdd"></user-form>

    </div>
</template>

<script>
    import {page} from '@/api/user'
    import {getDepartmentTree} from '@/api/department'
    import {parseTime} from '@/utils/index'
    import {getDictDataByCode} from '@/api/dict'
    import userForm from './form'

    export default {
        components: {userForm},
        data() {
            return {
                height: document.documentElement.clientHeight - 180 + 'px;',
                loading: false,
                delLoading: false,
                downloadLoading: false,
                departmentTreeData: [],
                data: [],
                departmentIds: [],
                drop: false,
                dropDownContent: '展开',
                dropDownIcon: 'angle-down',
                pageInfo: {
                    records: [],
                    total: 0,
                    current: 1,
                    size: 10,
                    sort: [],
                    params: {},
                },
                treeProps: {
                    label: 'name',
                    value: 'id'
                },
                isAdd: true
            }
        },
        created() {
            this.$nextTick(() => {
                this.init();
            })
        },
        mounted: function () {
            const that = this
            window.onresize = function temp() {
                that.height = document.documentElement.clientHeight - 180 + 'px;'
            }
        },
        watch: {
            departmentName(val) {
                this.$refs.departmentTree.filter(val);
            }
        },
        methods: {
            parseTime,
            init() {
                this.getDepartmentTreeData();
                this.query();
            },
            //查询用户列表
            query() {
                const _this = this;
                if (_this.isNotEmpty(_this.departmentIds)) {
                    _this.pageInfo.params.departmentId = _this.departmentIds[_this.departmentIds.length - 1]
                } else {
                    _this.pageInfo.params.departmentId = []
                }
                this.loading = true;
                //剔除无效参数
                Object.keys(_this.pageInfo.params).forEach(function (key) {
                    if (_this.isEmpty(_this.pageInfo.params[key])) {
                        delete _this.pageInfo.params[key];
                    }
                });

                this.pageInfo.total=0
                page(this.pageInfo).then(res => {
                    this.pageInfo = res.data;
                    this.loading = false;
                }).catch(err => {
                    this.loading = false;
                })
            },
            sizeChange(e) {
                this.pageInfo.size = e;
                this.pageInfo.current = 1;
                this.query();
            },
            pageChange(e) {
                this.pageInfo.current = e;
                this.query();
            },
            subDelete(id) {
                this.delLoading = true
                del(id).then(res => {
                    this.delLoading = false
                    this.$refs[id].doClose()
                    this.init()
                    this.$notify({
                        title: '删除成功',
                        type: 'success',
                        duration: 2500
                    })
                }).catch(err => {
                    this.delLoading = false
                    this.$refs[id].doClose()
                    console.log(err.response.data.message)
                })
            },
            getDepartmentTreeData() {
                getDepartmentTree().then(res => {
                    let data = res.data;
                    this.clearNullChildren(data);
                    this.departmentTreeData = data
                })
            },
            add() {
                this.isAdd = true;
                this.$refs.form.dialog = true;
            },
            edit(row) {
                this.isAdd = false;
                this.$refs.form.dialog = true;
                this.$refs.form.form = this.copyObj(row);
            },
            download() {

            },
            dropDown() {
                if (this.drop) {
                    this.dropDownContent = "展开";
                    this.dropDownIcon = "angle-down";
                } else {
                    this.dropDownContent = "收起";
                    this.dropDownIcon = "angle-up";
                }
                this.drop = !this.drop;
            },
            clearNullChildren(data) {
                const _this = this;
                data.forEach(function (data, index) {
                    if (data.children == null || data.children == undefined || data.children.length < 1) {
                        delete data.children;
                    } else {
                        _this.clearNullChildren(data.children)
                    }
                })
            }
        }
    }
</script>

<style>

</style>
