<template>
    <el-table
            :data="serviceList"
            tooltip-effect="dark"
            stripe
            style="width: 100%">
        <el-table-column
                type="index"
                width="50">
        </el-table-column>
        <el-table-column
                label="状态"
                width="80" align="center">
            <template slot-scope="scope">
                <el-tag
                        :type="scope.row.status === 'UP' ? 'success' : 'danger'"
                        close-transition>{{scope.row.status}}
                </el-tag>
            </template>
        </el-table-column>
        <el-table-column
                prop="instanceId"
                label="实例id"
                width="240">
        </el-table-column>
        <el-table-column
                prop="metadata.weight"
                label="权重"
                width="60">
        </el-table-column>
        <el-table-column
                prop="ipAddr"
                label="ip地址"
                width="120">
        </el-table-column>
        <el-table-column
                prop="port"
                label="端口"
                width="60">
        </el-table-column>
        <el-table-column
                label="注册时间"
                prop="leaseInfo.registrationTime"
                width="180">
        </el-table-column>
        <el-table-column
                label="上线时间"
                prop="leaseInfo.serviceUpTime"
                width="180">
        </el-table-column>
        <el-table-column
                prop="metadata.labelOr"
                label="标签.或">
        </el-table-column>
        <el-table-column
                prop="metadata.labelAnd"
                label="标签.且">
        </el-table-column>
        <el-table-column label="操作">
            <template slot-scope="scope">
                <el-dropdown @command="handleOperate" v-if="scope.row.status === 'UP'">
          <span class="el-dropdown-link">
            操作<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item :command="['a', scope.row]">详情</el-dropdown-item>
                        <el-dropdown-item v-if="permissions['taroco_admin_set_weight']" :command="['b', scope.row]">
                            设置权重
                        </el-dropdown-item>
                        <el-dropdown-item :command="['c', scope.row]">Turbine监控</el-dropdown-item>
                        <el-dropdown-item :command="['d', scope.row]" divided>标签.或</el-dropdown-item>
                        <el-dropdown-item :command="['e', scope.row]">标签.且</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
                <el-button v-if="scope.row.status === 'DOWN'" type="text" size="mini" icon="el-icon-delete"
                           @click="deleteInstance(scope.row.instanceId)">删除
                </el-button>
            </template>
        </el-table-column>
    </el-table>
</template>

<script>
import request from '@/plugin/axios'
import {mapGetters} from 'vuex'

export default {
  data () {
    return {}
  },
  props: {
    serviceList: {
      type: Array
    }
  },
  computed: {
    ...mapGetters(['permissions'])
  },
  methods: {
    handleOperate (command) {
      let row = command[1]
      switch (command[0]) {
        case 'a':
          // 详情
          this.$router.push({
            path: '/service/info',
            query: {
              serviceName: row.appName,
              instanceId: row.instanceId
            }
          })
          break
        case 'b':
          // 设置权重
          this.$prompt('权重0~100,设置后会过一段时间才能生效,这取决于服务发现、心跳设置的频率。', '设置权重', {
            closeOnClickModal: false,
            closeOnPressEscape: false,
            inputPattern: /^100$|^(\d|[1-9]\d)$/,
            inputErrorMessage: '请输入0~100的正整数'
          }).then(({value}) => {
            let weight = value
            let url = '/taroco-registry/eureka/apps/' + row.appName + '/' + row.instanceId + '/metadata?weight=' + weight
            request({
              url: url,
              method: 'put'
            })
              .then((data) => {
                this.$message({
                  message: '设置成功，请稍候刷新列表！',
                  type: 'success'
                })
              })
          })
          break
        case 'c':
          // Turbine监控
          let turbinePrefix = process.env.VUE_APP_TURBINE_URL
          let url = turbinePrefix + '/hystrix/monitor?stream=' + encodeURIComponent(row.homePageUrl + 'actuator/hystrix.stream')
          this.$router.push({
            path: '/myiframe/urlPath',
            query: {
              src: url,
              title: 'Turbine监控'
            }
          })
          break
        case 'd':
          // 标签.或
          this.$prompt('多个标签以\'，\'号隔开,设置后会过一段时间才能生效,这取决于服务发现、心跳设置的频率。', '标签.或', {
            closeOnClickModal: false,
            closeOnPressEscape: false,
            inputPattern: /\S/,
            inputErrorMessage: '不能为空'
          }).then(({value}) => {
            let url = '/taroco-registry/eureka/apps/' + row.appName + '/' + row.instanceId + '/metadata?labelOr=' + value
            request({
              url: url,
              method: 'put'
            })
              .then((data) => {
                this.$message({
                  message: '设置成功，请稍候刷新列表！',
                  type: 'success'
                })
              })
          })
          break
        case 'e':
          // 标签.且
          this.$prompt('多个标签以\'，\'号隔开,设置后会过一段时间才能生效,这取决于服务发现、心跳设置的频率。', '标签.且', {
            closeOnClickModal: false,
            closeOnPressEscape: false,
            inputPattern: /\S/,
            inputErrorMessage: '不能为空'
          }).then(({value}) => {
            let url = '/taroco-registry/eureka/apps/' + row.appName + '/' + row.instanceId + '/metadata?labelAnd=' + value
            request({
              url: url,
              method: 'put'
            })
              .then((data) => {
                this.$message({
                  message: '设置成功，请稍候刷新列表！',
                  type: 'success'
                })
              })
          })
          break
        default:
          break
      }
    },
    deleteInstance (instanceId) {
      this.$confirm('是否确认移除实例: ' + instanceId + '?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const url = '/taroco-admin/api/applications/' + instanceId
        request({
          url: url,
          method: 'delete'
        })
          .then((data) => {
            if (data.status === 'SUCCEED') {
              this.$message({
                message: '恭喜你，删除成功！',
                type: 'success'
              })
            }
          })
      }).catch(() => {
      })
    }
  }
}
</script>
<style lang="scss" scoped>
    .el-dropdown-link {
        cursor: pointer;
        color: #409eff;
    }

    .btn-delete {
        cursor: pointer;
        color: #F56C6C;
        font-size: 14px;
        margin-left: 5px;
    }
</style>
