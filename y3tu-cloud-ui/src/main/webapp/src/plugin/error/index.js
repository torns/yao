

export default {
    install(Vue, options) {
        Vue.config.errorHandler = function (err, vm, info) {
            console.log(err)
            Vue.nextTick(() => {

            })
        }
    }
}