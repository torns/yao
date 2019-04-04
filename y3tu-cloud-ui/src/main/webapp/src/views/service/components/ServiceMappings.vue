<template>
    <el-table
            :data="mappings" stripe
            tooltip-effect="dark"
            style="width: 100%">
        <el-table-column
                type="index"
                width="50">
        </el-table-column>
        <el-table-column
                label="路劲">
            <template slot-scope="scope">
                <span>{{ scope.row.path }}</span>
            </template>
        </el-table-column>
        <el-table-column
                prop="beanName"
                label="Bean名称">
        </el-table-column>
        <el-table-column
                prop="methodName"
                label="方法名称">
        </el-table-column>
    </el-table>
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
      mappings: []
    }
  },
  mounted () {
    this.queryMappings()
  },
  methods: {
    queryMappings () {
      const url = '/taroco-admin/api/applications/' + this.instanceId + '/mappings'
      request({
        url: url,
        method: 'get'
      })
        .then((res) => {
          let data = res.data
          let rows = []
          for (let i in data) {
            let row = {
              'path': i,
              'beanName': data[i].bean,
              'methodName': data[i].method
            }
            rows.push(row)
          }
          this.mappings = rows
        })
    }
  }
}
</script>

<style scoped>

</style>
