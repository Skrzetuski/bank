import axios from 'axios'

const API_URL:String = "http://localhost:8080/";

class AuthService {

    async login(email:String, password:String) : Promise<any>{
        return axios.post(API_URL + "login", {
            email,
            password
        }).then(res => {
            if(res.status === 200){
                console.log(res.headers)
                localStorage.setItem("user", res.headers["authorization"]);
            }
            return res.status;
        })
    }

    logout() : void {
        localStorage.removeItem("user");
    }

    async register(name: String, email: String, password:String): Promise<any>{
        return axios.post(API_URL + "register",{
            name,
            email,
            password 
        }).then(res => {
            return res.status;
        })
    }

    getUser(){
        return localStorage.getItem("user");
    }

}

export default new AuthService();