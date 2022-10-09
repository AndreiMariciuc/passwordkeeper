<template>
  <p></p>
</template>
<script>
import config from "@/config/config";

export default {
  name: "logged-valid",
  mounted() {
    if (!this.$session.exists() || !this.$session.get(config.USER)) {
      this.$router.push({name: "login"});
    }
  }
}
</script>
