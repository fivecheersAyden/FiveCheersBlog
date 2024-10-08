<script setup>
import {loadSlim} from "tsparticles-slim";
import {random2} from "@/particles/random2";
import {random3} from "@/particles/random3";

const particlesInit = async engine => {
  await loadSlim(engine);
};
const currentDate = new Date();
const currentDay = currentDate.getDate();
let particlesOption;
switch (currentDay % 2) {
  case 0:
    particlesOption = random2;
    break;
  case 1:
    particlesOption = random3;
    break;
  default:
    particlesOption = random2; // 默认为random1
}
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