<template>
    <div class="app-container">
        <el-row>
            <el-button @click="addMenu" icon="el-icon-plus" size="small" type="primary">添加子节点</el-button>
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

            <el-switch v-model="strict"
                       active-text="级联"
                       inactive-text="单选"
                       style="margin-left:10px">
            </el-switch>
        </el-row>

        <el-row type="flex" justify="start">
            <el-col :span="6">
                <el-alert
                        type="info"
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
                        :expand-on-click-node="false"
                        :check-strictly="!strict">
                </el-tree>

            </el-col>

            <el-col :span="10" style="margin-left:10px;">
                <el-form ref="menuForm"
                         :model="menuForm"
                         :rules="menuFormValidate"
                         size="medium"
                         label-width="100px">
                    <el-form-item label="类型">
                        <div v-show="menuForm.type==-1">
                            <i class="el-icon-arrow-up" style="margin:0 5px 3px 0;"></i>
                            <span>顶部菜单</span>
                        </div>
                        <div v-show="menuForm.type==0">
                            <i class="el-icon-tickets" style="margin:0 5px 3px 0;"></i>
                            <span>页面菜单</span>
                        </div>
                        <div v-show="menuForm.type==1">
                            <i class="el-icon-edit-outline" style="margin:0 5px 3px 0;"></i>
                            <span>操作按钮</span>
                        </div>
                    </el-form-item>

                    <el-form-item label="名称" v-if="menuForm.type==-1||menuForm.type==0" prop="title">
                        <el-input v-model="menuForm.title"/>
                    </el-form-item>

                    <el-form-item label="名称" v-if="menuForm.type==1" prop="title">
                        <el-tooltip placement="right" content="操作按钮名称不得重复">
                            <el-input v-model="menuForm.title"></el-input>
                        </el-tooltip>
                    </el-form-item>

                    <el-form-item label="路径" v-if="menuForm.type==0" prop="path">
                        <el-input v-model="menuForm.path"></el-input>
                    </el-form-item>

                    <el-form-item label="请求路径" v-if="menuForm.type==1" prop="path">
                        <el-tooltip
                                placement="right"
                                :max-width="230"
                                transfer
                                content="填写后台请求URL，后台将作权限拦截，若无可填写'无'或其他">
                            <el-input v-model="menuForm.path"></el-input>
                        </el-tooltip>
                    </el-form-item>

                    <el-form-item label="按钮权限类型" prop="buttonType" v-if="menuForm.type==1">
                        <el-select
                                v-model="menuForm.buttonType"
                                placeholder="请选择或输入搜索"
                                filterable
                                clearable>
                            <el-option
                                    v-for="(item, i) in dictPermissions"
                                    :key="i"
                                    :value="item.value">{{item.title}}
                            </el-option>
                        </el-select>
                    </el-form-item>

                    <el-form-item label="图标" v-if="menuForm.type===-1||menuForm.type===0" prop="icon">
                        <icon-choose v-model="menuForm.icon"></icon-choose>
                    </el-form-item>

                    <el-form-item label="前端组件" v-if="menuForm.type===0" prop="component">
                        <el-input v-model="menuForm.component"></el-input>
                    </el-form-item>

                    <el-form-item label="第三方网页链接" v-if="menuForm.type===0">
                        <el-tooltip placement="right" content="前端组件需为 sys/monitor/monitor 时生效">
                            <el-input v-model="menuForm.url" placeholder="http://"></el-input>
                        </el-tooltip>
                    </el-form-item>

                    <el-form-item label="排序值" prop="sortOrder">
                        <el-input-number size="mini" :max="1000" :min="0"
                                         v-model="menuForm.sortOrder"></el-input-number>
                        <span style="margin-left:5px">值越小越靠前</span>
                    </el-form-item>

                    <el-form-item label="是否启用" prop="status">
                        <el-switch size="large" v-model="menuForm.status" :true-value="0" :false-value="-1">
                            <span slot="open">启用</span>
                            <span slot="close">禁用</span>
                        </el-switch>
                    </el-form-item>

                    <el-form-item>
                        <el-button
                                style="margin-right:5px"
                                @click="submitEdit"
                                :loading="submitLoading"
                                type="primary"
                                icon="ios-create-outline">修改并保存
                        </el-button>
                        <el-button @click="handleReset">重置</el-button>
                    </el-form-item>


                </el-form>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import Developing from '@/components/Developing'
    import IconChoose from '@/components/IconChoose'

    import {getAllReource} from '@/api/menu'
    import {getDictDataByCode} from '@/api/dict'

    export default {
        data() {
            return {
                treeProps: {
                    label: 'name',
                    children: 'children',
                },
                treeData: [],
                count: 1,
                strict: true,
                editTitle: '',
                searchKey: '',
                menuForm: {
                    id: "",
                    title: "",
                    name: "",
                    icon: "",
                    path: "",
                    component: "",
                    parentId: "",
                    buttonType: "",
                    type: 0,
                    sortOrder: 0,
                    level: null,
                    status: 0,
                    url: ""
                },
                menuFormValidate: {
                    title: [{required: true, message: "名称不能为空", trigger: "blur"}],
                    name: [{required: true, message: "路由英文名不能为空", trigger: "blur"}],
                    icon: [{required: true, message: "图标不能为空", trigger: "click"}],
                    path: [{required: true, message: "路径不能为空", trigger: "blur"}],
                    component: [{required: true, message: "前端组件不能为空", trigger: "blur"}]
                },
                submitLoading: false,
                dictPermissions: []
            }
        },
        components: {
            Developing,
            IconChoose
        },
        created() {
            this.getMenuList();
            this.getDictPermissions();
        },
        methods: {
            //获取按钮权限字典类型
            getDictPermissions() {
                getDictDataByCode("permission_type").then(res => {
                    this.dictPermissions = res.data;
                });
            },
            //获取菜单树
            getMenuList() {
                getAllReource().then(res => {
                    this.treeData = res.data;
                });
            },
            //添加菜单
            addMenu() {

            },
            //添加根菜单
            addRootMenu() {

            },
            //批量删除
            delBatch() {

            },
            //取消编辑
            cancelEdit() {
                this.$refs.menuForm.resetFields();
                this.menuForm.id = "";
                delete this.menuForm.id;
                this.editTitle = "";
                console.log("edit" + this.editTitle)
            },
            //根据菜单名搜索菜单
            search() {

            },
            handleCheckChange() {

            },
            handleTreeClick(data, node, treeNode) {
                if (data) {
                    // 转换null为""
                    for (let attr in data) {
                        if (data[attr] == null) {
                            data[attr] = "";
                        }
                    }
                    let str = JSON.stringify(data);
                    let menu = JSON.parse(str);
                    this.menuForm = menu;
                    this.editTitle = menu.title;
                } else {
                    debugger;
                    this.cancelEdit();
                }
            },
            submitEdit() {

            },
            handleReset() {

            },


        }
    }

</script>

<style>
    .el-row {
        margin-top: 10px;
    }

    .el-dropdown {
        font-size: 13px;
        margin-left: 8px;
    }

    .el-form-item__label {
        font-size: 12px;
    }

    .select-title {
        font-size: 12px;
        font-weight: 600;
        color: #40a9ff;
    }

    .select-clear {
        margin-left: 10px;
    }
</style>