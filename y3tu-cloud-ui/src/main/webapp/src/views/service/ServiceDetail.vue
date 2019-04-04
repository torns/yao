/** 服务实例详情 */
<template>
    <d2-container>
        <div class="top" slot="header">
            <span class="name">服务名称: {{serviceName}}</span>&nbsp;&nbsp;
            <span class="name">实例ID: {{instanceId}} </span>
        </div>
        <el-tabs v-model="activeName" style="padding: 0 10px;">
            <el-tab-pane label="服务日志" name="logger">
                <service-logger :instanceId="instanceId"></service-logger>
            </el-tab-pane>
            <el-tab-pane label="服务指标" name="metrics">
                <service-metrics :instanceId="instanceId"></service-metrics>
            </el-tab-pane>
            <el-tab-pane label="映射列表" name="mappings">
                <service-mappings :instanceId="instanceId"></service-mappings>
            </el-tab-pane>
            <el-tab-pane label="环境参数" name="envs">
                <service-envs :instanceId="instanceId"></service-envs>
            </el-tab-pane>
            <el-tab-pane label="请求追踪" name="trace">
                <service-trace :instanceId="instanceId"></service-trace>
            </el-tab-pane>
            <el-tab-pane name="heapdump">
                <span slot="label"><el-button type="text" @click="threadDump">线程DUMP</el-button></span>
            </el-tab-pane>
        </el-tabs>
    </d2-container>
</template>

<script>
import ServiceLogger from './components/ServiceLogger'
import ServiceMetrics from './components/ServiceMetrics'
import ServiceMappings from './components/ServiceMappings'
import ServiceEnvs from './components/ServiceEnvs'
import ServiceTrace from './components/ServiceTrace'
import util from '@/libs/util'

export default {
  components: {
    ServiceLogger, ServiceMetrics, ServiceMappings, ServiceEnvs, ServiceTrace
  },
  data () {
    return {
      activeName: 'logger',
      serviceName: '',
      instanceId: '',
      baseUrl: process.env.VUE_APP_BASE_URL
    }
  },
  created () {
    if (this.$route.query) {
      this.serviceName = this.$route.query.serviceName
      this.instanceId = this.$route.query.instanceId
    }
  },
  methods: {
    threadDump () {
      const url = this.baseUrl + '/taroco-admin/api/applications/' + this.instanceId + '/heapdump'
      window.open(url + '?access_token=' + util.getToken())
    }
  }
}
</script>

<style lang="scss" scoped>
    .top {
        a {
            cursor: pointer;
            color: #409EFF;
            &:hover {
                text-decoration: underline;
            }
        }
        .name {
            font-size: 14px;
            color: #333333;
        }
    }
</style>
