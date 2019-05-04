import util from '@/libs/emums'

export default {
  install (Vue, options) {
    Vue.prototype.$open = util.open
  }
}
