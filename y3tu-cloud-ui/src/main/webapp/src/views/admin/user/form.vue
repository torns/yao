<template>
    <el-dialog :visible.sync="dialog" :title="isAdd ? '新增用户' : '编辑用户'" append-to-body width="550px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="66px">
            <el-form-item label="用户名" prop="username">
                <el-input v-model="form.username"/>
            </el-form-item>
            <el-form-item label="密码" prop="password">
                <el-tooltip placement="right" content="如果不填初始密码默认123456">
                    <el-input v-model="form.password"></el-input>
                </el-tooltip>
            </el-form-item>
            <el-form-item label="状态" prop="status">
                <el-switch
                        v-model="form.status"
                        inactive-text="锁定"
                        active-text="启用"
                        active-value="0"
                        inactive-value="1">
                </el-switch>
            </el-form-item>
            <el-form-item label="电话" prop="mobile">
                <el-input v-model.number="form.mobile"/>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
                <el-input v-model="form.email"/>
            </el-form-item>
            <el-form-item label="部门">

            </el-form-item>

            <el-form-item style="margin-bottom: 0px;" label="角色">
                <el-select v-model="roleIds" style="width: 450px;" multiple placeholder="请选择">
                    <el-option
                            v-for="(item, index) in roles"
                            :disabled="level !== 1 && item.level <= level"
                            :key="item.name + index"
                            :label="item.name"
                            :value="item.id"/>
                </el-select>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button type="text" @click="cancel">取消</el-button>
            <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
        </div>
    </el-dialog>
</template>

<script>

    import {add, edit} from '@/api/user'
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
                form: {
                    username: '',
                    password: '',
                    status: '0',
                    email: '',
                    mobile: '',
                    roles: [],
                    departmentId: '',
                    phone: null
                },
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
                }
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
                //获取角色列表

                //获取部门树
            },
            cancel() {
                this.resetForm()
            },
            doSubmit() {
                this.$refs['form'].validate((valid) => {

                })
            },
            doAdd() {

            },
            doEdit() {

            },
            resetForm() {

            },

        }
    }
</script>

<style scoped>

</style>
