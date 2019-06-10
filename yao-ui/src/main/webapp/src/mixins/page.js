/**
 * 通用分页查询的实现
 */
import {initPageData} from '@/api/page'

export default {
    data() {
        return {
            pageUrl: '',//数据请求地址
            pageLoading: false,//分页表格loading
            pageInfo: {
                records: [],//表格中的数据
                total: 0,//数据总数
                current: 1,//当前页数
                size: 10,//默认每页数量
                ascs: [],//升序字段
                descs: [],//降序字段
                params: {},//请求筛选参数
            },
            time: 100
        }
    },
    methods: {
        /**
         * 后台查询分页数据
         */
        async page() {
            const _this = this;
            if (!await _this.pageInit()) {
                return
            }
            return new Promise((resolve, reject) => {

                _this.pageLoading = true

                //剔除无效参数
                Object.keys(_this.pageInfo.params).forEach(function (key) {
                    if (_this.pageInfo.params[key] == null || _this.pageInfo.params[key] === '') {
                        delete _this.pageInfo.params[key];
                    }
                });

                initPageData(_this.pageUrl, _this.pageInfo).then(res => {
                    _this.pageInfo = res.data;
                    setTimeout(() => {
                        _this.pageLoading = false
                    }, _this.time)
                    resolve(res)
                }).catch(err => {
                    _this.pageLoading = false;
                    reject(err)
                })
            })
        },
        /**
         * 查询前的操作，比如说对查询参数赋值或特殊处理
         *
         */
        pageInit() {
            return true
        },
        pageChange(e) {
            this.pageInfo.current = e
            this.page()
        },
        sizeChange(e) {
            this.pageInfo.current = 0
            this.pageInfo.size = e
            this.page()
        }
    }
}