<template>
    <div>
        <el-row :gutter="10">
            <el-col :span="24">
                <el-collapse accordion>
                    <el-collapse-item class="box-card" v-for="(key, item) in metricsAll" :key="instanceId + '-' + item">
                        <template slot="title">
                            <h3>
                                <i class="el-icon-info"></i> {{item}}
                            </h3>
                        </template>
                        <div v-for="entry in key" :key="entry.name" class="item">
                            <span>{{entry.name}}</span>
                            <span class="value">{{entry.value}}</span>
                        </div>
                    </el-collapse-item>
                </el-collapse>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import request from '@/plugin/axios'

export default {
  props: {
    instanceId: {
      type: String
    }
  },
  data () {
    return {
      metrics: [],
      counter: [],
      gauge: [],
      gc: [],
      heap: [],
      threads: [],
      classes: [],
      metricsAll: {}
    }
  },
  watch: {
    metrics (newValue, oldValue) {
      if (newValue) {
        this.classes = []
        this.counter = []
        this.gauge = []
        this.gc = []
        this.heap = []
        this.threads = []
      }

      // 服务指标分组
      for (const key in newValue) {
        if (newValue.hasOwnProperty(key)) {
          const val = newValue[key]
          if (key.includes('classes')) {
            this.classes.push({
              name: key,
              value: val
            })
          } else if (key.includes('counter')) {
            this.counter.push({
              name: key,
              value: val
            })
          } else if (key.includes('gauge')) {
            this.gauge.push({
              name: key,
              value: val
            })
          } else if (key.includes('gc')) {
            this.gc.push({
              name: key,
              value: val
            })
          } else if (key.includes('heap')) {
            this.heap.push({
              name: key,
              value: val
            })
          } else if (key.includes('threads')) {
            this.threads.push({
              name: key,
              value: val
            })
          }
        }
      }

      this.metricsAll = {
        classes: this.classes,
        counter: this.counter,
        gauge: this.gauge,
        gc: this.gc,
        heap: this.heap,
        threads: this.threads
      }
    }
  },
  methods: {
    queryMetrics () {
      const url = '/taroco-admin/api/applications/' + this.instanceId + '/metrics'
      request({
        url: url,
        method: 'get'
      })
        .then((res) => {
          let data = res.data
          this.metrics = data
        })
    }
  },
  mounted () {
    this.queryMetrics()
  }
}
</script>
<style lang="scss" scoped>

    .box-card {
        margin-bottom: 10px;
    }

    .item {
        font-size: 14px;
        margin-bottom: 5px;
        .value {
            float: right;
            margin-right: 10px;
        }
    }
</style>
