<template>
    <div>
        <el-row>
            <el-col>
                <el-card>
                    <Divider style="margin-top:-10px;margin-bottom:0px;"/>
                    <Row>
                        <div v-loading="loading" style="position:relative;">
                            <iframe
                                    id="iframe"
                                    :src="go"
                                    frameborder="0"
                                    width="100%"
                                    :height="height"
                                    scrolling="auto">
                            </iframe>
                        </div>
                    </Row>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    export default {
        name: "iframe",
        data() {
            return {
                loading: false,
                go: "",
                url: "",
                html: "",
                height: "525px"
            };
        },
        computed: {},
        methods: {
            initUrl() {
                let url = this.$route.meta.url;
                if (url !== null && url !== undefined) {
                    this.url = url;
                    this.go = url;
                    // window.open(this.go);
                    this.loading = true;
                    let that = this;
                    // 判断iframe是否加载完毕
                    let iframe = document.getElementById("iframe");
                    if (iframe.attachEvent) {
                        iframe.attachEvent("onload", function () {
                            //iframe加载完成后你需要进行的操作
                            that.loading = false;
                        });
                    } else {
                        iframe.onload = function () {
                            //iframe加载完成后你需要进行的操作
                            that.loading = false;
                        };
                    }
                }
            },
            handleGo() {
                let url = this.url;
                this.go = this.url;
            },
            handleOpen() {
                window.open(this.url);
            }
        },
        watch: {
            $route(to, from) {
                this.initUrl();
            }
        },
        mounted() {
            // 计算高度
            setTimeout(() => {
                this.loading = false
            }, 230);
            let height = document.documentElement.clientHeight;
            this.height = Number(height - 217) + "px";
            this.initUrl();
        }
    };
</script>

<style>
</style>
