<template>
  <v-main class="grey lighten-3">
    <Logged/>
    <v-container>
      <v-row>
        <v-col cols="4">
          <v-sheet rounded="lg" min-height="70vh">
            <v-container>
              <v-row>
                <v-col cols="10">
                  <v-card-title>
                    <v-dialog
                        v-model="dialog"
                        persistent
                        max-width="290"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-btn
                            tile
                            color="dark"
                            v-bind="attrs"
                            v-on="on"
                        >
                          <v-icon left>
                            mdi-content-save-settings
                          </v-icon>
                          New personal folder
                        </v-btn>
                      </template>
                      <v-card>
                        <v-card-title class="text-h5">
                          Adauga un nou folder
                        </v-card-title>
                        <v-card-text>
                          <v-text-field
                              v-model="createdFolderName"
                              label="Folder name"
                              required
                          ></v-text-field>
                        </v-card-text>
                        <v-card-actions>
                          <v-spacer></v-spacer>
                          <v-btn
                              color="green darken-1"
                              text
                              @click="dialog = false"
                          >
                            Anuleaza
                          </v-btn>
                          <v-btn
                              color="green darken-1"
                              text
                              @click="createFolder"
                          >
                            Adauga
                          </v-btn>
                        </v-card-actions>
                      </v-card>
                    </v-dialog>
                  </v-card-title>
                </v-col>
              </v-row>
              <v-divider></v-divider>
              <v-row>
                <v-col>
                  <v-treeview
                      v-model="tree"
                      :open="initiallyOpen"
                      :items="secrets"
                      activatable
                      item-key="id"
                      open-on-click
                  >
                    <template v-slot:prepend="{ item, open }">
                      <v-icon v-if="!item.password" @click="getValue(item.id)">
                        {{ open ? 'mdi-folder-open' : 'mdi-folder' }}
                      </v-icon>
                      <v-icon v-else @click="getValue(item.id)">
                        {{ 'mdi-key-variant' }}
                      </v-icon>
                    </template>
                    <template v-slot:label="{ item }">
                      <label class="text--darken-1" @click="getValue(item.id)">{{ item.name }}</label>
                    </template>
                  </v-treeview>
                </v-col>
              </v-row>
            </v-container>
          </v-sheet>
        </v-col>
        <v-col>
          <v-sheet
              min-height="70vh"
              rounded="lg"

          >
            <v-container>
              <v-row>
                <v-col cols="10">
                  <v-card-title>
                    <v-dialog
                        v-model="dialog1"
                        persistent
                        max-width="400"
                        v-if="!selectedSecret.password"
                    >
                      <template v-slot:activator="{ on, attrs }">
                        <v-btn
                            color="dark"
                            v-bind="attrs"
                            v-on="on"
                        >
                          <v-icon v-if="!selectedSecret.password">
                            {{ 'mdi-folder' }}
                          </v-icon>
                          <v-icon v-else>
                            {{ 'mdi-key-variant' }}
                          </v-icon>
                          {{ selectedSecret.name }}
                        </v-btn>
                      </template>

                      <v-card>
                        <v-card-title class="text-h5">
                          Editeaza {{ selectedSecret.name }}
                        </v-card-title>
                        <v-card-text>
                          <v-text-field
                              v-model="createdFolderName"
                              label="Folder name"
                              required
                              v-if="!selectedSecret.password"
                          ></v-text-field>
                        </v-card-text>
                        <v-card-actions>
                          <v-spacer></v-spacer>
                          <v-btn
                              color="green darken-1"
                              text
                              @click="updateFolder"
                          >
                            Updateaza
                          </v-btn>
                          <v-btn
                              color="green darken-1"
                              text
                              @click="deleteFolder"
                          >
                            Delete
                          </v-btn>
                          <v-btn
                              color="green darken-1"
                              text
                              @click="dialog1 = false"
                          >
                            Cancel
                          </v-btn>
                        </v-card-actions>
                      </v-card>
                    </v-dialog>
                    <v-btn
                        color="dark"
                        v-else
                    >
                      <v-icon v-if="!selectedSecret.password">
                        {{ 'mdi-folder' }}
                      </v-icon>
                      <v-icon v-else>
                        {{ 'mdi-key-variant' }}
                      </v-icon>
                      {{ selectedSecret.name }}
                    </v-btn>
                  </v-card-title>
                </v-col>
              </v-row>
              <v-divider></v-divider>
              <v-row v-if="!selectedSecret.password">
                <v-col cols="6">
                  <v-list dense>
                    <v-list-item-group
                        v-model="listSelectedSecret"
                        color="primary"
                    >
                      <v-list-item
                          v-for="(item, i) in selectedSecret.children"
                          :key="i"
                          @click="listSelect(item)"
                      >
                        <v-list-item-icon>
                          <v-icon v-if="!item.password">
                            {{ 'mdi-folder' }}
                          </v-icon>
                          <v-icon v-else>
                            {{ 'mdi-key-variant' }}
                          </v-icon>
                        </v-list-item-icon>
                        <v-list-item-content>
                          <v-list-item-title v-text="item.name"></v-list-item-title>
                        </v-list-item-content>
                      </v-list-item>
                    </v-list-item-group>
                  </v-list>
                  <v-btn
                      tile
                      color="dark"
                      @click="changeLeftTabTo(1)"
                      v-if="!selectedSecret.password"
                  >
                    <v-icon left>
                      mdi-key
                    </v-icon>
                    Add Password
                  </v-btn>
                  <v-btn
                      tile
                      color="dark"
                      @click="changeLeftTabTo(2)"
                      v-if="!selectedSecret.password"
                  >
                    <v-icon left>
                      mdi-folder
                    </v-icon>
                    Add folder
                  </v-btn>
                </v-col>
                <v-col v-if="this.listModal === 0">

                </v-col>
                <v-col v-if="this.listModal === 1">
                  <v-text-field
                      v-model="passwordCreate.name"
                      label="name"
                      required
                  >
                  </v-text-field>
                  <v-text-field
                      v-model="passwordCreate.username"
                      label="username"
                      required
                  ></v-text-field>
                  <v-text-field
                      v-model="passwordCreate.password"
                      label="password"
                      required
                  ></v-text-field>
                  <v-text-field
                      v-model="passwordCreate.link"
                      label="link"
                      required
                  ></v-text-field>
                  <v-btn
                      tile
                      color="dark"
                      @click="addPassword"
                  >
                    <v-icon left>
                      mdi-plus-circle
                    </v-icon>
                    add
                  </v-btn>
                </v-col>
                <v-col v-if="this.listModal === 2">
                  <v-text-field
                      v-model="folderCreate.name"
                      label="name"
                      required
                  >
                  </v-text-field>
                  <v-btn
                      tile
                      color="dark"
                      @click="addFolder"
                  >
                    <v-icon left>
                      mdi-plus-circle
                    </v-icon>
                    add
                  </v-btn>
                </v-col>
                <v-col v-if="this.listModal === 3">
                </v-col>
              </v-row>
              <v-row v-else>
                <v-container style="margin-top: 3%; margin-left: 3%; margin-right: 3%">
                  <v-row>
                    <v-text-field
                        v-model="selectedPassword.username"
                        label="username"
                        outlined
                        @keydown.enter="updateSelectedPassword">
                      <template v-slot:append>
                        <v-icon @click="addToClipboard(selectedPassword.username)">
                          mdi-content-copy
                        </v-icon>
                      </template>
                    </v-text-field>
                  </v-row>
                  <v-row>
                    <v-text-field
                        v-model="selectedPassword.password"
                        label="password"
                        outlined
                        @keydown.enter="updateSelectedPassword">
                      <template v-slot:append>
                        <v-icon @click="addToClipboard(selectedPassword.password)">
                          mdi-content-copy
                        </v-icon>
                      </template>
                    </v-text-field>
                  </v-row>
                  <v-row>
                    <v-text-field
                        v-model="selectedPassword.link"
                        label="link"
                        outlined
                        @keydown.enter="updateSelectedPassword">
                      <template v-slot:append>
                        <v-icon @click="addToClipboard(selectedPassword.link)">
                          mdi-content-copy
                        </v-icon>
                      </template>
                    </v-text-field>
                  </v-row>
                </v-container>
              </v-row>
            </v-container>
          </v-sheet>
        </v-col>
      </v-row>
    </v-container>
  </v-main>
</template>
<script>
import axios from "axios";
import config from "../../config/config"
import Logged from "@/components/isLoggedIn/Logged";

export default {
  name: 'HelloWorld', components: {
    Logged
  },
  data: () => ({
    initiallyOpen: [],
    tree: [],
    secrets: [],
    dialog: false,
    dialog1: false,
    createdFolderName: null,
    user: null,
    selectedSecret: {},
    listSelectedSecret: null,
    listModal: 0, //0-gol, 1-password la folderul curent resetat, 2-folder, 3
    passwordCreate: {
      name: null,
      username: null,
      password: null,
      link: null
    },
    folderCreate: {
      name: null
    },
    selectedPassword: {
      name: null,
      username: null,
      password: null,
      link: null
    }
  }),
  methods: {
    searchForSecrete(secretId, secrets) {
      if (secrets == null || secrets.length === 0) return null;

      for (let i = 0; i < secrets.length; i++) {
        if (secrets[i].id === secretId) return secrets[i];
        let rez = this.searchForSecrete(secretId, secrets[i].children);
        if (rez != null) return rez;
      }

      return null;
    },
    getValue(selected) {
      this.selectedSecret = this.searchForSecrete(selected, this.secrets);
      if (this.selectedSecret.password) {
        this.selectedPassword = this.selectedSecret;
        console.log('am setat parola secreta');
      }
      this.listModal = 3;
      console.log(this.selectedPassword);
    },
    getSecrets() {
      this.user = this.$session.get(config.USER);

      axios.get(config.SPRING_BOOT_PORT + '/user/' + this.user.id + '/get-secrets')
          .then(response => {
            console.log(response.data)
            this.secrets = response.data;
          });
    },
    createFolder() {
      console.log(this.createdFolderName);

      axios.post(config.SPRING_BOOT_PORT + "/user/" + this.user.id + "/add-secret-folder", {
        name: this.createdFolderName
      }).then(response => {
        console.log(response.data);
        this.dialog = false;
        this.getSecrets();
      });
    },
    updateFolder() {
      console.log(this.createdFolderName);
      console.log(this.selectedSecret.id);
      axios.put(config.SPRING_BOOT_PORT + "/user/" + this.user.id + "/update-folder", {
        id: this.selectedSecret.id,
        name: this.createdFolderName
      }).then(response => {
        console.log(response.data);
        this.dialog1 = false;
        this.getSecrets();
        this.selectedSecret = response.data;
      });
    },
    deleteFolder() {
      console.log(this.selectedSecret.id);
      axios.delete(config.SPRING_BOOT_PORT + "/user/" + this.user.id + "/delete-secret/" + this.selectedSecret.id)
          .then(response => {
            console.log(response.data);
            this.dialog1 = false;
            this.getSecrets();
            this.selectedSecret = {};
          });
    }, changeLeftTabTo(id) {
      this.listModal = id;
    },
    addPassword() {
      console.log(this.passwordCreate)
      if (!this.selectedSecret.id) return;
      this.user = this.$session.get(config.USER);

      axios.post(config.SPRING_BOOT_PORT + '/user/' + this.user.id + '/add-secret-password/' + this.selectedSecret.id, this.passwordCreate)
          .then(response => {
            console.log(response.data)
            this.getSecrets();
            this.$forceUpdate();
          });
    },
    addFolder() {
      this.user = this.$session.get(config.USER);
      axios.post(config.SPRING_BOOT_PORT + '/user/' + this.user.id + '/add-secret-folder/' + this.selectedSecret.id, this.folderCreate)
          .then(response => {
            console.log(response.data)
            this.getSecrets();
            this.$forceUpdate();
          });
    },
    listSelect(item) {
      console.log(item);
      this.selectedSecret = item;
    },
    addToClipboard(txt) {
      navigator.clipboard.writeText(txt);
    },
    updateSelectedPassword() {
      this.user = this.$session.get(config.USER);
      axios.put(config.SPRING_BOOT_PORT + "/user/" + this.user.id + "/update-password", {
        id: this.selectedPassword.id,
        name: this.selectedPassword.name,
        username: this.selectedPassword.username,
        password: this.selectedPassword.password,
        link: this.selectedPassword.link
      }).then(response => {
        console.log(response);
        this.getSecrets();
      });
    }
  },
  mounted() {
    this.getSecrets();
  }
}
</script>

