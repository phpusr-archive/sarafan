<template>
    <iframe allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
            allowfullscreen
            frameborder="0"
            height="315"
            :src="targetSrc"
            width="560">
    </iframe>
</template>

<script>
    export default {
        name: "YouTube",
        props: ['src'],
        computed: {
            targetSrc() {
                const matcher = this.src.match(/.*\/(watch\?v=)?(\w*)/)
                return `https://www.youtube.com/embed/${matcher[2]}`
            }
        }
    }
</script>

<style scoped>

</style>