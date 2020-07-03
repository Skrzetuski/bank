import React, {useState} from "react";
import { Button, Alert, Form, FormControl, Card, FormGroup  } from "react-bootstrap";
import { useHistory } from "react-router-dom";

import AuthService from "../services/auth-service"


function Login(){

    let history = useHistory();

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState("");

    const handleLogin  = (e:React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        setLoading(false);
        setMessage("")
    }

    function onChangeEmail(e:React.ChangeEvent<HTMLInputElement>){
        setEmail(e.currentTarget.value);
    }

    function onChangePassword(e:React.ChangeEvent<HTMLInputElement>){
        setPassword(e.currentTarget.value);
    }


    function loginState(){
        setLoading(true);
        if(email.length > 0 && password.length > 0){
            AuthService.login(
                email,
                password
                ).then(res =>{
                    if(res === 200){
                        setLoading(false);
                        history.push("/dashboard")
                        window.location.reload();
                    }
                }, error => {
                    setMessage("Błędne dane logowania");
                    setLoading(false);
                })
        }
        setLoading(false);
    }


    return(
        <Card style={{width: '26rem'}} >
        <Card.Body>
            <Form onSubmit={handleLogin}>
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
                            placeholder="Wpisz swoje hasło"/>
                </FormGroup>

                <FormGroup controlId="buttonLogin">
                    <Button variant="outline-primary" type="submit" onClick={loginState} disabled={loading}>
                        {loading && (
                            <span className="spinner-border spinner-border-sm"></span>
                        )}
                        <span>Zaloguj</span>
                    </Button>{' '}
                </FormGroup>


                {message && (<div>
                    <FormGroup>
                       <Alert variant="danger">{message}</Alert>
                   </FormGroup>   
                </div>)}
            </Form>
        </Card.Body>
    </Card>
    )
}

export default Login;