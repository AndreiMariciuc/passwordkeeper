<template>
  <v-container>
    <v-stepper
        v-model="e6"
        vertical
        v-if="this.$session.exists()"
    >
      <v-stepper-step
          :complete="e6 > 1"
          step="1"
      >
        Bun venit in aplicatie
        <small>Impartasim sercuritate</small>
      </v-stepper-step>

      <v-stepper-content step="1">
        <v-card
            color="grey lighten-1"
            class="mb-12"
            height="200px"
        >Ceva mesaj de bun venit!!</v-card>
        <v-btn
            color="primary"
            @click="e6 = 2"
        >
          Continua
        </v-btn>
      </v-stepper-content>

      <v-stepper-step
          :complete="e6 > 2"
          step="2"
      >
        Numele oficial!
      </v-stepper-step>

      <v-stepper-content step="2">
        <v-card
            color="grey lighten-1"
            class="mb-12"
            height="200px"
        >
          Nu uita sa te loghezi cu numele oficial! <br>
          Numele tau oficial este: {{ user.officialName }}
        </v-card>
        <v-btn
            color="primary"
            @click="e6 = 3"
        >
          Continua
        </v-btn>
      </v-stepper-content>

      <v-stepper-step
          :complete="e6 > 3"
          step="3"
      >
        Foarte important este si tokenul!
        <br>
        Asigura-te ca il salvezi!
      </v-stepper-step>

      <v-stepper-content step="3">
        <v-card
            color="grey lighten-1"
            class="mb-12"
            height="200px"
        >
          Tokenul tau de logare este: {{ user.token }}
        </v-card>
        <v-btn
            color="primary"
            @click="e6 = 4"
        >
          Continua
        </v-btn>
      </v-stepper-content>

      <v-stepper-step step="4">
        Bucura-te de securitate!
      </v-stepper-step>
      <v-stepper-content step="4">
        <v-card
            color="grey lighten-1"
            class="mb-12"
            height="200px"
        >Aici o sa tinem cont de fiecare opinie. Vei putea securiza si imparti parole blablabla :)
        </v-card>
        <v-btn
            color="primary"
            @click="toDashboard"
        >
          Continua spre dashboard
        </v-btn>
      </v-stepper-content>
    </v-stepper>
    <v-alert
        v-else
        border="right"
        color="blue-grey"
        dark
    >
      NU TE-AI LOGAT!
    </v-alert>
  </v-container>
</template>
<script>
import config from "../../config/config"

export default {
  name: "Register-sucess",
  data: () => ({
    e6: 1,
    user: null
  }),
  methods: {
    toDashboard() {
      this.$router.push({name: "home"});
    }
  },
  mounted() {
    if (this.$session.exists()) {
      this.user = this.$session.get(config.USER);
      console.log(this.user);
    }
  }
}
</script>

<style scoped>

</style>