<template>
    <div class="app-container">
        <div class="head-container">
            <!-- 搜索 -->
            <el-input v-model="query.value" clearable placeholder="输入搜索内容" style="width: 200px;" class="filter-item"
                      @keyup.enter.native="toQuery">
            </el-input>
            <el-select v-model="query.type" clearable placeholder="类型" class="filter-item" style="width: 130px">
                <el-option v-for="item in queryTypeOptions" :key="item.key" :label="item.display_name"
                           :value="item.key">
                </el-option>
            </el-select>
            <el-button class="filter-item" size="mini" type="success" icon="el-icon-search" @click="toQuery">搜索
            </el-button>
            <!-- 新增 -->
            <div style="display: inline-block;margin: 0px 2px;">
                <el-button
                        v-permission="['dataSource:add']"
                        class="filter-item"
                        size="mini"
                        type="primary"
                        icon="el-icon-plus"
                        @click="add">新增
                </el-button>
            </div>
        </div>

        <!--表单组件-->
        <eForm ref="form" :is-add="isAdd"/>

        <!--表格渲染-->
        <el-table v-loading="pageInfo.loading" :data="pageInfo.records" size="small" style="width: 100%;">
            <el-table-column prop="id" label="数据源ID"></el-table-column>
            <el-table-column prop="name" label="数据源名称"></el-table-column>
            <el-table-column prop="type" label="数据源类型 mysql oracle"></el-table-column>
            <el-table-column prop="jdbcUrl" label="数据源连接字符串(JDBC)"></el-table-column>
            <el-table-column prop="user" label="数据源登录用户名"></el-table-column>
            <el-table-column prop="comment" label="说明备注"></el-table-column>
            <el-table-column prop="options" label="数据源配置选项(JSON格式）"></el-table-column>
            <el-table-column prop="createTime" label="记录创建时间">
                <template slot-scope="scope">
                    <span>{{ parseTime(scope.row.createTime) }}</span>
                </template>
            </el-table-column>
            <el-table-column prop="updateTime" label="记录修改时间">
                <template slot-scope="scope">
                    <span>{{ parseTime(scope.row.updateTime) }}</span>
                </template>
            </el-table-column>
            <el-table-column prop="createUser" label="创建人"></el-table-column>
            <el-table-column prop="updateUser" label="更新人"></el-table-column>
            <el-table-column label="操作" width="150px" align="center">
                <template slot-scope="scope">
                    <el-button v-permission="['dataSource:edit']" size="mini" type="primary" icon="el-icon-edit"
                               @click="edit(scope.row)"/>
                    <el-popover
                            v-permission="['dataSource:delete']"
                            :ref="scope.row.id"
                            placement="top"
                            width="180">
                        <p>确定删除本条数据吗？</p>
                        <div style="text-align: right; margin: 0">
                            <el-button size="mini" type="text" @click='cancelDelete(scope.row.id)'>
                                取消
                            </el-button>
                            <el-button :loading="delLoading" type="primary" size="mini"
                                       @click="subDelete(scope.row.id)">
                                确定
                            </el-button>
                        </div>
                        <el-button slot="reference" type="danger" icon="el-icon-delete" size="mini"></el-button>
                    </el-popover>
                </template>
            </el-table-column>
        </el-table>
        <!--分页组件-->
        <el-pagination
                :total="pageInfo.total"
                style="margin-top: 8px;"
                :current-page="pageInfo.current"
                layout="total, prev, pager, next, sizes"
                @size-change="sizeChange"
                @current-change="pageChange">
        </el-pagination>
    </div>
</template>

<script>

    import page from '@/mixins/page'
    import {del} from '@/api/dataSource'
    import {parseTime} from '@/utils/index'
    import eForm from './form'

    export default {
        components: {eForm},
        mixins: [page],
        data() {
            return {
                delLoading: false,
                query: {value: '', type: ''},
                isAdd: false,
                queryTypeOptions: [
                    {key: 'id', display_name: '数据源ID'}, {key: 'name', display_name: '数据源名称'}, {
                        key: 'type',
                        display_name: '数据源类型 mysql oracle'
                    }, {key: 'driverClass', display_name: '数据源驱动类'}, {
                        key: 'jdbcUrl',
                        display_name: '数据源连接字符串(JDBC)'
                    }, {key: 'user', display_name: '数据源登录用户名'}, {
                        key: 'password',
                        display_name: '数据源登录密码'
                    }, {key: 'queryClass', display_name: '获取报表引擎查询器类名'}, {
                        key: 'poolClass',
                        display_name: '报表引擎查询器使用的数据源连接池类名'
                    }, {key: 'comment', display_name: '说明备注'}, {
                        key: 'options',
                        display_name: '数据源配置选项(JSON格式）'
                    }, {key: 'createTime', display_name: '记录创建时间'}, {
                        key: 'updateTime',
                        display_name: '记录修改时间'
                    }, {key: 'createUser', display_name: '创建人'}, {key: 'updateUser', display_name: '更新人'}]
            }
        },
        created() {
            this.$nextTick(() => {
                this.init()
            })
        },
        methods: {
            parseTime,
            init() {
                this.pageInfo.current = 1;
                this.page();
            },
            pageInit() {
                this.pageUrl = 'back/report/dataSource/page'
                const query = this.query
                const type = query.type
                const value = query.value
                if (type && value) {
                    this.pageInfo.params[type] = value
                }
                return true;
            },
            toQuery() {
                this.init();
            },
            cancelDelete(id) {
                let refs = this.$refs;
                refs[id].doClose()
            },
            subDelete(id) {
                this.delLoading = true
                del(id).then(res => {
                    this.delLoading = false
                    let refs = this.$refs;
                    refs[id].doClose()
                    this.init()
                    this.$notify({
                        title: '删除成功',
                        type: 'success',
                        duration: 2500
                    })
                }).catch(err => {
                    this.delLoading = false
                    let refs = this.$refs;
                    refs[id].doClose()
                    console.log(err.response.data.message)
                })
            },

            add() {
                this.isAdd = true
                this.$refs.form.dialog = true
            },
            edit(row) {
                this.isAdd = false
                const _form = this.$refs.form;
                _form.form = this.copyObj(row);
                _form.dialog = true
            }
        }
    }
</script>

<style scoped>

</style>
