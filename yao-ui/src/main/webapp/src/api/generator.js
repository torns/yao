import request from '@/utils/request'

/**
 * 获取数据源中的所有表信息
 * @param data
 */
export function getTables(data) {
    return request({
        url: 'back/generator/getTables',
        data,
        method: 'post'
    })
}

/**
 * 获取表的字段
 * @param tableName
 */
export function getTable(tableName) {
    return request({
        url: `back/generator/getTable/${tableName}`,
        method: 'get'
    })
}

/**
 * 获取代码生成配置
 */
export function getGeneratorConfig() {
    return request({
        url: `back/generator/getGeneratorConfig`,
        method: 'get'
    })
}

/**
 * 更新代码生成配置
 */
export function updateGeneratorConfig(data) {
    return request({
        url: `back/generator/updateGeneratorConfig`,
        data,
        method: 'post'
    })
}

/**
 * 代码生成
 * @param data
 * @param tableName
 */
export function build(data, tableName) {
    return request({
        url: 'back/generator/build?tableName=' + tableName,
        data,
        method: 'post',
        responseType: 'blob'
    }).then((response) => {
        //处理返回的文件流
        let blob = new Blob([response], {type: 'application/zip'})
        let filename = tableName + '.zip'
        if (window.navigator.msSaveOrOpenBlob) {// 兼容IE10
            navigator.msSaveBlob(blob, filename);
        } else {// 其他非IE内核支持H5的浏览器
            let url = window.URL.createObjectURL(blob);
            let link = document.createElement('a');
            link.style.display = 'none';
            link.href = url;
            link.setAttribute('download', filename);
            document.body.appendChild(link);
            link.click()
        }
    })
}