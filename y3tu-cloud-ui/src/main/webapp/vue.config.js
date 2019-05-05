//基础路径 注意发布之前要先修改这里
//打包使用
const baseUrl = '';

module.exports = {
  publicPath: baseUrl,
  lintOnSave: true,
  devServer: {
    // host: '127.0.0.1',
    // port: 9999,
    // proxy: {
    //     '/y3tu': {
    //         target: 'http://127.0.0.1:8888',  // 请求本地admin
    //         ws: true
    //     },
    //     '/foo': {
    //         target: '<other_url>'
    //     }
    // },
    disableHostCheck: true,
  },
  // 打包时不生成.map文件 避免看到源码
  productionSourceMap: false
}