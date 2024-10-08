<script setup>
import {loadSlim} from "tsparticles-slim";
import {random4} from "@/particles/random4";

const particlesInit = async engine => {
  await loadSlim(engine);
};
let particlesOption;
particlesOption = random4;
</script>

<template>
  <div>
    <vue-particles
        id="tsparticles"
        :particlesInit="particlesInit"
        :options="particlesOption"
    />
  </div>
</template>

<style scoped>

</style>