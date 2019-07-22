<template>
    <div class="app-container">
        <el-row>
            <el-button @click="addMenu" icon="el-icon-plus" size="small" type="primary">添加节点</el-button>
            <el-button @click="delBatch" icon="el-icon-delete" size="small" :loading="btnLoading">批量删除</el-button>
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

        <el-row type="flex" justify="start">
            <el-col :span="6">
                <el-alert
                        type="warning"
                        show-icon
                        :closable="false">
                    当前选择编辑：<span class="select-title">{{editTitle}}</span>
                    <el-button type="primary" size="mini" class="select-clear" v-if="menuForm.id" @click="cancelEdit">
                        取消
                    </el-button>
                </el-alert>

                <el-input
                        style="margin-top: 10px"
                        size="small"
                        v-model="searchKey"
                        placeholder="输入菜单名搜索"
                        suffix-icon="el-icon-search"
                        @change="search"
                        clearable>
                </el-input>

                <el-tree
                        v-loading="treeLoading"
                        ref="tree"
                        :props="treeProps"
                        :data="treeData"
                        class="filter-tree"
                        show-checkbox
                        :filter-node-method="filterNode"
                        @check-change="handleCheckChange"
                        @node-click="handleTreeClick"
                        :check-strictly="strictly"
                        :expand-on-click-node="false">
                </el-tree>

            </el-col>

            <el-col :span="10" style="margin-left:10px;">
                <menu-form ref="menuForm"
                           :treeData="treeData"
                           :menuForm="menuForm"
                           @finish="formFinish">
                </menu-form>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import MenuForm from './form'

    import {getMenuTree, deleteResourceById} from '@/api/menu'
    import {getDictDataByCode} from '@/api/dict'

    import {replaceAll} from '@/utils/util'

    export default {
        components: {
            MenuForm
        },
        data() {
            return {
                treeProps: {
                    label: 'name',
                    children: 'children',
                },
                treeData: [],
                count: 1,
                strictly: false,
                editTitle: '',
                searchKey: '',
                menuForm: {
                    id: "",
                    name: "",
                    icon: "",
                    path: "",
                    component: "",
                    parentId: null,
                    buttonType: "",
                    type: 0,
                    sort: 0,
                    status: 0,
                    url: ""
                },
                menuModalVisible: false,
                modalTitle: '',
                showParent: false,
                parentTitle: '',
                btnLoading: false,
                treeLoading: false
            }
        },

        mounted() {
            this.init();
        },
        watch: {
            searchKey(val) {
                this.$refs.tree.filter(val);
            }
        },
        computed: {},
        methods: {
            init() {
                // 计算高度
                let height = document.documentElement.clientHeight;
                this.maxHeight = Number(height - 287) + "px";
                this.getMenuList();
            },
            //获取菜单树
            getMenuList() {
                const _this = this;
                _this.treeLoading = true;
                getMenuTree().then(res => {
                    _this.treeData = res.data;
                    _this.treeLoading = false;
                }).catch(error => {
                    _this.treeLoading = false;
                })
            },
            filterNode(value, data) {
                if (!value) return true;
                return data.name.indexOf(value) !== -1;
            },
            //添加菜单
            addMenu() {
                this.cancelEdit();
            },
            //批量删除
            delBatch() {
                let _this = this;
                let checkNodes = this.$refs.tree.getCheckedNodes();
                if (checkNodes.length < 1) {
                    _this.$message({
                        type: 'warning',
                        message: "请勾选需要删除的菜单数据"
                    });
                    return;
                }
                this.$confirm('此操作将删除菜单和按钮, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    _this.btnLoading = true;

                    let ids = checkNodes.map(node => {
                        return node.id;
                    });
                    deleteResourceById(ids).then(res => {
                        if (res.status === 'SUCCESS') {
                            this.$message({
                                type: 'success',
                                message: '删除成功!'
                            })
                        }
                        _this.btnLoading = false
                        _this.getMenuList();
                    }).catch(err => _this.btnLoading = false)
                }).catch(() => {
                });
            },
            //取消编辑
            cancelEdit() {
                this.$refs['menuForm'].handleReset();
                this.menuForm.id = "";
                delete this.menuForm.id;
                this.editTitle = "";
            },
            //根据菜单名搜索菜单
            search() {

            },
            handleDropdown(command) {
                if (command === 'refresh') {
                    this.getMenuList();
                }
            },
            handleCheckChange(v) {

            },
            handleTreeClick(v, node, treeNode) {
                let data = v.data;
                if (data && data.id != this.menuForm.id) {
                    // 转换null为""
                    for (let attr in data) {
                        if (data[attr] == null) {
                            data[attr] = "";
                        }
                    }
                    let str = JSON.stringify(data);
                    let menu = JSON.parse(str);
                    this.menuForm = menu;
                    this.editTitle = menu.name;
                } else {
                    this.cancelEdit();
                }

            },
            formFinish() {
                this.init();
                this.editTitle = "";
            }

        }
    }

</script>

<style>

    .select-title {
        font-size: 12px;
        font-weight: 600;
        color: #40a9ff;
    }

    .select-clear {
        margin-left: 10px;
    }
</style>