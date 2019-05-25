<template>
    <div class="app-container">
        <el-row>
            <el-button @click="addMenu" icon="el-icon-plus" size="small" type="primary">添加节点</el-button>
            <el-button @click="addRootMenu" icon="el-icon-plus" size="small">添加顶部菜单</el-button>
            <el-button @click="delBatch" icon="el-icon-delete" size="small">批量删除</el-button>
            <el-dropdown size="small">
                <el-button size="small">更多操作<i class="el-icon-caret-bottom"></i></el-button>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item name="refresh">刷新</el-dropdown-item>
                    <el-dropdown-item name="expandOne">仅显示一级</el-dropdown-item>
                    <el-dropdown-item name="expandTwo">仅展开一级</el-dropdown-item>
                    <el-dropdown-item name="expandThree">仅展开两级</el-dropdown-item>
                    <el-dropdown-item name="expandAll">展开所有</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>

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
                        ref="tree"
                        :props="treeProps"
                        :data="treeData"
                        show-checkbox
                        @check-change="handleCheckChange"
                        @node-click="handleTreeClick"
                        :expand-on-click-node="false">
                </el-tree>

            </el-col>

            <el-col :span="10" style="margin-left:10px;">
                <menu-form ref="menuForm"
                        :treeData="treeData"
                        :menuForm="menuForm">
                </menu-form>
            </el-col>
        </el-row>


    </div>
</template>

<script>
    import Developing from '@/components/Developing'
    import MenuForm from './module/form'

    import {getAllReource} from '@/api/menu'
    import {getDictDataByCode} from '@/api/dict'

    import {replaceAll} from '@/utils/util'

    export default {
        components: {
            Developing,
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
            }
        },

        mounted() {
            // 计算高度
            let height = document.documentElement.clientHeight;
            this.maxHeight = Number(height - 287) + "px";
            this.getMenuList();
        },
        computed: {},
        methods: {

            //获取菜单树
            getMenuList() {
                getAllReource().then(res => {
                    this.treeData = res.data;
                });
            },
            //添加菜单
            addMenu() {
               this.cancelEdit();
            },
            //添加根菜单
            addRootMenu() {

            },
            //批量删除
            delBatch() {

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
            handleCheckChange() {

            },
            handleTreeClick(v, node, treeNode) {
                if (v && v.id != this.menuForm.id) {
                    // 转换null为""
                    for (let attr in v) {
                        if (v[attr] == null) {
                            v[attr] = "";
                        }
                    }
                    let str = JSON.stringify(v);
                    let menu = JSON.parse(str);
                    this.menuForm = menu;
                    this.editTitle = menu.name;
                } else {
                    this.cancelEdit();
                }

            },

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