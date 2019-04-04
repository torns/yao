<template>
    <div>
        <el-form :model="search">
            <el-row :gutter="10">
                <el-col :span="6">
                    <el-input placeholder="请输入要查询的path路劲" v-model="search.path" class="input-with-select" clearable>
                        <el-button slot="append" icon="el-icon-search" @click="searchRequest"></el-button>
                    </el-input>
                </el-col>
                <el-col :span="2" :offset="16">
                    <div style="text-align: right">
                        <el-button icon="el-icon-refresh" @click="query"></el-button>
                    </div>
                </el-col>
            </el-row>
        </el-form>
        <el-table
                :data="traces" stripe
                tooltip-effect="dark"
                style="width: 100%">
            <el-table-column
                    type="index"
                    width="50">
            </el-table-column>
            <el-table-column
                    max-width="200"
                    show-overflow-tooltip
                    label="请求路劲">
                <template slot-scope="scope">
                    <span>{{ scope.row.info.path }}</span>
                </template>
            </el-table-column>
            <el-table-column
                    width="80"
                    label="请求方法">
                <template slot-scope="scope">
                    <span>{{ scope.row.info.method }}</span>
                </template>
            </el-table-column>
            <el-table-column
                    width="110"
                    label="花费时间(ms)">
                <template slot-scope="scope">
                    <span>{{ scope.row.info.timeTaken }}</span>
                </template>
            </el-table-column>
            <el-table-column
                    label="请求时间">
                <template slot-scope="scope">
                    <span>{{scope.row.timestamp | parseTime('{y}-{m}-{d} {h}:{i}')}}</span>
                </template>
            </el-table-column>
            <el-table-column
                    max-width="240"
                    label="Request Headers"
                    show-overflow-tooltip>
                <template slot-scope="scope">
                    <span>{{ scope.row.info.headers.request }}</span>
                </template>
            </el-table-column>
            <el-table-column
                    max-width="220"
                    label="Response Headers"
                    show-overflow-tooltip>
                <template slot-scope="scope">
                    <span>{{ scope.row.info.headers.response }}</span>
                </template>
            </el-table-column>
        </el-table>
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
      traces: [],
      filterRows: [],
      search: {
        path: ''
      }
    }
  },
  created () {
    this.query()
  },
  methods: {
    searchRequest () {
      if (this.search.path.trim() === '') {
        this.query()
        return
      }
      this.traces = this.traces.filter((item) => {
        return item.info.path.indexOf(this.search.path) > 0
      })
    },
    query () {
      const url = '/taroco-admin/api/applications/' + this.instanceId + '/httptrace'
      request({
        url: url,
        method: 'get'
      })
        .then((res) => {
          this.traces = res.data
          this.search.path = ''
        })
    }
  }
}
</script>

<style scoped>

</style>
