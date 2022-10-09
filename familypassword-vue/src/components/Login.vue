<template>
  <v-main class="grey lighten-3">
    <v-container>
      <v-sheet rounded="lg" min-height="70vh" min-width="70vh" style="margin: 8%">
        <template>
          <v-form v-model="valid">
            <v-container>

              <v-alert
                  v-if="errorMessageFlag"
                  border="right"
                  color="blue-grey"
                  dark
              >
                {{ errorMessages }}
              </v-alert>
              <v-row
                  class="px-16"
              >
                <v-text-field
                    v-model="username"
                    :rules="nameRules"
                    label="username"
                    required
                ></v-text-field>
              </v-row>

              <v-row
                  class="px-16"
              >
                <v-text-field
                    v-model="token"
                    :rules="tokenRules"
                    label="token"
                    required
                ></v-text-field>
              </v-row>
              <v-row class="px-16">
                <div class="my-2">
                  <v-btn
                      x-large
                      color="dark"
                      dark
                      @click="login"
                  >
                    LOGIN
                  </v-btn>
                </div>
              </v-row>
            </v-container>
          </v-form>
        </template>
      </v-sheet>
    </v-container>
  </v-main>
</template>

<script>
import axios from "axios";
import config from "../config/config";

export default {
  name: "login-home",
  data: () => ({
    valid: false,
    username: '',
    token: '',
    nameRules: [
      v => !!v || 'username este necesar'
    ],
    tokenRules: [
      v => !!v || 'Token este necesar'
    ],
    errorMessages: "Mai incearca! Nu am gasit user sau token! Foloseste numele oficial!",
    errorMessageFlag: false
  }),
  methods: {
    login() {
      axios.post(config.SPRING_BOOT_PORT + "/user", {
        officialName: this.username,
        token: this.token
      }).then(response => {
        if (!response.data) {
          this.errorMessageFlag = true;
          console.log("Nu am gasit userul!");
        } else {
          this.$session.start();
          this.$session.set(config.USER, response.data);
          console.log(response.data);
          this.$router.push({name: "home"});
        }
      });
    }
  }
}
</script>
