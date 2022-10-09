<template>
  <v-app id="inspire">
    <v-app-bar
        app
        color="white"
        flat
        v-if="this.$session.exists() && this.$session.get('user') != null"
    >
      <v-container class="py-0 fill-height">
        <v-avatar
            class="mr-10"
            color="grey darken-1"
            size="32"

        >
          <img
              :src="path"
              alt="John"
          >
        </v-avatar>


        <v-btn
            v-for="link in links"
            :key="link"
            text
            @click="goTo(link)"
        >
          {{ link }}
        </v-btn>

        <v-spacer></v-spacer>

        <v-responsive max-width="260">
          <v-text-field
              dense
              flat
              hide-details
              rounded
              solo-inverted
          ></v-text-field>
        </v-responsive>
      </v-container>
    </v-app-bar>
    <router-view>
    </router-view>
  </v-app>
</template>

<script>
import config from "@/config/config";

export default {
  name: 'App',
  data: () => ({
    links: [
      'Dashboard',
      'SecretGroup',
      'Profile',
      'Updates',
    ],
    linksMap: {
      'Dashboard': 'home',
      'Profile': 'user-details',
      'SecretGroup': 'secret-group'
    },
    img: null,
    path: "",
    user: null
  }),
  methods: {
    goTo(link) {
      this.$router.push({name: this.linksMap[link]}).catch(() => {
        console.log("Nu am putut schimba ruta!")
      });
    },
    getImgPath() {
      console.log(config.SPRING_BOOT_PORT + "/" + this.user?.imageUrl);
      return config.SPRING_BOOT_PORT + "/" + this.user?.imageUrl;
    }
  },
  mounted() {
    this.user = this.$session.get(config.USER);
    this.path = this.getImgPath();
  }
};
</script>
