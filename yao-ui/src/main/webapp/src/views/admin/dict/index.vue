<template>
    <div class="app-container">
        <el-row :gutter="10">
            <el-col :xs="24" :sm="24" :md="10" :lg="10" :xl="10">
                <el-card class="box-card">
                    <div slot="header" class="clearfix">
                        <span>字典列表</span>
                        <el-button
                                class="filter-item"
                                size="mini"
                                style="float: right;padding: 4px 10px"
                                type="primary"
                                icon="el-icon-plus"
                                @click="doAdd">新增
                        </el-button>

                        <el-button
                                size="mini"
                                type="success"
                                style="float: right;padding: 4px 10px"
                                icon="el-icon-search"
                                @click="query">
                            查询
                        </el-button>
                    </div>

                    <div class="head-container">
                        <el-form :inline="true">
                            <el-form-item label="字典名">
                                <el-input
                                        v-model="pageInfo.params.name"
                                        clearable
                                        style="width: 100px"
                                />
                            </el-form-item>

                            <el-form-item label="字典编码">
                                <el-input
                                        v-model="pageInfo.params.code"
                                        clearable
                                        style="width: 100px"
                                />
                            </el-form-item>

                            <el-form-item label="类型">
                                <el-select
                                        v-model="pageInfo.params.type"
                                        placeholder="请选择"
                                        clearable
                                        style="width: 110px">
                                    <el-option value="0" label="普通字典"></el-option>
                                    <el-option value="1" label="SQL字典"></el-option>
                                </el-select>
                            </el-form-item>

                        </el-form>

                    </div>

                    <!--表格渲染-->
                    <el-table v-loading="pageLoading" :data="pageInfo.records" size="small" highlight-current-row
                              style="width: 100%;"
                              @current-change="handleCurrentChange">
                        <el-table-column :show-overflow-tooltip="true" prop="name" label="名称"/>
                        <el-table-column :show-overflow-tooltip="true" prop="code" label="编码"/>
                        <el-table-column prop="type" label="类型">
                            <template slot-scope="scope">
                                <span>{{ scope.row.type==0?'普通字典':'SQL字典' }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column :show-overflow-tooltip="true" prop="description" label="描述"/>
                        <el-table-column label="操作" width="130px" align="center">
                            <template slot-scope="scope">

                                <el-button type="primary" icon="el-icon-edit" size="mini" @click="doEdit(scope.row)"/>

                                <el-popover
                                        :ref="scope.row.id"
                                        placement="top"
                                        width="180">
                                    <p>此操作将删除字典与对应的字典数据，确定要删除吗？</p>
                                    <div style="text-align: right; margin: 0">
                                        <el-button size="mini" type="text" @click="$refs[scope.row.id].doClose()">取消
                                        </el-button>
                                        <el-button :loading="delLoading" type="primary" size="mini"
                                                   @click="subDelete(scope.row.id)">确定
                                        </el-button>
                                    </div>
                                    <el-button slot="reference" type="danger" icon="el-icon-delete" size="mini"/>
                                </el-popover>
                            </template>
                        </el-table-column>
                    </el-table>
                    <!--分页组件-->
                    <el-pagination
                            :total="pageInfo.total"
                            style="margin-top: 8px;"
                            layout="total, prev, pager, next, sizes"
                            @size-change="sizeChange"
                            @current-change="pageChange"/>
                </el-card>
            </el-col>
            <el-col :xs="24" :sm="24" :md="14" :lg="14" :xl="14">
                <dict-data ref="dictData"/>
            </el-col>
        </el-row>

        <el-dialog :visible.sync="dialog" :title="isAdd ? '新增字典' : '编辑字典'" append-to-body width="550px" @close="cancel">
            <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px">
                <el-form-item label="字典名称" prop="name">
                    <el-input v-model="form.name" clearable/>
                </el-form-item>
                <el-form-item label="字典编码" prop="code">
                    <el-input v-model="form.code"/>
                </el-form-item>
                <el-form-item label="字典类型" prop="type">
                    <el-select
                            v-model="form.type"
                            placeholder="请选择"
                            clearable
                            style="width: 110px">
                        <el-option :value="0" label="普通字典"></el-option>
                        <el-option :value="1" label="SQL字典"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="描述" prop="description">
                    <el-input v-model="form.description"/>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="text" @click="cancel">取消</el-button>
                <el-button :loading="submitLoading" type="primary" @click="doSubmit">确认</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import {saveDict, editDict, delDict} from '@/api/dict'
    import page from '@/mixins/page'

    import dictData from './dictData'

    export default {
        components: {dictData},
        mixins: [page],
        data() {
            return {
                dialog: false,
                isAdd: true,
                form: {},
                rules: {
                    name: [{required: true, message: '请输入字典名称', trigger: 'blur'}],
                    code: [{required: true, message: '请输入字典编码', trigger: 'blur'}],
                    type: [{required: true, message: '请选择字典类型', trigger: 'blur'}],
                },
                submitLoading: false,
                delLoading: false,
                dictId: '',
                dictName: ''
            }
        },
        created() {
            this.$nextTick(() => {
                this.query();
            })
        },
        methods: {
            query() {
                this.page();
            },
            pageInit() {
                this.pageUrl = '/upms/dict/page'
                return true;
            },
            subDelete(id) {
                this.delLoading = true
                delDict(id).then(res => {
                    this.delLoading = false
                    this.$refs[id].doClose()
                    this.$notify({
                        title: '删除成功',
                        type: 'success',
                        duration: 2500
                    });
                    this.page();
                }).catch(err => {
                    this.delLoading = false
                    this.$refs[id].doClose()
                    console.log(err.response.data.message)
                })
            },
            handleCurrentChange(val) {
                if (val) {
                    this.$refs.dictData.dictName = val.name
                    this.$refs.dictData.dictId = val.id
                    this.$refs.dictData.init()
                }
            },
            doEdit(row) {
                this.dialog = true;
                this.isAdd = false;
                this.form = this.copyObj(row);
            },
            doAdd() {
                this.isAdd = true;
                this.dialog = true;
            },
            cancel() {
                this.resetForm();
            },
            doSubmit() {

                this.submitLoading = true;
                this.$refs['form'].validate((valid) => {
                    if (valid) {
                        if (this.isAdd) {
                            //新增
                            saveDict(this.form).then(res => {
                                this.$message({
                                    message: '新增成功!',
                                    type: 'success'
                                });
                                this.submitLoading = false;
                                this.cancel();
                                this.query();
                            }).catch(err => {
                                this.submitLoading = false;
                            })
                        } else {
                            //编辑
                            editDict(this.form).then(res => {
                                this.$message({
                                    message: '编辑成功!',
                                    type: 'success'
                                });
                                this.submitLoading = false;
                                this.cancel();
                                this.query();
                            }).catch(err => {
                                this.submitLoading = false;
                            })
                        }
                    } else {
                        this.submitLoading = false;
                    }
                })

            },
            resetForm() {
                this.dialog = false;
                this.form = {};
                this.$refs['form'].resetFields();
            }

        }
    }
</script>

<style scoped>

</style>
