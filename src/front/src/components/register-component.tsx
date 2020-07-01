import React, { useState, useEffect } from "react";

import AuthService from "../services/auth-service"
import { Button } from "react-bootstrap";


function RegisterComponent(){


    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [successful, setSuccessful] = useState(false);
    const [message, setMessage] = useState("");

    const handleRegister  = (e:React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        setSuccessful(false);
        setMessage("")
    }

    function onChangeUsername(e:React.FormEvent<HTMLInputElement>){
        setUsername(e.currentTarget.value);
    }

    function onChangeEmail(e:React.FormEvent<HTMLInputElement>){
        setEmail(e.currentTarget.value);
    }

    function onChangePassword(e:React.FormEvent<HTMLInputElement>){
        setPassword(e.currentTarget.value);
    }


    function registationState(){
        if(username.length > 0 && email.length > 0 && password.length > 0){
            AuthService.register(
                username,
                email,
                password
                ).then(res =>{
                    if(res === 201){
                        setMessage("Uzytkownik zarejestrowany");
                        setSuccessful(true);
                    }
                }, error => {
                    setMessage("Uzytkownik istnieje");
                    setSuccessful(false);
                })
        }
    }



    
    return(
        <div className="col-md-12">
            <div className="card card-container">
                <form onSubmit={handleRegister}>
                    {!successful && (
                        <div>
                            <div className="form-group">
                                <label htmlFor="username">Username</label>
                                <input type="text"
                                        className="form-cotrol"
                                        name="username"
                                        value={username}
                                        onChange={onChangeUsername}
                                        required/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="email">Email</label>
                                <input type="text"
                                        className="form-cotrol"
                                        name="email"
                                        value={email}
                                        onChange={onChangeEmail}
                                        required/>
                            </div>
                            <div className="form-group">
                                <label htmlFor="password">Hasło</label>
                                <input type="password"
                                        className="form-cotrol"
                                        name="password"
                                        value={password}
                                        onChange={onChangePassword}
                                        required/>
                            </div>
                            <Button type="submit" onClick={registationState}>Zarejestruj</Button>
                        </div>
                        

                        
                    )}

                    {message &&(
                        <div className="form-group">
                            <div className={successful ? "alert alert-success"
                                                        : "alert alert-danger"}
                                                        role="alert">
                                                            {message}

                            </div>
                        </div>
                    )}
                    
                </form>
            </div>
        </div>
    )
}

export default RegisterComponent;