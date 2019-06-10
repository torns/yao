<template>
    <div class="app-container">

        <el-row>
            <el-button @click="addDepartment" icon="el-icon-plus" size="small" type="primary">添加部门</el-button>
            <el-button @click="delBatch" icon="el-icon-delete" size="small" :loading="delLoading">批量删除</el-button>
            <el-dropdown size="small" @command="handleDropdown">
                <el-button size="small">更多操作<i class="el-icon-caret-bottom"></i></el-button>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="refresh">刷新</el-dropdown-item>
                    <el-dropdown-item command="expandOne">仅显示一级</el-dropdown-item>
                    <el-dropdown-item command="expandTwo">仅展开一级</el-dropdown-item>
                    <el-dropdown-item command="expandThree">仅展开两级</el-dropdown-item>
                    <el-dropdown-item command="expandAll">展开所有</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>

            <el-switch
                    style="margin-left: 10px"
                    v-model="strictly"
                    active-text="单选"
                    inactive-text="级联">
            </el-switch>

        </el-row>

        <el-row :gutter="20" style="margin-top: 10px">
            <el-col :span="6">
                <div class="head-container">
                    <el-input v-model="departmentName"
                              clearable
                              placeholder="输入部门名称搜索"
                              prefix-icon="el-icon-search"
                              style="width: 100%;"
                              class="filter-item"/>
                </div>
                <el-alert
                        type="warning"
                        show-icon
                        :closable="false">
                    当前选择编辑：<span class="select-title">{{currentDepartmentName}}</span>
                    <el-button type="primary"
                               size="mini"
                               class="select-clear"
                               v-if="currentDepartmentName!=''"
                               style="margin-left: 10px"
                               @click="cancelCurrent">
                        取消
                    </el-button>
                </el-alert>
                <el-tree
                        :v-loading="treeLoading"
                        ref="departmentTree"
                        :data="departmentTreeData"
                        :props="defaultProps"
                        :expand-on-click-node="false"
                        show-checkbox
                        :filter-node-method="filterNode"
                        @node-click="handleNodeClick"/>
            </el-col>

            <el-col :span="10" style="margin-left:10px;">
                <el-form
                        ref="form"
                        :model="form"
                        :rules="formValidate"
                        size="medium"
                        label-width="100px">
                    <el-form-item label="名称" prop="name">
                        <el-input v-model="form.name"/>
                    </el-form-item>

                    <el-form-item label="状态" prop="status">
                        <el-radio-group v-model="form.status">
                            <el-radio :label="0">正常</el-radio>
                            <el-radio :label="1">禁用</el-radio>
                        </el-radio-group>

                    </el-form-item>

                    <el-form-item label="上级部门" prop="parentId" v-show="!topDepartment">
                        <tree-select
                                :options="departmentTreeData"
                                v-model="form.parentId"
                                :normalizer="treeSelectNormalizer"
                                placeholder="选择上级部门"/>
                    </el-form-item>

                    <el-form-item>
                        <el-button
                                style="margin-right:5px"
                                @click="submit"
                                :loading="submitLoading"
                                type="primary">{{form.id?'编辑保存':'新增'}}
                        </el-button>
                        <el-button @click="handleReset">重置</el-button>
                    </el-form-item>
                </el-form>

            </el-col>
        </el-row>
    </div>
</template>

<script>
    import {getDepartmentTree, del, save, update} from '@/api/department'
    import {parseTime} from '@/utils/index'
    import {getDictDataByCode} from '@/api/dict'

    import TreeSelect from '@riophae/vue-treeselect'
    import '@riophae/vue-treeselect/dist/vue-treeselect.css'


    export default {
        components: {TreeSelect},
        data() {
            return {
                height: document.documentElement.clientHeight - 180 + 'px;',
                treeLoading: false,
                delLoading: false,
                submitLoading: false,
                departmentName: '',
                currentDepartmentName: '',
                departmentTreeData: [],
                strictly: false,
                topDepartment: false,
                defaultProps: {
                    children: 'children',
                    label: 'name'
                },
                form: {
                    id: '',
                    name: '',
                    status: 1,
                    parentId: null
                },
                formValidate: {
                    name: [{required: true, message: "名称不能为空", trigger: "blur"}],
                },
                treeSelectNormalizer(node) {
                    if (node.children == null || node.children.length < 1) {
                        delete node.children
                    }
                    return {
                        label: node.name
                    }
                },
            }
        },
        created() {
            this.$nextTick(() => {
                this.getDepartmentTreeData();
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
            getDepartmentTreeData() {
                this.treeLoading = true;
                getDepartmentTree().then(res => {
                    this.departmentTreeData = res.data;
                    this.treeLoading = false;
                }).catch(err => {
                    this.treeLoading = false;
                })

            },
            filterNode(value, data) {
                if (!value) return true;
                return data.name.indexOf(value) !== -1;
            },
            handleNodeClick(data) {
                this.currentDepartmentName = data.name;
                this.form = data.data;
                if (this.form.parentId === '0') {
                    this.topDepartment = true;
                } else {
                    this.topDepartment = false;
                }
            },
            cancelCurrent() {
                this.currentDepartmentName = '';
                this.handleReset();
            },
            handleDropdown(command) {
                if (command === 'refresh') {
                    this.getDepartmentTreeData();
                }
            },
            addDepartment() {
                this.topDepartment = false;
                delete this.form.id;
                this.handleReset();
            },
            delBatch() {
                let _this = this;
                let checkNodes = this.$refs.departmentTree.getCheckedNodes();
                if (checkNodes.length < 1) {
                    _this.$message({
                        type: 'warning',
                        message: "请勾选需要删除的部门数据"
                    });
                    return;
                }
                this.$confirm('此操作将删除部门, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    _this.delLoading = true;

                    let ids = checkNodes.map(node => {
                        return node.id;
                    });
                    del(ids).then(res => {
                        if (res.status === 'SUCCESS') {
                            this.$message({
                                type: 'success',
                                message: '删除成功!'
                            })
                        }
                        _this.delLoading = false
                        _this.getDepartmentTreeData();
                        _this.handleReset();
                    }).catch(err => _this.delLoading = false)
                }).catch(() => {
                });
            },
            submit() {
                if (this.form.id) {
                    //修改
                    this.submitLoading = true;
                    update(this.form).then(res => {
                        if (res.status === 'SUCCESS') {
                            this.$message.success("修改成功！")
                        }
                        this.submitLoading = false;
                        this.getDepartmentTreeData();
                        this.handleReset();
                    }).catch(err => {
                        this.submitLoading = false;
                    });
                } else {
                    //新增
                    this.submitLoading = true;
                    save(this.form).then(res => {
                        if (res.status === 'SUCCESS') {
                            this.$message.success("新增成功！");
                        }
                        this.getDepartmentTreeData();
                        this.handleReset();
                        this.submitLoading = false;
                    }).catch(err => {
                        this.submitLoading = false;
                    })
                }
            },
            handleReset() {
                this.$refs['form'].resetFields();
            },
        }
    }
</script>

<style scoped>
</style>
