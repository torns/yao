<template>
    <el-form ref="menuForm"
             :model="menuForm"
             :rules="menuFormValidate"
             size="medium"
             label-width="100px">
        <el-form-item label="类型" prop="type" style="width: 38%;">

            <el-select v-model="menuForm.type">
                <el-option
                        v-for="item in menuType"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                    <icon :name="item.icon"></icon>
                    <span style="margin-left: 15px">{{item.label}}</span>
                </el-option>
            </el-select>

        </el-form-item>

        <el-form-item label="名称" prop="name">
            <el-tooltip placement="right" content="操作按钮名称不得重复">
                <el-input v-model="menuForm.name"></el-input>
            </el-tooltip>
        </el-form-item>

        <el-form-item label="路径" prop="path">
            <el-tooltip
                    placement="right"
                    :max-width="230"
                    transfer
                    content="填写后台请求URL，后台将作权限拦截，若无可填写'无'或其他">
                <el-input v-model="menuForm.path"></el-input>
            </el-tooltip>
        </el-form-item>

        <el-form-item label="按钮权限类型" prop="buttonType" v-show="menuForm.type===1" style="width: 45%;">
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
            <icon-select v-model="menuForm.icon"></icon-select>
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

        <el-form-item v-show="menuForm.type!==-1" label="上级类目" prop="parentId" style="width: 45%;">
            <tree-select
                    :options="treeData"
                    v-model="menuForm.parentId"
                    :normalizer="treeSelectNormalizer"
                    placeholder="选择上级类目"/>
        </el-form-item>

        <el-form-item>
            <el-button
                    style="margin-right:5px"
                    @click="submit"
                    :loading="submitLoading"
                    type="primary">{{menuForm.id?'编辑保存':'新增'}}
            </el-button>
            <el-button @click="handleReset">重置</el-button>
        </el-form-item>

    </el-form>
</template>

<script>

    import {getDictDataByCode} from '@/api/dict'
    import {saveReource, updateReource} from '@/api/menu'
    import TreeSelect from '@riophae/vue-treeselect'
    import IconSelect from '@/components/IconSelect'

    import '@riophae/vue-treeselect/dist/vue-treeselect.css'

    export default {
        name: 'menuForm',
        components: {IconSelect, TreeSelect},
        props: {
            menuForm: {
                type: Object,
                required: true
            },
            treeData: {
                type: Array,
                required: true
            }
        },
        computed: {},
        data() {
            return {
                submitLoading: false,
                menuFormValidate: {
                    name: [{required: true, message: "名称不能为空", trigger: "blur"}],
                    icon: [{required: true, message: "图标不能为空", trigger: "click"}],
                    path: [{required: true, message: "路径不能为空", trigger: "blur"}],
                    component: [{required: true, message: "前端组件不能为空", trigger: "blur"}]
                },
                dictPermissions: [],
                menuType: [
                    {
                        value: -1,
                        label: '顶级菜单',
                        icon: 'arrow-circle-up'
                    },
                    {
                        value: 0,
                        label: '页面菜单',
                        icon: 'list'
                    },
                    {
                        value: 1,
                        label: '按钮',
                        icon: 'dot-circle-o'
                    }
                ],
                treeSelectNormalizer(node) {
                    if (node.children == null || node.children.length < 1) {
                        delete node.children
                    } else {
                        let flag = true;
                        node.children.forEach(childNode => {
                            if (childNode.type !== 1) {
                                flag = false
                            }
                        });
                        if (flag) {
                            delete node.children
                        }
                    }

                    if (node.type == 1) {
                        //按钮不能作为父级
                        return
                    }
                    return {
                        label: node.name
                    }
                },
            }
        },
        mounted() {
            this.getDictPermissions()
        },
        methods: {
            //获取按钮权限字典类型
            getDictPermissions() {
                getDictDataByCode("permission_type").then(res => {
                    this.dictPermissions = res.data;
                });
            },
            handleReset() {
                let type = this.menuForm.type;
                this.$refs.menuForm.resetFields();
                this.menuForm.type = type;
            },
            submit() {
                const _this = this;
                _this.$refs.menuForm.validate(valid => {
                    if (valid) {
                        _this.submitLoading = true;
                        if (_this.isEmpty(this.menuForm.id)) {
                            saveReource(this.menuForm).then(res => {
                                this.$message({
                                    message: '保存成功!',
                                    type: 'success'
                                })
                                _this.submitLoading = false
                                _this.$emit('finish');
                            }).catch(err => {
                                _this.submitLoading = false;
                            })
                        } else {
                            updateReource(this.menuForm).then(res => {
                                this.$message({
                                    message: '更新成功！',
                                    type: 'success'
                                })
                                _this.submitLoading = false;
                                _this.$emit('finish');
                            }).catch(err => {
                                _this.submitLoading = false;
                            })
                        }

                    } else {
                        return false
                    }
                });
            }

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
</style>