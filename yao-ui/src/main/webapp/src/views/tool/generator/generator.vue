<template>
    <div>
        <el-button type="primary" size="mini" @click="to">生成代码</el-button>
        <el-dialog :visible.sync="dialog" title="代码生成配置" append-to-body width="800px">
            <el-table v-loading="loading" :data="data" size="small" style="width: 100%;">
                <el-table-column label="序号" width="80" align="center">
                    <template slot-scope="scope">
                        <div>{{ scope.$index + 1 }}</div>
                    </template>
                </el-table-column>
                <el-table-column prop="name" label="字段名称"></el-table-column>
                <el-table-column prop="typeName" label="字段类型"></el-table-column>
                <el-table-column prop="comment" label="字段标题">
                    <template slot-scope="scope">
                        <el-input v-model="data[scope.$index].comment" class="edit-input"></el-input>
                    </template>
                </el-table-column>
                <el-table-column label="查询方式">
                    <template slot-scope="scope">
                        <el-select v-model="data[scope.$index].columnQuery" class="edit-input" clearable
                                   placeholder="请选择">
                            <el-option
                                    label="模糊查询"
                                    value="1">
                            </el-option>
                            <el-option
                                    label="精确查询"
                                    value="2">
                            </el-option>
                        </el-select>
                    </template>
                </el-table-column>
                <el-table-column align="center" prop="columnShow" label="列表显示">
                    <template slot-scope="scope">
                        <el-tooltip :content="scope.row.columnShow === 'true' ?'显示':'不显示'" placement="top">
                            <el-switch
                                    v-model="data[scope.$index].columnShow"
                                    active-color="#13ce66"
                                    inactive-color="#ff4949"
                                    active-value="true"
                                    inactive-value="false">
                            </el-switch>
                        </el-tooltip>
                    </template>
                </el-table-column>
            </el-table>
            <div slot="footer">
                <el-button type="text" @click="cancel">取消</el-button>
                <el-button :loading="genLoading" type="primary" @click="doSubmit">生成</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>

    import {getTable, build} from '@/api/generator'

    export default {
        name: 'generator',
        props: {
            name: {
                type: String,
                required: true
            }
        },
        data() {
            return {
                dialog: false,
                loading: false,
                genLoading: false,
                data: [],
            }
        },
        methods: {
            to() {
                this.dialog = true;
                this.time = 130;
                this.$nextTick(() => {
                    this.initData();
                })
            },
            initData() {
                const _this = this;
                //初始化数据
                _this.data = [];
                getTable(this.name).then(res => {
                    let columns = res.data;
                    for (let key in columns) {
                        _this.data.push(columns[key])
                    }
                })
            },
            cancel() {
                this.dialog = false
            },
            doSubmit() {
                this.genLoading = true;
                build(this.data, this.name).then(res => {
                    this.$notify({
                        title: '生成成功',
                        type: 'success',
                        duration: 2500
                    });
                    this.dialog = false;
                    this.genLoading = false
                }).catch(err => {
                    this.dialog = false;
                    this.genLoading = false;
                    console.log(err.response.data.message)
                })
            }
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss">
    .edit-input {
        .el-input__inner {
            border: none;
        }
    }
</style>