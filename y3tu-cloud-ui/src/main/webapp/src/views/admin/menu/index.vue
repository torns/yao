<template>
    <d2-container>
        <!-- header按钮组 -->
        <template slot="header">
            <el-button-group>
                <el-button size="mini" type="primary" v-if="menuManager_btn_add" icon="el-icon-plus"
                           @click="handlerAdd">新 增
                </el-button>
                <el-button size="mini" type="primary" v-if="menuManager_btn_edit" icon="el-icon-edit"
                           @click="handlerEdit">编 辑
                </el-button>
                <el-button size="mini" type="primary" v-if="menuManager_btn_del" icon="el-icon-delete"
                           @click="handleDelete">删 除
                </el-button>
            </el-button-group>
        </template>
        <el-row>
            <el-col :span="8">
                <el-tree
                        class="filter-tree"
                        node-key="id"
                        highlight-current
                        :data="treeData"
                        :default-expanded-keys="aExpandedKeys"
                        :filter-node-method="filterNode"
                        :props="defaultProps"
                        @node-click="getNodeData"
                        @node-expand="nodeExpand"
                        @node-collapse="nodeCollapse"
                >
                </el-tree>
            </el-col>
            <el-col :span="16">
                <el-form :label-position="labelPosition" label-width="80px" :model="form" ref="form">
                    <el-form-item label="父级节点" prop="parentId">
                        <el-input v-model="form.parentId" :disabled="true" placeholder="请输入父级节点"></el-input>
                    </el-form-item>
                    <el-form-item label="节点ID" prop="menuId">
                        <el-input v-model="form.menuId" :disabled="formEdit" placeholder="请输入节点ID"></el-input>
                    </el-form-item>
                    <el-form-item label="标题" prop="name">
                        <el-input v-model="form.name" :disabled="formEdit" placeholder="请输入标题"></el-input>
                    </el-form-item>
                    <el-form-item label="权限标识" prop="permission">
                        <el-input v-model="form.permission" :disabled="formEdit" placeholder="请输入权限标识"></el-input>
                    </el-form-item>
                    <el-form-item label="图标" prop="icon">
                        <!-- <el-input v-model="form.icon" :disabled="formEdit" placeholder="请输入图标"></el-input> -->
                        <d2-icon-select v-model="form.icon" placeholder="请选择图标"></d2-icon-select>
                    </el-form-item>
                    <el-form-item label="资源路径" prop="url">
                        <el-input v-model="form.url" :disabled="formEdit" placeholder="请输入资源路径"></el-input>
                    </el-form-item>
                    <el-form-item label="请求方法" prop="method">
                        <el-select class="filter-item" v-model="form.method" :disabled="formEdit"
                                   placeholder="请输入资源请求类型">
                            <el-option v-for="item in  methodOptions" :key="item" :label="item"
                                       :value="item"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="类型" prop="type">
                        <el-select class="filter-item" v-model="form.type" :disabled="formEdit" placeholder="请输入资源请求类型">
                            <el-option v-for="item in typeOptions" :key="item" :label="item | typeFilter"
                                       :value="item"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="排序" prop="sort">
                        <el-input v-model="form.sort" :disabled="formEdit" placeholder="请输入排序"></el-input>
                    </el-form-item>
                    <el-form-item label="前端组件" prop="component">
                        <el-input v-model="form.component" :disabled="formEdit" placeholder="请输入描述"></el-input>
                    </el-form-item>
                    <el-form-item label="前端地址" prop="component">
                        <el-input v-model="form.path" :disabled="formEdit" placeholder="iframe嵌套地址"></el-input>
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
import {fetchTree, getObj, addObj, delObj, putObj} from '@/api/menu'
import {mapGetters} from 'vuex'

export default {
  name: 'user-menu',
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
      oExpandedKey: {
        // key (from tree id) : expandedOrNot boolean
      },
      oTreeNodeChildren: {
        // id1 : [children] (from tree node id1)
        // id2 : [children] (from tree node id2)
      },
      aExpandedKeys: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      labelPosition: 'right',
      form: {
        permission: undefined,
        name: undefined,
        menuId: undefined,
        parentId: undefined,
        url: undefined,
        icon: undefined,
        sort: undefined,
        component: undefined,
        type: undefined,
        method: undefined,
        path: undefined
      },
      currentId: -1,
      menuManager_btn_add: false,
      menuManager_btn_edit: false,
      menuManager_btn_del: false
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
    this.menuManager_btn_add = this.permissions['sys_menu_add']
    this.menuManager_btn_edit = this.permissions['sys_menu_edit']
    this.menuManager_btn_del = this.permissions['sys_menu_del']
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
      // console.log(value);
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },

    nodeExpand (data) {
      let aChildren = data.children
      if (aChildren.length > 0) {
        this.oExpandedKey[data.id] = true
        this.oTreeNodeChildren[data.id] = aChildren
      }
      this.setExpandedKeys()
    },
    nodeCollapse (data) {
      this.oExpandedKey[data.id] = false
      // 如果有子节点
      this.treeRecursion(this.oTreeNodeChildren[data.id], (oNode) => {
        this.oExpandedKey[oNode.id] = false
      })
      this.setExpandedKeys()
    },
    setExpandedKeys () {
      let oTemp = this.oExpandedKey
      this.aExpandedKeys = []
      for (let sKey in oTemp) {
        if (oTemp[sKey]) {
          this.aExpandedKeys.push(parseInt(sKey))
        }
      }
    },
    treeRecursion (aChildren, fnCallback) {
      if (aChildren) {
        for (let i = 0; i < aChildren.length; ++i) {
          let oNode = aChildren[i]
          fnCallback && fnCallback(oNode)
          this.treeRecursion(oNode.children, fnCallback)
        }
      }
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
      if (this.form.menuId) {
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
        method: undefined,
        path: undefined
      }
    }
  }
}
</script>
