<template>
    <el-menu-item v-if="sidebarLogo" :class="{'submenu-title-noDropdown':isCollapse}" index="0"
                  style="pointer-events: none;">
        <!-- 缩小时显示的logo，可以自定义，这里直接使用图标库中的 -->
        <icon v-if="isCollapse" name="home"></icon>
        <!--正常状态下显示的，可以使用本地的logoImg-->
        <div class="logo-con">
            <img :src="logo">
        </div>
    </el-menu-item>
</template>

<script>
    import logo from '@/assets/logo/logo.png'
    import logoMin from '@/assets/logo/logomin.png'
    import {mapState} from 'vuex'

    export default {
        name: 'Logo',
        props: {
            isCollapse: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
                logo: logo,
                logoMin: logoMin
            }
        },
        computed: {
            ...mapState({
                sidebarLogo: state => state.settings.sidebarLogo
            })
        }
    }
</script>

<style scoped>

</style>
