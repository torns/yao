<template>

    <el-dialog
            :title="modalTitle"
            :visible.sync="menuModalVisible">
        <el-form ref="menuFormAdd" :model="menuForm" :label-width="100" :rules="menuFormValidate">
            <div v-if="showParent">
                <el-form-item label="上级节点：">{{parentTitle}}</el-form-item>
            </div>
            <el-form-item label="类型" prop="type">
                <div v-show="menuForm.type===-1">
                    <i class="el-icon-arrow-up" style="margin:0 5px 3px 0;"></i>
                    <span>顶部菜单</span>
                </div>
                <div v-show="menuForm.type===0">
                    <i class="el-icon-tickets" style="margin:0 5px 3px 0;"></i>
                    <span>页面菜单</span>
                </div>
                <div v-show="menuForm.type===1">
                    <i class="el-icon-edit-outline" style="margin:0 5px 3px 0;"></i>
                    <span>操作按钮</span>
                </div>
            </el-form-item>

            <el-form-item label="名称" prop="name" v-if="menuForm.type===-1||menuForm.type===0">
                <el-input v-model="menuForm.name"></el-input>
            </el-form-item>

            <el-form-item label="名称" prop="name" v-if="menuForm.type===1">
                <el-tooltip placement="right" content="操作按钮名称不得重复">
                    <el-input v-model="menuForm.name"></el-input>
                </el-tooltip>
            </el-form-item>

            <el-form-item label="路径" prop="path" v-if="menuForm.type===0">
                <el-input v-model="menuForm.path"></el-input>
            </el-form-item>

            <el-form-item label="请求路径" prop="path" v-if="menuForm.type===1">
                <el-tooltip
                        placement="right"
                        :max-width="230"
                        transfer
                        content="填写后台请求URL，后台将作权限拦截，若无可填写'无'或其他">
                    <el-input v-model="menuForm.path"></el-input>
                </el-tooltip>
            </el-form-item>

            <el-form-item label="按钮权限类型" prop="buttonType" v-show="menuForm.type===1">
                <el-select v-model="menuForm.buttonType"
                           clearable
                           placeholder="请选择">
                    <el-option
                            v-for="item in dictPermissions"
                            :key="item.value"
                            :label="item.title"
                            :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>

            <el-form-item label="图标" prop="icon" v-show="menuForm.type===-1||menuForm.type===0">
                <icon-choose v-model="menuForm.icon"></icon-choose>
            </el-form-item>

            <el-form-item label="前端组件" prop="component" v-show="menuForm.type===0">
                <el-input v-model="menuForm.component"></el-input>
            </el-form-item>

            <el-form-item label="第三方网页链接" prop="url" v-show="menuForm.type===0">
                <el-tooltip placement="right" content="前端组件需为 sys/monitor/monitor 时生效">
                    <el-input v-model="menuForm.url" placeholder="http://"></el-input>
                </el-tooltip>
            </el-form-item>


            <el-form-item label="排序值" prop="sort">
                <el-input-number size="mini"
                                 :max="1000"
                                 :min="0"
                                 v-model="menuForm.sort">
                </el-input-number>
                <span style="margin-left:5px">值越小越靠前</span>
            </el-form-item>

            <el-form-item label="是否启用" prop="status">
                <el-switch size="large" v-model="menuForm.status" :active-value="0" :inactive-value="-1">
                </el-switch>
            </el-form-item>
        </el-form>

        <div slot="footer" class="dialog-fo">
            <el-button type="text" @click="menuModalVisible = false">取消</el-button>
            <el-button type="primary" :loading="submitLoading" @click="submitAdd">提交</el-button>
        </div>
    </el-dialog>
</template>

<script>
    export default {
        props: {
            //弹框是否可见
            menuModalVisible: {
                type: Boolean,
                required: false,
                default: false
            },
            modalTitle: {
                type: String,
                required: false,
                default: ''
            },
            menuFormValidate: {
                type: Object,
                required: false,
                default: {}
            },
            showParent: {
                type: Boolean,
                required: false,
                default: false
            },
            parentTitle: {
                type: String,
                required: false,
                default: ''
            },
        },
        data() {
            return {
                menuForm: {},
                submitLoading:false
            }
        },
        methods: {
            submitAdd() {
                this.$refs.menuFormAdd.validate(valid => {
                    if (valid) {
                        this.submitLoading = true;
                        if (this.menuForm.type == 1) {
                            this.menuForm.name = "";
                            this.menuForm.icon = "";
                            this.menuForm.component = "";
                        }
                        addPermission(this.menuFormAdd).then(res => {
                            this.submitLoading = false;
                            if (res.success == true) {
                                this.$Message.success("添加成功");
                                util.initRouter(this);
                                this.init();
                                this.menuModalVisible = false;
                            }
                        });
                    }
                });
            }
        }
    }
</script>

<style>

</style>