<template>
    <div class="app-container">
        <el-row :gutter="15">
            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">

                <el-card>
                    <el-row type="flex">
                        <el-col :span="8">
                            <el-row class-name="made-child-con-middle" type="flex" align="middle">
                                <img class="avator-img" :src="user.avatar"/>
                            </el-row>
                        </el-col>
                        <el-col :span="16" style="padding-left:6px;">
                            <el-row class-name="made-child-con-middle" type="flex" align="middle">
                                <div>
                                    <b class="card-user-infor-name">{{ user.username }}</b>
                                    <p>欢迎您使用Yao</p>
                                </div>
                            </el-row>
                        </el-col>
                    </el-row>
                    <div class="line-gray"></div>
                    <el-row style="margin-top: 8px">
                        <el-col :span="8">
                            <span>本次登录地点:</span>
                        </el-col>
                        <el-col :span="15" style="margin-left: 5px">
                            <span>{{city}}({{ip}})</span>
                        </el-col>
                    </el-row>
                    <el-row style="margin-top: 8px">
                        <el-col :span="8">
                            <span>天气:</span>
                        </el-col>
                        <el-col :span="15" style="margin-left: 5px"><span>{{weather}}</span>
                        </el-col>
                    </el-row>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script>

    import {mapGetters} from 'vuex'
    import {getIp, getCity, getWeather} from "@/api/common";

    export default {
        name: 'Dashboard',
        components: {},
        data() {
            return {
                ip: '',
                city: '',
                weather: '',
            }
        },
        computed: {
            ...mapGetters([
                'user',
            ]),
        },
        mounted() {
            this.init();
        },
        methods: {
            init() {
                const _this = this;
                getIp().then(res => {
                    _this.ip = res.data;
                })

                getWeather().then(res => {
                    let data = res.data;
                    if (data != null && data != undefined) {
                        let weather = JSON.parse(data);
                        if (weather.retCode = '200') {
                            let result = weather.result
                            let future = result[0].future;
                            _this.weather = future[0].dayTime + " " + future[0].night + " " + future[0].temperature;
                            if (result[0].province !== result[0].city) {
                                _this.city = result[0].province + " " + result[0].city
                            } else {
                                _this.city = result[0].city
                            }
                        }
                    }
                })
            }
        }
    }
</script>

<style>
    .avator-img {
        display: block;
        width: 100px;
        height: 100px;
        border-radius: 50%;
        margin: 0px auto 10px;
    }
</style>
