<template>
    <div class="home">
        <div class="background">
            <img src="../assets/ken-theimer-PoE6Q48B-5k-unsplash.jpg" alt="Background Image" />
            <div class="title">欢迎使用工资管理系统</div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'HomeView',
};
</script>

<style>
.home {
    position: relative;
    height: 100vh;
    overflow: hidden;
}

.background {
    position: relative;
    width: 100%;
    height: 100%;
}

.background img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.title {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    font-size: 36px;
    font-weight: bold;
    color: #fff;
    text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
}
</style>
