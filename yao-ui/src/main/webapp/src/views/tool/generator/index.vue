<template>
    <div class="app-container">
        <div class="head-container">
            <el-input clearable placeholder="请输入表名" style="width:200px" class="filter-item"
                      @keyup.enter.native="toQuery"></el-input>
            <el-button class="filter-item" size="mini" type="success" icon="el-icon-search" @click="toQuery">搜索
            </el-button>
            <el-button class="filter-item" size="mini" type="warning" icon="el-icon-setting" @click="toConfig">生成器配置
            </el-button>
        </div>

        <el-table v-loading="pageLoading" :data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)"
                  size="small" style="width: 100%;">
            <el-table-column label="序号" width="80" align="center">
                <template slot-scope="scope">
                    <div>{{ scope.$index + 1 }}</div>
                </template>
            </el-table-column>

            <el-table-column :show-overflow-tooltip="true" prop="name" label="表名"></el-table-column>

            <el-table-column label="操作" width="160px" align="center">
                <template slot-scope="scope">
                    <Generator :name="scope.row.name"/>
                </template>
            </el-table-column>

        </el-table>
        <!--分页组件-->
        <el-pagination
                :total="tableData.length"
                style="margin-top: 8px;"
                layout="total, prev, pager, next, sizes"
                :current-page="currentPage"
                :page-size="pageSize"
                @size-change="sizeChange"
                @current-change="pageChange">
        </el-pagination>

        <Form ref="form"></Form>
    </div>
</template>

<script>
    import {getTables,getGeneratorConfig} from '@/api/generator'
    import Generator from "./generator";
    import Form from './form'

    export default {
        components: {Generator,Form},
        data() {
            return {
                tableData: [],
                pageLoading: false,
                currentPage: 1, // 当前页码
                total: 20, // 总条数
                pageSize: 10 // 每页的数据条数
            }

        },
        created() {
            this.$nextTick(() => {
                this.init();
            })
        },
        methods: {
            init() {
                this.toQuery();
            },

            toQuery() {
                getTables().then(res => {
                    this.tableData = res.data;
                })
            },
            toConfig() {
                const _this = this.$refs.form;
                getGeneratorConfig().then(res => {
                    _this.form = res.data;
                    _this.form.cover = _this.form.cover.toString()
                });
                _this.dialog = true
            },
            sizeChange(val) {
                this.currentPage = 1;
                this.pageSize = val;
            },
            pageChange(val) {
                this.currentPage = val;
            }
        }
    }

</script>

<style>

</style>