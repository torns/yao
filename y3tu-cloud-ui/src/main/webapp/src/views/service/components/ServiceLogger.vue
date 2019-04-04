<template>
    <div>
        <el-form :model="search">
            <el-row>
                <el-col :span="12">
                    <el-input placeholder="请输入要查询的名称" v-model="search.name" class="input-with-select" clearable>
                        <el-button slot="append" icon="el-icon-search" @click="searchNames"></el-button>
                    </el-input>
                </el-col>
            </el-row>
        </el-form>
        <el-table
                :data="loggers"
                tooltip-effect="dark"
                style="width: 100%"
                stripe>
            <el-table-column
                    type="index"
                    width="50">
            </el-table-column>
            <el-table-column
                    prop="name"
                    label="name">
            </el-table-column>
            <el-table-column
                    label="操作"
                    width="460">
                <template slot-scope="scope">
                    <el-button-group>
                        <el-button v-if="scope.row.value == item" type="primary" v-for="(item, index) in levels"
                                   :key="index" size="small">{{item}}
                        </el-button>
                        <el-button v-if="scope.row.value != item" v-for="(item, index) in levels" :key="index"
                                   size="small" @click="setLevel(scope.row.name, item)">{{item}}
                        </el-button>
                    </el-button-group>
                </template>
            </el-table-column>
            <div slot="append" style="text-align: center;" v-if="hasMore">
                <el-button icon="el-icon-refresh" type="text" @click="showMore">展开更多...</el-button>
            </div>
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
      hasMore: true,
      search: {
        name: ''
      },
      filterRows: [],
      loggers: [],
      loggersAll: [],
      loggersTotal: 0,
      level: '',
      levels: []
    }
  },
  methods: {
    query () {
      const url = '/taroco-admin/api/applications/' + this.instanceId + '/loggers'
      request({
        url: url,
        method: 'get'
      })
        .then((res) => {
          let data = res.data
          let rows = []
          this.loggersTotal = 0
          this.hasMore = true
          for (var i in data.loggers) {
            var level = data.loggers[i].effectiveLevel
            rows[this.loggersTotal] = {
              'name': i,
              'value': level,
              'group': level
            }
            this.loggersTotal++
          }
          this.loggersAll = rows
          this.loggers = rows.concat().splice(0, 40)
          this.levels = data.levels
        })
    },
    showMore () {
      let mores
      if (this.filterRows.length > 0) {
        mores = this.filterRows.concat()
      } else {
        mores = this.loggersAll.concat()
      }
      if (this.loggers.length + 40 < mores.length) {
        this.loggers = mores.splice(0, this.loggers.length + 40)
      } else {
        this.hasMore = false
        this.loggers = mores
      }
    },
    setLevel (name, value) {
      let url = '/taroco-admin/api/applications/' + this.instanceId + '/loggers/' + name
      request({
        url: url,
        method: 'post',
        data: {configuredLevel: value}
      })
        .then((data) => {
          this.$message({
            message: '设置成功！',
            type: 'success'
          })
          this.query()
        })
    },
    searchNames () {
      if (this.search.name.trim() === '') {
        this.filterRows = []
        this.query()
        return
      }
      this.filterRows = this.loggersAll.filter((item) => {
        return item.name.startsWith(this.search.name)
      })
      if (this.filterRows.length > 40) {
        this.hasMore = true
        this.loggers = this.filterRows.concat().splice(0, 40)
      } else {
        this.hasMore = false
        this.loggers = this.filterRows
      }
    }
  },
  mounted () {
    this.query()
  }
}
</script>

<style lang="scss" scoped>

</style>
