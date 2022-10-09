<template>
  <v-main class="grey lighten-3">
    <Logged/>
    <v-container>
      <v-sheet rounded="lg" min-height="70vh" min-width="70vh">
        <v-container>
          <v-card
              class="mx-auto"
              max-width="434"
              tile
          >
            <v-img
                height="100%"
                :src="path"
            >

            </v-img>
            <v-card-title>
              {{ user.name }}
            </v-card-title>
            <v-card-subtitle>
              {{ user.officialName }}
            </v-card-subtitle>
            <v-card-actions>
              <input
                  @change="upload"
                  type="file"
                  accept=".jpg, .jpeg, .png"
              />
              <v-btn
                  @click="logout"
              >
                Logged-out
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-container>
      </v-sheet>
    </v-container>
  </v-main>
</template>

<script>
import config from "@/config/config";
import axios from "axios";
import Logged from "@/components/isLoggedIn/Logged";

export default {
  name: "UserDetails", components: {
    Logged
  },
  data: () => ({
    user: null,
    path: ""
  })
  , methods: {
    upload(event) {
      this.image = event.target.files[0];
      const formData = new FormData();
      formData.append('image', this.image);
      const user = this.$session.get(config.USER);
      const url = config.SPRING_BOOT_PORT + "/user/" + user.id + "/upload-image";
      axios.post(url, formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      }).then(response => {
        console.log(response.data);
        this.user.imageUrl = response.data;
        this.$session.set(config.USER, this.user);
        this.path = this.getImgPath();
      });
    },
    logout() {
      this.$session.clear();
      this.$router.push({name: 'login'});
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
}
</script>

<style scoped>
.custom-file-upload {
  border: 1px solid #ccc;
  display: inline-block;
  padding: 6px 12px;
  cursor: pointer;
}
</style>