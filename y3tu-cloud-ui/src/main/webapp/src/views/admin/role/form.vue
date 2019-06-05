<template>
    <el-dialog :visible.sync="dialog" :title="isAdd ? '新增角色' : '编辑角色'" append-to-body width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
            <el-form-item label="角色名称" prop="name">
                <el-input v-model="form.name" style="width: 370px;"/>
            </el-form-item>
            <el-form-item label="角色编码" prop="roleCode">
                <el-input v-model="form.roleCode" style="width: 370px;"/>
            </el-form-item>
            <el-form-item label="描述信息" prop="description">
                <el-input v-model="form.description" style="width: 370px;" rows="5" type="textarea"/>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button type="text" @click="cancel">取消</el-button>
            <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
        </div>
    </el-dialog>
</template>

<script>
    import {saveRole, updateRole} from '@/api/role'

    export default {
        name: 'roleForm',
        components: {},
        props: {
            isAdd: {
                type: Boolean,
                required: true
            }
        },
        data() {
            return {
                loading: false, dialog: false,
                form: {name: '', description: '', roleCode: ''},
                rules: {
                    name: [
                        {required: true, message: '请输入名称', trigger: 'blur'}
                    ],
                    roleCode: [
                        {required: true, message: '请输入编码', trigger: 'blur'}
                    ]
                }
            }
        }
        ,
        methods: {
            cancel() {
                this.resetForm()
            },
            doSubmit() {
                this.$refs['form'].validate((valid) => {
                    if (valid) {
                        this.loading = true
                        if (this.isAdd) {
                            this.doAdd()
                        } else this.doEdit()
                    } else {
                        return false
                    }
                })
            },
            doAdd() {
                saveRole(this.form).then(res => {
                    this.resetForm()
                    this.$notify({
                        title: '添加成功',
                        type: 'success',
                        duration: 2500
                    })
                    this.loading = false
                    this.$parent.init()
                }).catch(err => {
                    this.loading = false
                    console.log(err.response.data.message)
                })
            },
            doEdit() {
                updateRole(this.form).then(res => {
                    this.resetForm()
                    this.$notify({
                        title: '修改成功',
                        type: 'success',
                        duration: 2500
                    })
                    this.loading = false
                    this.$parent.init()
                }).catch(err => {
                    this.loading = false
                    console.log(err.response.data.message)
                })
            },
            resetForm() {
                this.dialog = false
                this.form = {name: '', description: '', roleCode: ''};
                this.$refs['form'].resetFields();
            }
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    /deep/ .el-input-number .el-input__inner {
        text-align: left;
    }
</style>
