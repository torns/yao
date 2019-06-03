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
        async initPage() {
            const _this = this;
            if (!await _this.beforePageInit()) {
                return
            }
            return new Promise((resolve, reject) => {

                _this.pageLoading = true

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
        beforePageInit() {
            return true
        },
        pageChange(e) {
            this.pageInfo.current = e - 1
            this.initPage()
        },
        sizeChange(e) {
            this.pageInfo.current = 0
            this.pageInfo.size = e
            this.initPage()
        }
    }
}