<template>
    <d2-container>
        <!-- header按钮组 -->
        <template slot="header">
            <el-button-group>
                <el-button size="mini" type="primary" v-if="deptManager_btn_add" icon="el-icon-plus"
                           @click="handlerAdd">新 增
                </el-button>
                <el-button size="mini" type="primary" v-if="deptManager_btn_edit" icon="el-icon-edit"
                           @click="handlerEdit">编 辑
                </el-button>
                <el-button size="mini" type="primary" v-if="deptManager_btn_del" icon="el-icon-delete"
                           @click="handleDelete">删 除
                </el-button>
            </el-button-group>
        </template>
        <el-row>
            <el-col :span="8" style='margin-top:15px;'>
                <el-tree
                        class="filter-tree"
                        :data="treeData"
                        node-key="id"
                        highlight-current
                        :props="defaultProps"
                        :filter-node-method="filterNode"
                        @node-click="getNodeData"
                >
                </el-tree>
            </el-col>
            <el-col :span="16" style='margin-top:15px;'>
                <el-form :label-position="labelPosition" label-width="80px" :model="form" ref="form">
                    <el-form-item label="父级节点" prop="parentId">
                        <el-input v-model="form.parentId" :disabled="formEdit" placeholder="请输入父级节点"></el-input>
                    </el-form-item>
                    <el-form-item label="节点编号" prop="parentId" v-if="formEdit">
                        <el-input v-model="form.deptId" :disabled="formEdit" placeholder="节点编号"></el-input>
                    </el-form-item>
                    <el-form-item label="部门名称" prop="name">
                        <el-input v-model="form.name" :disabled="formEdit" placeholder="请输入名称"></el-input>
                    </el-form-item>
                    <el-form-item label="排序" prop="orderNum">
                        <el-input v-model="form.orderNum" :disabled="formEdit" placeholder="请输入排序"></el-input>
                    </el-form-item>
                </el-form>
            </el-col>
        </el-row>
        <!-- footer -->
        <template slot="footer" v-if="formStatus == 'create'">
            <div style="margin:-5px; text-align:center">
                <el-button size="small" @click="onCancel" icon="el-icon-close">取消</el-button>
                <el-button size="small" type="primary" @click="create" icon="el-icon-check">保 存</el-button>
            </div>
        </template>
        <template slot="footer" v-if="formStatus == 'update'">
            <div style="margin:-5px; text-align:center">
                <el-button size="small" @click="onCancel" icon="el-icon-close">取消</el-button>
                <el-button size="small" type="primary" @click="update" icon="el-icon-check">更 新</el-button>
            </div>
        </template>
    </d2-container>
</template>

<script>
import {fetchTree, getObj, addObj, delObj, putObj} from '@/api/dept'
import {mapGetters} from 'vuex'

export default {
  name: 'department',
  data () {
    return {
      list: null,
      total: null,
      formEdit: true,
      formAdd: true,
      formStatus: '',
      showElement: false,
      typeOptions: ['0', '1'],
      methodOptions: ['GET', 'POST', 'PUT', 'DELETE'],
      listQuery: {
        name: undefined
      },
      treeData: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      labelPosition: 'right',
      form: {
        name: undefined,
        orderNum: undefined,
        parentId: undefined,
        deptId: undefined
      },
      currentId: 0,
      deptManager_btn_add: false,
      deptManager_btn_edit: false,
      deptManager_btn_del: false
    }
  },
  filters: {
    typeFilter (type) {
      const typeMap = {
        0: '菜单',
        1: '按钮'
      }
      return typeMap[type]
    }
  },
  created () {
    this.getList()
    this.deptManager_btn_add = this.permissions['sys_dept_add']
    this.deptManager_btn_edit = this.permissions['sys_dept_edit']
    this.deptManager_btn_del = this.permissions['sys_dept_del']
  },
  computed: {
    ...mapGetters([
      'elements',
      'permissions'
    ])
  },
  methods: {
    getList () {
      fetchTree(this.listQuery).then(response => {
        this.treeData = response.data
      })
    },
    filterNode (value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    getNodeData (data) {
      if (!this.formEdit) {
        this.formStatus = 'update'
      }
      getObj(data.id).then(response => {
        this.form = response.data
      })
      this.currentId = data.id
      this.showElement = true
    },
    handlerEdit () {
      if (this.form.deptId) {
        this.formEdit = false
        this.formStatus = 'update'
      }
    },
    handlerAdd () {
      this.resetForm()
      this.formEdit = false
      this.formStatus = 'create'
    },
    handleDelete () {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        delObj(this.currentId).then(() => {
          this.getList()
          this.resetForm()
          this.onCancel()
          this.$notify({
            title: '成功',
            message: '删除成功',
            type: 'success',
            duration: 2000
          })
        })
      })
    },
    update () {
      putObj(this.form).then(() => {
        this.getList()
        this.$notify({
          title: '成功',
          message: '更新成功',
          type: 'success',
          duration: 2000
        })
      })
    },
    create () {
      addObj(this.form).then(() => {
        this.getList()
        this.$notify({
          title: '成功',
          message: '创建成功',
          type: 'success',
          duration: 2000
        })
      })
    },
    onCancel () {
      this.formEdit = true
      this.formStatus = ''
    },
    resetForm () {
      this.form = {
        permission: undefined,
        name: undefined,
        menuId: undefined,
        parentId: this.currentId,
        url: undefined,
        icon: undefined,
        sort: undefined,
        component: undefined,
        type: undefined,
        method: undefined
      }
    }
  }
}
</script>
