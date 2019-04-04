<template>
    <d2-container-iframe :src="src"/>
</template>
<script>
export default {
  name: 'Taroco-Zipkin',
  data () {
    return {
      src: `${process.env.VUE_APP_ZIPKIN_URL}`
    }
  }
}
</script>
