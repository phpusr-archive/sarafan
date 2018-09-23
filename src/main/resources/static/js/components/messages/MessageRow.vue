<template>
    <b-list-group-item>
        <i>({{ message.id }})</i> {{ message.text }}
        <span style="position: absolute; right: 0;">
            <b-button variant="link" @click="edit">Edit</b-button>
            <button type="button" class="close mr-2 mt-1" aria-label="Close" @click="del">
              <span aria-hidden="true">&times;</span>
            </button>
        </span>
    </b-list-group-item>
</template>

<script>
    export default {
        props: ['message', 'editMessage', 'deleteMessage', 'messages'],
        methods: {
            edit () {
                this.editMessage(this.message)
            },
            del() {
                this.deleteMessage(this.message);
            }
        }
    }
</script>

<style>

</style>
