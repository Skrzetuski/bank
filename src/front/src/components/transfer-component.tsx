import React, { useState } from "react";
import { Button, Alert, Form, FormControl, Card, FormGroup  } from "react-bootstrap";
import TransferService from "../services/transfer-service";

function TransferComponent(){

    
    const [amountMoney, setAmountMoney] = useState("");
    const [sender, setSender] = useState("");
    const [receiver, setReceiver] = useState("");
    const [message, setMessage] = useState("");
    const [loading, setLoading] = useState(false);
    const [messageSuccessful, setMessageSuccessful] = useState("");


    const handleTransfer  = (e:React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();
        setLoading(false);
        setMessage("")
    }

    function onChangeAmountMoney(e:React.ChangeEvent<HTMLInputElement>){
        setAmountMoney(e.currentTarget.value);
    }

    function onChangeSender(e:React.ChangeEvent<HTMLInputElement>){
        setSender(e.currentTarget.value);
    }

    function onChangeReceiver(e:React.ChangeEvent<HTMLInputElement>){
        setReceiver(e.currentTarget.value);
    }


    function transferState(){
        
        setLoading(true);
        if(sender.length > 0 && amountMoney.length > 0 && receiver.length > 0){
            TransferService.sendMoney(localStorage.getItem("user") || "",amountMoney,sender,receiver).then(res => {
                if(res?.status === 200){
                    setLoading(false);
                    setMessage("");
                    setMessageSuccessful("Zlecenie przelewu zostało przesłane!");
                }
            }, error => {
                setMessage("Sprawdź dane przelewu");
                setMessageSuccessful("");
                setLoading(false);
            })
            setLoading(false);
        }
    }


    return(
        <Card style={{width: '26rem'}} >
        <Card.Body>
            <Form onSubmit={handleTransfer}>
                <FormGroup controlId="formSender">
                <Form.Label>Numer konta</Form.Label>
                <FormControl required defaultValue={sender} 
                            onChange={onChangeSender} type="sender" 
                            placeholder="Wpisz swój numer konta"/>
                </FormGroup>
                <FormGroup controlId="formReceiver">
                <Form.Label>Numer konta odbiorcy</Form.Label>
                <FormControl required defaultValue={receiver} 
                            onChange={onChangeReceiver} type="receiver" 
                            placeholder="Wpisz numer konta odbiorcy"/>
                </FormGroup>
                <FormGroup controlId="formAmountMoney">
                <Form.Label>Kwota przelewu</Form.Label>
                <FormControl required defaultValue={amountMoney} 
                            onChange={onChangeAmountMoney} type="amountMoney" 
                            placeholder="Wpisz numer konta odbiorcy"/>
                </FormGroup>
                

                <FormGroup controlId="buttonTransfer">
                    <Button variant="outline-primary" type="submit" onClick={transferState} disabled={loading}>
                        {loading && (
                            <span className="spinner-border spinner-border-sm"></span>
                        )}
                        <span>Wyślij!</span>
                    </Button>{' '}
                </FormGroup>


                {message && (<div>
                    <FormGroup>
                       <Alert variant="danger">{message}</Alert>
                   </FormGroup>   
                </div>)}
                {messageSuccessful && (<div>
                    <FormGroup>
                       <Alert variant="success">{messageSuccessful}</Alert>
                   </FormGroup>   
                </div>)}
            </Form>
        </Card.Body>
    </Card>)
}

export default TransferComponent;