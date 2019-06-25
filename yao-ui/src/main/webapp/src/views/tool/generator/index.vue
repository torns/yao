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

        <el-table v-loading="pageLoading" :data="tableData.slice((currentPage-1)*pageSize,currentPage*pageSize)" size="small" style="width: 100%;">
            <el-table-column label="序号" width="80" align="center">
                <template slot-scope="scope">
                    <div>{{ scope.$index + 1 }}</div>
                </template>
            </el-table-column>

            <el-table-column :show-overflow-tooltip="true" prop="tableName" label="表名"></el-table-column>

            <el-table-column prop="createTime" label="创建日期">
                <template slot-scope="scope">
                    <span>{{ parseTime(scope.row.createTime) }}</span>
                </template>
            </el-table-column>

            <el-table-column label="操作" width="160px" align="center">
                <template slot-scope="scope">
                    <Generator :name="scope.row.tableName"/>
                </template>
            </el-table-column>

        </el-table>
        <!--分页组件-->
        <el-pagination
                :total="tableData.length"
                style="margin-top: 8px;"
                layout="total, prev, pager, next, sizes"
                current-page="currentPage"
                page-size="pageSize"
                @size-change="sizeChange"
                @current-change="pageChange">
        </el-pagination>

    </div>
</template>

<script>
    import Generator from "./generator";

    export default {
        components: {Generator},
        comments: {Generator},
        data() {
            return {
                tableData:[],
                pageLoading:false,
                currentPage: 1, // 当前页码
                total: 20, // 总条数
                pageSize: 1 // 每页的数据条数
            }

        },
        created() {
            this.$nextTick(() => {
                this.init();
            })
        },
        methods: {
            init(){

            },

            toQuery() {

            },
            toConfig() {

            },
            sizeChange(){
                console.log(`每页 ${val} 条`);
                this.currentPage = 1;
                this.pageSize = val;
            },
            pageChange(){
                console.log(`当前页: ${val}`);
                this.currentPage = val;
            }
        }
    }

</script>

<style>

</style>