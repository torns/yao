<template>
    <d2-container>
        <template slot="header">
            <el-button size="mini" icon="el-icon-refresh" @click="refresh">刷 新</el-button>
        </template>
        <el-table
                :data="serviceList"
                element-loading-text="拼命加载中..."
                highlight-current-row
                stripe
                style="width: 100%">

            <el-table-column type="expand">
                <template slot-scope="scope">
                    <service-table :serviceList="scope.row.instances"></service-table>
                </template>
            </el-table-column>

            <el-table-column
                    prop="serviceId"
                    label="服务名称"
                    align="left">
            </el-table-column>

            <el-table-column label="实例数量" align="center">
                <template slot-scope="scope">
                    <i class="el-icon-info icon-main"></i>
                    {{scope.row.instances.length}}
                </template>
            </el-table-column>

            <el-table-column label="状态" align="center">
                <template slot-scope="scope">
                    <span v-if="scope.row.up > 0" style="color: green">{{scope.row.up}} UP</span>
                    <span v-if="scope.row.down > 0" style="color: red">{{scope.row.down}} DOWN</span>
                </template>
            </el-table-column>

            <el-table-column label="操作" width="140" align="center">
                <template slot-scope="scope">
                    <el-button type="default" size="mini" @click="turbineMonitor(scope.row.serviceId)">Turbine监控
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
    </d2-container>
</template>

<script>
import ServiceTable from './table/ServiceTable'
import request from '@/plugin/axios'

export default {
  components: {ServiceTable},
  data () {
    return {
      serviceList: []
    }
  },
  computed: {},
  created () {
    this.queryServiceList()
  },
  methods: {
    refresh () {
      this.queryServiceList()
    },
    queryServiceList () {
      const url = '/taroco-admin/api/applications'
      request({
        url: url,
        method: 'get'
      }).then((res) => {
        let data = res.data
        if (data.status === 'SUCCEED') {
          data.result.forEach(app => {
            app.up = 0
            app.down = 0
            app.instances.forEach(instance => {
              if (instance.status === 'UP') {
                app.up++
              }
              if (instance.status === 'DOWN') {
                app.down++
              }
            })
          })
          this.serviceList = data.result
        }
      })
    },
    turbineMonitor (serviceId) {
      let turbinePrefix = process.env.VUE_APP_TURBINE_URL
      let url = turbinePrefix + '/hystrix/monitor?stream=' + encodeURIComponent(turbinePrefix + '/turbine.stream?cluster=' + serviceId.toUpperCase())
      this.$router.push({
        path: '/myiframe/urlPath',
        query: {
          src: url,
          title: 'Turbine监控'
        }
      })
    }
  }
}
</script>
