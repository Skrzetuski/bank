import axios from 'axios'

const API_URL:String = "http://localhost:8080/";

class AuthService {

    async login(email:String, password:String) : Promise<any>{
        return axios.post(API_URL + "login", {
            email,
            password
        }).then(res => {
            if(res.status === 200){
                localStorage.setItem("user", JSON.stringify(res.headers["Authorization"]));
            }
            return res.status;
        })
    }

    logout() : void {
        localStorage.removeItem("user");
    }

    async register(username: String, email: String, password:String): Promise<any>{
        return axios.post(API_URL + "register",{
            username,
            email,
            password 
        }).then(res => {
            return res.status;
        })
    }

    getUser(){
        return JSON.parse(localStorage.getItem("user") || "{}");
    }

}

export default new AuthService();