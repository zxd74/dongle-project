<template>
    <div class="header">
        <!-- 内容区 -->
        <h1>{{ msg }}</h1>
    </div>
</template>

<script>
export default {
    name:'Header',
    props: {
        msg: String
    }
}
</script>