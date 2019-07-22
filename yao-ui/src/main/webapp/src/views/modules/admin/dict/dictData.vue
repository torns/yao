<template>
    <el-card class="box-card">
        <div slot="header" class="clearfix">
            <span>字典详情</span>
            <el-button
                    class="filter-item"
                    size="mini"
                    style="float: right;padding: 4px 10px"
                    type="primary"
                    icon="el-icon-plus"
                    @click="doAdd">新增
            </el-button>
        </div>

        <div v-if="dictName === ''">
            <div class="my-code">点击字典查看详情</div>
        </div>
        <div v-else>
            <!--表格渲染-->
            <el-table v-loading="pageLoading" :data="pageInfo.records" size="small" style="width: 100%;">
                <el-table-column label="所属字典">
                    <template slot-scope="scope">
                        {{ dictName }}
                    </template>
                </el-table-column>
                <el-table-column prop="name" label="字典名称"/>
                <el-table-column prop="value" label="字典值"/>
                <el-table-column prop="sort" label="排序"/>
                <el-table-column label="操作" width="130px" align="center">
                    <template slot-scope="scope">

                        <el-button type="primary" icon="el-icon-edit" size="mini" @click="doEdit(scope.row)"/>

                        <el-popover
                                :ref="scope.row.id"
                                placement="top"
                                width="180">
                            <p>确定删除本条数据吗？</p>
                            <div style="text-align: right; margin: 0">
                                <el-button size="mini" type="text" @click="$refs[scope.row.id].doClose()">取消</el-button>
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
                    :current-page="pageInfo.current"
                    style="margin-top: 8px;"
                    layout="total, prev, pager, next, sizes"
                    @size-change="sizeChange"
                    @current-change="pageChange"/>
        </div>

        <el-dialog :visible.sync="dialog" :title="isAdd ? '新增字典' : '编辑字典'" append-to-body width="550px" @close="cancel">
            <el-form ref="form" :model="form" :rules="rules" size="small" label-width="100px">
                <el-form-item label="字典名称" prop="name">
                    <el-input v-model="form.name" clearable/>
                </el-form-item>


                <el-form-item label="字典值" prop="value">
                    <el-input v-model="form.value"/>
                </el-form-item>

                <el-form-item label="排序" prop="sort">
                    <el-input v-model.number="form.sort"/>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="text" @click="cancel">取消</el-button>
                <el-button :loading="submitLoading" type="primary" @click="doSubmit">确认</el-button>
            </div>
        </el-dialog>
    </el-card>

</template>

<script>
    import page from '@/mixins/page'
    import {saveDictData, editDictData, delDictData} from '@/api/dict'

    export default {

        mixins: [page],
        created() {
            this.$nextTick(() => {
                this.init();
            })
        },
        data() {
            return {
                dialog: false,
                isAdd: true,
                form: {},
                rules: {
                    name: [{required: true, message: '请输入字典名称', trigger: 'blur'}],
                    value: [{required: true, message: '请输入字典值', trigger: 'blur'}],
                    sort: [{required: true, message: '请输入字典排序', trigger: 'blur'}],
                },
                submitLoading: false,
                delLoading: false,
                dictName: '',
                dictId: ''
            }
        },
        methods: {
            init() {
                this.pageInfo.current = 1;
                this.page();
            },
            pageInit() {
                if (this.isNotEmpty(this.dictId)) {
                    this.pageUrl = '/back/dictData/page'
                    this.pageInfo.params.dictId = this.dictId;
                    return true;
                }
            },
            subDelete(id) {
                this.delLoading = true;
                delDictData(id).then(res => {
                    this.$message({
                        message: '删除成功!',
                        type: 'success'
                    });
                    this.delLoading = false;
                    this.$refs[id].doClose()
                    this.page();
                }).catch(err => {
                    this.delLoading = false
                    this.$refs[id].doClose()
                })
            },
            doEdit(row) {
                this.dialog = true;
                this.isAdd = false;
                this.form.dictId = this.dictId;
                this.form = this.copyObj(row);
            },
            doAdd() {
                this.isAdd = true;
                this.dialog = true;
                this.form.dictId = this.dictId;
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
                            saveDictData(this.form).then(res => {
                                this.$message({
                                    message: '新增成功!',
                                    type: 'success'
                                });
                                this.submitLoading = false;
                                this.cancel();
                                this.init();
                            }).catch(err => {
                                this.submitLoading = false;
                            })
                        } else {
                            //编辑
                            editDictData(this.form).then(res => {
                                this.$message({
                                    message: '编辑成功!',
                                    type: 'success'
                                });
                                this.submitLoading = false;
                                this.cancel();
                                this.init();
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
    .my-code {
        padding: 15px;
        line-height: 20px;
        border-left: 3px solid #ddd;
        color: #333;
        font-family: Courier New;
        font-size: 12px
    }
</style>