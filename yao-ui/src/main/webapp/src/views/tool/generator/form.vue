<template>
    <el-dialog :visible.sync="dialog" title="生成器配置" append-to-body width="550px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="78px">
            <el-form-item label="作者名称" prop="author">
                <el-input v-model="form.author" style="width: 420px;"></el-input>
            </el-form-item>
            <el-form-item label="包路径" prop="pack">
                <el-input v-model="form.pack" style="width: 420px;"></el-input>
            </el-form-item>
            <el-form-item label="模块名称" prop="moduleName">
                <el-input v-model="form.moduleName" style="width: 420px;"></el-input>
            </el-form-item>
            <el-form-item label="表前缀" prop="path">
                <el-input v-model="form.prefix" style="width: 420px;"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer">
            <el-button type="text" @click="cancel">取消</el-button>
            <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
        </div>
    </el-dialog>
</template>

<script>
    import {updateGeneratorConfig} from '@/api/generator'

    export default {
        data() {
            return {
                loading: false,
                dialog: false,
                form: {author: '', pack: '', moduleName: '', prefix: ''},
                rules: {
                    author: [
                        {required: true, message: '作者不能为空', trigger: 'blur'}
                    ],
                    pack: [
                        {required: true, message: '包路径不能为空', trigger: 'blur'}
                    ],
                    moduleName: [
                        {required: true, message: '模块名称', trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            cancel() {
                this.resetForm()
            },
            doSubmit() {
                this.$refs['form'].validate((valid) => {
                    if (valid) {
                        this.loading = true;
                        this.doUpdate()
                    } else {
                        return false
                    }
                })
            },
            doUpdate() {
                updateGeneratorConfig(this.form).then(res => {
                    this.resetForm();
                    this.$notify({
                        title: '更新成功',
                        type: 'success',
                        duration: 2500
                    })
                    this.loading = false
                }).catch(err => {
                    this.loading = false
                    console.log(err.response.data.message)
                })
            },
            resetForm() {
                this.dialog = false
                this.$refs['form'].resetFields()
                this.form = {author: '', pack: '', path: '', moduleName: '', cover: 'false', apiPath: ''}
            }
        }
    }
</script>