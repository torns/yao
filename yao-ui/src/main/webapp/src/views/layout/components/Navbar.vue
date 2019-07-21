<template>
    <div class="navbar">
        <hamburger :toggle-click="toggleSideBar" :is-active="sidebar.opened" class="hamburger-container"/>

        <div>
            <el-menu v-if="topNav.navList!=null&&topNav.navList.length>0" class="hamburger-container" mode="horizontal"
                     :default-active="topNav.currNav" @select="handleSelect">
                <el-menu-item
                        style="border-bottom:none"
                        :index="item.id" v-for="(item, i) in topNav.navList.slice(0, 3)"
                        :key="i"
                        :name="item.name">
                    {{item.name}}
                </el-menu-item>
                <el-submenu name="sub" v-if="topNav.navList.length>3">
                    <template slot="title">更多</template>
                    <el-menu-item
                            v-for="(item, i) in topNav.navList.slice(3, topNav.navList.length)"
                            :key="i"
                            :index="item.id"
                            :name="item.name">
                        {{item.name}}
                    </el-menu-item>
                </el-submenu>
            </el-menu>
        </div>

        <!--<breadcrumb class="breadcrumb-container"/>-->

        <div class="right-menu">
            <template v-if="device!=='mobile'">
                <el-tooltip content="全屏" effect="dark" placement="bottom">
                    <screenfull class="screenfull right-menu-item"/>
                </el-tooltip>
            </template>
            <el-dropdown class="avatar-container right-menu-item" trigger="click">
                <div class="avatar-wrapper">
                    <img :src="user.avatar" class="user-avatar">
                    <i class="el-icon-caret-bottom"/>
                </div>
                <el-dropdown-menu slot="dropdown">
                    <a target="_blank" href="https://github.com/y3tu/y3tu-cloud">
                        <el-dropdown-item>
                            项目文档
                        </el-dropdown-item>
                    </a>
                    <span style="display:block;" @click="show = true">
                        <el-dropdown-item>
                          布局设置
                        </el-dropdown-item>
                    </span>
                    <router-link to="/userCenter">
                        <el-dropdown-item>
                            个人中心
                        </el-dropdown-item>
                    </router-link>
                    <span style="display:block;" @click="refresh">
                        <el-dropdown-item>
                          刷新页面
                        </el-dropdown-item>
                    </span>
                    <el-dropdown-item divided>
                        <span style="display:block;" @click="logout">退出登录</span>
                    </el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
    </div>
</template>

<script>
    import {mapGetters} from 'vuex'
    import Breadcrumb from '@/components/Breadcrumb'
    import Hamburger from '@/components/Hamburger'
    import Screenfull from '@/components/Screenfull'

    export default {
        components: {
            Breadcrumb,
            Hamburger,
            Screenfull
        },
        computed: {
            ...mapGetters([
                'sidebar',
                'user',
                'device',
                'topNav'
            ]),
            show: {
                get() {
                    return this.$store.state.settings.showRightPanel
                },
                set(val) {
                    this.$store.dispatch('changeSetting', {
                        key: 'showRightPanel',
                        value: val
                    })
                }
            }
        },
        methods: {
            toggleSideBar() {
                this.$store.dispatch('ToggleSideBar')
            },
            logout() {
                this.$store.dispatch('FedLogOut').then(() => {
                    location.reload() // 为了重新实例化vue-router对象 避免bug
                })
            },
            refresh() {
                location.reload()
            },
            handleSelect(key, keyPath) {
                this.$store.dispatch('setTopNavCurrent', key)
            }
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .navbar {
        height: 50px;
        line-height: 50px;
        border-radius: 0px !important;

        .hamburger-container {
            line-height: 58px;
            height: 50px;
            float: left;
            padding: 0 10px;
        }

        .breadcrumb-container {
            float: left;
        }

        .errLog-container {
            display: inline-block;
            vertical-align: top;
        }

        .right-menu {
            float: right;
            height: 100%;

            &:focus {
                outline: none;
            }

            .right-menu-item {
                display: inline-block;
                margin: 0 8px;
            }

            .screenfull {
                height: 20px;
            }

            .international {
                vertical-align: top;
            }

            .theme-switch {
                vertical-align: 15px;
            }

            .avatar-container {
                height: 50px;
                margin-right: 30px;

                .avatar-wrapper {
                    margin-top: 5px;
                    position: relative;

                    .user-avatar {
                        cursor: pointer;
                        width: 40px;
                        height: 40px;
                        border-radius: 10px;
                    }

                    .el-icon-caret-bottom {
                        cursor: pointer;
                        position: absolute;
                        right: -20px;
                        top: 25px;
                        font-size: 12px;
                    }
                }
            }
        }
    }

    .el-menu--horizontal > .el-menu-item {
        height: 40px;
    }

    .el-menu-item {
        font-size: 17px;
    }

</style>
