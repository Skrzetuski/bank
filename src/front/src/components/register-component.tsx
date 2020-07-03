import React, { useState } from "react";
import { Button, Alert, Form, FormControl, Card, FormGroup  } from "react-bootstrap";

import AuthService from "../services/auth-service"


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

    function onChangeUsername(e:React.ChangeEvent<HTMLInputElement>){
        setUsername(e.currentTarget.value);
    }

    function onChangeEmail(e:React.ChangeEvent<HTMLInputElement>){
        setEmail(e.currentTarget.value);
    }

    function onChangePassword(e:React.ChangeEvent<HTMLInputElement>){
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
                        setMessage("Użytkownik zarejestrowany");
                        setSuccessful(true);
                    }
                }, error => {
                    setMessage("Użytkownik istnieje");
                    setSuccessful(false);
                })
        }
    }

    
    return(
        //<div className="d-flex justify-content-center">
            <Card style={{width: '26rem'}} >
                <Card.Body>
                    <Form onSubmit={handleRegister}>
                        {!successful && (<div>
                        <FormGroup controlId="formUsername">
                        <Form.Label>Username</Form.Label>
                        <FormControl required defaultValue={username} 
                                    onChange={onChangeUsername} type="text" 
                                    placeholder="Wpisz swój nick"/>
                        
                        </FormGroup>
                        <FormGroup controlId="formEmail">
                        <Form.Label>Email</Form.Label>
                        <FormControl required defaultValue={email} 
                                    onChange={onChangeEmail} type="email" 
                                    placeholder="Wpisz swój adres email"/>
                        </FormGroup>
                        <FormGroup controlId="formPassword">
                        <Form.Label>Hasło</Form.Label>
                        <FormControl required defaultValue={password} 
                                    onChange={onChangePassword} type="password" 
                                    placeholder="Wpisz silne hasło"/>
                        </FormGroup>
                        <Button variant="outline-primary" type="submit" onClick={registationState}>Zarejestruj</Button>{' '}
                        </div>
                        )}

                        {message && (<div>
                            <FormGroup>
                               <Alert variant={successful? "success" : "danger"}>{message}</Alert>
                           </FormGroup>
                           </div>
                        )}
                    </Form>
                </Card.Body>
            </Card>
        //</div>
    )
}

export default RegisterComponent;