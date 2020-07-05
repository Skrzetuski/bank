import axios from 'axios'
import {UserDetails} from "../interfaces/user-details-interface";


const API_URL:string = "http://localhost:8080/info";

class InfoService{


     async getUserDetails<UserDetails=undefined>(token:string):Promise<UserDetails>{
        return axios.get(API_URL, {
            headers:{ "Authorization" : token}
        }).then(res => {
            if(res.status === 200){
                return res.data;
                    
            } else if (res.status === 204){
                return {};
            }
            
        });
    }




}


export default new InfoService();