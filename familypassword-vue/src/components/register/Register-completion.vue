<template>
  <v-app id="inspire">
    <v-main class="grey lighten-3">
      <v-container>
        <v-alert
            v-if="errorMessageFlag"
            border="right"
            color="blue-grey"
            dark
        >
          {{ errorMessages }}
        </v-alert>
        <v-sheet rounded="lg" min-height="70vh" min-width="70vh" style="margin: 8%">
          <template>
            <v-form v-model="valid">
              <v-container>
                <v-row
                    class="px-16"
                >
                  <v-text-field
                      v-model="name"
                      :rules="nameRules"
                      label="username"
                      required
                  ></v-text-field>
                </v-row>

                <v-row
                    class="px-16"
                >
                  <v-text-field
                      v-model="email"
                      :rules="emailRules"
                      label="E-mail"
                      required
                  ></v-text-field>
                </v-row>
                <v-row class="px-16">
                  <div class="my-2">
                    <v-btn
                        x-large
                        color="dark"
                        dark
                        @click="register"
                    >
                      REGISTER
                    </v-btn>
                  </div>
                </v-row>
              </v-container>
            </v-form>
          </template>
        </v-sheet>
      </v-container>
    </v-main>
  </v-app>
</template>

<script>
import axios from "axios";
import config from "../../config/config";

export default {
  name: "login-home",
  data: () => ({
    valid: false,
    name: '',
    email: '',
    nameRules: [
      v => !!v || 'username este necesar'
    ],
    emailRules: [
      v => !!v || 'E-mail este necesar',
      v => /.+@.+/.test(v) || 'E-mail invalid',
    ],
    errorMessages: "Ceva nu a mers bine!",
    errorMessageFlag: false
  }),
  methods: {
    async register() {
      const response = await axios.post(config.SPRING_BOOT_PORT + "/register", {
        name: this.name,
        email: this.email
      });
      if(!response.data) {
        this.errorMessageFlag = true;
      } else {
        this.$session.set(config.USER, response.data);
        await this.$router.push({name : "register-success"});
      }
    }
  }
}
</script>
