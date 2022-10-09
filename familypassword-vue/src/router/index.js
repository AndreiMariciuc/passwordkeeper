import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../components/dashboard/Home";
import Login from "../components/Login";
import RegisterCompletion from "@/components/register/Register-completion";
import RegisterSuccess from "@/components/register/Register-sucess";
import Group from "@/components/group/Group";
import UserDetails from "@/components/userdetails/UserDetails";

Vue.use(VueRouter)

const routes = [
    {
        path: "/",
        name: "home",
        component: Home
    },
    {
        path: "/login",
        name: "login",
        component: Login
    },
    {
        path: "/register",
        name: "register-completion",
        component: RegisterCompletion
    },
    {
        path: "/register/success",
        name: "register-success",
        component: RegisterSuccess
    },
    {
        path: "/secret-group",
        name: "secret-group",
        component: Group
    },
    {
        path: "/user-details",
        name: "user-details",
        component: UserDetails
    }
];

const router = new VueRouter({
    mode: "history",
    base: process.env.BASE_URL,
    routes
})

export default router;