<template>
    <div class="sorry">
        <img :src="sorry">
        <span class="text">抱歉，开发中...</span>
    </div>
</template>

<script>
    import sorry from '@/assets/images/sorry.png';

    export default {
        name: 'developing',
        data() {
            return {sorry: sorry}
        }

    }

</script>

<style scoped>
    .sorry {
        height: 500px;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
    }

    .text {
        font-size: 20px;
        margin: 20px 0;
    }

</style>