import axios from 'axios'
import {TransferDataRes} from "../interfaces/transfer-data-interface";


const API_URL:string = "http://localhost:8080/transfer";


class TransferService {
    

     async sendMoney(token:string, amountMoney: string, sender: string, receiver: string):Promise<any>{
        return axios.post<TransferDataRes>(API_URL, {
            amountMoney,
            sender,
            receiver,
        }, {
            headers:{ "Authorization" : token}
        }).then(res => {
            if(res.status === 200){
                console.log(res.data.status);
                return res;        
            }
        });
    }




}


export default new TransferService();