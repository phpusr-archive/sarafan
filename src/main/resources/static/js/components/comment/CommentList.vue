<template>
    <v-list two-line>
        <v-subheader>Comments</v-subheader>
        <template v-for="(item, index) in comments">
            <v-divider v-if="index > 0" />
            <comment-item :comment="item" />
        </template>

        <comment-form :message-id="messageId" />
    </v-list>
</template>

<script>
    import CommentItem from "./CommentItem.vue"
    import CommentForm from "./CommentForm.vue"

    export default {
        name: "CommentList",
        components: {CommentForm, CommentItem},
        props: ["comments", "messageId"]
    }
</script>

<style scoped>

</style>