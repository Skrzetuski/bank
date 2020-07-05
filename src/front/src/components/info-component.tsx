import React, {useState, useEffect} from "react";
import {UserDetails} from "../interfaces/user-details-interface";
import InfoService from "../services/info-service";

function InfoComponent(){
    
    const [userDetails, setUserDetails] = useState<UserDetails>();
    const [success, setSuccess] = useState(false);
    const [message, setMessage] = useState("");


    function fetchData(){
        InfoService.getUserDetails(localStorage.getItem("user") || "")
            .then(res => {
                if(res !== {}){
                    setSuccess(true);
                    setUserDetails(res);
                }
                    
            }, error => {
                setSuccess(false);
                setMessage("Błąd ładowania informacji")
            })
    }
         


    useEffect(()=>{
        fetchData();
    },[]);
    
    return(
    <div>   
        <h1>Informacje o użytkowniku</h1>
        <p>{userDetails?.name}</p>
        <p>{userDetails?.email}</p>
        <p>{userDetails?.balance + " zł"}</p>  
      
    </div>
    )
}

export default InfoComponent;