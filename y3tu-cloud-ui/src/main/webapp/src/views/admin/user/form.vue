<template>
    <el-dialog :visible.sync="dialog" :title="isAdd ? '新增用户' : '编辑用户'" append-to-body width="550px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="66px">
            <el-form-item label="用户名" prop="username">
                <el-input v-model="form.username" clearable/>
            </el-form-item>
            <el-form-item label="密码" prop="password">
                <el-tooltip placement="right" content="如果不填初始密码默认123456">
                    <el-input v-model="form.password"
                              placeholder="请输入密码"
                              show-password
                              clearable/>
                </el-tooltip>
            </el-form-item>
            <el-form-item label="状态" prop="status">
                <el-switch
                        v-model="form.status"
                        inactive-text="锁定"
                        active-text="启用"
                        :active-value="0"
                        :inactive-value="1">
                </el-switch>
            </el-form-item>
            <el-form-item label="电话" prop="mobile">
                <el-input v-model.number="form.mobile"/>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
                <el-input v-model="form.email"/>
            </el-form-item>
            <el-form-item label="部门" prop="departmentId">
                <tree-select
                        :options="departmentTreeData"
                        v-model="form.departmentId"
                        :normalizer="treeSelectNormalizer"
                        placeholder="选择部门"/>
            </el-form-item>

            <el-form-item label="角色" prop="roleIds">
                <el-select v-model="roleIds" style="width: 450px;" multiple placeholder="请选择">
                    <el-option
                            v-for="item in roles"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id"/>
                </el-select>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button type="text" @click="cancel">取消</el-button>
            <el-button :loading="submitLoading" type="primary" @click="doSubmit">确认</el-button>
        </div>
    </el-dialog>
</template>

<script>

    import {save, edit} from '@/api/user'
    import {getAll as getAllRole} from '@/api/role'
    import {getDepartmentTree} from '@/api/department'
    import {validateMobile, validateEmail} from '@/utils/validate'
    import TreeSelect from '@riophae/vue-treeselect'
    import '@riophae/vue-treeselect/dist/vue-treeselect.css'

    export default {
        name: 'userForm',
        components: {TreeSelect},
        props: {
            isAdd: {
                type: Boolean,
                required: true
            },
        },
        data() {
            return {
                dialog: false,
                loading: false,
                submitLoading: false,
                form: {
                    username: '',
                    password: '',
                    status: 0,
                    email: '',
                    mobile: '',
                    roles: [],
                    departmentId: null,
                },
                departmentTreeData: [],
                roleIds: [],
                roles: [],
                style: 'width: 184px',
                rules: {
                    username: [
                        {required: true, message: '请输入用户名', trigger: 'blur'},
                        {min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur'}
                    ],
                    email: [
                        {required: true, message: '请输入邮箱地址', trigger: 'blur'},
                        {type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur'}
                    ],
                    mobile: [
                        {
                            required: true, trigger: 'blur', validator: (rule, value, callback) => {
                                if (!value) {
                                    callback(new Error('请输入电话号码'))
                                } else if (!validateMobile(value)) {
                                    callback(new Error('请输入正确的11位手机号码'))
                                } else {
                                    callback()
                                }
                            }
                        }
                    ],
                    departmentId: [{required: true, message: '请选择部门', trigger: 'blur'}],
                    roleIds: [
                        {
                            required: true, message: '请选择角色', trigger: 'change', validator: (rule, value, callback) => {
                                if (this.roleIds.length < 1) {
                                    callback(new Error('请选择角色'))
                                } else {
                                    callback();
                                }
                            }
                        }
                    ],
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
            const explorer = navigator.userAgent
            if (explorer.indexOf('Chrome') >= 0) {
                this.style = 'width: 184px'
            } else {
                this.style = 'width: 172px'
            }

            this.$nextTick(() => {
                this.init();
            })
        },
        methods: {
            init() {
                const _this = this;
                //获取角色列表
                getAllRole().then(res => {
                    _this.roles = res.data;
                });
                //获取部门树
                getDepartmentTree().then(res => {
                    _this.departmentTreeData = res.data;
                })
            },
            cancel() {
                this.resetForm()
            },
            doSubmit() {
                const _this = this;
                _this.submitLoading = true;
                _this.$refs['form'].validate((valid) => {
                    if (valid) {
                        if (_this.isAdd) {
                            _this.doAdd()
                        } else {
                            _this.doEdit()
                        }
                    } else {
                        _this.submitLoading = true;
                    }
                })
            },
            doAdd() {
                const _this = this;
                if (_this.isNotEmpty(_this.roleIds)) {
                    let roleArr = [];
                    _this.roleIds.forEach(roleId => {
                        roleArr.push({
                            id: roleId
                        })
                    });
                    _this.form.roles = roleArr;
                }

                save(_this.form).then(res => {
                    this.$message({
                        message: '添加成功！',
                        type: 'success'
                    })
                    _this.resetForm();
                    _this.$parent.init();
                    _this.submitLoading = false;
                }).catch(err => {
                    _this.submitLoading = false;
                })

            },
            doEdit() {

            },
            resetForm() {
                this.dialog = false;
                this.form = {
                    username: '',
                    password: '',
                    status: 0,
                    email: '',
                    mobile: '',
                    roles: [],
                    departmentId: null,
                };
                this.$refs['form'].resetFields();
            }

        }
    }
</script>

<style scoped>

</style>
