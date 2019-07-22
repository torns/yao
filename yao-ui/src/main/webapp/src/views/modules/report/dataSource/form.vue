<template>
    <el-dialog :append-to-body="true" :visible.sync="dialog" :title="isAdd ? '新增' : '编辑'" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
            <el-form-item label="数据源名称" prop="name">
                <el-input v-model="form.name" style="width: 370px;"/>
            </el-form-item>
            <el-form-item label="数据源类型 mysql oracle" prop="type">
                <el-input v-model="form.type" style="width: 370px;"/>
            </el-form-item>
            <el-form-item label="数据源驱动类" prop="driverClass">
                <el-input v-model="form.driverClass" style="width: 370px;"/>
            </el-form-item>
            <el-form-item label="数据源连接字符串(JDBC)" prop="jdbcUrl">
                <el-input v-model="form.jdbcUrl" style="width: 370px;"/>
            </el-form-item>
            <el-form-item label="数据源登录用户名" prop="user">
                <el-input v-model="form.user" style="width: 370px;"/>
            </el-form-item>
            <el-form-item label="数据源登录密码" prop="password">
                <el-input v-model="form.password" style="width: 370px;"/>
            </el-form-item>
            <el-form-item label="获取报表引擎查询器类名" prop="queryClass">
                <el-input v-model="form.queryClass" style="width: 370px;"/>
            </el-form-item>
            <el-form-item label="报表引擎查询器使用的数据源连接池类名" prop="poolClass">
                <el-input v-model="form.poolClass" style="width: 370px;"/>
            </el-form-item>
            <el-form-item label="说明备注" prop="comment">
                <el-input v-model="form.comment" style="width: 370px;"/>
            </el-form-item>
            <el-form-item label="数据源配置选项(JSON格式）" prop="options">
                <el-input v-model="form.options" style="width: 370px;"/>
            </el-form-item>
            <el-form-item label="记录创建时间" prop="createTime">
                <el-input v-model="form.createTime" style="width: 370px;"/>
            </el-form-item>
            <el-form-item label="记录修改时间" prop="updateTime">
                <el-input v-model="form.updateTime" style="width: 370px;"/>
            </el-form-item>
            <el-form-item label="创建人" prop="createUser">
                <el-input v-model="form.createUser" style="width: 370px;"/>
            </el-form-item>
            <el-form-item label="更新人" prop="updateUser">
                <el-input v-model="form.updateUser" style="width: 370px;"/>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button type="text" @click="cancel">取消</el-button>
            <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
        </div>
    </el-dialog>
</template>

<script>
    import {save, update} from '@/api/dataSource'

    export default {
        props: {
            isAdd: {
                type: Boolean,
                required: true
            }
        },
        data() {
            return {
                loading: false, dialog: false,
                form: {
                    id: '',
                    name: '',
                    type: '',
                    driverClass: '',
                    jdbcUrl: '',
                    user: '',
                    password: '',
                    queryClass: '',
                    poolClass: '',
                    comment: '',
                    options: '',
                    createTime: '',
                    updateTime: '',
                    createUser: '',
                    updateUser: ''
                },
                rules: {
                    id: [{
                        required: true,
                        message: 'please enter',
                        trigger: 'blur'
                    }], name: [{
                        required: true,
                        message: 'please enter',
                        trigger: 'blur'
                    }], type: [{
                        required: true,
                        message: 'please enter',
                        trigger: 'blur'
                    }], driverClass: [{
                        required: true,
                        message: 'please enter',
                        trigger: 'blur'
                    }], jdbcUrl: [{
                        required: true,
                        message: 'please enter',
                        trigger: 'blur'
                    }], user: [{
                        required: true,
                        message: 'please enter',
                        trigger: 'blur'
                    }], password: [{
                        required: true,
                        message: 'please enter',
                        trigger: 'blur'
                    }], queryClass: [{
                        required: true,
                        message: 'please enter',
                        trigger: 'blur'
                    }], poolClass: [{
                        required: true,
                        message: 'please enter',
                        trigger: 'blur'
                    }], comment: [{
                        required: true,
                        message: 'please enter',
                        trigger: 'blur'
                    }], options: [{
                        required: true,
                        message: 'please enter',
                        trigger: 'blur'
                    }], createTime: [{
                        required: true,
                        message: 'please enter',
                        trigger: 'blur'
                    }], updateTime: [{
                        required: true,
                        message: 'please enter',
                        trigger: 'blur'
                    }], createUser: [{
                        required: true,
                        message: 'please enter',
                        trigger: 'blur'
                    }], updateUser: [{
                        required: true,
                        message: 'please enter',
                        trigger: 'blur'
                    }]
                }
            }
        },
        methods: {
            cancel() {
                this.resetForm()
            },
            doSubmit() {
                this.loading = true
                if (this.isAdd) {
                    this.doAdd()
                } else this.doEdit()
            },
            doAdd() {
                save(this.form).then(res => {
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
                update(this.form).then(res => {
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
                this.$refs['form'].resetFields();
                this.form = {
                    id: '',
                    name: '',
                    type: '',
                    driverClass: '',
                    jdbcUrl: '',
                    user: '',
                    password: '',
                    queryClass: '',
                    poolClass: '',
                    comment: '',
                    options: '',
                    createTime: '',
                    updateTime: '',
                    createUser: '',
                    updateUser: ''
                }
            }
        }
    }
</script>

<style scoped>

</style>
