<template>
    <div class="app-container">
        <el-row :gutter="20">
            <el-col :span="6">
                <div class="head-container">
                    <el-input v-model="departmentName"
                              clearable
                              placeholder="输入部门名称搜索"
                              prefix-icon="el-icon-search"
                              style="width: 100%;"
                              class="filter-item"/>
                </div>
                <el-alert
                        type="warning"
                        show-icon
                        :closable="false">
                    当前选择编辑：<span class="select-title">{{currentDepartmentName}}</span>
                    <el-button type="primary"
                               size="mini"
                               class="select-clear"
                               v-if="currentDepartmentName!=''"
                               @click="cancelCurrent">
                        取消
                    </el-button>
                </el-alert>
                <el-tree
                        ref="departmentTree"
                        :data="departmentTreeData"
                        :props="defaultProps"
                        :expand-on-click-node="false"
                        show-checkbox
                        :filter-node-method="filterNode"
                        @node-click="handleNodeClick"/>
            </el-col>

            <el-col :span="18">

            </el-col>
        </el-row>
    </div>
</template>

<script>
    import {getDepartmentTree} from '@/api/department'
    import {parseTime} from '@/utils/index'
    import {getDictDataByCode} from '@/api/dict'


    export default {
        components: {},
        data() {
            return {
                height: document.documentElement.clientHeight - 180 + 'px;',
                loading: false,
                delLoading: false,
                downloadLoading: false,
                departmentName: '',
                currentDepartmentName: '',
                departmentTreeData: [],
                defaultProps: {
                    children: 'children',
                    label: 'name'
                }
            }
        },
        created() {
            this.$nextTick(() => {
                this.getDepartmentTreeData();
            })
        },
        mounted: function () {
            const that = this
            window.onresize = function temp() {
                that.height = document.documentElement.clientHeight - 180 + 'px;'
            }

        },
        watch: {
            departmentName(val) {
                this.$refs.departmentTree.filter(val);
            }
        },
        methods: {
            parseTime,
            getDepartmentTreeData() {
                getDepartmentTree().then(res => {
                    this.departmentTreeData = res.data
                })
            },
            filterNode(value, data) {
                if (!value) return true;
                return data.name.indexOf(value) !== -1;
            },
            handleNodeClick(data) {
                this.currentDepartmentName = data.name;
            },
            cancelCurrent() {
                this.currentDepartmentName = '';
            },
        }
    }
</script>

<style scoped>
</style>
