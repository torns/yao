<template>
    <div>
        <icon :name="icon"></icon> <span style="margin-left: 15px">{{ title }}</span>
    </div>
</template>
<script>
    export default {
        name: 'MenuItem',
        props: {
            icon: {
                type: String,
                default: ''
            },
            title: {
                type: String,
                default: ''
            }
        }
    }
</script>
