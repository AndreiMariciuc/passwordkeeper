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
                        v-model="createGroupDialog"
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
                          Create group
                        </v-btn>
                      </template>
                      <v-card>
                        <v-card-title class="text-h5">
                          Creaza un nou grup
                        </v-card-title>
                        <v-card-text>
                          <v-text-field
                              v-model="createdGroupName"
                              label="Group name"
                              required
                          ></v-text-field>
                        </v-card-text>
                        <v-card-actions>
                          <v-spacer></v-spacer>
                          <v-btn
                              color="green darken-1"
                              text
                              @click="createGroupDialog = false"
                          >
                            Anuleaza
                          </v-btn>
                          <v-btn
                              color="green darken-1"
                              text
                              @click="createGroup"
                          >
                            Creaza
                          </v-btn>
                        </v-card-actions>
                      </v-card>
                    </v-dialog>
                    <v-dialog
                        v-model="joinGroupDialog"
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
                          <v-icon left>mdi-account-multiple-plus-outline</v-icon>
                          Join a group
                        </v-btn>
                      </template>
                      <v-card>
                        <v-card-title class="text-h5">
                          Intra intr-un grup nou
                        </v-card-title>
                        <v-card-text>
                          <v-text-field
                              v-model="joinGroupToken"
                              label="token"
                              required
                          ></v-text-field>
                        </v-card-text>
                        <v-card-actions>
                          <v-spacer></v-spacer>
                          <v-btn
                              color="green darken-1"
                              text
                              @click="joinGroupDialog = false"
                          >
                            Anuleaza
                          </v-btn>
                          <v-btn
                              color="green darken-1"
                              text
                              @click="joinGroup"
                          >
                            Join
                          </v-btn>
                        </v-card-actions>
                      </v-card>
                    </v-dialog>
                  </v-card-title>
                  <v-snackbar v-model="requestSnackBar.flag">
                    {{ requestSnackBar.txt }}
                    <template v-slot:action="{ attrs }">
                      <v-btn
                          color="pink"
                          text
                          v-bind="attrs"
                          @click="requestSnackBar.flag = false"
                      >
                        Inchide
                      </v-btn>
                    </template>
                  </v-snackbar>
                  <v-snackbar v-model="wantsToJoinSnackBar.flag">
                    {{ wantsToJoinSnackBar.txt }}
                    <template v-slot:action="{ attrs }">
                      <v-btn
                          color="pink"
                          text
                          v-bind="attrs"
                          @click="wantsToJoinSnackBar.flag = false"
                      >
                        Inchide
                      </v-btn>
                    </template>
                  </v-snackbar>
                </v-col>
              </v-row>
              <v-divider></v-divider>
              <v-row>
                <v-col>
                  <v-list dense>
                    <v-list-item-group>
                      <v-list-item
                          v-for="(group, i) in groups"
                          :key="i"
                      >
                        <v-list-item-icon>
                          <v-icon>mdi-account-group</v-icon>
                        </v-list-item-icon>
                        <v-list-item-content>
                          <v-list-item-title v-text="group.name"></v-list-item-title>
                        </v-list-item-content>
                        <v-list-item-icon @click="listGroup(group, 1)">
                          <v-icon>mdi-account-box</v-icon>
                        </v-list-item-icon>
                        <v-list-item-icon @click="listGroup(group, 2)">
                          <v-icon>mdi-folder-key</v-icon>
                        </v-list-item-icon>
                      </v-list-item>
                    </v-list-item-group>
                  </v-list>
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
            <v-container v-if="!selectedGroup">
              <v-card-title>
                <v-card-text>Selecteaza un grup</v-card-text>
              </v-card-title>
            </v-container>
            <v-container v-else>
              <v-row>
                <v-col>
                  <v-card>
                    <v-card-title v-text="selectedGroup.name"/>
                    <v-card-subtitle v-text="selectedGroup.token"/>
                  </v-card>
                </v-col>
              </v-row>
              <v-divider></v-divider>
              <v-row>
                <v-col v-if="selectedGroupCode === 1">
                  <v-card>
                    <v-card-title>Members</v-card-title>
                    <v-list dense>
                      <v-list-item-group>
                        <v-list-item
                            v-for="(member, i) in selectedGroup.members"
                            :key="i"
                        >
                          <v-list-item-icon>
                            <v-avatar
                                class="mr-10"
                                color="grey darken-1"
                                size="32"

                            >
                              <img
                                  :src="getPathForMember(member)"
                                  alt="John"
                              >
                            </v-avatar>
                          </v-list-item-icon>
                          <v-list-item-content>
                            <v-list-item-title v-text="member.name"></v-list-item-title>
                            <v-list-item-subtitle v-text="member.officialName"></v-list-item-subtitle>
                            <v-list-item-subtitle v-if="selectedGroup.admin.id === member.id">
                              role: ADMIN
                            </v-list-item-subtitle>
                            <v-list-item-subtitle v-if="selectedGroup.admin.id !== member.id">
                              role: MEMBER
                            </v-list-item-subtitle>
                          </v-list-item-content>
                          <v-list-item-icon v-if="selectedGroup.admin.id === member.id">
                            <v-icon>mdi-hard-hat</v-icon>
                          </v-list-item-icon>
                          <v-list-item-icon v-if="member.id === user.id">
                            <v-icon>mdi-omega</v-icon>
                          </v-list-item-icon>
                          <v-list-item-icon v-if="selectedGroup.admin.id === user.id && member.id !== user.id"
                                            @click="deleteMember(member)">
                            <v-icon>mdi-delete</v-icon>
                          </v-list-item-icon>
                        </v-list-item>
                      </v-list-item-group>
                    </v-list>
                    <v-card-title v-if="selectedGroup.requests && selectedGroup.requests.length !== 0">Requests
                    </v-card-title>
                    <v-list dense v-if="selectedGroup.requests">
                      <v-list-item-group>
                        <v-list-item
                            v-for="(member, i) in selectedGroup.requests"
                            :key="i"
                        >
                          <v-list-item-icon>
                            <v-icon>mdi-account</v-icon>
                          </v-list-item-icon>
                          <v-list-item-content>
                            <v-list-item-title v-text="member.name"></v-list-item-title>
                            <v-list-item-subtitle v-text="member.officialName"></v-list-item-subtitle>
                          </v-list-item-content>
                          <v-list-item-icon @click="sendRequestConfirmation(member, true)">
                            <v-icon>mdi-bookmark-check</v-icon>
                          </v-list-item-icon>
                          <v-list-item-icon @click="sendRequestConfirmation(member, false)">
                            <v-icon>mdi-close-box</v-icon>
                          </v-list-item-icon>
                        </v-list-item>
                      </v-list-item-group>
                    </v-list>
                  </v-card>
                </v-col>
                <v-col v-if="selectedGroupCode === 2">
                  <v-card>
                    <v-breadcrumbs :items="breadCrumbsItems">
                      <template v-slot:item="{ item }">
                        <v-breadcrumbs-item
                            @click="moveTo(item.id)"
                        >
                          <div class="bread-crumbs"> {{ item.name }}</div>
                        </v-breadcrumbs-item>
                      </template>
                    </v-breadcrumbs>
                    <v-card-title v-if="selectedSecret === null || !selectedSecret.password">
                      <v-dialog
                          v-model="addFolderObj.dialog"
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
                            New group folder
                          </v-btn>
                        </template>
                        <v-card>
                          <v-card-title class="text-h5">
                            Adauga un nou folder
                          </v-card-title>
                          <v-card-text>
                            <v-text-field
                                v-model="addFolderObj.folder"
                                label="Folder name"
                                required
                            ></v-text-field>
                          </v-card-text>
                          <v-card-actions>
                            <v-spacer></v-spacer>
                            <v-btn
                                color="green darken-1"
                                text
                                @click="addFolderObj.dialog = false"
                            >
                              Anuleaza
                            </v-btn>
                            <v-btn
                                color="green darken-1"
                                text
                                @click="addFolder"
                            >
                              Adauga
                            </v-btn>
                          </v-card-actions>
                        </v-card>
                      </v-dialog>
                      <!--                      <v-card-actions @click="addPassword">-->
                      <!--                        <v-icon>mdi-folder</v-icon>-->
                      <!--                      </v-card-actions>-->
                    </v-card-title>
                    <v-list dense>
                      <v-list-item-group
                          v-model="currentSecretList"
                          color="primary"
                      >
                        <v-list-item
                            v-for="(secret, i) in currentSecretList"
                            :key="i"
                            @click="selectSecret(secret)"
                        >
                          <v-list-item-icon>
                            <v-icon v-if="!secret.password">
                              {{ 'mdi-folder' }}
                            </v-icon>
                            <v-icon v-else>
                              {{ 'mdi-key-variant' }}
                            </v-icon>
                          </v-list-item-icon>
                          <v-list-item-content>
                            <v-list-item-title v-text="secret.name"></v-list-item-title>
                          </v-list-item-content>
                        </v-list-item>
                      </v-list-item-group>
                    </v-list>
                  </v-card>
                </v-col>
              </v-row>
            </v-container>
          </v-sheet>
        </v-col>
      </v-row>
    </v-container>
  </v-main>
</template>

<script>
import config from "@/config/config";
import axios from "axios";
import Logged from "@/components/isLoggedIn/Logged";
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';

export default {
  name: "group-s", components: {
    Logged
  },
  data: () => ({
    user: null,
    createGroupDialog: false,
    joinGroupDialog: false,
    joinGroupToken: null,
    createdGroupName: null,
    groups: [],
    selectedGroup: null,
    selectedGroupCode: 0,
    requestSnackBar: {
      txt: null,
      flag: false
    },
    wantsToJoinSnackBar: {
      txt: null,
      flag: false
    }, breadCrumbsItems: [],
    selectedSecret: null,
    currentSecretList: [],
    addFolderObj: {
      dialog: false,
      folder: null
    }
  }),
  mounted() {
    this.user = this.$session.get(config.USER);
    this.getGroups();

    var scoket = new SockJS(config.SPRING_BOOT_PORT + "/gs-websockets");
    var stompClient = Stomp.over(scoket);

    stompClient.connect({}, () => {
      stompClient.subscribe("/websockets", message => {
        const body = JSON.parse(message.body);
        if (this.tokenInGroups(body.second, body.first)) {
          this.wantsToJoinSnackBar.txt = "User " + body.first.name + " wants to enter in group: " + body.second;
          this.wantsToJoinSnackBar.flag = true;
        }
      });
    });
  },
  methods: {
    tokenInGroups(token, user) {
      for (let i = 0; i < this.groups.length; i++) {
        console.log(this.groups[i].token + " vs " + token);
        if (this.groups[i].token === token) {
          this.groups[i].requests.push(user);
          return true;
        }
      }

      return false;
    },
    createGroup() {
      axios.post(config.SPRING_BOOT_PORT + '/user/' + this.user?.id + '/create-group?groupName='
          + this.createdGroupName).then(response => {
        console.log('Group created:\n');
        console.log(response.data);
        this.createGroupDialog = false;
        this.groups.push(response.data);
      });
    },
    getGroups() {
      axios.get(config.SPRING_BOOT_PORT + '/user/' + this.user?.id + '/groups?name=' + this.user?.name)
          .then(response => {
            console.log("Groups:\n");
            console.log(response.data);
            this.groups = response.data;
          });
    },
    listGroup(group, code) {
      console.log(group, code);
      this.selectedGroupCode = code;
      this.selectedGroup = group;
      this.currentSecretList = group.secrets;
      this.breadCrumbsItems = [];
      this.breadCrumbsItems.push({name: this.selectedGroup.name, id: this.selectedGroup.token});
    },
    joinGroup() {
      const token = this.joinGroupToken;
      const id = this.user.id;
      console.log(token);
      console.log(id);
      axios.post(config.SPRING_BOOT_PORT + "/group/" + token + "/" + id)
          .then(response => {
            console.log(response.status);
            this.requestSnackBar.txt = "Am adaugat request-ul tau! Asteapta ca adminul sa-ti dea accept!";
            this.requestSnackBar.flag = true;
            this.joinGroupDialog = false;
          });
    },
    sendRequestConfirmation(member, flag) {
      axios.put(config.SPRING_BOOT_PORT + "/group/" + this.selectedGroup.token + "/" + member.id + "/" + flag)
          .then(() => {
            this.selectedGroup.requests.pop(member);
            if (flag === true) {
              this.selectedGroup.members.push(member);
            }
          });
    },
    deleteMember(member) {
      axios.delete(config.SPRING_BOOT_PORT + "/group/" + this.selectedGroup.token + "/" + member.id).then(
          response => {
            console.log(response.status);
            this.selectedGroup.members.splice(member, 1);
          });
    },
    searchForSecrete(secretId, secrets) {
      if (secrets == null || secrets.length === 0) return null;

      for (let i = 0; i < secrets.length; i++) {
        if (secrets[i].id === secretId) return secrets[i];
        let rez = this.searchForSecrete(secretId, secrets[i].children);
        if (rez != null) return rez;
      }

      return null;
    },
    moveTo(token) {
      if (token === this.selectedGroup.token) {
        this.selectedSecret = null;
        this.currentSecretList = this.selectedGroup.secrets;
        this.breadCrumbsItems.splice(1, this.breadCrumbsItems.length - 1);
      } else {
        this.selectedSecret = this.searchForSecrete(token, this.selectedGroup.secrets);
        this.currentSecretList = this.selectedSecret?.children;
        const index = this.breadCrumbsItems.findIndex(object => {
          return object.id === token;
        });
        this.breadCrumbsItems.splice(index + 1, this.breadCrumbsItems.length - index - 1);
      }
    },
    selectSecret(secret) {
      this.breadCrumbsItems.push(secret);
      this.moveTo(secret.id);
    },
    addFolder() {
      console.log(this.addFolderObj.folder);
      this.addFolderObj.dialog = false;
      let url = config.SPRING_BOOT_PORT + "/group/" + this.selectedGroup.token + "/add-secret-folder";

      if (this.selectedSecret !== null) {
        url += '/' + this.selectedSecret.id;
      }

      axios.post(url, {name: this.addFolderObj.folder})
          .then(response => {
            console.log(response.data);
            this.selectedGroup.secrets.push(response.data);
            this.secrets = this.selectedGroup.secrets;
          });
    },
    getPathForMember(member) {
      return config.SPRING_BOOT_PORT + "/" + member.imageUrl;
    }
  }
}
</script>

<style>
.bread-crumbs {
  cursor: pointer;
}
</style>